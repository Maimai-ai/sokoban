package credits;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;

/**
	* Credits est une classe qui herite de la classe JFrame
*/
public class Credits extends JFrame{
	public boolean tableauchoix[];
	
	public Credits(){
		this.setTitle("CREDITS !");
		//Définit sa taille 
		this.setSize(545,510);
		setResizable(false);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel cp = new JPanel(){
			String chemin = "images/backgroundjeu.png";
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				int fontSize = 20;

				g.setFont(new Font("TimesRoman", Font.BOLD, 12));

				g.setColor(Color.black);
				g.drawString("Copyright 2019, A. ZINEB, M. BOUDET, L. LEPETIT, T. YANG",20,450);
			}
		};
		
		ImageIcon fond = new ImageIcon("images/background9.png");
		JPanel fondLabel = new JPanel(){
			protected void paintComponent(Graphics g){
				Image im = fond.getImage();
				g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
			}
		};
        fondLabel.add(cp);
		
		cp.setOpaque(false);
		cp.setPreferredSize(new Dimension(545,510));
		this.setContentPane(fondLabel);
		
		this.setVisible(true);
	}       
}
	
	
	
	
	
	


