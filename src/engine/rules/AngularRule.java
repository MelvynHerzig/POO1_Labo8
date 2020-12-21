package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

public class AngularRule extends Rule
{
   boolean limitToOne;

   public AngularRule(Board board, boolean limitToOne)
   {
      super(board);

      this.limitToOne = limitToOne;
   }

   public LinkedList<Movement> possibleMovements(Piece p)
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();


      int x = p.getX();
      int y = p.getY();
      int offset = 1;

      //En haut-droite
      while(board.isValidPosition(  x+offset, y+offset) && !board.isAllySpot(p.getColor(),  x+offset, y+offset))
      {
         movements.add(new Movement(board, p, x+offset, y+offset));
         if(limitToOne || !board.isFreeSpot(x+offset, y+offset) && !board.isAllySpot(p.getColor(),x+offset, y+offset)) break;
         ++offset;
      }

      //En haut-gauche
      offset = 1;
      while(board.isValidPosition(  x-offset, y+offset) && !board.isAllySpot(p.getColor(),  x-offset, y+offset))
      {
         movements.add(new Movement(board, p, x-offset, y+offset));
         if(limitToOne || !board.isFreeSpot(x-offset, y+offset) && !board.isAllySpot(p.getColor(),x-offset, y+offset)) break;
         ++offset;
      }

      //En bas-droite
      offset = 1;
      while(board.isValidPosition(  x+offset, y-offset) && !board.isAllySpot(p.getColor(),  x+offset, y-offset))
      {
         movements.add(new Movement(board, p, x+offset, y-offset));
         if(limitToOne || !board.isFreeSpot(x+offset, y-offset) && !board.isAllySpot(p.getColor(),x+offset, y-offset)) break;
         ++offset;
      }

      //En bas-gauche
      offset = 1;
      while(board.isValidPosition(  x-offset, y-offset) && !board.isAllySpot(p.getColor(),  x-offset, y-offset))
      {
         movements.add(new Movement(board, p, x-offset, y-offset));
         if(limitToOne || !board.isFreeSpot(x-offset, y-offset) && !board.isAllySpot(p.getColor(),x-offset, y-offset)) break;
         ++offset;
      }

      return  movements;
   }
}
