package sokoban.modele.piece;

/**
	* PositionFinal est une classe et herite de sa classe mere Piece
*/


public class PositionFinal extends Piece{
	
	private int ligne;
	private int colonne;
	public boolean estDessus = false;
	
	public PositionFinal(){
		
	}
	
	public PositionFinal(int ligne, int colonne){
		this.ligne = ligne;
		this.colonne= ligne;
	}
	
	public boolean getEstDessus(){
		return this.estDessus;
	}
	
	public void setEstDessus(boolean bool){
		this.estDessus = bool;
	}
	
}
