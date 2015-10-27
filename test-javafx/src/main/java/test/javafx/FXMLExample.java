package test.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLExample extends Application {

	public static void main(String[] args) {
		Application.launch(FXMLExample.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
//		System.out.println(getClass().getResource("/fxml/fxml_example.fxml"));
//		System.out.println(getClass().getClassLoader().getResource("fxml/fxml_example.fxml"));

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/fxml_example.fxml"));
		stage.setTitle("FXML Welcome");
		stage.setScene(new Scene(root, 300, 275));
		stage.show();
	}

}