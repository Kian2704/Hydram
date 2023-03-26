package main;

import javafx.geometry.BoundingBox;

public class MovingEntity extends Entity{
	
	public int moveDirection = 1;
	public int nextMoveDirection = 1;
	private boolean controlsActive = true;
	public static final double velocity = 3;

	protected boolean detectCollisionY(int collider, double y)//collider : 0 = wall, 1 = entity
	{
		
		for(int i = 0; i < Map.tiles1d.length;i++)
		{
			BoundingBox nextBounds = new BoundingBox(getX(),y,width,0.5);
			
			if (collider == 0 && (Map.tiles1d[i].type == 0) && nextBounds.intersects(Map.tiles1d[i].getBoundsInParent()))
			{	
				return false;
			}
			if((collider == 1) && (type == 0) && (Map.tiles1d[i].getEnt() != null) && (getBoundsInParent().intersects(Map.tiles1d[i].getEnt().getBoundsInParent())))
			{
				Main.currentGame.collectPoint(Map.tiles1d[i]);
			}
		}
		return true;
		
		
	}
	
	protected boolean detectCollisionX(int collider,double x)//collider : 0 = wall, 1 = entity
	{
		for(int i = 0; i < Map.tiles1d.length;i++)
		{
			BoundingBox nextBounds = new BoundingBox(x,getY(),0.1,height);

			if ((collider == 0) && (Map.tiles1d[i].type == 0) && nextBounds.intersects(Map.tiles1d[i].getBoundsInParent()))
			{	
				return false;
			}
			if((collider == 1) && (type == 0) && (Map.tiles1d[i].getEnt() != null) && (getBoundsInParent().intersects(Map.tiles1d[i].getEnt().getBoundsInParent())))
			{
				Main.currentGame.collectPoint(Map.tiles1d[i]);
			}
		}
		return true;
	}
	
	
	public void checkEntityCollision()
	{
		detectCollisionX(1,getX());
		detectCollisionY(1,getY());
	}
	
	
	public boolean move()
	{
		
		if (moveDirection != nextMoveDirection && controlsActive == true)
		{
			switch(nextMoveDirection)
			{
			case 0: {
				if(detectCollisionY(0,getY()+velocity+getFitHeight()))
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
				if(detectCollisionX(0,getX()-velocity))
				{
					moveDirection = nextMoveDirection;
					setRotate(0);
					setScaleY(1);
					setScaleX(-1);
				}
				break;	
			}
			case 2:	{
				if(detectCollisionY(0,getY()-velocity))
				{
					moveDirection = nextMoveDirection;
					setRotate(270);
					setScaleY(1);
					setScaleX(1);
				}
				break;	
			}
			case 3:	{
				if(detectCollisionX(0,getX()+velocity+getFitHeight()))
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
		
		
		switch(moveDirection)
		{
		case 0: {
			if(detectCollisionY(0,getY()+velocity+getFitHeight()))
			{setY(getY()+velocity);
			if(getY() > Game.gameScene.getHeight())
			{
				setY(0-height);
				
			}
			if(getY() < 0)
			{
				controlsActive = false;
				return true;
			} 
				controlsActive = true;
			
			return true;
			}break;	
		}
		case 1: 
		{
			if(detectCollisionX(0,getX()-velocity))
			{setX(getX()-velocity);
			if(getX() < 0)
			{
				setX(Game.gameScene.getWidth() + width);
				
					
			}
			if(getX() > Game.gameScene.getWidth())
			{
				controlsActive = false;
				return true;
			} 
				controlsActive = true;
			
			return true;}break;
		}
		case 2:	{
			if(detectCollisionY(0,getY()-velocity))
			{setY(getY()-velocity);
			if(getY() < 0)
			{
				setY(Game.gameScene.getHeight() + height);
				
			}
			if (getY() > Game.gameScene.getHeight())
			{
				controlsActive = false;
				return true;
			} 
				controlsActive = true;
			
			return true;
			}break;
		}
		case 3:	{
			if(detectCollisionX(0,getX()+velocity+getFitHeight()))
			{setX(getX()+velocity);
			if(getX() > Game.gameScene.getWidth())
			{
				setX(0 - width);
				
			}
			if(getX() < 0)
			{
				controlsActive = false;
				return true;
			} 
				controlsActive = true;
			
			return true;
}break;
		}
		}
		return false;
	}
	
}
