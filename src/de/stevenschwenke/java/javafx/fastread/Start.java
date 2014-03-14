package de.stevenschwenke.java.javafx.fastread;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private void init(Stage primaryStage) throws IOException {

		primaryStage.setHeight(430);
		primaryStage.setWidth(610);

		Parent parent = FXMLLoader.load(this.getClass().getResource("fastread.fxml"));
		primaryStage.setTitle("FastRead");
		primaryStage.setScene(new Scene(parent));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		init(primaryStage);
		primaryStage.show();
	}
}
