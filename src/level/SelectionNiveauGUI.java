package level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; 

import menu.Vue;
import sokoban.vue.*;

/**
	* SelectionNiveauGUI est une classe qui herite de la classe JFrame et de l'interface ActionListener
*/
public class SelectionNiveauGUI extends JFrame implements ActionListener{
	
	private boolean tableauchoix[];
	ImageIcon imageNormale = new ImageIcon("images/player.PNG");
    ImageIcon imagePassage = new ImageIcon("images/bloc.PNG");
    ImageIcon imageEnfoncee = new ImageIcon("images/posfin.png");
	
	private JLabel label = new JLabel("NIVEAUX :");

	JButton menu;
	
	ArrayList<JButton> bouton;
    ArrayList<String> levels;
	public SelectionNiveauGUI(boolean tableauchoix[]){
		super();
		this.tableauchoix = tableauchoix;
		
		this.bouton = new ArrayList<JButton>(); 
		this.levels = new ArrayList<String>();
		
		for (int i=1;i<=10;i++){
			String niveaux ="niveau/niveau";
			niveaux +=Integer.toString(i) + ".xsb";
			levels.add(niveaux);
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Level");
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		
		this.setResizable(false);
		
		this.setAlwaysOnTop(false);
		JPanel top = new JPanel();
		Font font = new Font("Arial",Font.BOLD,40);
		label.setFont(font);
		top.add(label);
		top.setOpaque(false);
		JPanel cp = new JPanel(){
			String chemin = "images/backgroundjeu.png";
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				ImageIcon m = new ImageIcon(chemin);
				Image im = m.getImage();
				g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
			}
		};
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.add(cp);
		cp.add(top, BorderLayout.WEST);

		cp.setLayout(new GridLayout(6, 2,2,2));
		Font f = new Font("Arial",Font.BOLD,25);
		for (int j=1; j<=10; j++){
			String bout = "niveau ";
			bout += Integer.toString(j);
			JButton boutonNiv = new JButton(bout,imageNormale);
			boutonNiv.setFont(f);
			boutonNiv.setIcon(imageNormale);
			boutonNiv.setBorderPainted(false);
			boutonNiv.setPressedIcon(imageEnfoncee);
			boutonNiv.setBackground(Color.GRAY);
			boutonNiv.setRolloverEnabled(true);
			boutonNiv.setRolloverIcon(imagePassage);
			bouton.add(boutonNiv);
			boutonNiv.setOpaque(false);
			cp.add(boutonNiv);
			boutonNiv.addActionListener(this);	
		}	
		this.menu = new JButton("Menu");
		this.menu.setOpaque(false);
		this.menu.setBorderPainted(false);
		this.menu.setFont(font);
		cp.add(this.menu);
		this.menu.setBackground(Color.GRAY);
		
		this.menu.addActionListener(this);
		this.setVisible(true);
	}
	
	/**
		* Methode qui lors du clique sur un bouton de niveau, affiche le fichier (= la planche XSB) qui lui correspond
		* @param e
		*		une instance de ActionEvent
	*/
	public void actionPerformed(ActionEvent e){
		int cp =0;
		Object appuiesur = e.getSource();
		for(JButton niv : this.bouton ){
			if (niv == appuiesur){
				if (this.tableauchoix[0] == true) {
					SoloFenetre fen = new SoloFenetre(this.levels.get(cp));
				}
				else if(this.tableauchoix[1] == true) {
					DuoFenetre fenetre = new DuoFenetre(this.levels.get(cp));
				}
				else {
					FenetreIA fenetre = new FenetreIA(this.levels.get(cp));
				}
				this.dispose();
			}
			else{
				cp++;
			}
		}
		if (appuiesur == this.menu){
			new Vue().setVisible(true);
			this.dispose();
			
		}
		setVisible(false);
	}
}
