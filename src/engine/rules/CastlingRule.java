package engine.rules;

import engine.Board;
import engine.movements.CastlingMovement;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.PieceSpecialFirstMove;
import engine.pieces.Rook;

import java.util.LinkedList;

public class CastlingRule extends Rule
{
   public CastlingRule(Board board)
   {
      super(board);
   }

   public LinkedList<Movement> possibleMovements(Piece p)
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();
      int offset = 1;

      int x = p.getX();
      int y = p.getY();

      if(!(p instanceof PieceSpecialFirstMove) || ((PieceSpecialFirstMove) p).hasMoved())
         return movements;

      // Grand Roques
      boolean castlingPossible = true;
      if(board.isValidPosition(x-4, y) && board.isAllySpot(p.getColor(),x-4, y))
      {
         if(board.getPiece(x-4,y).getClass() == Rook.class)
         {
            for (int offsetX = 1; offsetX < 4; ++offsetX)
            {
               if (!board.isFreeSpot(x - offsetX, y))
               {
                  castlingPossible = false;
                  break;
               }
            }
            if (castlingPossible)
               movements.add(new CastlingMovement(board, p, x - 2, y, (Rook)board.getPiece(x - 4, y)));
         }
      }

      castlingPossible = true;
      // Petit Roques
      if(board.isValidPosition(x+3, y) && board.isAllySpot(p.getColor(),x+3, y))
      {
         if (board.getPiece(x+3, y).getClass() == Rook.class)
         {
            for (int offsetX = 1; offsetX < 3; ++offsetX)
            {
               if (!board.isFreeSpot(x + offsetX, y))
               {
                  castlingPossible = false;
                  break;
               }
            }
            if (castlingPossible)
               movements.add(new CastlingMovement(board, p, x + 2, y,(Rook)board.getPiece(x + 3, y)));
         }
      }
      return  movements;
   }
}
