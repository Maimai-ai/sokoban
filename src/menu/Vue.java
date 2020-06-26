package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import rules.Rules;
import soloduo.SoloDuo;
import javax.swing.JLabel;

/** 
	* Vue est une classe qui herite de JFrame et implemente la classe ActionListener
*/
public class Vue extends JFrame implements ActionListener{

	private JButton play;
	private JButton rules;
	
	public Vue(){
		this.setTitle("SOKOBAN");
		this.setLocation(0,0);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.setSize(1400,720);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon fond = new ImageIcon("images/background.png");
		JLabel fondLabel = new JLabel(fond);
		
		Container frame = getContentPane();
		ImageIcon imageNormale = new ImageIcon("images/player.PNG");
		ImageIcon imagePassage = new ImageIcon("images/bloc.PNG");
		ImageIcon imageEnfoncee = new ImageIcon("images/posfin.png");
		
		this.play = new JButton("PLAY", imageNormale); //création du bouton
		this.rules = new JButton("RULES", imageNormale);
		
		this.play.setBackground(Color.GRAY);
		this.rules.setBackground(Color.GRAY);
		
		this.play.setForeground(Color.WHITE);
		this.rules.setForeground(Color.WHITE);
		
		
		this.play.setOpaque(true);
		fondLabel.add(this.play);
		fondLabel.add(this.rules);
		setContentPane(fondLabel);
	   
		play.setPressedIcon(imageEnfoncee);
		play.setRolloverIcon(imagePassage);
		play.setRolloverEnabled(true);

		rules.setPressedIcon(imageEnfoncee);
		rules.setRolloverIcon(imagePassage);
		rules.setRolloverEnabled(true);

		play.addActionListener(this);
		play.setBounds(275,100,175,70);
		
		rules.addActionListener(this);
		rules.setBounds(950,100,175,70);
		
		this.setVisible(true);
	}
	
	/**
		* Une methode qui lors du clique sur un bouton affiche soit une fenêtre de Rules ou bien SoloDuo
		* @param e
		*		Une instance de type ActionEvent
	*/
	public void actionPerformed(ActionEvent e){
		Object appuiesur = e.getSource();
		if (appuiesur == this.rules){
			new Rules().setVisible(true);
		}
		else if(appuiesur == this.play){
			new SoloDuo().setVisible(true);
			this.dispose();
		}
	}
}
  

