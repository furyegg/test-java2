package com.lombardrisk.ocelot.efc.component.pane;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HScalePane extends AnchorPane {

	private static final Duration TRANS_DURATION = Duration.millis(1000);

	private static final int LEFT_DEACT_WIDTH = 300;
	private static final int RIGHT_DEACT_WIDTH = 200;
	private static final int SPACING_WIDTH = 5;

	private Pane left;
	private Pane right;

	private boolean isLeftActive = true;
	private boolean isInit;

	public HScalePane() {
		left = new VBox();
		left.getStyleClass().add("boxBorder1");
		right = new VBox();
		right.getStyleClass().add("boxBorder2");
		isInit = true;
	}

	@Override
	protected void layoutChildren() {
		if (getChildren().size() != 2) {
			throw new RuntimeException("Horizontal slide pane can only and must contains two children.");
		}

		if (isInit) {
			isInit = false;
			Node leftNode = getChildren().get(0);
			Node rightNode = getChildren().get(1);

			left.getChildren().add(leftNode);
			right.getChildren().add(rightNode);

			getChildren().clear();
			getChildren().add(left);
			getChildren().add(right);

			setLeftAnchor(left, 0.0);
			setRightAnchor(right, 0.0);

			if (isLeftActive) {
				left.setPrefWidth(getLeftActiveWidth());
				right.setPrefWidth(RIGHT_DEACT_WIDTH);
			} else {
				left.setPrefWidth(LEFT_DEACT_WIDTH);
				right.setPrefWidth(getRightActiveWidth());
			}
		}

		super.layoutChildren();
	}

	public void activeLeft() {
		isLeftActive = true;
		activePaneChange(left, right);
	}

	public void activeRight() {
		isLeftActive = false;
		activePaneChange(right, left);
	}

	private double getLeftActiveWidth() {
		return getWidth() - RIGHT_DEACT_WIDTH - SPACING_WIDTH;
	}

	private double getRightActiveWidth() {
		return getWidth() - LEFT_DEACT_WIDTH - SPACING_WIDTH;
	}

	private void activePaneChange(Pane activePane, Pane deactivePane) {
		double activeTargetWidth = deactivePane.getWidth();
		double deactiveTargetWidth = activePane.getWidth();

		ParallelTransition pt = new ParallelTransition();
		Animation leftAnm = getAnimation(activePane, activeTargetWidth);
		Animation rightAnm = getAnimation(deactivePane, deactiveTargetWidth);
		pt.getChildren().addAll(leftAnm, rightAnm);
		pt.play();
	}

	private Animation getAnimation(Pane pane, double targetWidth) {
		Timeline timeline = new Timeline();
		final KeyValue kv = new KeyValue(pane.prefWidthProperty(), targetWidth);
		final KeyFrame kf = new KeyFrame(TRANS_DURATION, kv);
		timeline.getKeyFrames().addAll(kf);
		return timeline;
	}

}