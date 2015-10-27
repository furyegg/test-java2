package com.lombardrisk.ocelot.efc.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ScaleController {

	private static final Duration TRANS_DURATION = Duration.millis(1000);

	@FXML
	private VBox box1;

	@FXML
	private VBox box2;

	@FXML
	private VBox box3;

	private ScaleTransition scaleTrans;

	@FXML
	private void start(ActionEvent e) {
		System.out.println("Box2 width: " + box2.getWidth());
		System.out.println("Box2 X: " + box2.getLayoutX());

		getWidthAnimation(box2, 400).play();
		getTransition(box2, -200).play();
	}

	@FXML
	private void pause(ActionEvent e) {
		scaleTrans.pause();
	}

	@FXML
	private void end(ActionEvent e) {
		scaleTrans.stop();
	}

	private Animation getWidthAnimation(Pane pane, double targetWidth) {
		Timeline timeline = new Timeline();
		final KeyValue kv = new KeyValue(pane.prefWidthProperty(), targetWidth);
		final KeyFrame kf = new KeyFrame(TRANS_DURATION, kv);
		timeline.getKeyFrames().add(kf);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		return timeline;
	}

	private Animation getHeightAnimation(Pane pane, double targetHeight) {
		Timeline timeline = new Timeline();
		final KeyValue kv = new KeyValue(pane.prefHeightProperty(), targetHeight);
		final KeyFrame kf = new KeyFrame(TRANS_DURATION, kv);
		timeline.getKeyFrames().add(kf);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		return timeline;
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

	private Transition getTransition(Pane pane, double x) {
		TranslateTransition tt = new TranslateTransition();
		tt.setNode(pane);
		tt.setDuration(TRANS_DURATION);
		tt.setCycleCount(Timeline.INDEFINITE);
		tt.setAutoReverse(true);
		tt.setToX(x);
		return tt;
	}

}
