package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class LiveCounter extends HBox {
	public static Image liveHeartImage = new Image("file:resources/graphics/live.png");
	public void update(int lives)
	{
		this.getChildren().clear();
		for(int i = 0; i < lives;i++)
		{
			ImageView liveHeart = new ImageView(liveHeartImage);
			liveHeart.setFitHeight(Game.tileSize);
			liveHeart.setFitWidth(Game.tileSize);
			liveHeart.opacityProperty().set(0.6);
			this.getChildren().add(liveHeart);
		}
	}
	public LiveCounter()
	{
		super();
		
	}
}
