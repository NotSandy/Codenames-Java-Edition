package guiJavaFX;

import javafx.stage.*;

import java.net.URISyntaxException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class WinningDialog {
	/**
	 * This method displays a message that pops up when one of the teams wins
	 * @param title Title of the popup
	 * @param message The message containing which team won
	 * @param main The program being modified
	 */
	public static void display(String title, String message, Main main) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(200);
		window.setMinHeight(200);
		
		Label label = new Label();
		label.setText(message);
		label.setAlignment(Pos.CENTER);
		
		Button menuButton = new Button("Main Menu");
		menuButton.setOnAction(e -> mainMenu(window, main));
		
		Button restartButton = new Button("Restart");
		restartButton.setOnAction(e -> restartGame(window, main));
		
		Button exitButton = new Button("Exit To Desktop");
		exitButton.setOnAction(e -> exitGame(window, main));
		
		VBox layout = new VBox(10);
		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(menuButton, restartButton, exitButton);
		buttonBox.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(label, buttonBox);
		layout.setPadding(new Insets(25, 25, 25, 25));
		layout.setAlignment(Pos.CENTER);
		layout.getStyleClass().add("dialog");
		
		Scene theScene = new Scene(layout);
		try {
			theScene.getStylesheets().add(WinningDialog.class.getResource("stylesheet.css").toURI().toString());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		window.setScene(theScene);
		try {
			window.getIcons().add(new Image(WinningDialog.class.getResource("Codename.png").toURI().toString()));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		window.showAndWait();
	}
	
	public static void mainMenu(Stage window, Main main)
	{
		window.close();
		main.getWindow().setScene(main.getMainMenuScene());
		main.getMainMenuScreen().setTop(main.getMenu());
	}

	public static void restartGame(Stage window, Main main)
	{
		window.close();
		if(main.getThreeTeamMode()==0) {
			main.newBoard();
		}
		else {
			main.threeTeamBoard();
		}
	}
	
	public static void exitGame(Stage window, Main main)
	{
		window.close();
		main.getWindow().close();
	}

}