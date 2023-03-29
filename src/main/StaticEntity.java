package main;

public class StaticEntity extends Entity {

	protected StaticEntity()
	{
		setFitHeight(Game.tileSize);
		setFitWidth(Game.tileSize);
		height = getFitHeight();
		width = getFitWidth();
	}
	
	
}
