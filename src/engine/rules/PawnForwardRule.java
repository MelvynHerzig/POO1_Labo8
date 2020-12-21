package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.Pawn;
import engine.pieces.PieceSpecialFirstMove;

import java.util.LinkedList;

public class PawnForwardRule extends Rule
{
   public PawnForwardRule(Board board)
   {
      super(board);
   }

   public LinkedList<Movement> possibleMovements(Piece p)
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();

      int x = p.getX();
      int y = p.getY();

      if(!(p instanceof Pawn)) return movements;
      int directionFactor = ((Pawn)p).getDirectionFactor();

      // Vérification avancer d'une case
      if(board.isValidPosition(x, y + 1 * directionFactor) && board.isFreeSpot(x, y + 1 * directionFactor))
      {
         movements.add(new Movement(board, p, x, y + 1 * directionFactor));

         // Vérification avancer de 2 cases
         if( !((PieceSpecialFirstMove) p).hasMoved() && board.isValidPosition(x, y + 2 * directionFactor) && board.isFreeSpot(x, y + 2 * directionFactor))
         {
            movements.add(new Movement(board, p, x, y + 2 * directionFactor));
         }
      }

      return movements;
   }
}
