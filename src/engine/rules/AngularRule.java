package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

/**
 * Classe modélisant la règle des mouvements linéaires.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class AngularRule extends Rule
{
   private boolean limitToOne;

   /**
    * Constructeur
    * @param board Echiquier sur lequel appliquer la règle.
    * @param limitToOne La règle doit elle être limité à une case dans les
    *                   différentes directions.
    */
   public AngularRule(Board board, Piece piece, boolean limitToOne)
   {
      super(board, piece);

      this.limitToOne = limitToOne;
   }

   /**
    * Calcule les mouvements possibles pour la pièce p.
    * @return Retourne la liste des mouvements possibles.
    */
   public LinkedList<Movement> possibleMovements()
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();


      int x = piece.getX();
      int y = piece.getY();
      int offset = 1;

      //En haut-droite
      while(board.isValidPosition(  x+offset, y+offset) && !board.isAllySpot(piece.getColor(),  x+offset, y+offset))
      {
         movements.add(new Movement(board, piece, x+offset, y+offset));
         if(limitToOne || !board.isFreeSpot(x+offset, y+offset) && !board.isAllySpot(piece.getColor(),x+offset, y+offset)) break;
         ++offset;
      }

      //En haut-gauche
      offset = 1;
      while(board.isValidPosition(  x-offset, y+offset) && !board.isAllySpot(piece.getColor(),  x-offset, y+offset))
      {
         movements.add(new Movement(board, piece, x-offset, y+offset));
         if(limitToOne || !board.isFreeSpot(x-offset, y+offset) && !board.isAllySpot(piece.getColor(),x-offset, y+offset)) break;
         ++offset;
      }

      //En bas-droite
      offset = 1;
      while(board.isValidPosition(  x+offset, y-offset) && !board.isAllySpot(piece.getColor(),  x+offset, y-offset))
      {
         movements.add(new Movement(board, piece, x+offset, y-offset));
         if(limitToOne || !board.isFreeSpot(x+offset, y-offset) && !board.isAllySpot(piece.getColor(),x+offset, y-offset)) break;
         ++offset;
      }

      //En bas-gauche
      offset = 1;
      while(board.isValidPosition(  x-offset, y-offset) && !board.isAllySpot(piece.getColor(),  x-offset, y-offset))
      {
         movements.add(new Movement(board, piece, x-offset, y-offset));
         if(limitToOne || !board.isFreeSpot(x-offset, y-offset) && !board.isAllySpot(piece.getColor(),x-offset, y-offset)) break;
         ++offset;
      }

      return  movements;
   }
}
