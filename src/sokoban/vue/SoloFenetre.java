package sokoban.vue;

import javax.swing.JButton;
import java.util.ArrayList; 
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.Observer;
import java.util.Observable;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.*;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import sokoban.modele.piece.players.*;
import sokoban.modele.plateau.*;
import sokoban.vue.*;
import level.SelectionNiveauGUI;
import sokoban.controleur.*;
import credits.Credits;

/**
	* SoloFenetre est une classe qui herite de la classe JPanel et de l'interface Observer et ActionListener
*/
public class SoloFenetre extends JFrame implements ActionListener,Observer{
	public GamePlayer player = new Player();
    public Plateau plateau;
	public PlateauView vuehumain;
	public static String unfichier;
	public int deplacements;
	
	public VueCompteur compt;
	
	private JButton exit;
	private JButton niveaux;
	private JButton reset;

	private  static int DIM;
	static {
		DIM = 60;
		try{
			DIM=ImageIO.read(new File("images/player60.png")).getWidth();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	private final static Image imageMur = new ImageIcon("images/mur60.png").getImage();
	private final static Image imageCaisse = new ImageIcon("images/bloc60.png").getImage();
	private final static Image imagePositionFin = new ImageIcon("images/posfin60.png").getImage();
	private final static Image imageJoueur = new ImageIcon("images/player60.png").getImage();
	private final static Image imageVide = new ImageIcon("images/vide.png").getImage();

	public SoloFenetre(String unfichier) {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sokoban");
		
		this.unfichier = unfichier;
		
		setResizable(false);
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setLayout(new BorderLayout());
		this.plateau = new Plateau(this.player, this.unfichier);
		this.vuehumain = new PlateauView(this.plateau, imageJoueur, imageMur, imageCaisse, imagePositionFin, imageVide, DIM);
		
		this.vuehumain.setPreferredSize(new Dimension(1200,660));
		this.vuehumain.setFocusable(true);
		this.vuehumain.requestFocus();
		this.plateau.addObserver(this);
		this.player.setPlateau(this.plateau);
		
		this.compt = new VueCompteur(this.plateau);
		
		this.exit = new JButton("EXIT");
		this.niveaux = new JButton("NIVEAUX");
		this.reset = new JButton("RESET");
		
		this.exit.setBackground(Color.BLACK);
		this.niveaux.setBackground(Color.BLACK);
		this.reset.setBackground(Color.BLACK);
		
		this.reset.setForeground(new Color(157,231,122));
		this.exit.setForeground(new Color(255,53,53));
		this.niveaux.setForeground(new Color(81,168,255));
		
		compt.setOpaque(false);
		
		//Bouton retour level. Voir pour image bouton.
		
		this.exit.addActionListener(this);
		this.niveaux.addActionListener(this);
		this.reset.addActionListener(this);
		
		JPanel nord = new JPanel();
		nord.setPreferredSize(new Dimension(400,70));
		
		nord.add(exit);
		this.exit.setFocusable(false);
		
		nord.add(niveaux);
		this.niveaux.setFocusable(false);
		
		nord.add(reset);
		this.reset.setFocusable(false);
		
		nord.add(this.compt);
		
		ControleurClavier leclavier = new ControleurClavier(this.player);
		this.vuehumain.addKeyListener(leclavier);		
		
		/*  LES CONTOURS
		*/
		

		JPanel LePlateaux = new JPanel();

		LePlateaux.add(this.vuehumain);
		
		this.vuehumain.setOpaque(false);
		
		JPanel ContourJeu = new JPanel(){
			String chemin = "images/background9.png";
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				ImageIcon m = new ImageIcon(chemin);
				Image im = m.getImage();
				g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
			} 
		};
		
		ContourJeu.setLayout(new BorderLayout());
		nord.setOpaque(false);
		LePlateaux.setOpaque(false);
		
		ContourJeu.add(LePlateaux, BorderLayout.CENTER);
		
		ContourJeu.add(nord, BorderLayout.NORTH);
		this.setContentPane(ContourJeu);
		

		this.pack();
		this.setVisible(true);
    }
    
	/**
		* Methode qui fait l'action lors du clique sur un bouton
	*/
	public void actionPerformed(ActionEvent e){
		Object appuiesur = e.getSource();
		if (appuiesur == exit){
			new Credits().setVisible(true);
			this.dispose();
		}
		else if (appuiesur == niveaux){
			boolean tabsolo[] = {true,false,false};
			new SelectionNiveauGUI(tabsolo).setVisible(true);
			this.dispose();
		}
		else if (appuiesur == reset){
			new SoloFenetre(this.unfichier).setVisible(true);
			this.dispose();
		}
	}
    
	/**
		* Methode qui passe d'un niveau à un autre 
		* @param niveau
		*		une string du niveau
	*/	
    public void passageNivSuiv(String niveau){
		char[] tab = niveau.toCharArray();
		int i = (int) tab[13];
		if(i==57){
			new SoloFenetre("niveau/niveau10.xsb").setVisible(true);
			this.dispose();
		}else{
			if(tab.length!=18){
				new Credits().setVisible(true);
				this.dispose();
			}
			else{
				i++;
				char n = (char) i;
				tab[13] = n;
				new SoloFenetre(String.valueOf(tab)).setVisible(true);
				this.dispose();
			}
		}
	}

    /**
		* Redefinition de la methode update, si le plateau n'est pas terminer il repaint la vue du plateau sinon on passe au niveau suivant
		* @param o
		*		une instance de Observable
		* @param arg
		*		une instance de Object
	*/
    @Override
	public void update(Observable o, Object arg) {
        if(!this.plateau.isOver()){
            this.vuehumain.repaint();
        }
        else{
			if(this.plateau.win()){
				passageNivSuiv(unfichier);
			}
			else{
				new SoloFenetre(this.unfichier).setVisible(true);
				this.dispose();
			}
        }
    }
}
