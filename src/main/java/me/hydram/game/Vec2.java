package me.hydram.game;

public class Vec2 {
	double x;
	double y;
	
	public Vec2(double xcord, double ycord)
	{
		x = xcord;
		y = ycord;
	}
	//Gibt ein boolean zurück ob eine Koordinate sich über der anderen befindet
	public boolean isAbove(Vec2 compare)
	{
		return (y < compare.y);
	}
	//Gibt ein boolean zurück ob eine Koordinate sich unter der anderen befindet
	public boolean isUnder(Vec2 compare)
	{
		return (y > compare.y);
	}
	//Gibt ein boolean zurück ob eine Koordinate sich links von der anderen befindet
	public boolean isLeftTo(Vec2 compare)
	{
		return (x < compare.x);
	}
	//Gibt ein boolean zurück ob eine Koordinate sich rechts von der anderen befindet
	public boolean isRightTo(Vec2 compare)
	{
		return (x > compare.x);
	}
	
	//Berechnet die Distanz von zwei Punkten
	public static double getDistance(Vec2 first, Vec2 second)
	{
		return Math.sqrt((first.x-second.x)*(first.x-second.x) + (first.y-second.y)*(first.y-second.y));
	}
	//Berechnet die Distanz von zwei Punkten
	public static double getMHDistance(Vec2 first, Vec2 second)
	{
		return (Math.abs(first.x-second.x) + Math.abs(first.x-second.x));
	}
	
	
	
}
