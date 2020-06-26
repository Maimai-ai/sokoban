package sokoban.controleur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import sokoban.modele.piece.players.*;
import sokoban.modele.plateau.*;
import sokoban.Direction;

/**
	* ControleurTouche est une classe qui herite de la classe KeyAdapter
*/
public class ControleurToucheIA extends KeyAdapter{
	public GamePlayer player;
	public GamePlayer player2;
	public Plateau plateau;
	public Plateau plateau2;
	
	public ControleurToucheIA(GamePlayer player, GamePlayer player2){
		this.player = player;
		this.player2 = player2;
		this.plateau = player.getPlateau();
		this.plateau2 = player2.getPlateau();
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
			this.plateau2.movePlay(this.player2.chooseMove());
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

