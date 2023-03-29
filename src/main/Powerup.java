package main;

public class Powerup extends StaticEntity {

	
	Powerup()
	{
		type = 4;
		sizeFactor = .4;
		setFitHeight(Game.tileSize*sizeFactor);
		setFitWidth(Game.tileSize*sizeFactor);
		setImage(Game.powerUpTexture);
	}
	
	
}
