package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.Pawn;
import engine.pieces.PieceSpecialFirstMove;

import java.util.LinkedList;

public class PawnForwardRule extends Rule
{
   public PawnForwardRule(Board board, Piece piece)
   {
      super(board, piece);
   }

   public LinkedList<Movement> possibleMovements()
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();

      int x = piece.getX();
      int y = piece.getY();

      if(!(piece instanceof Pawn)) return movements;
      int directionFactor = ((Pawn)piece).getDirectionFactor();

      // Vérification avancer d'une case
      if(board.isValidPosition(x, y + 1 * directionFactor) && board.isFreeSpot(x, y + 1 * directionFactor))
      {
         movements.add(new Movement(board, piece, x, y + 1 * directionFactor));

         // Vérification avancer de 2 cases
         if( !((PieceSpecialFirstMove) piece).hasMoved() && board.isValidPosition(x, y + 2 * directionFactor) && board.isFreeSpot(x, y + 2 * directionFactor))
         {
            movements.add(new Movement(board, piece, x, y + 2 * directionFactor));
         }
      }

      return movements;
   }
}
