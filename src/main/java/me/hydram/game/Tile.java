package me.hydram.game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
//Klasse für alle Felder im Spiel
public class Tile extends Rectangle {

	int type;//0 = Wall, 1 = Path,2 = Ghost Spawn, 3 Pacman Spawn, 4 empty tile, 5 Spawn Blocker, 6 Powerup, 7 Breakable wall tile
	private Entity ent;
	public Vec2 coords;
	public int col;
	public int row;
	//berechnet den Mittelpunkt des Feldes
	public Vec2 getCenter(Entity ent)
	{
		Vec2 center = new Vec2(((coords.x+getWidth()/2)-ent.getFitWidth()/2),((coords.y+getHeight()/2)) - ent.getFitWidth()/2);
		return center;
	}
	//Wechselt den Typ des Feldes(Für die zerstörbaren Wände)
	public void changeType(int type)
	{
		this.type = type;
		if(type == 7)
			this.setFill(Main.breakableWallTilePattern);
		if(type == 1)
		{
			this.setFill(Color.BLACK);
		}
		
	}
	
	//Setzt das Wesen welches sich auf dem Feld befindet
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
	
	//getter
	public Entity getEnt()
	{
			return ent;
	}
	//Löscht ein Wesen von einem Feld
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
	//Erstellt ein Objekt vom Typ Tile
	public Tile(double x, double y, double width, double height,int vtype) {
		super(x,y,width,height);
		type = vtype;
		coords = new Vec2(x,y);
		
		switch(type)
		{
		case 0: setFill(Main.wallTilePattern);break; 
		case 7: setFill(Main.breakableWallTilePattern);break;
		}
		
	}

}
