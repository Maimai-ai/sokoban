package sokoban.modele.piece.players;

import sokoban.modele.piece.Piece;

/**
	* Noeud est une classe que nous utilisons dans la classe Algorithme, plus precisement dans notre openListe et closeListe
*/

public class Noeud{
	public Piece piece; //contient sa Position
	public int coutTotal;
	public Piece predecesseur;

	public Noeud(Piece piece, Piece predecesseur){
		this.piece = piece;
		this.predecesseur = predecesseur;
	}

	public Noeud(Piece piece, int coutTotal, Piece predecesseur){
		this.piece = piece;
		this.coutTotal = coutTotal;
		this.predecesseur = predecesseur;
	}
	
	/**
		* Accesseur de piece
		* @return une Piece
	*/
	public Piece getPiece(){
		return this.piece;
	}
	
	/**
		* Mutateur de piece
		* @param p qui est une instance de Piece
	*/
	public void setPiece(Piece p){
		this.piece = p;
	}
	
	/**
		* Accesseur de coutTotal
		* @return un entier
	*/
	public int getCoutTotal(){
		return this.coutTotal;
	}
	
	/**
		* Mutateur de coutTotal
		* @param cout qui est un entier
	*/
	public void setCoutTotal(int cout){
		this.coutTotal = cout;
	}
	
	/**
		* Accesseur de predecesseur
		* @return une Piece
	*/
	public Piece getPredecesseur(){
		return this.predecesseur;
	}
	
	/**
		* Mutateur de predecesseur
		* @param pred qui est une instance de Piece
	*/
	public void setPredecesseur(Piece pred){
		this.predecesseur = pred;
	}

	@Override
	public String toString(){
		return "( " + this.piece + " ;\n " + this.coutTotal + " ;\n " + this.predecesseur + " ) \n";
	}
}
