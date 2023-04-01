package main;

public class Vec2 {
	double x;
	double y;
	
	public Vec2(double xcord, double ycord)
	{
		x = xcord;
		y = ycord;
	}
	
	public boolean isAbove(Vec2 compare)
	{
		return (y < compare.y);
	}
	
	public boolean isUnder(Vec2 compare)
	{
		return (y > compare.y);
	}
	
	public boolean isLeftTo(Vec2 compare)
	{
		return (x < compare.x);
	}
	public boolean isRightTo(Vec2 compare)
	{
		return (x > compare.x);
	}
	
	
	public static double getDistance(Vec2 first, Vec2 second)
	{
		return Math.sqrt((first.x-second.x)*(first.x-second.x) + (first.y-second.y)*(first.y-second.y));
	}
	
	public static double getMHDistance(Vec2 first, Vec2 second)
	{
		return (Math.abs(first.x-second.x) + Math.abs(first.x-second.x));
	}
	
	
	
}
