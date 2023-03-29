package main;
import javafx.scene.shape.Rectangle;
public class Tile extends Rectangle {

	int type;//0 = Wall, 1 = Path,2 = Ghost Spawn, 3 Pacman Spawn, 4 empty tile, 5 Spawn Blocker
	private Entity ent;
	public Vec2 coords;
	public int col;
	public int row;
	public Vec2 getCenter(Entity ent)
	{
		Vec2 center = new Vec2(((coords.x+getWidth()/2)-ent.getFitWidth()/2),((coords.y+getHeight()/2)) - ent.getFitWidth()/2);
		return center;
	}
	
public boolean setEnt(Entity entity)
	{
		if(ent == null)
		{
			ent = entity;
			if(ent.sizeFactor != 1)
			{
				ent.setLayoutX(getCenter(ent).x);
				ent.setLayoutY(getCenter(ent).y);
			}else
			{
				ent.setLayoutX(coords.x);
				ent.setLayoutY(coords.y);
			}
			return true;
		}
		return false;
	}
	
	
	public Entity getEnt()
	{
			return ent;
	}
	
	public boolean removeEnt()
	{
			if(ent != null)
			{
				ent.setVisible(false);
				ent = null;
				
				return true;
			}else
			{
				return false;
			}
				
				
	}
	
	public Tile(double x, double y, double width, double height,int vtype) {
		super(x,y,width,height);
		type = vtype;
		coords = new Vec2(x,y);
		
		switch(type)
		{
		case 0: setFill(Main.wallTilePattern);break; 
		}
		
	}

}
