package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

/**
 * Classe modélisant la régle des déplacements diagonaux.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class AngularRule extends LimitableRule
{
   /**
    * Constructeur.
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    * @param limitToOne Si vrai, le sondage dans les différentes directions
    *                   est limité à 1 de distance.
    */
   public AngularRule(Board board, Piece piece, boolean limitToOne)
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
