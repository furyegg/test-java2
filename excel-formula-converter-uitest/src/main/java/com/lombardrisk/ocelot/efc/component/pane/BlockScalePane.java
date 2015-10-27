package com.lombardrisk.ocelot.efc.component.pane;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class BlockScalePane extends AnchorPane {

	private static final Duration TRANS_DURATION = Duration.millis(1000);

	private static final int DEACT_SIZE = 128;
	private static final int SPACING_WIDTH = 5;

	private Pane first;
	private Pane second;
	private Pane third;

	private boolean isFirstActive = true;
	private boolean isSecondActive = false;
	private boolean isThirdActive = false;
	private boolean isInit;

	public BlockScalePane() {
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

			setTopAnchor(first, 0.0);
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
		if (isSecondActive) {
			setLeftAnchor(first, null);
			setRightAnchor(first, 0.0);
		} else {
			setLeftAnchor(first, 0.0);
			setRightAnchor(first, null);
		}

		double[][] size = getFirstActiveSize();
		Animation a1 = createScaleAnimation(second, size[1][0], size[1][1]);
		Animation a2 = createScaleAnimation(third, size[2][0], size[2][1]);
		Animation a3 = createScaleAnimation(first, size[0][0], size[0][1]);
		createCombAnimation(new Animation[]{a1, a2}, a3).play();

		isFirstActive = true;
		isSecondActive = false;
		isThirdActive = false;
	}

	public void activeSecond() {
		Animation a1;
		Animation a2;
		Animation a3;
		double[][] size = getSecondActiveSize();
		if (isFirstActive) {
			setLeftAnchor(first, null);
			setRightAnchor(first, 0.0);
			a1 = createScaleAnimation(first, size[0][0], size[0][1]);
			a2 = createScaleAnimation(third, size[2][0], size[2][1]);
			a3 = createScaleAnimation(second, size[1][0], size[1][1]);
			createCombAnimation(new Animation[]{a1, a2}, a3).play();
		} else {
			a1 = createScaleAnimation(third, size[2][0], size[2][1]);
			a2 = createMoveAnimation(first, getWidth() - DEACT_SIZE);
			a3 = createScaleAnimation(second, size[1][0], size[1][1]);
			createCombAnimation(a1, a2, a3).play();
		}

		isFirstActive = false;
		isSecondActive = true;
		isThirdActive = false;
	}

	public void activeThird() {
		Animation a1;
		Animation a2;
		Animation a3;
		double[][] size = getThirdActiveSize();
		if (isFirstActive) {
			setLeftAnchor(first, 0.0);
			setRightAnchor(first, null);
			a1 = createScaleAnimation(first, size[0][0], size[0][1]);
			a2 = createScaleAnimation(second, size[1][0], size[1][1]);
			a3 = createScaleAnimation(third, size[2][0], size[2][1]);
			createCombAnimation(new Animation[]{a1, a2}, a3).play();
		} else {
			a1 = createScaleAnimation(second, size[1][0], size[1][1]);
			a2 = createMoveAnimation(first, 0);
			a3 = createScaleAnimation(third, size[2][0], size[2][1]);
			createCombAnimation(a1, a2, a3).play();
		}

		isFirstActive = false;
		isSecondActive = false;
		isThirdActive = true;
	}

	private double[][] getFirstActiveSize() {
		double outerWidth = getWidth();
		double outerHeight = getHeight();

		double firstWidth = outerWidth;
		double firstHeight = outerHeight - DEACT_SIZE - SPACING_WIDTH;

		double secondWidth = (outerWidth - SPACING_WIDTH) / 2;
		double secondHeight = DEACT_SIZE;

		double thirdWidth = outerWidth - secondWidth - SPACING_WIDTH;
		double thirdHeight = secondHeight;

		double[][] size = {{firstWidth, firstHeight}, {secondWidth, secondHeight}, {thirdWidth, thirdHeight}};
		return size;
	}

	private double[][] getSecondActiveSize() {
		double outerWidth = getWidth();
		double outerHeight = getHeight();

		double firstWidth = DEACT_SIZE;
		double firstHeight = (outerHeight - SPACING_WIDTH) / 2;

		double secondWidth = outerWidth - DEACT_SIZE - SPACING_WIDTH;
		double secondHeight = outerHeight;

		double thirdWidth = firstWidth;
		double thirdHeight = outerHeight - firstHeight - SPACING_WIDTH;

		double[][] size = {{firstWidth, firstHeight}, {secondWidth, secondHeight}, {thirdWidth, thirdHeight}};
		return size;
	}

	private double[][] getThirdActiveSize() {
		double outerWidth = getWidth();
		double outerHeight = getHeight();

		double firstWidth = DEACT_SIZE;
		double firstHeight = (outerHeight - SPACING_WIDTH) / 2;

		double secondWidth = firstWidth;
		double secondHeight = outerHeight - firstHeight - SPACING_WIDTH;

		double thirdWidth = outerWidth - DEACT_SIZE - SPACING_WIDTH;
		double thirdHeight = outerHeight;

		double[][] size = {{firstWidth, firstHeight}, {secondWidth, secondHeight}, {thirdWidth, thirdHeight}};
		return size;
	}

	private Animation createScaleAnimation(Pane pane, double targetWidth, double targetHeight) {
		Timeline timeline = new Timeline();
		final KeyValue kvW = new KeyValue(pane.prefWidthProperty(), targetWidth);
		final KeyFrame kfW = new KeyFrame(TRANS_DURATION, kvW);
		final KeyValue kvH = new KeyValue(pane.prefHeightProperty(), targetHeight);
		final KeyFrame kfH = new KeyFrame(TRANS_DURATION, kvH);
		timeline.getKeyFrames().addAll(kfW, kfH);
		return timeline;
	}

	private Animation createCombAnimation(Animation[] a1, Animation a3) {
		ParallelTransition pt = new ParallelTransition();
		pt.getChildren().addAll(a1);

		SequentialTransition st = new SequentialTransition();
		st.getChildren().addAll(pt, a3);
		return st;
	}

	private Animation createCombAnimation(Animation a1, Animation a2, Animation a3) {
		SequentialTransition st = new SequentialTransition();
		st.getChildren().addAll(a1, a2, a3);
		return st;
	}

	private Animation createMoveAnimation(Pane pane, double x) {
		TranslateTransition tt = new TranslateTransition();
		tt.setNode(pane);
		tt.setDuration(TRANS_DURATION);
		tt.setToX(x);
		return tt;
	}

}