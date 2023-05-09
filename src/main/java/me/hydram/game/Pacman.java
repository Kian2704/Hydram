package me.hydram.game;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Pacman extends MovingEntity {

	
	boolean breakWall = false;
	boolean doubleSpeed = false;
	static int numberPlayers;
	private int playerNumber = 0;
	public Pacman()
	{
		super();
		System.out.println("Pacman created");
		playerNumber = numberPlayers;
		numberPlayers++;
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		texture = new Image(this.getClass().getResourceAsStream("graphics/pacman.gif"));
		setImage(texture);
		setScaleX(-1);
		reset();
		type = 0;
	}
	
	
	public void setDoubleSpeed(boolean set)
	{
		this.doubleSpeed = set;
	}
	
	public boolean getDoubleSpeed()
	{
		return doubleSpeed;
	}
	
	
	
	public void reset()
	{
		Vec2 spawn = null;
		if(this.playerNumber == 0)
			spawn = Map.pacmanSpawn;
		else if(this.playerNumber == 1)
			spawn = Map.pacmanSpawn2;
		setLayoutX(Map.pacmanSpawn.x+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setLayoutY(Map.pacmanSpawn.y+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setNextMoveDirection(1);
		freeze(2000);
	}
	
	public void enableControl(boolean bool)
	{
		
		if(bool == true)
		{
			Main.stage.getScene().setOnKeyPressed(e -> {
				this.breakWall = false;
				if(Main.currentGame != null && !Main.currentGame.isPaused())
				{
					if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
				    	setNextMoveDirection(0);
				    	if(e.isControlDown())
				    	{
				    		this.breakWall = true;
				    	}
				    		
				    } else if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A)
				    {
				    	setNextMoveDirection(1);
				    	if(e.isControlDown())
				    	{
				    		this.breakWall = true;
				    	}
				    } else if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W)
				    {
				    	setNextMoveDirection(2);
				    	if(e.isControlDown())
				    	{
				    		this.breakWall = true;
				    	}
				    }else if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D)
				    {
				    	setNextMoveDirection(3);
				    	if(e.isControlDown())
				    	{
				    		this.breakWall = true;
				    	}
				    }
				}
			    
			});	
		}else
		{
			Main.stage.getScene().setOnKeyPressed(null);
		}
	}
}
