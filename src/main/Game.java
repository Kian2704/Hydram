package main;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Game {

	private int score = 0;
	public static boolean isGameOver = false;
	private boolean isPaused = false;
	private static int level = 1;
	protected int pointsLeft = 0;
	public static double tileSize = 40;
	protected int remainingLives = 1;
	public Ghost[] ghosts;
	public final int numberGhosts = 5;
	public static Image pointTexture = new Image("file:graphics/point.png");
	
	public static Pane gameScene;
	private Pacman pacman;
	
	public Pacman getPacman()
	{
		return pacman;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public static void resetLevel()
	{
		level = 1;
	}
	
	public static void levelUp()
	{
		level++;
	}
	
	private void checkWin()
	{
		if (pointsLeft == 0)
		{
			isGameOver = true;
		}
	}
	
	
	
	
	private void clearGame()
	{
		
		isGameOver = false;
	}
	
	
	private void setRemainingPoints()
	{ 
		pointsLeft = Map.numberPathTiles;
	}
	
	private void freezePlayer(int time)
	{
		
	}
	
	private void freezeGhosts(int time)
	{
		
	}
	
	private void gameOver()
	{
		
		isGameOver = true;
	}
	
	private void decreaseLives()
	{
		remainingLives--;
		if(remainingLives <= 0)
		{
			gameOver();
		}
		System.out.println(remainingLives);
	}
	
	private void resetPacman()
	{
		pacman.setX(Map.pacmanSpawn.x);
		pacman.setY(Map.pacmanSpawn.y);
		pacman.nextMoveDirection = 1;
	}
	
	
	public void pacmanEaten()
	{
		resetPacman();
		decreaseLives();
		score -= 200;
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
			checkWin();
		}
	}
	
	
	private void initializeEntities()
	{
		pacman = new Pacman();
		ghosts = new Ghost[numberGhosts];
		
		for(int i = 0; i < ghosts.length;i++)
		{
			ghosts[i] = new Ghost();
			gameScene.getChildren().add(ghosts[i]);
		}
	
		
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
						System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBB");
						e.printStackTrace();
					}
			        while(!isGameOver)
			        {
			        	try {
							Thread.sleep((int)(90/MovingEntity.velocity));
						} catch (InterruptedException e) {
							System.out.println("AAAAAAAAAAAAAAAAAAAA");
							e.printStackTrace();
						}
			        	pacman.move();
			        	for(int i = 0; i < ghosts.length;i++)
			        	{
			        		ghosts[i].setMoves();
			        	}

			        	pacman.checkEntityCollision();

			        }
			        
			        
			        Platform.runLater(() -> {
			        	stopGame();
			        });
			        
			        
				}};
				Thread gameThread = new Thread(task);
				gameThread.setDaemon(true);
				gameThread.start();

	}
	
	private void stopGame()
	{
		Ghost.numberGhosts = 0;
		Main.currentGame = null;
		boolean won = (remainingLives > 0);
		if(won)
		{
			levelUp();
		}
        Scenes.setGameOverScene(score,won);
	}
	public Game()
	{
		Scenes.setGameScene(getLevel());
		clearGame();
		setRemainingPoints();
		initializeEntities();
		play();
		
	}
}
