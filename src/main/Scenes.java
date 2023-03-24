package main;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Scenes {
	
	
	
	
	public static void main(String[] args) {

	}
	
	public static void setGameScene(Stage primaryStage)
	{
		primaryStage.setScene(new Scene(Scenes.getGameScene(primaryStage), 1200, 800));
	}
	
	public static void setMainMenuScene(Stage primaryStage)
	{
		primaryStage.setScene(new Scene(Scenes.getMainMenu(primaryStage), 1200, 800));
	}
	
	public static VBox getMainMenu(Stage primaryStage)
	{
		primaryStage.setTitle("PacMan - Main Menu");
		final Font buttonFont = Font.font("Courier New",FontWeight.BOLD,25);
    	final Font headlineFont = Font.font("Courier New",FontWeight.BLACK,30);
		Button btnSettings = new Button();
        Button btnPlay = new Button();
        Button btnClose = new Button();
        Label headline = new Label("Main Menu");
        btnPlay.setText("Start Game");
        btnSettings.setText("Settings");
        btnClose.setText("Exit Game");
        headline.setFont(headlineFont);
        btnPlay.setFont(buttonFont);
        btnSettings.setFont(buttonFont);
        btnSettings.setDisable(true);
        btnSettings.setTooltip(new Tooltip("Hello"));
        btnClose.setFont(buttonFont);
        
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	Platform.exit();
            }
        });
        
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	setGameScene(primaryStage);
            }
        });
        
        
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(20);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.getChildren().addAll(headline,btnPlay,btnSettings,btnClose);
        
        return mainMenu;
	}
	
	public static VBox getGameScene(Stage primaryStage)
	{
		primaryStage.setTitle("PacMan - Game");
		VBox gameScene = new VBox();
		Label headline = new Label("This is the game!");
		Group group = new Group();
		
		
		gameScene.getChildren().addAll(headline,group);
		return gameScene;
	}

}
