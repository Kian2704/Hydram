package main;
 
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import accounts.Accounts;
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
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private static Accounts account;
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
	
	
	public static Accounts getAccounts()
	{
		return account;
	}
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.show();
        primaryStage.setResizable(false);
        executor.submit(() -> { 
        	account = new Accounts();
        });
        stage = primaryStage;
        
        
        //Scenes.setMainMenuScene();
        Scenes.setLoginScene();
        
        
    }
}