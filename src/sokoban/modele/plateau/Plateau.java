package sokoban.modele.plateau;

import java.util.List;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import sokoban.modele.piece.*;
import sokoban.modele.piece.players.*;
import sokoban.modele.plateau.PlateauLoader;
import sokoban.Direction;

/**
	* Plateau est classe qui nous permet de creer une grille de Piece et de faire la modification de grille 
*/

public class Plateau extends Observable{
	
	public GamePlayer player;
	private Caisse caisse;
	private Mur mur;
	private PositionFinal posfinal;
	public Piece[][] plateau;
	public boolean[][] boolTab;
	public ArrayList<Caisse> listCaisse;
	public ArrayList<PositionFinal> listPosif;
	public static String unfichier;
	public PlateauLoader chargement;
	public final static int[] nombre ={-1,1};
	public int ligne;
	public int colonne;
	
	public Plateau(GamePlayer player, String unfichier){
		super();
		this.player = player;
		this.chargement = new PlateauLoader(unfichier, player);
		this.listCaisse = this.chargement.listCaisse;
		this.listPosif = this.chargement.listPosif;
		this.plateau = this.chargement.getPlateau();
		this.boolTab = this.chargement.boolTab;
		this.listCaisse = this.chargement.listCaisse;
		this.listPosif = this.chargement.listPosif;
		this.caisse = this.chargement.caisse;
		this.mur = this.chargement.mur;
		this.posfinal = this.chargement.posfinal;
		this.ligne = this.chargement.ligne;
		this.colonne = this.chargement.colonne;
	}
	/**
		* Accesseur de plateau
		* @return un tableau de Piece
	*/
	public Piece[][] getPlateau(){
		return this.plateau;
	}
	
	/**
		* Accesseur de player
		* @return un GamePlayer
	*/
	public GamePlayer getPlayer(){
		return this.player;
	}
	
	/**
		* Methode qui permet de donner un entier à partir de deux autres entiers
		* @param i un entier
		* @param j un entier
		* @return un entier qui correspond au produit du premier entier inserer par le nombre de ligne du plateau, et de l'ajout du second entier
	*/
	public int entiercase(int i, int j){
		return i*(this.ligne)+j;
	}
	
	/**
		* Accesseur du nombre de ligne du plateau
		* @return un entier
	*/
	public int getNbLigne(){
		return this.plateau.length;
	}
	
	/**
		* Accesseur du nombre de colonne du plateau
		* @return un entier
	*/
	public int getNbColonne(){
		return this.plateau[this.ligne].length;
	}
	
	/**
		* Accesseur de la liste de Caisse
		* @return une ArrayList de Caisse
	*/
	public ArrayList<Caisse> getCaisse(){
		return this.listCaisse;
	}
	
	/**
		* Accesseur de la liste de position final
		* @return une ArrayList de PositionFinal
	*/
	public ArrayList<PositionFinal> getPos(){
		return this.listPosif;
	}
	
	/** 
		* @param piece
		*		est une instance de Piece
		* @return une liste de Direction possible pour la piece pris en parametre
	*/
	public ArrayList<Direction> validMoves(Piece piece){
		ArrayList<Direction> list = new ArrayList<Direction>();
		for(Direction d : Direction.values()){
			int newX = piece.getX() + d.getDx();
			int newY = piece.getY() + d.getDy();
			if(piece == this.player){
				if( newX <= this.plateau.length && newX >= 0){
					if( newY <= this.plateau[newX].length && newY >= 0){
						if(this.plateau[newX][newY] == null || this.plateau[newX][newY] == this.posfinal || this.plateau[newX][newY] == this.caisse){
							if(this.plateau[newX][newY] == this.caisse){
								/* Parcours de la liste de caisse, et si l'emplacement d'un de ses caisses est a
								 *  l'emplacement ou le joueur veut aller, alors on recherche si les mouvements
								 * valides de ce caisse contient la direction que nous voulons, si oui, alors on ajoute
								 *  cette direction a notre liste de mouvements possible
								 */
								for(Caisse b : this.listCaisse){
									if(entiercase(b.getX(),b.getY()) == entiercase(newX,newY)){
										if(validMoves(b).contains(d)){
											list.add(d);
										}
									}

								}
							}
							else{
								list.add(d);
							}
						}
					}
				}
			}
			else{
				if( newX <= this.plateau.length && newX >= 0){
					if( newY <= this.plateau[newX].length && newY >= 0){
						if(this.plateau[newX][newY] == null || this.plateau[newX][newY] == this.posfinal){
							list.add(d);
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
		* Methode qui permet de faire la modification dans le plateau lors d'un deplacement du joueur dans la direction pris en parametre
		* @param d
		*		est une instance de Direction
	*/
	public void movePlay(Direction d){
		this.setChanged();
		this.notifyObservers();
		if(validMoves(this.player).contains(d)){
			/* S'il y a un caisse à l'emplacement ou le joueur veut aller, alors le caisse est pousser */
			for(Caisse caisse : this.listCaisse){
				if(validMoves(caisse).contains(d)){
					if(caisse.getX() == (this.player.getX() + d.getDx())){
						if(caisse.getY() == this.player.getY() + d.getDy()){
							this.plateau[caisse.getX()][caisse.getY()] = null;
							int testX = caisse.getX() + d.getDx();
							int testY = caisse.getY() + d.getDy();
							caisse.setX(testX);
							caisse.setY(testY);
							if(this.plateau[testX][testY] == this.posfinal){
								this.plateau[testX][testY] = this.caisse;
								caisse.setEstFinal(true);
								this.boolTab[testX][testY] = false;
							}
							else {
								this.plateau[caisse.getX()][caisse.getY()] = this.caisse;
								caisse.setEstFinal(false);
								this.boolTab[caisse.getX()][caisse.getY()] = true;
							}
						}
					}
				}
			}
			/* Change l'emplacement du joueur, ca place initiale devient nulle et il est a l'emplacement souhaité*/
			this.plateau[this.player.getX()][this.player.getY()]=null;
			this.player.setX(this.player.getX() + d.getDx());
			this.player.setY(this.player.getY() + d.getDy());
			this.plateau[this.player.getX()][this.player.getY()]=this.player;
		}
		/* Permet de savoir si à une position finale, s'il y a un player sur cette position le player sera mis à cette endroit, de même pour un bloc. Sinon il met une position finale */
		for(PositionFinal posifin : this.listPosif){
			if(this.plateau[posifin.getX()][posifin.getY()] == this.player){
				this.plateau[posifin.getX()][posifin.getY()] = this.player;
			}
			else if(this.plateau[posifin.getX()][posifin.getY()] == this.caisse){
				this.plateau[posifin.getX()][posifin.getY()] = this.caisse;
			}
			else{
				this.plateau[posifin.getX()][posifin.getY()] = this.posfinal;
			}
		}
	}
	
	/** 
		* Methode qui permet de regarder dans le plateau et modifie la grille de boolean
		* Si une caisse est sur une position finale alors il renvoie true sinon il renvoie false
	*/
	public void loadBoolTab(){
		for(PositionFinal n : this.listPosif){
			if(this.plateau[n.getX()][n.getY()] == this.caisse){
				this.boolTab[n.getX()][n.getY()] = true;
			}
			else{
				this.boolTab[n.getX()][n.getY()] = false;
			}
		}
	}
	
	/** 
		* Methode qui regarde dans la liste de position finale, et prend son X et son Y et regarde dans la grille de boolean
		* @return un boolean
		*		retourne true si la partie est terminer, sinon il retourne false
	*/
	public boolean isOver(){
		loadBoolTab();
		if(estBloquer()){
			return true;
		}
		for(PositionFinal n : this.listPosif){
			if(this.boolTab[n.getX()][n.getY()] == false){
				return false;
			}
		}
		
		return true;
	}
	
	/**
		* Methode qui permet de savoir si la partie est fini si on a gagner ou parce qu'une caisse est bloquée
		* @return un booléen
	*/
	public boolean win(){
		if(isOver()){
			if(estBloquer()){
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
		* Une méthode qui regarde si une caisse est bloqué ou non
		* @param caisse
		*		une instance de Caisse
		* @return un booleen
	*/
	public boolean peutPasBouger(Caisse caisse){
		if((this.plateau[caisse.getX()-1][caisse.getY()] instanceof Mur )&&(this.plateau[caisse.getX()][caisse.getY()-1] instanceof Mur) && !caisse.getEstFinal()){
			return true;
		}
		if((this.plateau[caisse.getX()][caisse.getY()-1] instanceof Mur)&&(this.plateau[caisse.getX()+1][caisse.getY()] instanceof Mur) && !caisse.getEstFinal()){
			return true;
		}
		if((this.plateau[caisse.getX()+1][caisse.getY()] instanceof Mur)&&(this.plateau[caisse.getX()][caisse.getY()+1] instanceof Mur) && !caisse.getEstFinal()){
			return true;
		}
		if((this.plateau[caisse.getX()-1][caisse.getY()] instanceof Mur)&&(this.plateau[caisse.getX()][caisse.getY()+1] instanceof Mur) && !caisse.getEstFinal()){
			return true;
		}
		return false;
	}
	
	/**
		* Une methode qui regarde si au moins une des caisses est bloqué
		* @return un booleen
	*/
	public boolean estBloquer(){
		for(Caisse caisse : getCaisse()){
			if(peutPasBouger(caisse)){
				return true;
			}
		}
		return false;
	}
	
	/** 
		* Methode qui affiche le plateau
		* @return une string
		*		dans le terminal, qui represente notre plateau a l'etat actuelle
	*/
	public String afficherTerminal(){
		String phrase ="";
		for(int i = 0; i < this.plateau.length; i++){
			for(int j = 0 ; j < this.plateau[i].length; j++){
				if(this.plateau[i][j] == this.mur){
					phrase += '#';
				}
				if(this.plateau[i][j] == this.caisse){
					if(this.boolTab[i][j] == false){
						phrase += '+';
					}
					else{
						phrase += '$';
					}
				}
				if(this.plateau[i][j] == this.posfinal){
					phrase += '.';
				}
				if(this.plateau[i][j] == this.player){
					phrase += '@';
				}
				if(this.plateau[i][j] == null){
					phrase += ' ';
				}
			}
			phrase = phrase + System.lineSeparator();
		}
		return phrase;
	}
	
	/**
		* Methode qui créé une copie de notre plateau à l'état actuel
		* @return une copie du Plateau
	*/
	public Plateau getCopy(){
		Plateau copy =new Plateau(this.player, this.unfichier);
		copy.player=this.player;
		copy.plateau = new Piece[getNbLigne()][getNbColonne()];
		for (int i =0; i<getNbLigne(); i++){
			for (int j =0; j<getNbColonne(); j++){
			copy.plateau[i][j]=this.plateau[i][j];
			}
		}
		return copy;
	}
}
	
