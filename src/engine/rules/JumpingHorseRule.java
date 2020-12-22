package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;

import java.util.LinkedList;


public class JumpingHorseRule extends Rule
{
   public JumpingHorseRule(Board board, Piece piece)
   {
      super(board, piece);
   }

   public LinkedList<Movement> possibleMovements()
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();

      int x = piece.getX();
      int y = piece.getY();

      //Deux cases au dessus et en dessous
      for(int offsetX = -1; offsetX <= 1; offsetX +=2)
      {
         if(board.isValidPosition(x+offsetX, y+2) && !board.isAllySpot(piece.getColor(), x+offsetX, y+2))
            movements.add(new Movement(board, piece, x+offsetX, y+2));
         if(board.isValidPosition(x+offsetX, y-2) && !board.isAllySpot(piece.getColor(),x+offsetX, y-2))
            movements.add(new Movement(board, piece, x+offsetX, y-2));
      }

      //Deux cases à droite et à gauche
      for(int offsetY = -1; offsetY <= 1; offsetY +=2)
      {
         if(board.isValidPosition(x+2, y+offsetY) && !board.isAllySpot(piece.getColor(), x+2, y+offsetY))
            movements.add(new Movement(board, piece, x+2, y+offsetY));
         if(board.isValidPosition(x-2, y-offsetY) && !board.isAllySpot(piece.getColor(), x-2, y-offsetY))
            movements.add(new Movement(board,piece, x-2, y-offsetY));
      }


      return movements;
   }
}
