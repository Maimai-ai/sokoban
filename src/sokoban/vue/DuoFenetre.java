package sokoban.vue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.util.Observer;
import java.util.Observable;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.*;
import javax.swing.JButton;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.*;
import java.awt.Graphics;

import level.SelectionNiveauGUI;
import sokoban.modele.piece.players.*;
import sokoban.modele.plateau.*;
import sokoban.vue.*;
import sokoban.controleur.*;
import credits.Credits;

/**
	* DuoFenetre est une classe qui herite de la classe JFrame et de l'interface Observer
	* Elle observe les deux Plateau
*/
public class DuoFenetre extends JFrame implements ActionListener,Observer{
	public GamePlayer player1;
	public GamePlayer player2;
	public Plateau plateau1;
	public Plateau plateau2;
	public PlateauView vuejoueur1;
	public PlateauView vuejoueur2;
	public static String unfichier;
	private JButton exit;
	private JButton retour;
	private JButton reset;

	public VueCompteur compt2;
	public VueCompteur compt1;

	private  static int DIM;
	static {
		DIM = 60;
		try{
			DIM=ImageIO.read(new File("images/player.png")).getWidth();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private final static Image imageMur = new ImageIcon("images/mur.png").getImage();
	private final static Image imageCaisse = new ImageIcon("images/bloc.png").getImage();
	private final static Image imagePositionFin = new ImageIcon("images/posfin.png").getImage();
	private final static Image imageJoueur = new ImageIcon("images/player.png").getImage();
	private final static Image imageVide = new ImageIcon("images/vide.png").getImage();

	private final static Image imageMur2 = new ImageIcon("images/mur2.png").getImage();
	private final static Image imageCaisse2 = new ImageIcon("images/bloc2.png").getImage();
	private final static Image imagePositionFin2 = new ImageIcon("images/posfin2.png").getImage();
	private final static Image imageJoueur2 = new ImageIcon("images/player2.png").getImage();

	public DuoFenetre(String unfichier) {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sokoban");

		this.unfichier = unfichier;

		setResizable(false);
		setUndecorated(true);
		/*Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		this.setPreferredSize(new Dimension(height, width));*/
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLayout(new BorderLayout());
		this.player1 = new Player();
		this.player2 = new Player();
		this.plateau1 = new Plateau(this.player1, unfichier);
		this.plateau2 = new Plateau(this.player2, unfichier);

		this.vuejoueur1 = new PlateauView(this.plateau1, imageJoueur, imageMur, imageCaisse, imagePositionFin, imageVide, DIM);
		this.vuejoueur1.setPreferredSize(new Dimension(660,600));
		this.vuejoueur2 = new PlateauView(this.plateau2, imageJoueur2, imageMur2, imageCaisse2, imagePositionFin2, imageVide, DIM);
		this.vuejoueur2.setPreferredSize(new Dimension(660,600));
		this.plateau1.addObserver(this);
		this.plateau2.addObserver(this);

		this.player1.setPlateau(this.plateau1);
		this.player2.setPlateau(this.plateau2);

		ControleurTouche touche = new ControleurTouche(this.player1, this.player2);
		this.vuejoueur1.addKeyListener(touche);
		this.vuejoueur2.addKeyListener(touche);

		/*
		*  LES CONTOURS
		*
		*/
		this.compt1 = new VueCompteur(this.plateau1);
		this.compt2 = new VueCompteur(this.plateau2);

		JPanel nord = new JPanel();
		nord.setPreferredSize(new Dimension(400,70));

		compt1.setOpaque(false);
		compt2.setOpaque(false);

		nord.add(compt1);

		this.exit = new JButton("EXIT");
		this.retour = new JButton("NIVEAUX");
		this.reset = new JButton("RESET");


		this.exit.setBackground(Color.BLACK);
		this.retour.setBackground(Color.BLACK);
		this.reset.setBackground(Color.BLACK);

		this.reset.setForeground(new Color(157,231,122));
		this.exit.setForeground(new Color(255,53,53));
		this.retour.setForeground(new Color(81,168,255));

		this.exit.addActionListener(this);
		this.retour.addActionListener(this);
		this.reset.addActionListener(this);

		nord.add(exit);
		this.exit.setFocusable(false);
		nord.add(retour);
		this.retour.setFocusable(false);

		nord.add(reset);

		nord.add(compt2);
		this.reset.setBounds(40, 100, 90, 30);
		this.reset.setFocusable(false);


		JPanel lesdeuxPlateaux = new JPanel();
		//lesdeuxPlateaux.setPreferredSize(new Dimension(2000, 700));

		lesdeuxPlateaux.add(this.vuejoueur1);
		lesdeuxPlateaux.add(this.vuejoueur2);
		this.vuejoueur1.setOpaque(false);
		this.vuejoueur2.setOpaque(false);

		JPanel ContourJeu = new JPanel(){
			String chemin = "images/backgroundjeu2.png";
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				ImageIcon m = new ImageIcon(chemin);
				Image im = m.getImage();
				g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
			}
		};
		ContourJeu.setLayout(new BorderLayout());
		nord.setOpaque(false);
		lesdeuxPlateaux.setOpaque(false);
		ContourJeu.add(nord, BorderLayout.NORTH);
		ContourJeu.add(lesdeuxPlateaux, BorderLayout.CENTER);
		this.setContentPane(ContourJeu);


		this.pack();
		this.setVisible(true);
	}

	/**
		* Cette methode nous permet de fermer la fenêtre lorsque nous cliquons sur le bouton exit
		* Si nous cliquons sur niveaux, nous retournons à la fenêtre de niveau, contenant les boutons des dix niveaux
		* Enfin lors du clique de reset, cela réinitialise notre jeu
		* @param e
		*	est une instance de ActionEvent
	*/
	public void actionPerformed(ActionEvent e){
		Object appuiesur = e.getSource();
		if (appuiesur == exit){
			new Credits().setVisible(true);
			this.dispose();
		}
		else if (appuiesur == retour){
			boolean tabsolo[] = {false,true,false};
			new SelectionNiveauGUI(tabsolo).setVisible(true);
			this.dispose();
		}
		else if (appuiesur == reset){
			new DuoFenetre(this.unfichier).setVisible(true);
			this.dispose();
		}
	}

	/**
		* Cette méthode nous permet de passer a un niveau suivant
		* @param niveau
		* 		est une String
	*/
	public void passageNivSuiv(String niveau){
		char[] tab = niveau.toCharArray();
		int i = (int) tab[13];
		if(i==57){
			new DuoFenetre("niveau/niveau10.xsb").setVisible(true);
			this.dispose();
		}
		else{
			if(tab.length!=18){
				new Credits().setVisible(true);
				this.dispose();
			}
			else{
				i++;
				char n = (char) i;
				tab[13] = n;
				new DuoFenetre(String.valueOf(tab)).setVisible(true);
				this.dispose();
			}
		}
	}

	/**
		* Redéfinition de la méthode update, qui repaint le plateau des deux joueurs tant qu'aucun des deux aient fini la partie
		* @param o
		*		une instance de Observable
		* @param arg
		*		une instance de Object
	*/
	@Override
	public void update(Observable o, Object arg) {
		Win afficheGagnant = null;
		if(!this.plateau1.isOver() || !this.plateau2.isOver()){
			this.vuejoueur1.repaint();
			this.vuejoueur2.repaint();
		}
		else{
			if(this.plateau1.win() || this.plateau2.win()){
				if(this.plateau1.win() && this.plateau2.win()){
					if(this.player1.getCompteur() < this.player2.getCompteur()){
						this.player1.score++;
						afficheGagnant = new Win(this, "BRAVO" , "Le joueur 1 (gauche) gagne !");
					}
					else if( this.player1.getCompteur() == this.player2.getCompteur()){
						afficheGagnant = new Win(this, "Match nul" , "Aucun gagnant");
					}
					else{
						this.player2.score++;
						afficheGagnant = new Win(this, "BRAVOO" , "Le joueur 2 (droite) gagne !");
					}
				}
				
				else if(this.plateau1.win()){
					this.player1.score++;
					afficheGagnant = new Win(this, "BRAVOOO" , "Le joueur 1 (gauche) gagne ! \n Joueur 2: pris au piege");
				}
				
				else{
					this.player2.score++;
					afficheGagnant = new Win(this, "BRAVOOOOI" , "Le joueur 2 (droite) gagne ! \n Joueur 1: pris au piege");
				}
				
				afficheGagnant.setSize(600,400);
				// passageNivSuiv(unfichier); // passe au niveau suivant <- a revoir supprimé pour l'affichage du gagnant
			}
			
			else{
				new DuoFenetre(this.unfichier).setVisible(true);
				this.dispose();
			}
		}
    }
}
