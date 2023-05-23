package me.hydram.game;

//Klasse für die blauen Powerups die Pacman aufsammeln kann.
public class Powerup extends StaticEntity {

	//Erstellt ein Object des Typs Powerup
	public Powerup()
	{
		type = 4;
		sizeFactor = .4;
		setFitHeight(Game.tileSize*sizeFactor);
		setFitWidth(Game.tileSize*sizeFactor);
		setImage(Game.powerUpTexture);
	}
	
	
}
