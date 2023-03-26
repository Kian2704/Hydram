package main;
import javafx.geometry.BoundingBox;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

public class Entity extends ImageView {
	
	protected ImagePattern texture;
	protected int id;
	protected int type;//0 Player, 1 Ghost/NPC,2 Powerup
	protected double velx = 3;
	private double height;
	private double width;
	protected double vely = 3;
	protected double sizeFactor = 0.9;
	public int moveDirection = 1;
	public int nextMoveDirection = 1;
	
	
	protected Entity()
	{
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
	}

	
	
	

	//TODO Collision richtig erkennen! Ist verbuggt.
	protected boolean detectCollisionY(double y)
	{
		
		for(int i = 0; i < Map.wallTiles.length;i++)
		{
			BoundingBox nextBounds = new BoundingBox(getX(),y,width,0.1);
			
			if ((Map.wallTiles[i].type == 1) && nextBounds.intersects(Map.wallTiles[i].getBoundsInParent()))
			{	
				return false;
			}
		}
		return true;
	}
	
	protected boolean detectCollisionX(double x)
	{
		for(int i = 0; i < Map.wallTiles.length;i++)
		{
			BoundingBox nextBounds = new BoundingBox(x,getY(),0.1,height);
			
			if ((Map.wallTiles[i].type == 1) && nextBounds.intersects(Map.wallTiles[i].getBoundsInParent()))
			{	
				return false;
			}
		}
		return true;
	}
	
	public boolean move()
	{
		if (moveDirection != nextMoveDirection)
		{
			switch(nextMoveDirection)
			{
			case 0: {
				if(detectCollisionY(getY()+vely+getFitHeight()))
				{moveDirection = nextMoveDirection ;}break;	
			}
			case 1: 
			{
				if(detectCollisionX(getX()-velx))
				{moveDirection = nextMoveDirection;}break;
			}
			case 2:	{
				if(detectCollisionY(getY()-vely))
				{moveDirection = nextMoveDirection;}break;
			}
			case 3:	{
				if(detectCollisionX(getX()+velx+getFitHeight()))
				{moveDirection = nextMoveDirection;}break;
			}
			}
		}
		
		
		switch(moveDirection)
		{
		case 0: {
			if(detectCollisionY(getY()+vely+getFitHeight()))
			{setY(getY()+vely);return true;}break;	
		}
		case 1: 
		{
			if(detectCollisionX(getX()-velx))
			{setX(getX()-velx);return true;}break;
		}
		case 2:	{
			if(detectCollisionY(getY()-vely))
			{setY(getY()-vely);return true;}break;
		}
		case 3:	{
			if(detectCollisionX(getX()+velx+getFitHeight()))
			{setX(getX()+velx);return true;}break;
		}
		}
		return false;
	}

}
