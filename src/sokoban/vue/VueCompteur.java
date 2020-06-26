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
	* VueCompteur est une classe qui herite de JPanel et implemente l'interface Observer
	* Cette classe nous permet de créer un affichage du compteur de pas d'un joueur
*/
public class VueCompteur extends JPanel implements Observer{
	public Plateau plateau;
	public int compteur;
	
	public VueCompteur(Plateau plateau){
		super();
		this.plateau = plateau;

		this.compteur = 0;
		this.setPreferredSize(new Dimension(100,100));
		this.plateau.addObserver(this);

	}

	/**
		* Cette methode nous permet l'affichage en String du compteur
		* @param g
		*		est une instance de Graphics
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("pas : "+String.valueOf(this.compteur),40,55);
	}

	/**
		* Methode qui repeint notre JPanel à chaque fois que le joueur fera un mouvement
		* @param o
		*		une instance de Observable
		* @param obj
		*		une instance de Object
	*/
	@Override
	public void update(Observable o, Object obj){
		this.compteur = this.plateau.getPlayer().getCompteur();
		this.repaint();
	}
}
