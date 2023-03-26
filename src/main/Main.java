package main;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

//import javafx.geometry.Insets;


public class Main extends Application {
	
	final static ImagePattern wallTilePattern = new ImagePattern(new Image("file:graphics/wallTile.png"));
	static final double screenWidth = 1200;
	static final double screenHeight = 800;
	static Stage stage;
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setScene(new Scene(Scenes.getMainMenu(primaryStage), screenWidth, screenHeight));
        primaryStage.show();
        primaryStage.setResizable(false);
        stage = primaryStage;
        
        
    }
}