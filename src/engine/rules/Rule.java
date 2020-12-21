package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

public abstract class Rule
{
   Board board;

   public Rule(Board board)
   {
      this.board = board;
   }

   public abstract LinkedList<Movement> possibleMovements(Piece p);
}
