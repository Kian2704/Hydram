package main;

import javafx.geometry.BoundingBox;

public class MovingEntity extends Entity{
	
	protected int moveDirection = 1;
	protected int nextMoveDirection = 1;
	//public static final double velocity = 20; TODO non functional. Needs new implementation
	//TODO detectCollision y & x in one function (if statement for nextBounds)
	//true = no collision
	protected boolean noCollisionY(int collider, double y)//collider : 0 = wall, 1 = entity
	{
		
		
		
		for(int i = 0; i < Map.tiles1d.length;i++)
		{
			BoundingBox nextBounds = new BoundingBox(getLayoutX(),y,width,0.1);
			
			if (collider == 0 && (Map.tiles1d[i].type == 0) && nextBounds.intersects(Map.tiles1d[i].getBoundsInParent()))
			{	
				return false;
			}
			if((collider == 1) && (type == 0) && (Map.tiles1d[i].getEnt() != null) && (getBoundsInParent().intersects(Map.tiles1d[i].getEnt().getBoundsInParent())))
			{
				Main.currentGame.collectPoint(Map.tiles1d[i]);
			}
		}
		
		for(int i = 0; i < Main.currentGame.numberGhosts;i++)
		{
			
			if(collider == 1 && type==0 && Main.currentGame.ghosts[i] != null && getBoundsInParent().intersects(Main.currentGame.ghosts[i].getBoundsInParent()))
			{
				Main.currentGame.pacmanEaten();
			}
			
		}
		return true;
		
		
	}
	//true = no collision
	protected boolean noCollisionX(int collider,double x)//collider : 0 = wall, 1 = entity
	{
		for(int i = 0; i < Map.tiles1d.length;i++)
		{
			BoundingBox nextBounds = new BoundingBox(x,getLayoutY(),0.1,height);

			if ((collider == 0) && (Map.tiles1d[i].type == 0) && nextBounds.intersects(Map.tiles1d[i].getBoundsInParent()))
			{	
				return false;
			}
			if((collider == 1) && (type == 0) && (Map.tiles1d[i].getEnt() != null) && (getBoundsInParent().intersects(Map.tiles1d[i].getEnt().getBoundsInParent())))
			{
				Main.currentGame.collectPoint(Map.tiles1d[i]);
			}
		}
		
		for(int i = 0; i < Main.currentGame.numberGhosts;i++)
		{
			
			if(collider == 1 && type==0 && Main.currentGame.ghosts[i] != null && getBoundsInParent().intersects(Main.currentGame.ghosts[i].getBoundsInParent()))
			{
				Main.currentGame.pacmanEaten();
			}
			
		}
		return true;
	}
	
	
	public void checkEntityCollision()
	{
		noCollisionX(1,getLayoutX());
		noCollisionY(1,getLayoutY());
	}
	
	public void move()
	{
		
		if (moveDirection != nextMoveDirection)
		{
			switch(nextMoveDirection) //changes move direction when possible
			{
			case 0: {
				if(noCollisionY(0,getLayoutY()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight()))
				{
					moveDirection = nextMoveDirection;
					setRotate(90);
					setScaleX(1);
					setScaleY(-1);
									}
				break;	
			}
			
			case 1: 
			{
				if(noCollisionX(0,getLayoutX()-((Game.tileSize-Game.tileSize*sizeFactor-1))))
				{
					moveDirection = nextMoveDirection;
					setRotate(0);
					setScaleY(1);
					setScaleX(-1);
					
				}
				break;	
			}
			case 2:	{
				if(noCollisionY(0,getLayoutY()-((Game.tileSize-Game.tileSize*sizeFactor-1))))
				{
					moveDirection = nextMoveDirection;
					setRotate(270);
					setScaleY(1);
					setScaleX(1);
				}
				break;	
			}
			case 3:	{
				if(noCollisionX(0,getLayoutX()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight()))//No idea why ((Game.tileSize-Game.tileSize*sizeFactor-1))
				{
					moveDirection = nextMoveDirection;
					setRotate(0);
					setScaleY(1);
					setScaleX(1);
				}
				break;	
			}
			}
		}
		
		switch(moveDirection) //moves player
		{
		case 0: {
			if(noCollisionY(0,getLayoutY()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight()))
			{setLayoutY(getLayoutY()+1);
			if(getLayoutY() > Game.gameScene.getHeight())
			{
				setLayoutY(0-height);
				
				
			}
			}break;	
		}
		case 1: 
		{
			if(noCollisionX(0,getLayoutX()-((Game.tileSize-Game.tileSize*sizeFactor-1))))
			{setLayoutX(getLayoutX()-1);
			if(getLayoutX() < 0)
			{
				setLayoutX(Game.gameScene.getWidth() + width);
				
					
			}
			}break;
		}
		case 2:	{
			if(noCollisionY(0,getLayoutY()-((Game.tileSize-Game.tileSize*sizeFactor-1))))
			{
				setLayoutY(getLayoutY()-1)
				;
			if(getLayoutY() < 0)
			{
				setLayoutY(Game.gameScene.getHeight() + height);
				
			}
	
			}break;
		}
		case 3:	{
			if(noCollisionX(0,getLayoutX()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight()))
			{setLayoutX(getLayoutX()+1);
			if(getLayoutX() > Game.gameScene.getWidth())
			{
				setLayoutX(0 - width);
				
			}
			
}break;
		}
		}
	}
	
}
