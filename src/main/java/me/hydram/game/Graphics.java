package me.hydram.game;

import javafx.scene.image.Image;

public class Graphics {
	private static Image pointTexture;
	private static Image powerUpTexture;
	private static Image spawnBlockerTexture;
	private static Image eatableGhost;
	private static Image ghost1;
	private static Image ghost2;
	private static Image ghost3;
	private static Image ghost4;
	private static Image ghost5;
	
	
	
	public Graphics()
	{
		pointTexture = new Image(this.getClass().getResourceAsStream("graphics/point.png"));
		powerUpTexture = new Image(this.getClass().getResourceAsStream("graphics/powerup.png"));
		spawnBlockerTexture = new Image(this.getClass().getResourceAsStream("graphics/spawnBlocker.png"));
		eatableGhost = new Image(this.getClass().getResourceAsStream("graphics/eat.gif"));
		ghost1 = new Image(this.getClass().getResourceAsStream("graphics/red.gif"));
		ghost2 = new Image(this.getClass().getResourceAsStream("graphics/green.gif"));
		ghost3 = new Image(this.getClass().getResourceAsStream("graphics/blue.gif"));
		ghost4 = new Image(this.getClass().getResourceAsStream("graphics/orange.gif"));
		ghost5 = new Image(this.getClass().getResourceAsStream("graphics/purple.gif"));
		
	}
	
	
	public static Image getPointTexture()
	{
		return pointTexture;
	}
	public static Image getPowerUpTexture()
	{
		return powerUpTexture;
	}
	public static Image getSpawnBlockerTexture()
	{
		return spawnBlockerTexture;
	}
	public static Image getEatableGhostTexture()
	{
		return eatableGhost;
	}
	
	public static Image getGhostTexture(Ghost ghost)
	{
		if(ghost.isEatable() == false)
		{
			switch(ghost.getGhostId())
			{
			case 0: return ghost1;
			case 1:	return ghost2;
			case 2:	return ghost3;
			case 3:	return ghost4;
			case 4:	return ghost5;
			}
		}
		else
		{
			return eatableGhost;
		}
		return null;
	}
	
}
