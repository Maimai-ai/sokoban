package sokoban.modele.piece.players;
import java.util.List;
import java.util.ArrayList;
import sokoban.modele.piece.*;
import sokoban.modele.plateau.*;
import sokoban.Direction;

/**
	* GamePlayer est une classe abstraite qui herite de Piece
	* Elle nous permettra de differencier un joueur humain de l'Algorithm Astar
*/
public abstract class GamePlayer extends Piece{

    public Direction toucheDirection;
    public boolean toucheAppuyee; //pour si on appuie ou non que le player avance pas en boucle cf main, mais ca change pas..
	public Plateau plateau;
	public int compteur; 
	public int score;
	
	public GamePlayer(){
		
	}
	
	public GamePlayer(Plateau plateau){
		this.plateau = plateau;
	}
	
	/**
		* Accesseur de plateau
		* @return un Plateau
	*/
	public Plateau getPlateau(){
		return this.plateau;
	}
	
	/**
		* Mutateur de plateau
		* @param plat
		* 		une instance de Plateau
	*/
	public void setPlateau(Plateau plat){
		this.plateau = plat;
	}
	
	/**
		* Accesseur de score
		* @return un entier
	*/
	public int getScore(){
		return this.score;
	}
	/*
		* Methode abstraite de getCompteur
		* @return un entier
	*/
	public abstract int getCompteur();
	
	/**
		* Methode abstraite de chooseMove
		* @return une direction
	*/
	public abstract Direction chooseMove();
	
	/**
		* Methode qui prend dans une liste tous les elements dans le validMoves du player
		* @param plt
		*		une instance de Plateau
		* @return une liste de Direction possible pour ce joueur
	*/
	public List<Direction> moveValid(Plateau plt){
		List<Direction> list = new ArrayList<Direction>();
		GamePlayer player = plt.getPlayer();
		for(int i = 0; i < plt.validMoves(player).size(); i++){
			list.add(plt.validMoves(player).get(i));
		}
		return list;
	}
	


}
