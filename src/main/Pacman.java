package main;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Pacman extends MovingEntity {

	public Pacman()
	{
		super();
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		
		texture = new Image("file:graphics/pacman.gif");
		setImage(texture);
		setScaleX(-1);
		reset();
		type = 0;
	}
	
	
	public void reset()
	{
		setLayoutX(Map.pacmanSpawn.x+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setLayoutY(Map.pacmanSpawn.y+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		nextMoveDirection = 1;
	}
	
	public void enableControl(boolean bool)
	{
		if(bool == true)
		{
			Main.stage.getScene().setOnKeyPressed(e -> {
			    if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
			    	nextMoveDirection = 0;
			    } else if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A)
			    {
			    	nextMoveDirection = 1;
			    } else if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W)
			    {
			    	nextMoveDirection = 2;
			    }else if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D)
			    {
			    	nextMoveDirection = 3;
			    }
			});	
		}else
		{
			Main.stage.getScene().setOnKeyPressed(null);
		}
	}
}
