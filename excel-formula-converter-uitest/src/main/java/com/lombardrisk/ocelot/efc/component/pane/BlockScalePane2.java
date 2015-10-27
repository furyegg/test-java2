package com.lombardrisk.ocelot.efc.component.pane;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class BlockScalePane2 extends AnchorPane {

	private static final Duration TRANS_DURATION = Duration.millis(2000);

	private static final int DEACT_SIZE = 128;
	private static final int SPACING_WIDTH = 5;

	private Pane first;
	private Pane second;
	private Pane third;

	private boolean isInit;

	public BlockScalePane2() {
		first = new VBox();
		first.getStyleClass().add("boxBorder1");
		second = new VBox();
		second.getStyleClass().add("boxBorder2");
		third = new VBox();
		third.getStyleClass().add("boxBorder3");
		isInit = true;
	}

	@Override
	protected void layoutChildren() {
		if (getChildren().size() != 3) {
			throw new RuntimeException("Block slide pane can only and must contains three children.");
		}

		if (isInit) {
			isInit = false;
			Node firstNode = getChildren().get(0);
			Node secondNode = getChildren().get(1);
			Node thirdNode = getChildren().get(2);

			first.getChildren().add(firstNode);
			second.getChildren().add(secondNode);
			third.getChildren().add(thirdNode);

			getChildren().clear();
			getChildren().add(first);
			getChildren().add(second);
			getChildren().add(third);

			setTopAnchor(firstNode, 0.0);
			setLeftAnchor(second, 0.0);
			setBottomAnchor(second, 0.0);
			setRightAnchor(third, 0.0);
			setBottomAnchor(third, 0.0);

			double[][] size = getFirstActiveSize();
			first.setPrefWidth(size[0][0]);
			first.setPrefHeight(size[0][1]);
			second.setPrefWidth(size[1][0]);
			second.setPrefHeight(size[1][1]);
			third.setPrefWidth(size[2][0]);
			third.setPrefHeight(size[2][1]);
		}

		super.layoutChildren();
	}

	public void activeFirst() {
		playBlocksAnimation(getFirstActiveSize());
	}

	public void activeSecond() {
		setLeftAnchor(first, null);
		setRightAnchor(first, 0.0);
		playBlocksAnimation(getSecondActiveSize());
	}

	public void activeThird() {
		setLeftAnchor(first, 0.0);
		setRightAnchor(first, null);
		playBlocksAnimation(getThirdActiveSize());
	}

	private void playBlocksAnimation(double[][] size) {
		getAnimation(first, size[0][0], size[0][1]).play();
		getAnimation(second, size[1][0], size[1][1]).play();
		getAnimation(third, size[2][0], size[2][1]).play();
	}

	private double[][] getFirstActiveSize() {
		double outerWidth = getWidth();
		double outerHeight = getHeight();

		double firstWidth = outerWidth - DEACT_SIZE - SPACING_WIDTH;
		double firstHeight = outerHeight - DEACT_SIZE - SPACING_WIDTH;

		double secondWidth = firstWidth;
		double secondHeight = DEACT_SIZE;

		double thirdWidth = DEACT_SIZE;
		double thirdHeight = outerHeight;

		double[][] size = {{firstWidth, firstHeight}, {secondWidth, secondHeight}, {thirdWidth, thirdHeight}};
		return size;
	}

	private double[][] getSecondActiveSize() {
		double outerWidth = getWidth();
		double outerHeight = getHeight();

		double firstWidth = outerWidth;
		double firstHeight = outerWidth - DEACT_SIZE - SPACING_WIDTH;

		double secondWidth = outerWidth - DEACT_SIZE - SPACING_WIDTH;
		double secondHeight = outerHeight;

		double thirdWidth = firstWidth;
		double thirdHeight = firstHeight;

		double[][] size = {{firstWidth, firstHeight}, {secondWidth, secondHeight}, {thirdWidth, thirdHeight}};
		return size;
	}

	private double[][] getThirdActiveSize() {
		double outerWidth = getWidth();
		double outerHeight = getHeight();

		double firstWidth = DEACT_SIZE;
		double firstHeight = (outerHeight - SPACING_WIDTH) / 2;

		double secondWidth = firstWidth;
		double secondHeight = firstHeight;

		double thirdWidth = outerWidth - DEACT_SIZE - SPACING_WIDTH;
		double thirdHeight = outerHeight;

		double[][] size = {{firstWidth, firstHeight}, {secondWidth, secondHeight}, {thirdWidth, thirdHeight}};
		return size;
	}

	private Animation getAnimation(Pane pane, double targetWidth, double targetHeight) {
		Timeline timeline = new Timeline();
		final KeyValue kvW = new KeyValue(pane.prefWidthProperty(), targetWidth);
		final KeyFrame kfW = new KeyFrame(TRANS_DURATION, kvW);
		final KeyValue kvH = new KeyValue(pane.prefHeightProperty(), targetHeight);
		final KeyFrame kfH = new KeyFrame(TRANS_DURATION, kvH);
		timeline.getKeyFrames().addAll(kfW, kfH);
		return timeline;
	}

}