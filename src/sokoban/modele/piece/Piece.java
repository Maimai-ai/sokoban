package sokoban.modele.piece;

import java.util.Observer;
import java.util.Observable;

/**
	* Piece est une classe et extends la classe abstraite Observable
*/

public class Piece extends Observable{
	public int x;
	public int y;
	private boolean estFinal = false;
	
	/** 
		* Constructeur qui ne prend rien en parametre
	*/
	public Piece(){
		this.x = x;
		this.y = y;
	}

	public Piece(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
		* Accesseur de la position en x de la Piece
		* @return un entier
	*/
	public int getX(){
		return this.x;
	}
	/**
		* Mutateur de la position en X de la Piece
		* Notifie son Observer de son changement
		* @param n qui est un entier et modifie la position en X
	*/
	public void setX(int n){
		this.x = n;
		this.setChanged();
		this.notifyObservers();
	}
	/**
		* Accesseur de la position en y de la Piece
		* @return un entier
	*/
	public int getY(){
		return this.y;
	}
	/**
		* Mutateur de la position en X de la Piece
		* Notifie son Observer de son changement
		* @param n qui est un entier et modifie la position en X
	*/
	public void setY(int n){
		this.y = n;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
		* Accesseur de si elle est final ou non
		* @return un booleen
	*/
	public boolean getFinal(){
		return this.estFinal;
	}
	
	/**
		* Mutateur de la Piece pour modifier si elle est finale
		* @param fin est un booleen
	*/
	public void setFinal(boolean fin){
		this.estFinal = fin;
	}
	
	@Override
	public String toString(){
		return "( " + this.x + " ; " + this.y + " )";
	}
	
}
