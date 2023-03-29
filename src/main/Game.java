package main;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Game {

	private static int score = 0;
	public static boolean isGameOver = false;
	private boolean isPaused = false;
	private static int level = 1;
	protected int pointsLeft = 0;
	public static double tileSize = 40;
	public static int execution = 0;
	protected int remainingLives = 3;
	public Ghost[] ghosts;
	public final int numberGhosts = 1;
	public static Image pointTexture = new Image("file:graphics/point.png");
	public static Image spawnBlockerTexture = new Image("file:graphics/spawnBlocker.png");
	
	public static Pane gameScene;
	private Pacman pacman;
	
	public Pacman getPacman()
	{
		return pacman;
	}
	public int getScore()
	{
		return score;
	}
	
	public int getRemainingLives()
	{
		return remainingLives;
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
	
	private void resetScore()
	{
		score = 0;
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
	}
	
	
	public void pacmanEaten()
	{
		pacman.reset();
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
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					pacman.enableControl();
			        
			        AnimationTimer movement = new AnimationTimer() //Runs code in every Frame of the Game ~60fps
			        		{
			        	
			        	
			        	public void handle(long now)
			        	{
			        		
			        		if(isGameOver)
				        	{
				        		stopGame();
				        		stop();
				        		
				        	}
			        		if(!isGameOver)
			        		{
			        			pacman.move();
			        			if(Main.random.nextInt(0, 101) < 85)//Ghosts 15% slower than pacman
			        			{
			        				for(int i = 0; i < ghosts.length;i++)
						        	{
						        		ghosts[i].setMoves();
						        		
						 
						        	}
			        				
			        			}
					        	
					        	pacman.checkEntityCollision();
			        		}
			        		
				        	
				        	
			        		
			        	}};
			        		
			        	movement.start();
			        		
			        
			        	
			        
			        
			        
			        
			        
			        
			        

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
        if(!won)
        {
    			resetLevel();
    			resetScore();
        }
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
