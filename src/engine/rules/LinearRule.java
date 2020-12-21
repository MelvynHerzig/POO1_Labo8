package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

public class LinearRule extends Rule
{
   boolean limitToOne;

   public LinearRule(Board board, boolean limitToOne)
   {
      super(board);

      this.limitToOne = limitToOne;
   }

   public LinkedList<Movement> possibleMovements(Piece p)
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();
      int offset = 1;

      int x = p.getX();
      int y = p.getY();

      //En haut
      while(board.isValidPosition(x, y+offset) && !board.isAllySpot(p.getColor(),x ,y+offset))
      {
         movements.add(new Movement(board, p, x, y+offset));
         if(limitToOne || !board.isFreeSpot(x, y+offset) && !board.isAllySpot(p.getColor(),x, y+offset)) break;
         ++offset;
      }

      //En bas
      offset = 1;
      while(board.isValidPosition(x, y-offset) && !board.isAllySpot(p.getColor(),x ,y-offset))
      {
         movements.add(new Movement(board, p, x, y-offset));
         if(limitToOne || !board.isFreeSpot(x, y-offset) && !board.isAllySpot(p.getColor(), x, y-offset)) break;
         ++offset;
      }

      //A droite
      offset = 1;
      while(board.isValidPosition( x+offset, y) && !board.isAllySpot(p.getColor(), x+offset, y))
      {
         movements.add(new Movement(board, p, x+offset, y));
         if(limitToOne || !board.isFreeSpot(x+offset, y) && !board.isAllySpot(p.getColor(), x+offset, y)) break;
         ++offset;
      }

      //A gauche
      offset = 1;
      while(board.isValidPosition(  x-offset, y) && !board.isAllySpot(p.getColor(),  x-offset, y))
      {
         movements.add(new Movement(board, p, x-offset, y));
         if(limitToOne || !board.isFreeSpot(x-offset, y) && !board.isAllySpot(p.getColor(), x-offset, y)) break;
         ++offset;
      }

      return  movements;
   }
}
