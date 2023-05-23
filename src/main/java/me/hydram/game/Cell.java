package me.hydram.game;

/*Zelle*/

public class Cell implements Comparable<Cell> {
    public int parent_i, parent_j;
    public double f, g, h;
    public int col;
    public int row;


    public Cell(double f, double g, double h, int parent_i, int parent_j) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.parent_i = parent_i;
        this.parent_j = parent_j;
    }


    public static boolean isValid(int col, int row) {
        return (col < Map.tiles.length && col > 0 && row < Map.tiles[0].length && row > 0);
    }


    @Override
    public int compareTo(Cell o) {
        return Double.compare(f, o.f);
    }


}
