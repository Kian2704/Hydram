package main;

import javafx.geometry.BoundingBox;

public class MovingEntity extends Entity{
	
	protected int moveDirection = 1;
	protected int nextMoveDirection = 1;
	//public static final double velocity = 20; TODO non functional. Needs new implementation
	//TODO detectCollision y & x in one function (if statement for nextBounds)
	//true = no collision
	protected boolean noCollision(double coord,int axis)//axis: 0=x 1=y
	{	
		for(int i = 0; i < Map.tiles1d.length;i++)
		{
			BoundingBox nextBounds;
			if(axis == 0)
			{
				nextBounds = new BoundingBox(coord,getLayoutY(),0.1,height);
			}else
			{
				nextBounds = new BoundingBox(getLayoutX(),coord,width,0.1);
			}
			Vec2 posEnt = new Vec2(getLayoutX(),getLayoutY());
			Vec2 posTile = new Vec2(Map.tiles1d[i].getX(),Map.tiles1d[i].getY());
			//Check collision with wall
			if (((Map.tiles1d[i].type == 0) && nextBounds.intersects(Map.tiles1d[i].getBoundsInParent())))
			{	
				return false;
			}
			
			if(((Map.tiles1d[i].getEnt() != null) && (Map.tiles1d[i].getEnt().type == 5) &&nextBounds.intersects(Map.tiles1d[i].getEnt().getBoundsInParent()) && posEnt.isAbove(posTile)))
			{
				return false;
			}
			
			//Check collision with Point
			if((type == 0) && (Map.tiles1d[i].getEnt() != null) && (Map.tiles1d[i].getEnt().type == 3) && (getBoundsInParent().intersects(Map.tiles1d[i].getEnt().getBoundsInParent())))
			{
				Main.currentGame.collectPoint(Map.tiles1d[i]);
			}
			
			//Check Collision with Ghost
			if( i < Main.currentGame.numberGhosts && type==0 && Main.currentGame.ghosts[i] != null && getBoundsInParent().intersects(Main.currentGame.ghosts[i].getBoundsInParent()))
			{
				Main.currentGame.pacmanEaten();
			}
		}
	
		return true;
	}
	
	protected int getOppositeDirection()
	{
		switch(moveDirection)
		{
		case 0:return 2;
		case 1:return 3;
		case 2:return 0;
		case 3: return 1;
		}
		return -1;
	}
	
	public void checkEntityCollision()
	{
		noCollision(getLayoutX(),0);
		noCollision(getLayoutY(),1);
	}
	
	public boolean move()
	{
		
		if (moveDirection != nextMoveDirection)
		{
			switch(nextMoveDirection) //changes move direction when possible
			{
			case 0: {
				if(noCollision(getLayoutY()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight(),1))
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
				if(noCollision(getLayoutX()-((Game.tileSize-Game.tileSize*sizeFactor-1)),0))
				{
					moveDirection = nextMoveDirection;
					setRotate(0);
					setScaleY(1);
					setScaleX(-1);
					
				}
				break;	
			}
			case 2:	{
				if(noCollision(getLayoutY()-((Game.tileSize-Game.tileSize*sizeFactor-1)),1))
				{
					moveDirection = nextMoveDirection;
					setRotate(270);
					setScaleY(1);
					setScaleX(1);
				}
				break;	
			}
			case 3:	{
				if(noCollision(getLayoutX()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight(),0))//No idea why ((Game.tileSize-Game.tileSize*sizeFactor-1))
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
		
		switch(moveDirection) //moves entity
		{
		case 0: {
			if(noCollision(getLayoutY()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight(),1))
			{
			if(getLayoutY() > Game.gameScene.getHeight())
			{
				setLayoutY(0-height);
				
				
			}
			setLayoutY(getLayoutY()+1);
			return true;
			}break;
		}
		case 1: 
		{
			if(noCollision(getLayoutX()-((Game.tileSize-Game.tileSize*sizeFactor-1)),0))
			{
			if(getLayoutX() < 0)
			{
				setLayoutX(Game.gameScene.getWidth() + width);
				
					
			}
			setLayoutX(getLayoutX()-1);
			return true;
			}break;
		}
		case 2:	{
			if(noCollision(getLayoutY()-((Game.tileSize-Game.tileSize*sizeFactor-1)),1))
			{
				
			if(getLayoutY() < 0)
			{
				setLayoutY(Game.gameScene.getHeight() + height);
				
			}
			setLayoutY(getLayoutY()-1);
			return true;
			}break;
		}
		case 3:	{
			if(noCollision(getLayoutX()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight(),0))
			{
			if(getLayoutX() > Game.gameScene.getWidth())
			{
				setLayoutX(0 - width);
				
			}
			setLayoutX(getLayoutX()+1);
			return true;
}break;
		}
		}
		return false;
	}
	
}
