package main;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import javafx.geometry.Insets;


public class Main extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(Scenes.getMainMenu(primaryStage), 1200, 800));
        primaryStage.show();
        
    }
}