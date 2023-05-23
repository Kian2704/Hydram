package me.hydram.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/*Managed die Lebensanzeige im Spiel*/

public class LiveCounter extends HBox {
    public static Image liveHeartImage; //Initialisiert variable von Typ Image "liveHeartImage"

    public LiveCounter() {
        super();
        liveHeartImage = new Image(this.getClass().getResourceAsStream("graphics/live.png"));   //schreibt "liveHeartImage" eine Textur zu
    }

    public void update(int lives) {
        this.getChildren().clear();
        for (int i = 0; i < lives; i++) {                       //wird so of ausgefüht, wie der Spieler Leben hat
            ImageView liveHeart = new ImageView(liveHeartImage);//erstellt ein Objekt mit der Textur "liveHeartImage"
            liveHeart.setFitHeight(Game.tileSize);              //setzt Größe der Textur in der Höhe
            liveHeart.setFitWidth(Game.tileSize);               //setzt Größe der Textur in der Breite
            liveHeart.opacityProperty().set(0.6);               //setzt die Deckkraft der Textur auf 60%
            this.getChildren().add(liveHeart);                  //fügt "lifeHeart"
        }
    }

}
