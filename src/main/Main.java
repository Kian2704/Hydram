package main;
 
import java.util.Random;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//import javafx.geometry.Insets;


public class Main extends Application {
	
	final static ImagePattern wallTilePattern = new ImagePattern(new Image("file:resources/graphics/wallTile.png"));
	final static ImagePattern breakableWallTilePattern = new ImagePattern(new Image("file:resources/graphics/breakableWallTile.png"));
	static final double screenWidth = 1200;
	static final double screenHeight = 800;
	public static Random random = new Random();
	static Stage stage;
	static Game currentGame;
	
	public static int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude) {
	        if (random < ex) {
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	
    	primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setResizable(false);
        
        stage = primaryStage;
        
        Scenes.setMainMenuScene();
        
        
    }
}