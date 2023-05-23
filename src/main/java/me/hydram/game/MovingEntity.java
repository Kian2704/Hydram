package me.hydram.game;


import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
//Klasse für jedes Wesen welches sich bewegen kann
public class MovingEntity extends Entity{
	
	protected int moveDirection = 1;
	protected int nextMveDirection = 1;
	protected Vec2 currentTile = null;
	public final int velocity = 1;
	public boolean isFrozen = false;
	private long frozenTime;
	private long blockedTime;
	private boolean isBlockedDirectionChange;
	//true = no collision
	//Setzt die isFrozen Variable, welche dazu genutz wird zu bestimmen, ob ein Wesen sich bewegen darf.
	public void freeze(int duration)
	{
		frozenTime = System.currentTimeMillis();
		isFrozen = true;
		AnimationTimer freezeTimer = new AnimationTimer()
		{
			
			@Override
			public void handle(long arg0) {
				if(System.currentTimeMillis() >= frozenTime + duration)
				{
					isFrozen=false;
					stop();
				}
				
			}
	
		};
		freezeTimer.start();
		
	}
	//Überprüft ob sich ein Wesen außerhalb des Spielfeldes befindet.
	public boolean isOutOfMap()
	{
		Bounds bounds = this.getBoundsInParent();
		return (bounds.getMinX() < 0 || bounds.getMaxX() > Main.screenWidth || bounds.getMaxY() > Main.screenHeight || bounds.getMinY() < 0);

	}
	
	//Setzt die isFrozen Variable von true auf false, oder von false auf true
	public void changeFreezeState()
	{
		isFrozen = !isFrozen;
	}
	//Gibt zurück ob ein Wesen mit einer der Wand Felder kollidiert.
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
			if (((Map.tiles1d[i].type == 0 || Map.tiles1d[i].type == 7) && nextBounds.intersects(Map.tiles1d[i].getBoundsInParent())))
			{	
				if(Map.tiles1d[i].type == 7 && this.type == 0)
				{
					if(((Pacman)this).breakWall == true)
					{
						Map.tiles1d[i].changeType(1);
					}else
					{
						return false;
					}
				}else
				{
					return false;
				}
			}
			if(Map.tiles1d[i].contains(getLayoutX(),getLayoutY()))
			{
				currentTile = new Vec2(Map.tiles1d[i].col,Map.tiles1d[i].row);
			}
			if(((Map.tiles1d[i].getEnt() != null) && (Map.tiles1d[i].getEnt().type == 5) &&nextBounds.intersects(Map.tiles1d[i].getEnt().getBoundsInParent()) && posEnt.isAbove(posTile)))
			{
				return false;
			}
			
			//Check collision with Point
			if((type == 0) && (Map.tiles1d[i].getEnt() != null) && (Map.tiles1d[i].getEnt().type == 3 || Map.tiles1d[i].getEnt().type == 4) && (getBoundsInParent().intersects(Map.tiles1d[i].getEnt().getBoundsInParent())))
			{
				
				Main.currentGame.collectPoint(Map.tiles1d[i]);
			}
			
			//Check Collision with Ghost
			if( i < Main.currentGame.numberGhosts && type==0 && Main.currentGame.ghosts[i] != null && getBoundsInParent().intersects(Main.currentGame.ghosts[i].getBoundsInParent()))
			{
				BoundingBox smallerHitbox = new BoundingBox((
						Main.currentGame.ghosts[i].getBoundsInParent().getMinX()+Main.currentGame.ghosts[i].getBoundsInParent().getWidth()/4),
						Main.currentGame.ghosts[i].getBoundsInParent().getMinY()+Main.currentGame.ghosts[i].getBoundsInParent().getHeight()/4,
						Main.currentGame.ghosts[i].getBoundsInParent().getWidth()*0.5,
						Main.currentGame.ghosts[i].getBoundsInParent().getHeight()*0.5);
				if(getBoundsInParent().intersects(smallerHitbox))
				{
					if(Main.currentGame.ghosts[i].isEatable())
					{
						Main.currentGame.ghostEaten(Main.currentGame.ghosts[i]);
					}else
					{
						Main.currentGame.pacmanEaten();
					}
				}
				
			}
		}
	
		return true;
	}
	
	//gibt die entgegengesetzte Richtung zurück
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
	//Setzt die nächste Richtung, in welche ein Wesen sich bewegen soll.
	public void setNextMoveDirection(int next)
	{
			this.nextMveDirection = next;
	}
	
	public int getNextMoveDirection()
	{
		return nextMveDirection;
	}
	

	/*
	 * Setzt die Richtung anhand der Variable nextMoveDirection, sobald dies möglich ist.
	 * Rotiert die Textur des Wesens anhand der Bewegungsrichtung.
	 */
	public boolean move()
	{
		if(isFrozen)
		{
			return false;
		}
		if (moveDirection != getNextMoveDirection())
		{
			switch(getNextMoveDirection()) //changes move direction when possible
			{
			case 0: {
				if(noCollision(getLayoutY()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight(),1) && !isOutOfMap())
				{
					moveDirection = getNextMoveDirection();
					setRotate(90);
					setScaleX(1);
					setScaleY(-1);
									}
				break;	
			}
			
			case 1: 
			{
				if(noCollision(getLayoutX()-((Game.tileSize-Game.tileSize*sizeFactor-1)),0) && !isOutOfMap())
				{
					moveDirection = getNextMoveDirection();
					setRotate(0);
					setScaleY(1);
					setScaleX(-1);
					
				}
				break;	
			}
			case 2:	{
				if(noCollision(getLayoutY()-((Game.tileSize-Game.tileSize*sizeFactor-1)),1) && !isOutOfMap())
				{
					moveDirection = getNextMoveDirection();
					setRotate(270);
					setScaleY(1);
					setScaleX(1);
				}
				break;	
			}
			case 3:	{
				if(noCollision(getLayoutX()+((Game.tileSize-Game.tileSize*sizeFactor-1))+getFitHeight(),0) && !isOutOfMap())//No idea why ((Game.tileSize-Game.tileSize*sizeFactor-1))
				{
					moveDirection = getNextMoveDirection();
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
