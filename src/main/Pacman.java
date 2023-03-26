package main;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;

public class Pacman extends Entity {

	public Pacman()
	{
		super();
		Image img = new Image("file:graphics/pacman.gif");
		setImage(img);
		setX(Map.pacmanSpawn.x);
		setY(Map.pacmanSpawn.y+(1-sizeFactor));
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
