package main;
import javafx.scene.image.Image;

public class Ghost extends MovingEntity {

	private boolean eatable;
	public static int numberGhosts;
	private int id;
	
	public Ghost()
	{
		setX(Map.ghostSpawn.x);
		setY(Map.ghostSpawn.y);
		id = numberGhosts;
		numberGhosts++;
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		Image img = null;
		switch(id)
		{
		case 0: img = new Image("file:graphics/blue.gif");break;
		case 1:	img = new Image("file:graphics/green.gif");break;
		case 2:	img = new Image("file:graphics/red.gif");break;
		case 3:	img = new Image("file:graphics/purple.gif");break;
		case 4:	img = new Image("file:graphics/orange.gif");break;
		}
		if(img != null)
		{
			setImage(img);
		}
		
	}
	
	public void setMoves()
	{
		Pacman pacman = Main.currentGame.getPacman();
		double pacmanX = pacman.getX();
		double pacmanY = pacman.getY();
		boolean pacmanIsRight = (pacmanX > getX());
		boolean pacmanIsUnder = (pacmanY > getY());
		boolean moveXAxis = true;
		double pacmanDistance = Math.sqrt( ((pacmanX-getX()) * (pacmanX-getX())) + ((pacmanY-getY()) * (pacmanY-getY())) );
		Vec2 direction = new Vec2(pacmanX-getX(),pacmanY-getY());
		if(moveDirection != nextMoveDirection)
		{
			
			
			
			/*
			 if(moveXAxis)
			{
				if()
				if(pacmanIsRight)
				{
					nextMoveDirection = 3;
				}
				
				if(!pacmanIsRight)
				{
					nextMoveDirection=1;
				}
			}
			
			if(!moveXAxis)
			{
				if(pacmanIsUnder)
				{
					nextMoveDirection = 0;
				}
				if(!pacmanIsUnder)
				{
					nextMoveDirection = 2;
				}
			}
			
			
			moveXAxis = !moveXAxis;*/
		}
		
		
		
		
		
		
		
	}
	
	
	
	
	

}
