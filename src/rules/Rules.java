package rules;

import javax.swing.*;
import java.awt.*;

/**
	* Rules est une classe qui herite de JFrame
	* Elle affiche une fenpetre et appel un Panneau
*/
public class Rules extends JFrame{
	
    private JPanel container = new JPanel();
	
    public Rules(){
		this.setTitle("RULES !");
		//Définit sa taille 
		this.setSize(650,510);
		//Nous demandons maintenant à notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
    
		Container frame = getContentPane();
        //rendre la fenetre visible 
        
		Panneau jp = new Panneau();
        ImageIcon fond = new ImageIcon("images/backgroundjeu2.png");
		JPanel fondLabel = new JPanel(){
			protected void paintComponent(Graphics g){
				Image im = fond.getImage();
				g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
			}
		};
        fondLabel.add(jp);
		
		this.setContentPane(fondLabel);
		this.setVisible(true);
	}       
}
