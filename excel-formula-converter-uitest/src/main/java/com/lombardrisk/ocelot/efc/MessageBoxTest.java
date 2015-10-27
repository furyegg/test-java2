package com.lombardrisk.ocelot.efc;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MessageBoxTest extends Application {

	private Timeline animation;

	private void init(Stage primaryStage) {
		VBox root = new VBox();
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root, 800, 600, true));

		Path path;

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