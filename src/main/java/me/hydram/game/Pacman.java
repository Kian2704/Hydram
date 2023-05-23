package me.hydram.game;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Pacman extends MovingEntity {

	
	boolean breakWall = false;
	boolean doubleSpeed = false;
	static int numberPlayers;
	private int playerNumber = 0;
	//Erstellt den Pacman
	public Pacman()
	{
		super();
		
		playerNumber = numberPlayers;
		System.out.println("Pacman created at: " + this.getLayoutX() + " " + this.getLayoutY() + numberPlayers);
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
	
	//setzt die spielerzahl zurück auf 0
	public static void resetPacmans()
	{
		numberPlayers = 0;
	}
	
	// Getter & Setter
	public void setDoubleSpeed(boolean set)
	{
		this.doubleSpeed = set;
	}
	
	public boolean getDoubleSpeed()
	{
		return doubleSpeed;
	}
	
	
	//Setzt Pacman auf die startposition
	public void reset()
	{
		Vec2 spawn = null;
		if(this.playerNumber == 0)
			spawn = Map.pacmanSpawn;
		else if(this.playerNumber == 1)
			spawn = Map.pacmanSpawn2;
		System.out.println(spawn.x + "<--");
		setLayoutX(spawn.x+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setLayoutY(spawn.y+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setNextMoveDirection(1);
		freeze(2000);
	}
	//Führt die move() methode der Superklasse MovingEntity mehrmals aus um die Geschwindigkeit anzupassen.
	public boolean move()
	{
		int moves = 0;
		boolean bool = false;
		if(!this.doubleSpeed)
			moves = 3;
		else
			moves = 6;
		for(int i = 0; i < moves; i++)
		{
			bool = super.move();
		}
		return bool;
	}
	
	//Legt die Tasten fest mit welchen Pacman bewegt werden kann.
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
				    	System.out.println(this.getLayoutX() + " " + this.getLayoutY());
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
