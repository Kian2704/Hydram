package main;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Pacman extends MovingEntity {

	
	boolean breakWall = false;
	public Pacman()
	{
		super();
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		
		texture = new Image("file:resources/graphics/pacman.gif");
		setImage(texture);
		setScaleX(-1);
		reset();
		type = 0;
	}
	
	
	public void reset()
	{
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
