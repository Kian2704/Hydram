package main;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Game {

	private static int score = 0;
	public static boolean isGameOver = false;
	private boolean isPaused = false;
	private static int level = 1;
	protected int pointsLeft = 0;
	public static double tileSize = 40;
	public static int execution = 0;
	protected int remainingLives = 3;
	public static Label scoreLabel;
	public static LiveCounter liveCounter;
	public Ghost[] ghosts;
	private MediaPlayer mediaPlayer;
	long startTime;
	public final int numberGhosts = 5;
	public static Image pointTexture = new Image("file:resources/graphics/point.png");
	public static Image powerUpTexture = new Image("file:resources/graphics/powerUp.png");
	public static Image spawnBlockerTexture = new Image("file:resources/graphics/spawnBlocker.png");
	public static Image eatableGhost = new Image("file:resources/graphics/eat.gif");
	
	public static Pane gameScene;
	private Pacman pacman;
	
	
	
	public boolean isPaused()
	{
		return isPaused;
	}
	
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
	
	private void updateScore()
	{
		scoreLabel.setText("Score: " + score);
	}
	
	
	private void clearGame()
	{
		
		isGameOver = false;
	}
	
	private void resetScore()
	{
		score = 0;
		updateScore();
	}
	
	
	private void setRemainingPoints()
	{ 
		pointsLeft = Map.numberPathTiles;
	}
	
	private void gameOver()
	{
		
		isGameOver = true;
	}
	
	private void decreaseLives()
	{
		remainingLives--;
		liveCounter.update(remainingLives);
		if(remainingLives <= 0)
		{
			gameOver();
		}
	}
	
	
	public boolean changePause()
	{
		isPaused = !isPaused;
		return isPaused;
	}
	
	
	
	public void pacmanEaten()
	{
		
		MediaPlayer ughPlayer;
		String ugh = "resources/sound/dead.mp3";     // For example
		Media sound = new Media(new File(ugh).toURI().toString());
		ughPlayer = new MediaPlayer(sound);
		ughPlayer.play();
		pacman.reset();
		for(int i = 0; i < numberGhosts;i++)
		{
			ghosts[i].reset();
		}
		decreaseLives();
		score -= 200;
		updateScore();
	}
	
	public void ghostEaten(Ghost ghost)
	{
		MediaPlayer nomPlayer;
		String nom = "resources/sound/eating.mp3";     // For example
		Media sound = new Media(new File(nom).toURI().toString());
		nomPlayer = new MediaPlayer(sound);
		nomPlayer.play();
		ghost.reset();
		score += 150;
		updateScore();
	}
	
	
	public boolean isGameOver()
	{
		if(isGameOver)
			return true;
		return false;
	}
	
	private void Powerup()
	{
		AnimationTimer timer = new AnimationTimer()
				{
			boolean start = true;
					@Override
					public void handle(long arg0) {
						long currentTime = System.currentTimeMillis();
						if(start)
						{
							startTime = System.currentTimeMillis();
							for(int i = 0; i < numberGhosts; i++)
							{
								ghosts[i].setEatable(true);
							}
							start = false;
						}
						
						if(currentTime - startTime >= 8000)
						{
							for(int i = 0; i < numberGhosts; i++)
							{
								ghosts[i].setEatable(false);
								stop();
							}
						}
					}
			
				};
				timer.start();
	}
	
	public void collectPoint(Tile tile)
	{
		if((tile.getEnt() != null) && tile.getEnt().type == 4)
		{
			Powerup();
		}
		if((tile.getEnt() != null) && (tile.getEnt().type == 3 || tile.getEnt().type == 4) )
		{
			MediaPlayer nomPlayer;
			String nom = "resources/sound/eating.mp3";     // For example
			Media sound = new Media(new File(nom).toURI().toString());
			nomPlayer = new MediaPlayer(sound);
			nomPlayer.play();
			
			score += 10;
			updateScore();
			for(int i = 0; i < 200; i++)
			{
				
			}
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
		String musicFile = "resources/sound/background.mp3";     // For example
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
				mediaPlayer.play();
				
			}
			
		});
		
		
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					pacman.enableControl(true);
			        
			        AnimationTimer movement = new AnimationTimer() //Runs code in every Frame of the Game ~60fps
			        		{
			        	
			        	
			        	public void handle(long now)
			        	{
			        		
			        		if(isGameOver)
				        	{
				        		stopGame();
				        		stop();
				        	}
			        		if(!isGameOver &&  !isPaused)
			        		{
			        			pacman.move();
			        			for(int i = 0; i < pacman.velocity-1;i++)
			        			{
			        				pacman.move();
			        			}
			        			if(Main.random.nextInt(0, 101) < 85)//Ghosts 15% slower than pacman
			        			{
			        				for(int i = 0; i < ghosts.length;i++)
						        	{
						        		ghosts[i].setMoves();
						        	}}}
			        	}};
			        	movement.start();
			        		}
	
	public void stopGame()
	{
		if(mediaPlayer != null)
		{
			mediaPlayer.stop();
		}
		Ghost.numberGhosts = 0;
		Main.currentGame = null;
		boolean won = (pointsLeft == 0);
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
		
		Scenes.setGameScene(3);
		clearGame();
		setRemainingPoints();
		initializeEntities();
		play();
		liveCounter.update(remainingLives);
		
	}
}
