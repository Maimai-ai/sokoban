package sokoban.modele.plateau;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import sokoban.modele.piece.*;
import sokoban.modele.piece.players.*;
import sokoban.modele.plateau.PlateauLoader;
import sokoban.Direction;

/**
	* PlateauLoader est une classe qui charge un plateau a partir d'un fichier 
*/

public class PlateauLoader{
	public char[][] tab;
	public int ligne;
	public int colonne;
	public GamePlayer player;
	public Caisse caisse;
	public Mur mur;
	public PositionFinal posfinal;
	public Piece[][] plateau;
	public boolean[][] boolTab;
	public ArrayList<Caisse> listCaisse = new ArrayList<Caisse>();
	public ArrayList<PositionFinal> listPosif = new ArrayList<PositionFinal>();
	public static String unfichier;
	
	public PlateauLoader(String unfichier, GamePlayer player){
		this.player = player;
		this.caisse = new Caisse();
		this.mur = new Mur();
		this.posfinal = new PositionFinal();
		this.ligne = ligne;
		this.colonne = colonne;
		chargerFichier(unfichier);	
	}
	
	/**
		* Methode qui charge un fichier
		* @param string qui est une chaine de caractère et correspond a un niveau
	*/
	public void chargerFichier(String string){
		try{
			chargerPlateau(new FileReader(string));
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
	}
	
	/**
		* Accesseur de plateau
		* @return un tableau de Piece
	*/
	public Piece[][] getPlateau(){
		return this.plateau;
	}
	
	/**
		* Methode qui permet de charger le plateau, il cree petit a petit notre plateau en parcourant notre fichier
		* @param r qui est une instance de Reader
	*/
	public void chargerPlateau(Reader r) {
        try {
            BufferedReader br = new BufferedReader(r);
            String nextLine = br.readLine();
            List<String> toutesLesLignes = new ArrayList<>();
            this.ligne = -1;
            this.colonne = 0;
			this.mur = new Mur();
			this.caisse = new Caisse();
			this.posfinal = new PositionFinal();
			
            while(nextLine != null){
				toutesLesLignes.add(nextLine);
				String ligne = nextLine;
				this.colonne = Math.max(this.colonne,ligne.length()-1);
				this.ligne=this.ligne+1;
				nextLine = br.readLine();
			}
			this.tab = new char[(this.ligne)+1][(this.colonne)+1];
			this.plateau = new Piece[(this.ligne)+1][(this.colonne)+1];
			this.boolTab = new boolean[(this.ligne)+1][(this.colonne)+1];

			// Creation d'un plateau d'objet
			for(int i = 0; i<toutesLesLignes.size(); i++){ // Pour chaque mot de la liste
				String mot = toutesLesLignes.get(i);
				for(int j = 0; j<mot.length();j++){ //ajout des caractère du mot
					this.tab[i][j] = mot.charAt(j);
					switch (mot.charAt(j)){
						case '@':
							this.plateau[i][j] = this.player;
							this.boolTab[i][j] = true;
							this.player.setX(i);
							this.player.setY(j);
							break;
						case '#':
							this.plateau[i][j] = this.mur;
							this.boolTab[i][j] = true;
							break;
						case '$':
							this.plateau[i][j] = this.caisse;
							this.boolTab[i][j] = true;
							Caisse n = new Caisse(i,j,false);
							n.setX(i);
							n.setY(j);
							this.listCaisse.add(n);
							break;
						case '*':
							this.plateau[i][j] = this.caisse;
							this.boolTab[i][j] = true;
							Caisse k = new Caisse(i,j,true);
							k.setX(i);
							k.setY(j);
							this.listCaisse.add(k);
							break;
						case '.':
							this.plateau[i][j] = this.posfinal;
							this.boolTab[i][j] = false;
							PositionFinal g = new PositionFinal(i,j);
							g.setX(i);
							g.setY(j);
							this.listPosif.add(g);
							break;
						default:
							this.plateau[i][j] = null;
							break;
					}
				}
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
}
