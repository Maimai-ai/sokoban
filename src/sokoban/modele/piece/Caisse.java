package sokoban.modele.piece;

import sokoban.*;
import sokoban.modele.plateau.*;

/**
	* Caisse est une classe et herite de sa classe mere Piece
*/
public class Caisse extends Piece{
	private int ligne;
	private int colonne;
	private boolean estFinal;

	public Caisse(){
	}
	
	public Caisse(int ligne, int colonne, boolean estFinal){
		this.ligne = ligne;
		this.colonne= colonne;
		this.estFinal= false;
	}
	
	/** 
		* Accesseur de estFinal
		* @return un booléen
	*/
	public boolean getEstFinal(){
		return this.estFinal;
	}
	
	/**
		* Mutateur de estFinal
		* @param n
		*		booleen qui modifie l'instance estFinal
	*/
	public void setEstFinal(boolean n){
		this.estFinal= n;
	}
	
	/**
		* Cette méthode permet de savoir si la caisse est bougeable dans un plateau en fonction de la direction
		* @param plat
		*		Une instance de Plateau
		* @param dir
		* 		Une Direction
		* @return un booleen
	*/
	public boolean estBougeable(Plateau plat, Direction dir){
		Piece[][] plateau = plat.getPlateau();
		
		if(plateau[this.ligne + dir.getDx()][this.colonne + dir.getDy()] == null || plateau[this.ligne + dir.getDx()][this.colonne + dir.getDy()] instanceof PositionFinal){
			return true;
		}
		return false;
	}
	
}
