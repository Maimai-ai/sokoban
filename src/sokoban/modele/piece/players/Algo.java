package sokoban.modele.piece.players;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import sokoban.*;
import sokoban.modele.plateau.*;
import sokoban.modele.piece.*;

/**
	* Algorithm est une classe qui herite de GamePlayer
*/

public class Algo extends GamePlayer{
	public int compteur;
	public Plateau plt;
	public int score = 0;
	
	private String name;
	private int g = 1;
	
	private ArrayList<Noeud> openList;
	private ArrayList<Noeud> closeList;
	public Plateau plateau;
	
	public Algo(){
		this.name = "Algo #"+this.hashCode();
		this.openList = new ArrayList<Noeud>();
		this.closeList = new ArrayList<Noeud>();
		this.compteur = 0;
	}
	
	public Algo(Plateau plateau){
		super(plateau);
		this.compteur = 0;
	}
	
	/**
		* Mutateur de plateau
	*/
	
	public void setPlateau(Plateau plt){
		this.plateau = plt;
	}
	
	/**
		* Methode qui permet de faire la distance de Manhattan entre deux pieces
		* @param premier une instance de Piece
		* @param second une instance de Piece
		* @return un entier
	*/
	
	public int distManhattan(Piece premier, Piece second){
		int a = Math.abs(premier.getX() - second.getX());
		int b = Math.abs(premier.getY() - second.getY());
		return  a + b ;
	} 
	
	/**
		* Accesseur de score
		* @return un entier
	*/
	public int getScore(){
		return this.score;
	}
	
	/**
		* Methode qui permet de calculer la distance des caisses par rapport au position final
		* @param plateau un instance de Plateau
		* @return un entier
	*/
	
	public int sommeDistCaisse(Plateau plateau){
		int somme = 0;
		plateau.loadBoolTab();
		for(Caisse caisse : plateau.getCaisse()){
			if(!caisse.getEstFinal()){
				int res = Integer.MAX_VALUE;
				for(PositionFinal pos : plateau.getPos()){
					if(!pos.getEstDessus()){
						// System.out.println(pos);
						int h = distManhattan(caisse, pos);
						if(h < res){
							res = h;
						}
					}
				}
				somme += res;
			}
		}
		return somme;
	}
	
	/**
		* Accesseur de la openList
		* @return une ArrayList de Noeud
	*/
	public ArrayList<Noeud> getListOpen(){
		return this.openList;
	}
	
	/**
		* Accesseur de la closeList
		* @return une ArrayList de Noeud
	*/
	public ArrayList<Noeud> getListClose(){
		return this.closeList;
	}
	
	/**
		* Methode qui ajoute les noeuds explorés dans la liste prit en parametre
		* @param plat
		*		Une instance de Plateau
		* @param opListe
		*		Une ArrayList de Noeud
		* @return une ArrayList de Noeud
	*/
	public ArrayList<Noeud> addOpenListe(Plateau plat, ArrayList<Noeud> opListe){
		GamePlayer player = plat.getPlayer();
		Caisse lacaisse = new Caisse();
		Piece[][] grille = plat.getPlateau();
		
		int res = Integer.MAX_VALUE;
		for(Caisse caisse : plat.getCaisse()){
			if(!caisse.getEstFinal()){
				int dist = distManhattan(player,caisse);
				if(dist < res){
					res = dist;
					lacaisse = caisse;
				}
			}
		}
		
		for(Direction direction_joueur : player.moveValid(plat)){
			Piece joueur = new Piece(player.getX(), player.getY());
			if(grille[joueur.getX()+direction_joueur.getDx()][joueur.getY()+direction_joueur.getDy()] instanceof Caisse){ // ICI A MODIFIER, POUR QU'IL REGARDE SI LA CAISSE EST BOUGEABLE DANS SA POSITION ACTUELLE
				
				Piece noeudExplore = new Piece(joueur.getX()+direction_joueur.getDx(), joueur.getY()+direction_joueur.getDy());
				
				int distMan = distManhattan(noeudExplore, lacaisse);
				int h = distMan + sommeDistCaisse(plat);
				int f = this.g + h;
			
				Noeud noeud = new Noeud(noeudExplore, f, joueur);

				int trouve = -1;
				for(Noeud node : opListe){
					if(noeudExplore.getX() == node.getPiece().getX() && noeudExplore.getY() == node.getPiece().getY()){
						if(f <= node.getCoutTotal()){
							node.setCoutTotal(f);
							node.setPredecesseur(joueur);
						}
						trouve = 1;
					}
				}
				if(trouve == -1){
					opListe.add(noeud);
				}
			}
		}
		
		for(Direction dir : plat.validMoves(lacaisse)){
			Piece autour_caisse = new Piece(lacaisse.getX()+dir.getDx(), lacaisse.getY()+dir.getDy());
			
			int dx = lacaisse.getX() - autour_caisse.getX();
			int dy = lacaisse.getY() - autour_caisse.getY();
			
			Direction direction = null;
			for(Direction cherche : Direction.values()){
				if(cherche.getDx() == dx && cherche.getDy() == dy){
					direction = cherche;
				}
			}
			//Est bougeable dans la direction inverse
			if(lacaisse.estBougeable(plat,direction)){
				Piece joueur = new Piece(player.getX(), player.getY());
				
				for(Direction direct : player.moveValid(plat)){
					Piece noeudExpl = new Piece(joueur.getX()+direct.getDx(), joueur.getY()+direct.getDy());
					int distMan = distManhattan(noeudExpl, lacaisse);
					int h = distMan + sommeDistCaisse(plat);
					int f = this.g + h;
					
					Noeud noeud = new Noeud(noeudExpl, f, joueur);
	
					int trouve = -1;
					for(Noeud node : opListe){
						if(noeudExpl.getX() == node.getPiece().getX() && noeudExpl.getY() == node.getPiece().getY()){
							if(f <= node.getCoutTotal()){
								node.setCoutTotal(f);
								node.setPredecesseur(joueur);
							}
							trouve = 1;
						}
					}
					if(trouve == -1){
						opListe.add(noeud);
					}
				}
			}
		}
		return opListe;
	}
	
		/**
		* Methode qui ajoute le noeud le moins couteux dans la cloList en fonction de la opList
		* @param opListe
		*		Une ArrayList de Noeud
		* @param cloList
		*		Une ArrayList de Noeud
		* @return une ArrayList de Noeud
	*/
	public ArrayList<Noeud> addCloseListe(ArrayList<Noeud> opListe, ArrayList<Noeud> cloList){
		Noeud essaie = new Noeud(null, Integer.MAX_VALUE, null);
		ArrayList<Noeud> listclose = new ArrayList<Noeud>(cloList);
		boolean fin = false;
		int taille = cloList.size();
		int comp = 0;
		boolean ok = false;
		
		// while(!ok){
			if(!cloList.isEmpty()){
				for(Noeud noeud : opListe){
					// Noeud test = cloList.get(taille-1);
					// if(noeud.getPredecesseur().getX() == test.getPiece().getX() && noeud.getPredecesseur().getY() == test.getPiece().getY()){
						if(noeud.getCoutTotal() < essaie.getCoutTotal()){					
							essaie = noeud;
						}
					// }
				}
			}
			else{
				for(Noeud noeud : opListe){
					if(noeud.getCoutTotal() < essaie.getCoutTotal()){					
						essaie = noeud;
					}
				}
			}
			int ver = -1;
			int test = 0;
			for(Noeud noeud : opListe){// Trouve l'emplacement du noeud trouve
				if(essaie == noeud){
					comp = test;
				}
				test++;
			}
			
			opListe.remove(comp);
			
			for(Noeud noeud : cloList){
				if(essaie.getPiece().getX() == noeud.getPiece().getX() && essaie.getPiece().getY() == noeud.getPiece().getY()){ // Si le noeud trouvé dans l'openList est déjà dans la liste close
					if(essaie.getPredecesseur().getX() == noeud.getPredecesseur().getX() && essaie.getPredecesseur().getY() == noeud.getPredecesseur().getY()){
						ver = 1;
						addCloseListe(opListe,cloList);
					}
				}
				if(essaie.getPiece().getX() == noeud.getPredecesseur().getX() && essaie.getPiece().getY() == noeud.getPredecesseur().getY() && essaie.getPredecesseur().getX() == noeud.getPiece().getX() && essaie.getPredecesseur().getY() == noeud.getPiece().getY()){
					for(Noeud mot : opListe){// Trouve l'emplacement du noeud trouve
						if(essaie == mot){
							mot.setCoutTotal(mot.getCoutTotal()+1);
						}
					}
				}
			}
			// Noeud node = cloList.get(cloList.size()-1);
			// if(essaie.getPiece
			if(ver == -1){
				listclose.add(essaie);
				ok = true;
			}
		// }
		
		return listclose;
	}
	
	/**
		* Methode qui à partir d'une position à sa position futur, retourne une Direction
		* @param noeud
		*		un entier
		* @return une Direction
	*/
	public Direction choseClose(Noeud noeud){
		Piece piece = noeud.getPredecesseur();
		int dx = noeud.getPiece().getX() - piece.getX();
		int dy = noeud.getPiece().getY() - piece.getY();
		for(Direction dir : Direction.values()){
			if(dir.getDx() == dx && dir.getDy() == dy){
				return dir;
			}
		}
		return null;
	}
	
	/**
		* Cette méthode permet d'évaluer le meilleur chemin
		* @param plateau
		*		une instance de Plateau
		* @return une ArrayList de Noeud
	*/
	public ArrayList<Noeud> evaluer(Plateau plateau){
		System.out.println(plateau.afficherTerminal());
		GamePlayer joueur = plateau.getPlayer();
		int compteur = 0;
		this.openList = addOpenListe(plateau, this.openList);
		this.closeList = addCloseListe(this.openList, this.closeList);
		plateau.movePlay(choseClose(this.closeList.get(this.closeList.size()-1)));
		
		int depart = sommeDistCaisse(plateau);
		
		while(!plateau.isOver()){
			joueur = plateau.getPlayer();
			
			this.openList = addOpenListe(plateau, this.openList);
			this.closeList = addCloseListe(this.openList, this.closeList);
			
			if(this.closeList.get(this.closeList.size()-2).getPredecesseur().getX() == this.closeList.get(this.closeList.size()-1).getPredecesseur().getX() && this.closeList.get(this.closeList.size()-2).getPredecesseur().getY() == this.closeList.get(this.closeList.size()-1).getPredecesseur().getY()){
				plateau.getPlateau()[joueur.getX()][joueur.getY()]= null;
				joueur.setX((this.closeList.get(this.closeList.size()-1)).getPredecesseur().getX());
				joueur.setY((this.closeList.get(this.closeList.size()-1)).getPredecesseur().getY());
				this.closeList.remove(this.closeList.size()-2);
			}
			
			plateau.movePlay(choseClose(this.closeList.get(this.closeList.size()-1)));
			// plateau.getPlayer().setX((this.closeList.get(this.closeList.size()-1)).getPiece().getX());
			// plateau.getPlayer().setY((this.closeList.get(this.closeList.size()-1)).getPiece().getY());
			
			int actu = sommeDistCaisse(plateau);
			
			if(actu < depart){
				this.openList = new ArrayList<Noeud>();
				depart = actu;
			}
			this.compteur++;
			System.out.println("TA CLOSE LISTE TU LA VOIS ??? : \n" + this.closeList);
			System.out.println(plateau.afficherTerminal());
			// evaluer(plt);
			
		}
		return this.closeList;
	}
	
	
	/**
		* Redéfinition de la méthode chooseMove, qui crée un choix de mouvement par un Algorithm
		* @return une Direction 
	*/
	@Override
	public Direction chooseMove(){
		System.out.println(evaluer(this.plateau));
		return null;
	}
	
	/**
		* Redéfinition de la méthode
		* @return un entier
	*/
	@Override
	public int getCompteur(){
		return this.compteur;
	}
}
