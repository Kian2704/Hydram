package me.hydram.game;

import java.net.URI;
import java.net.URISyntaxException;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import me.hydram.accounts.User;

public class Game {

	private int startScore = 0;
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
	public static Image pointTexture;
	public static Image powerUpTexture;
	public static Image spawnBlockerTexture;
	public static Image eatableGhost ;
	
	public static Pane gameScene;
	private Pacman pacman = null;
	private Pacman pacman2 = null;
	
	
	
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
		Main.getAccounts().getUser().resetLevel();
	}
	
	public static void levelUp()
	{
		level++;
		Main.getAccounts().getUser().increaseLevel();
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
		Media sound;
		try {
			sound = new Media(getClass().getResource("sound/dead.mp3").toString());
			ughPlayer = new MediaPlayer(sound);
			ughPlayer.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		Media sound;
		try {
			sound = new Media(getClass().getResource("sound/eating.mp3").toString());
			nomPlayer = new MediaPlayer(sound);
			nomPlayer.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		int random = Main.random.nextInt(2);
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
						
						
						if(random == 0)
							ghosts[i].setEatable(true);
						else if(random == 1)
							pacman.setDoubleSpeed(true);
					}
					start = false;
				}
				
				if(currentTime - startTime >= 8000)
				{
					for(int i = 0; i < numberGhosts; i++)
					{
						if(random == 0)
							ghosts[i].setEatable(false);
						else if(random == 1)
							pacman.setDoubleSpeed(false);
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
			Media sound;
			try {
				sound = new Media(getClass().getResource("sound/eating.mp3").toString());
				nomPlayer = new MediaPlayer(sound);
				nomPlayer.play();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
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
		if(Main.enableMultiplayer == true)
			this.pacman2 = new Pacman();
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
		Media sound;
		try {
			sound = new Media(getClass().getResource("sound/background.mp3").toString());
			Thread.sleep(5000);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
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
			        			pacman.move();//Only 3 times if Pc bad
			        			pacman.move();
			        			if(pacman.getDoubleSpeed())
			        			{
			        				pacman.move();
				        			pacman.move();//Only 3 times if Pc bad
				        			pacman.move();
			        			}
			        			for(int i = 0; i < pacman.velocity-1;i++)
			        			{
			        				pacman.move();
			        			}
			        			if(Main.random.nextInt(100) < 85)//Ghosts 15% slower than pacman
			        			{
			        				for(int i = 0; i < ghosts.length;i++)
						        	{
						        		ghosts[i].setMoves();
						        		ghosts[i].setMoves();
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
		boolean won = (pointsLeft == 0 && remainingLives > 0);
		boolean gameAborted = (pointsLeft > 0 && remainingLives > 0);
		if(won)
		{
			levelUp();
		}

        Scenes.setGameOverScene(score,won);
        User user = Main.getAccounts().getUser();
        if(user.getHighscore() < score)
        	user.setHighscore(score);
        
        if(!won && !gameAborted)
        {
    			resetLevel();
    			resetScore();
        }
		if(gameAborted)
		{
			score = startScore;//TODO in reset score mit rein
		}
	}
	public Game()
	{
		initializeImages();
		this.startScore = score;
		Scenes.setGameScene(Main.getAccounts().getUser().getCurrentLevel());
		clearGame();
		setRemainingPoints();
		initializeEntities();
		play();
		liveCounter.update(remainingLives);
		
	}
	
	private void initializeImages()
	{
		 pointTexture = Graphics.getPointTexture();
		 powerUpTexture = Graphics.getPowerUpTexture();
		 spawnBlockerTexture = Graphics.getSpawnBlockerTexture();
		 eatableGhost = Graphics.getEatableGhostTexture();
	}
}
