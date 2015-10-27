package com.lombardrisk.ocelot.efc;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TitleBarTest extends Application {

	private Timeline animation;

	private void init(Stage primaryStage) {
		VBox root = new VBox();
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root, 800, 600, true));

		HBox outer = new HBox();
		outer.setSpacing(5);
		outer.setPrefHeight(50);

		HBox left = new HBox();
		left.setPadding(new Insets(3,0,0,0));
		left.setPrefWidth(30);
		left.setAlignment(Pos.TOP_LEFT);
		left.getChildren().add(new Rectangle(30, 5));

		HBox center = new HBox();
		center.setPrefWidth(50);
		center.setAlignment(Pos.CENTER);
		center.getChildren().add(new Rectangle(50, 50));

		HBox right = new HBox();
		right.setPadding(new Insets(0,0,3,0));
		right.setPrefWidth(600);
		right.setAlignment(Pos.BOTTOM_LEFT);
		right.getChildren().add(new Rectangle(600, 2));

		outer.getChildren().addAll(left,center,right);

		root.getChildren().addAll(outer);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}