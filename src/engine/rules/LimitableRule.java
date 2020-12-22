package engine.rules;

import engine.Board;
import engine.pieces.Piece;

public abstract class LimitableRule extends Rule
{
   protected boolean limitToOne;

   protected LimitableRule(Board board, Piece piece, boolean limitToOne)
   {
      super(board, piece);
      this.limitToOne = limitToOne;
   }
}
