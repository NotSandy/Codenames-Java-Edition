package guiJavaFX;

import javafx.stage.*;

import java.net.URISyntaxException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class MessageDialog {
	/**
	 * This method displays a message that pops up when necessary i.e when its the spymaster's turn.
	 * @param title Title of the popup
	 * @param message The message of the popup
	 */
	public static void display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(200);
		window.setMinHeight(200);
		
		Label label = new Label();
		label.setText(message);
		label.setAlignment(Pos.CENTER);
		
		Button closeButton = new Button("Close This Window");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setPadding(new Insets(25, 25, 25, 25));
		layout.setAlignment(Pos.CENTER);
		layout.getStyleClass().add("dialog");
		
		Scene theScene = new Scene(layout);
		try {
			theScene.getStylesheets().add(MessageDialog.class.getResource("stylesheet.css").toURI().toString());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		window.setScene(theScene);
		try {
			window.getIcons().add(new Image(MessageDialog.class.getResource("Codename.png").toURI().toString()));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		window.showAndWait();
	}

}
