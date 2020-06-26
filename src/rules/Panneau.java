package rules;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.*;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
	* Panneau est une classe qui herite de JPanel
	* Elle affiche les rêgles de notre jeu
*/
public class Panneau extends JPanel{
	
	public Panneau(){
		super();
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(545,510));
	}
	/**
		* Redéfinition de la methode paintComponent
		* @param g
		* 	Une instance de type Graphics
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int fontSize = 20;

		g.setFont(new Font("Arial", Font.BOLD, fontSize));

		g.setColor(Color.black);
		g.drawString("Vous incarnez une maman panda qui doit",20,155);
		g.drawString("mettre ses petits dans leur nids. Vous pouvez ",20,175);
		g.drawString("vous d\u00E9placez à l'aide quatre touche",20,195);
		g.drawString("directionnelle du clavier. Vous avez le",20,215);
		g.drawString("droit de pousser les petits mais pas de",20,235);
		g.drawString("les tirer. Une fois tous que chaque",20,255);
		g.drawString("b\u00E9b\u00E9 est dans son nid, vous passez ",20,275);
		g.drawString("directement au niveau suivant.",20,295);
		g.drawString("Essayer de finir un niveau avec le moins",20,315);
		g.drawString("de mouvement possible !",20,335);
	}
}
