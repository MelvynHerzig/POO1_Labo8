package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

public abstract class Rule
{
   protected Board board;
   protected Piece piece;

   public Rule(Board board, Piece piece)
   {
      this.board = board;
      this.piece = piece;
   }

   public abstract LinkedList<Movement> possibleMovements();
}
