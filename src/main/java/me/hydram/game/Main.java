package me.hydram.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import me.hydram.accounts.Accounts;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Main Klasse*/

public class Main extends Application {

    //setze diverse Starvariablen
    static ImagePattern wallTilePattern = null;
    static ImagePattern breakableWallTilePattern = null;
    static final double screenWidth = 1200;
    static final double screenHeight = 800;
    public static Random random = new Random();
    static Stage stage;
    boolean stopped = false;
    static Game currentGame;
    static boolean enableMultiplayer = true;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static Accounts account;

    //gibt zufallszahl mit speziellen Anforderungen aus
    public static int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {break;}
            random++;
        }
        return random;
    }

    //gibt account des Spielers aus
    public static Accounts getAccounts() {
        return account;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //öffnet Fenster
        primaryStage.show();
        new Graphics();
        wallTilePattern = new ImagePattern(new Image(this.getClass().getResourceAsStream("graphics/wallTile.png")));
        breakableWallTilePattern = new ImagePattern(new Image(this.getClass().getResourceAsStream("graphics/breakableWallTile.png")));

        primaryStage.setResizable(false);

        account = new Accounts();

        executor.submit(() -> {
            while (!stopped) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (account.isTimedOut()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Timeout!");
                        alert.setHeaderText("Timeout");
                        alert.setContentText("Connection to the database has timed out!");
                        alert.showAndWait();
                        Platform.exit();
                    });
                    stopped = true;
                }
                executor.shutdown();
            }
        });

        stage = primaryStage;

        //Scenes.setMainMenuScene();
        Scenes.setLoginScene();


    }
}
