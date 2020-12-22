package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

/**
 * Classe modélisant la régle des déplacements horizontaux et verticaux.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class LinearRule extends LimitableRule
{
   /**
    * Constructeur.
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    * @param limitToOne Si vrai, le sondage dans les différentes directions
    *                   est limité à 1 de distance.
    */
   public LinearRule(Board board, Piece piece, boolean limitToOne)
   {
      super(board, piece, limitToOne);
   }

   /**
    * Pour la règle calcul les movements possibles de la pièce piece dans
    * l'échiquier board.
    * @return Retourne la liste des déplacements possibles.
    */
   public LinkedList<Movement> possibleMovements()
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();
      int offset = 1;

      int x = piece.getX();
      int y = piece.getY();

      //En haut
      while(board.isValidPosition(x, y+offset) && !board.isAllySpot(piece.getColor(),x ,y+offset))
      {
         movements.add(new Movement(board, piece, x, y+offset));
         if(limitToOne || !board.isFreeSpot(x, y+offset) && !board.isAllySpot(piece.getColor(),x, y+offset)) break;
         ++offset;
      }

      //En bas
      offset = 1;
      while(board.isValidPosition(x, y-offset) && !board.isAllySpot(piece.getColor(),x ,y-offset))
      {
         movements.add(new Movement(board, piece, x, y-offset));
         if(limitToOne || !board.isFreeSpot(x, y-offset) && !board.isAllySpot(piece.getColor(), x, y-offset)) break;
         ++offset;
      }

      //A droite
      offset = 1;
      while(board.isValidPosition( x+offset, y) && !board.isAllySpot(piece.getColor(), x+offset, y))
      {
         movements.add(new Movement(board, piece, x+offset, y));
         if(limitToOne || !board.isFreeSpot(x+offset, y) && !board.isAllySpot(piece.getColor(), x+offset, y)) break;
         ++offset;
      }

      //A gauche
      offset = 1;
      while(board.isValidPosition(  x-offset, y) && !board.isAllySpot(piece.getColor(),  x-offset, y))
      {
         movements.add(new Movement(board, piece, x-offset, y));
         if(limitToOne || !board.isFreeSpot(x-offset, y) && !board.isAllySpot(piece.getColor(), x-offset, y)) break;
         ++offset;
      }

      return  movements;
   }
}
