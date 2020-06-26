package sokoban.controleur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import sokoban.modele.piece.players.*;
import sokoban.modele.plateau.*;
import sokoban.Direction;

/**
	* ControleurClavier est une classe qui herite de la classe KeyAdapter
*/
public class ControleurClavier extends KeyAdapter{
	public GamePlayer player;
	public Plateau plateau;
	
	public ControleurClavier(GamePlayer player){
		this.player = player;
		this.plateau = player.getPlateau();
	}
	
	/**
		* Redefinition de la methode keyPressed modifie la position du joueur dans le plateau
		* @param e
		*	une instance de KeyEvent
	*/
	@Override
	public void keyPressed(KeyEvent e){
		if(this.player.toucheAppuyee == false){
			Direction i = null;
			switch (e.getKeyCode()){
				case KeyEvent.VK_LEFT:
					this.player.toucheDirection = Direction.Gauche;
					break;
				case KeyEvent.VK_RIGHT:
					this.player.toucheDirection = Direction.Droite;
					break;
				case KeyEvent.VK_UP:
					this.player.toucheDirection = Direction.Haut;
					break;
				case KeyEvent.VK_DOWN:
					this.player.toucheDirection = Direction.Bas;
					break;
				default:
					this.player.toucheDirection = null;
					break;
			}
			i = this.player.chooseMove();
			this.plateau.movePlay(i);
			
		}
	}
	
	/**
		* Redefinition de la methode keyReleased modifie l'instance toucheDirection et toucheAppuyee
		* @param e
		*	une instance de KeyEvent
	*/
	@Override
	public void keyReleased(KeyEvent e){
		this.player.toucheDirection = null;
		this.player.toucheAppuyee=false;
	}
}

