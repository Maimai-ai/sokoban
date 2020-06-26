package soloduo;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;

import menu.Vue;
import level.SelectionNiveauGUI;
import sokoban.vue.*;

/**
	* SoloDuo est une classe qui hérite de JFrame et implémente ActionListener
	* Elle affiche une fenêtre contenant un seul Plateau
*/
public class SoloDuo extends JFrame implements ActionListener{
	private boolean tableauchoix[] = {false,false,false};
	public JButton solo;
	public JButton duo;
	public JButton IA;
	public JButton menu;
	
	public SoloDuo(){
		this.setTitle("SOKOBAN");
		this.setLocationRelativeTo(null);
		this.setLocation(0,0);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.setSize(1400,720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container frame = getContentPane();


		ImageIcon fond = new ImageIcon("images/backgroundmenu.png");
		JLabel fondLabel = new JLabel(fond);
		
		ImageIcon imageNormale = new ImageIcon("images/player.PNG");
		ImageIcon imagePassage = new ImageIcon("images/bloc.PNG");
		ImageIcon imageEnfoncee = new ImageIcon("images/posfin.png");
		//création des bouton
    
		this.solo = new JButton("un joueur", imageNormale);
		this.duo = new JButton("deux joueur", imageNormale);
		this.IA = new JButton("IA",imageNormale);
		this.menu = new JButton("retour au menu",imageNormale);
		
		this.solo.setBackground(Color.BLACK);
		this.duo.setBackground(Color.BLACK);
		this.IA.setBackground(Color.BLACK);
		this.menu.setBackground(Color.BLACK);
		
		this.solo.setForeground(Color.WHITE);
		this.duo.setForeground(Color.WHITE);
		this.IA.setForeground(Color.WHITE);
		this.menu.setForeground(Color.WHITE);

		fondLabel.add(this.solo);
		fondLabel.add(this.duo);
		fondLabel.add(this.IA);
		fondLabel.add(this.menu);
		
		setContentPane(fondLabel);
    
		this.solo.setPressedIcon(imageEnfoncee);
		this.solo.setRolloverIcon(imagePassage);
		this.solo.setRolloverEnabled(true);

		this.duo.setPressedIcon(imageEnfoncee);
		this.duo.setRolloverIcon(imagePassage);
		this.duo.setRolloverEnabled(true);

		this.IA.setPressedIcon(imageEnfoncee);
		this.IA.setRolloverIcon(imagePassage);
		this.IA.setRolloverEnabled(true);

		this.menu.setPressedIcon(imageEnfoncee);
		this.menu.setRolloverIcon(imagePassage);
		this.menu.setRolloverEnabled(true);

		this.solo.addActionListener(this);
		this.solo.setBounds(200,90,175,70);
		
		this.duo.addActionListener(this);
		this.duo.setBounds(475,90,175,70);
		
		this.IA.setBounds(750,90,175,70);
		this.IA.addActionListener(this);
		
		this.menu.setBounds(1025,90,175,70);
		this.menu.addActionListener(this);
		
    
		this.setVisible(true);
	}
	
	/**
		* Lors du sur l'un des boutons, il ouvre une fenêtre de SelectionNiveauGUI
		* et en fonction du bouton, il modifie un booléen de tableauchoix
		* @param e
		* 		 instance de ActionEvent
	*/
	public void actionPerformed(ActionEvent e){
		Object appuiesur = e.getSource();
		if (appuiesur == this.solo){
			this.tableauchoix[0]=true;
			new SelectionNiveauGUI(this.tableauchoix).setVisible(true);
		}
		if (appuiesur == this.duo){
			this.tableauchoix[1]=true;
			new SelectionNiveauGUI(this.tableauchoix).setVisible(true);
		}
		if (appuiesur == this.IA){
			this.tableauchoix[2]=true;
			new SelectionNiveauGUI(this.tableauchoix).setVisible(true);
		}
		if (appuiesur == this.menu){
			new Vue().setVisible(true);
			
		}
		this.dispose();
		setVisible(false);
	}
}
