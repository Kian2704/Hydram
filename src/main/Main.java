package main;
 
import java.util.Random;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

//import javafx.geometry.Insets;


public class Main extends Application {
	
	final static ImagePattern wallTilePattern = new ImagePattern(new Image("file:graphics/wallTile.png"));
	static final double screenWidth = 1200;
	static final double screenHeight = 800;
	public static Random random = new Random();
	static Stage stage;
	static Game currentGame;
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	
        primaryStage.show();
        primaryStage.setResizable(false);
        stage = primaryStage;
        Scenes.setMainMenuScene();
        
        
    }
}