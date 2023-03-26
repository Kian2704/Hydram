package main;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
public class Tile extends Rectangle {

	int type;//0 = Wall, 1 = Path,2 = Ghost Spawn, 3 Pacman Spawn
	int row;
	int col;
	public Vec2 coords;
	public Tile(double x, double y, double width, double height,int vtype) {
		super(x,y,width,height);
		type = vtype;
		coords = new Vec2(x,y);
		
		switch(type)
		{
		case 1: setFill(Main.wallTilePattern);break; 
		}
		
	}

}
