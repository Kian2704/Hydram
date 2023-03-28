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
		setLayoutX(Map.pacmanSpawn.x+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setLayoutY(Map.pacmanSpawn.y+((Game.tileSize-Game.tileSize*sizeFactor)/2));//TODO rausfinden wieso +3(2) nötig. Ist das richtig: (Game.tileSize-Game.tileSize*sizeFactor)/2)??
		type = 0;
	}
	
	
	public void enableControl()
	{
		Main.stage.getScene().setOnKeyPressed(e -> {//TODO maybe causes game crash 
		    if (e.getCode() == KeyCode.DOWN) {
		    	nextMoveDirection = 0;
		    } else if(e.getCode() == KeyCode.LEFT)
		    {
		    	nextMoveDirection = 1;
		    } else if(e.getCode() == KeyCode.UP)
		    {
		    	nextMoveDirection = 2;
		    }else if(e.getCode() == KeyCode.RIGHT)
		    {
		    	nextMoveDirection = 3;
		    }
		});	
	}
}
