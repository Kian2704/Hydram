package main;
import javafx.scene.image.Image;

public class Ghost extends Entity {

	private boolean eatable;
	
	public Ghost()
	{
		super();
		Image img = new Image("file:graphics/pacman.gif");
		setImage(img);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
