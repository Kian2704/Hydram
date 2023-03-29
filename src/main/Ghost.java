package main;
import javafx.scene.image.Image;

public class Ghost extends MovingEntity {

	private boolean eatable;
	public static int numberGhosts;
	private int id;
	private static int randomMoveTimer = 0;
	private static int randomMoveCounter = 0;
	private boolean axisSwitch;
	private boolean isEatable = false;
	public Ghost()
	{
		type = 1;
		setLayoutX(Map.ghostSpawn.x);
		setLayoutY(Map.ghostSpawn.y);
		id = numberGhosts;
		numberGhosts++;
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		changeSkin();
		
		
	}
	
	public void reset()
	{
		setEatable(false);
		setLayoutX(Map.ghostSpawn.x+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setLayoutY(Map.ghostSpawn.y+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		nextMoveDirection = 2;
	}
	
	public boolean isEatable()
	{
		return isEatable;
	}
	
	public void setEatable(boolean bool)
	{
		isEatable = bool;
		changeSkin();
	}
	
	
	public void changeSkin()
	{
		Image img = null;
		if(isEatable == false)
		{
			switch(id)
			{
			case 0: img = new Image("file:graphics/blue.gif");break;
			case 1:	img = new Image("file:graphics/green.gif");break;
			case 2:	img = new Image("file:graphics/red.gif");break;
			case 3:	img = new Image("file:graphics/purple.gif");break;
			case 4:	img = new Image("file:graphics/orange.gif");break;
			}
		}
		else
		{
			img = Game.eatableGhost;
		}
		if(img != null)
		{
			setImage(img);
		}
		
		
	}
	
	
	public void setMoves()
	{
		Pacman pacman = Main.currentGame.getPacman();
		double pacmanX = pacman.getLayoutX();
		double pacmanY = pacman.getLayoutY();
		boolean pacmanIsRight = (pacmanX > getLayoutX());
		boolean pacmanIsUnder = (pacmanY > getLayoutY());
		boolean moveXAxis = true;
		double pacmanDistance = Math.sqrt( ((pacmanX-getLayoutX()) * (pacmanX-getLayoutX())) + ((pacmanY-getLayoutY()) * (pacmanY-getLayoutY())) );
		Vec2 direction = new Vec2(pacmanX-getLayoutX(),pacmanY-getLayoutY());
		Vec2 positionPacman = new Vec2(pacman.getLayoutX(),pacman.getLayoutY());
		Vec2 positionGhost = new Vec2(getLayoutX(),getLayoutY());
		if(pacmanDistance >= 150)
		{
			if(randomMoveTimer == 0)
			{
				randomMoveTimer = Main.random.nextInt(1,51);
			}

				if(move() == false)
				{
					nextMoveDirection = Main.random.nextInt(0, 4);
				}
				if(randomMoveCounter == randomMoveTimer)
				{
					
					nextMoveDirection = Main.getRandomWithExclusion(Main.random, 0, 4, getOppositeDirection());
					
					randomMoveTimer = Main.random.nextInt(1,51);
					randomMoveCounter = 0;
				}
				randomMoveCounter++;
		}
		else
		{
				if(axisSwitch)
				{
					if(positionPacman.y != positionGhost.y)
					{
						if(positionPacman.isAbove(positionGhost))
						{
							nextMoveDirection = 2;
						}
						if(!positionPacman.isAbove(positionGhost))
						{
							nextMoveDirection = 0;
						}
					}
					
				}else
				{
					if(positionPacman.x != positionGhost.x)
					{
						if(positionPacman.isLeftTo(positionGhost))
						{
							nextMoveDirection = 1;
						}
						if(!positionPacman.isLeftTo(positionGhost))
						{
							nextMoveDirection = 3;
						}
					}
					
				}
				
				axisSwitch = !axisSwitch;
			
				move();
		}
		
		}

	}
