package com.lombardrisk.ocelot.efc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/switch.fxml"));
		stage.setTitle("Switch Test");
		stage.setScene(new Scene(root, 1000, 800));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}