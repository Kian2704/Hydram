package main;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.ImageView;

public class Entity extends ImageView {
	
	protected ImagePattern texture;
	protected int id;
	protected int type;//0 Player, 1 Ghost/NPC,2 Powerup
	private double velx = 4;
	private double vely = 4;
	public int moveDirection = 1;
	
	protected Entity()
	{
		setFitHeight(Game.tileSize-Game.tileSize/4);
		setFitWidth(Game.tileSize-Game.tileSize/4);
	}
	

	//TODO Collision richtig erkennen! Ist verbuggt.
	private boolean checkMovementY(double y)
	{
		for(int i = 0; i < Map.wallTiles.length;i++)
		{
			if (Map.wallTiles[i].type == 1 && (Map.wallTiles[i].contains(getX(),y) || Map.wallTiles[i].contains(getX(),y-40)))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean checkMovementX(double x)
	{
		for(int i = 0; i < Map.wallTiles.length;i++)
		{
			if (Map.wallTiles[i].type == 1 && Map.wallTiles[i].contains(x,getY()))
			{
				return false;
			}
		}
		return true;
	}
	
	public void move()
	{
		switch(moveDirection)
		{
		case 0: {
			if(checkMovementY(getY()+vely+getFitHeight()))
			{setY(getY()+vely);}break;
			
		}
		case 1: 
		{
			if(checkMovementX(getX()-velx))
			{setX(getX()-velx);}break;
		}
		case 2:	{
			if(checkMovementY(getY()-vely))
			{setY(getY()-vely);}break;
		}
		case 3:	{
			if(checkMovementX(getX()+velx+getFitHeight()))
			{setX(getX()+velx);}break;
		}
		}
	}

}
