package sokoban.modele.piece.players;

import sokoban.modele.plateau.*;
import sokoban.Direction;
/**
	* Player est une classe qui herite de GamePlayer
*/
public class Player extends GamePlayer{

    public int compteur;
	public Plateau plateau;
	public int score = 0;
	
	public Player(){
		this.compteur = 0;
	}
	
	public Player(Plateau plateau){
		super(plateau);
		this.compteur = 0;
	}
	
	/**
		* Accesseur de plateau
		* @return un Plateau
	*/
	public Plateau getPlateau(){
		return this.plateau;
	}
	
	/**
		* Mutateur de plateau
		* @param plat
		* 		une instance de Plateau
	*/
	public void setPlateau(Plateau plat){
		this.plateau = plat;
	}
	/**
		* Accesseur de score
		* @return un entier
	*/
	public int getScore(){
		return this.score;
	}
	
  	/**
		* Redefinition de la methode chooseMove
		* @return une Direction, qui correspond au mouvement choisis du joueur
	*/
	@Override
	public Direction chooseMove(){
		
		boolean mouvEffectue = false;
		
		Direction gauche = Direction.Gauche;
		Direction droite = Direction.Droite;
		Direction haut = Direction.Haut;
		Direction bas = Direction.Bas;
		
		Direction rep = null;

		while (!mouvEffectue){
			int dx = 0;
			int dy = 0;
			if(this.toucheDirection == gauche) {
				dx = 0;
				dy = -1;
			}
			else if(this.toucheDirection == droite) {
				dx = 0;
				dy = 1;
			}
			else if (this.toucheDirection == haut) {
				dx = -1;
				dy = 0;
			}
			else if (this.toucheDirection == bas) {
				dx =  1;
				dy =  0;
			}
			else if (this.toucheDirection == null) {
				dx =  0;
				dy =  0;
				toucheAppuyee=false;
			}
			if(toucheAppuyee==false){
				if (this.toucheDirection != null){
					toucheAppuyee=true;
				}
				for(Direction i : moveValid(this.plateau)){
					
					if(dx == i.getDx() && dy == i.getDy()){
						
						if(dx == gauche.getDx() && dy == gauche.getDy()){
							rep = gauche;
							this.compteur+=1;
							
						}
						else if (dx == droite.getDx() && dy == droite.getDy()){
							rep = droite;
							this.compteur+=1;
							
						}
						
						else if(dx == haut.getDx() && dy == haut.getDy()){
							rep = haut;
							this.compteur+=1;
						}
						else if (dx == bas.getDx() && dy == bas.getDy()){
							rep = bas;
							this.compteur+=1;
						}
						mouvEffectue = true;
					}
					mouvEffectue = true;
				}
			}
		}
		return rep;
	}
	
	/**
		* Redefinition de la methode getCompteur
		* @return un entier
	*/
	@Override
	public int getCompteur(){
		return this.compteur;
	}
}
