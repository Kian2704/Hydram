package main;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Game {

	private int score = 0;
	private boolean isGameOver = false;
	private boolean isPaused = false;
	private int pointsLeft = 0;
	public static double tileSize = 40;
	private Ghost[] ghosts;
	private int numberGhosts = 4;
	public static Image pointTexture = new Image("file:graphics/point.png");
	
	public static Pane gameScene;
	private Pacman pacman;

	private void setRemainingPoints()
	{ 
		pointsLeft = Map.numberPathTiles;
	}
	public boolean isGameOver()
	{
		if(isGameOver)
			return true;
		return false;
	}
	
	public void collectPoint(Tile tile)
	{
		if(tile.getEnt() != null && tile.getEnt().type == 3)
		{
			score += 10;
			pointsLeft--;
			tile.removeEnt();	
		}
	}
	
	
	private void initializeEntities()
	{
		pacman = new Pacman();
		//ghosts = new Ghost[numberGhosts];
	
		
		Debug.entityDebugEventHandler(pacman);
		gameScene.getChildren().add(pacman);
		
		
	}
	
	
	private void play()
	{
		Runnable task = new Runnable()
				{
				public void run()
				{

					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        while(!isGameOver)
			        {
			        	try {
							Thread.sleep((int)(90/MovingEntity.velocity));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	pacman.move();
			        	if(pacman.move() == true)
			        	{
			        		pacman.checkEntityCollision();
			        	}
			        }
				}};
				Thread gameThread = new Thread(task);
				gameThread.setDaemon(true);
				gameThread.start();

	}
	public Game()
	{
		setRemainingPoints();
		initializeEntities();
		play();
	}
}
