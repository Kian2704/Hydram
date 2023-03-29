package main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entity extends ImageView {
	
	protected Image texture;
	protected int type;//0 Player(Pacman), 1 Ghost/NPC,3 Point, 4 Powerup,5 Spawn Blocker
	protected double height;
	protected double width;
	protected double sizeFactor = 0.9;
	
	
	
	protected Entity()
	{
	}

}
