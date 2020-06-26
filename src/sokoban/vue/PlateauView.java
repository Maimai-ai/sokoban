package sokoban.vue;

import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.Observer;
import java.util.Observable;

import sokoban.modele.piece.players.*;
import sokoban.modele.piece.*;
import sokoban.modele.plateau.*;
import sokoban.vue.*;

/**
	* PlateauView est une classe qui herite de la classe JPanel et de l'interface Observer
*/
public class PlateauView extends JPanel implements Observer{
	public Plateau lancement;
	public boolean[][] boolTab;
	public GamePlayer joueur; 
	public Piece[][] plateau;
	private int DIM;
	private Image imageCaisse;
	private Image imagePositionFin;
	private Image imageJoueur;
	private Image imageVide;
	private Image imageMur;
	public String unfichier;
	
	public PlateauView(Plateau lancement, Image imageJoueur, Image imageMur, Image imageCaisse, Image imagePositionFin, Image imageVide, int DIM){
		super();
		
		this.DIM = DIM;
		this.imageMur = imageMur;
		this.imageCaisse = imageCaisse;
		this.imagePositionFin = imagePositionFin;
		this.imageJoueur = imageJoueur;
		this.imageVide= imageVide;
		
		this.lancement = lancement;
		this.boolTab = this.lancement.boolTab;
		this.joueur = this.lancement.getPlayer();
		this.plateau = this.lancement.getPlateau();
		this.joueur.setPlateau(this.lancement);
		this.setFocusable(true);
	}
	
	/**
		* Redefinition de la methode paintComponent, et affiche une image qui correspond a l'instance dans la plateau
		* @param g
		*		instance de Graphics
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < this.plateau.length; i++){
			for(int j = 0; j < this.plateau[i].length; j++){
				Image image=null;
				if(this.plateau[i][j] instanceof Mur){
					image=this.imageMur;
				}
				else if(this.plateau[i][j] instanceof Caisse){
					image=this.imageCaisse;
				}
				else if(this.plateau[i][j] == this.joueur){
					image=this.imageJoueur;
				}
				else if(this.plateau[i][j] instanceof PositionFinal){
					image=this.imagePositionFin;
				}
				else if(this.plateau[i][j] == null){						
					image=this.imageVide;
				}
				g.drawImage(image, j*DIM,i*DIM, this);
			}
		}
	}
	
	/**
		* Redefinition de la methode update qui repaint le PLateauView
		* @param o
		*		une instance de Observable
		* @param obj
		*		une instance de Object
	*/
	@Override
	public void update(Observable o, Object obj){
		this.repaint();
	}
}
