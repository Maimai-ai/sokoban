package sokoban;

/**
	* Direction est une enumeration
*/

public enum Direction {

    Gauche(0,-1),
    Droite(0,1),
    Haut(-1,0),
    Bas(1,0);

    private int dx;
    private int dy;

    Direction(int x, int y)
    {
        this.dx=x;
        this.dy=y;
    }

    public int getDx()
    {
        return dx;
    }

    public int getDy()
    {
        return dy;
    }
	
	@Override
	public String toString(){
		return "( " + dx + " ; " + dy + " )";
	}
}
