package engine.movements;

import chess.ChessView;
import engine.Board;
import engine.movements.Movement;
import engine.pieces.Pawn;
import engine.pieces.Piece;

/**
 * Classe modélisant le movement d'un pion
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class EnPassantMovement extends Movement
{
   Pawn toKill;

   public EnPassantMovement(Board board, Piece toMove, int toX, int toY, Pawn toKill)
   {
      super(board, toMove, toX, toY);
      this.toKill = toKill;
   }

   public Pawn getToKill()
   {
      return toKill;
   }

   @Override
   public void apply()
   {
      super.apply();
      board.killPiece(toKill.getX(), toKill.getY());
   }

   @Override
   public void undo()
   {
      super.undo();
      board.setPiece(toKill.getX(), toKill.getY(), toKill);
   }

   @Override
   public void updateView(ChessView view)
   {
      super.updateView(view);
      view.removePiece(toKill.getX(), toKill.getY());
   }

}
