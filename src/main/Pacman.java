package main;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Pacman extends MovingEntity {

	public Pacman()
	{
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		
		texture = new Image("file:graphics/pacman.gif");
		setImage(texture);
		setScaleX(-1);
		setX(Map.pacmanSpawn.x);
		setY(Map.pacmanSpawn.y+velocity);//TODO rausfinden wieso +3 nötig
		type = 0;
		enableControl();
	}
	
	
	public void enableControl()
	{
		Main.stage.getScene().setOnKeyPressed(e -> {
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
			
		
		
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	

}
