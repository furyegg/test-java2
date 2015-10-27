package com.lombardrisk.ocelot.efc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScaleTest extends Application {

	public static void main(String[] args) {
		launch(ScaleTest.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/scale.fxml"));
		stage.setTitle("Scale Test");
		stage.setScene(new Scene(root, 1000, 600));
		stage.show();
	}

}