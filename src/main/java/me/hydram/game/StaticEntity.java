package me.hydram.game;
//Überklasse für alle Wesen welche sich nicht bewegen können.
public class StaticEntity extends Entity {

	protected StaticEntity()
	{
		setFitHeight(Game.tileSize);
		setFitWidth(Game.tileSize);
		height = getFitHeight();
		width = getFitWidth();
	}
	
	
}
