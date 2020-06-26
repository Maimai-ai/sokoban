package sokoban.controleur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
//import sokoban.modele.piece.*;
import sokoban.modele.piece.players.*;
import sokoban.modele.plateau.*;
import sokoban.Direction;

/**
	* ControleurTouche est une classe qui herite de la classe KeyAdapter
*/
public class ControleurTouche extends KeyAdapter{
	public GamePlayer player;
	public GamePlayer player2;
	public Plateau plateau;
	public Plateau plateau2;
	
	public ControleurTouche(GamePlayer player, GamePlayer player2){
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
		if(this.player.toucheAppuyee == false || this.player2.toucheAppuyee == false){
			Direction i = null;
			Direction j = null;
			if(e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_LEFT){
				if( e.getKeyCode() == KeyEvent.VK_Q){
					this.player.toucheDirection = Direction.Gauche;
					i = this.player.chooseMove();
				}
				else if( e.getKeyCode() == KeyEvent.VK_LEFT){
					this.player2.toucheDirection = Direction.Gauche;
					j = this.player2.chooseMove();
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
				if( e.getKeyCode() == KeyEvent.VK_D){
					this.player.toucheDirection = Direction.Droite;
					i = this.player.chooseMove();
				}
				else if( e.getKeyCode() == KeyEvent.VK_RIGHT){
					this.player2.toucheDirection = Direction.Droite;
					j = this.player2.chooseMove();
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_UP){
				if( e.getKeyCode() == KeyEvent.VK_Z){
					this.player.toucheDirection = Direction.Haut;
					i = this.player.chooseMove();
				}
				else if( e.getKeyCode() == KeyEvent.VK_UP){
					this.player2.toucheDirection = Direction.Haut;
					j = this.player2.chooseMove();
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
				if( e.getKeyCode() == KeyEvent.VK_S){
					this.player.toucheDirection = Direction.Bas;
					i = this.player.chooseMove();
				}
				else if( e.getKeyCode() == KeyEvent.VK_DOWN){
					this.player2.toucheDirection = Direction.Bas;
					j = this.player2.chooseMove();
				}
			}
			
			else{
				this.player.toucheDirection = null;
				this.player2.toucheDirection = null;
			}
			this.plateau.movePlay(i);
			this.plateau2.movePlay(j);
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
		this.player2.toucheDirection = null;
		this.player2.toucheAppuyee=false;
	}
}

