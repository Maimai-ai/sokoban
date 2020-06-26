package sokoban.vue;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

import sokoban.modele.*;
import menu.*;
import credits.*;
/**
	* Win est un JDialog qui implemente l'interface ActionListener et Runnable
	* Elle permet d'afficher une fenetre de dialogue
*/
public class Win extends JDialog implements ActionListener, Runnable {
	private JButton exit;
	private JButton continuer;
	public JFrame parent;
	
	/**
		* Constructeur vide de la classe Win, utiliser pour le Thread
	*/
	public Win(){
	}
	
	/**
		* Second contructeur de la classe, elle affiche les boutons et image de la classe
		* @param parent une JFrame
		* @param titre une String
		* @param message une String
	*/
	public Win(JFrame parent, String titre, String message) {
		
		this.parent = parent;
		this.setLocation(this.parent.getWidth()/4,this.parent.getHeight()/4);
		this.setUndecorated(true);
        JPanel placementDuMessage = new JPanel();
        placementDuMessage.add(new JLabel(message));
        placementDuMessage.setOpaque(true);
        
        JPanel contour = new JPanel();
        
        contour.setLayout(new BorderLayout());
        contour.setOpaque(false);
        contour.add(placementDuMessage, BorderLayout.NORTH);
        
        JPanel placementBoutons = new JPanel();
        placementBoutons.setOpaque(false);
        
        //this.continuer = new JButton("Continuer");
        this.continuer = new JButton("Menu");
        this.exit = new JButton("Quitter");
        
        placementBoutons.add(this.continuer);
        placementBoutons.add(this.exit);
        
        //Couleur de fond des boutons
		this.continuer.setBackground(Color.BLACK);
		this.exit.setBackground(Color.BLACK);
		
		//Couleur texte des boutons
		this.continuer.setForeground(new Color(255,53,53));
		this.exit.setForeground(new Color(255,53,53));
        
        this.continuer.addActionListener(this);
        this.exit.addActionListener(this);
        
        contour.add(placementBoutons, BorderLayout.PAGE_END); // en bas
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //ferme le dialogue
        
        JPanel fond = new JPanel(){
			String chemin = "images/bravo.png";
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				ImageIcon m = new ImageIcon(chemin);
				Image im = m.getImage();
				g.drawImage(im,100,100,400,250,this);

			} 
		};
		
		fond.setLayout(new FlowLayout());
		fond.add(contour);
        
        this.setContentPane(fond);
        
        this.pack();
        this.setVisible(true);
	}
	
    
    /**
		* Methode qui fait l'action lors du clic sur un bouton comme exit ou reset
		* @param e 
		* 		une instance de ActionEvent
	*/
	public void actionPerformed(ActionEvent e){
		Object appuiesur = e.getSource();
		if (appuiesur == this.exit){
			this.parent.dispose();
			Thread test = new Thread(this);
			test.start();
		}
		if(appuiesur == this.continuer){
			//this.dispose();
			//this.parent.passageNiveauSuivant... impossible
			//tentative infructueuse pour passer au niveau suivant... remplacement du bouton continuer qui va finalement vers le menu
			this.parent.dispose();
			
			new Vue();
			
		}
	}
	
	/**
		* Redefinition de la methode run qui nous permet de fermer la fenetre Credits au bout de 10 sec
	*/
	@Override
	public void run(){
		int milli = 1000;
		int decompt = 5; //5 sec d'affichage
		Credits cred = new Credits();
		for(int i = 0; i < decompt; i++){
			try{
				Thread.sleep(milli);
			}
			catch(InterruptedException e){
				System.out.println("Interruption du Thread");
			}
		}
		cred.dispose();
		System.exit(0);
	}
}
