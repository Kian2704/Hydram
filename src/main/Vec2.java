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
	
	public boolean isLeftTo(Vec2 compare)
	{
		return (x < compare.x);
	}
	
}
