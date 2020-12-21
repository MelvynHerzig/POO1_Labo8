package engine.movements;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.Rook;

/**
 * Classe modélisant le roque d'un roi
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class CastlingMovement extends Movement
{


   private final Rook rook;
   private final Rook rookCopy;


   public CastlingMovement(Board board, Piece king, int toX, int toY, Rook rook)
   {
      super(board, king, toX, toY);
      this.rook = rook;
      this.rookCopy = (Rook) rook.clone();
   }

   public Rook getRook()
   {
      return rook;
   }

   @Override
   public void apply()
   {
      super.apply();

      int relativeX = super.getToMove().getX() < rook.getX() ? -1 : 1;
      board.movePiece(rook.getX(), rook.getY(), super.getToMove().getX() + relativeX, rook.getY());

      rook.setMoved();
   }

   @Override
   public void undo()
   {
      super.undo();
      board.killPiece(rook.getX(), rook.getY());
      board.setPiece(rookCopy.getX(), rookCopy.getY(), rook);

      rook.copyState(rookCopy);
   }
}
