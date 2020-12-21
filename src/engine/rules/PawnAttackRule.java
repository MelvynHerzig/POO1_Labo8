package engine.rules;

import chess.PieceType;
import engine.Board;
import engine.movements.EnPassantMovement;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.Pawn;
import engine.pieces.PieceSpecialFirstMove;

import java.util.LinkedList;

public class PawnAttackRule extends Rule
{
   public PawnAttackRule(Board board)
   {
      super(board);
   }

   @Override
   public LinkedList<Movement> possibleMovements(Piece p)
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();

      if(!(p instanceof Pawn)) return movements;

      int x = p.getX();
      int y = p.getY();
      int directionFactor = ((Pawn)p).getDirectionFactor();
      Piece adjacentPawn;

      //Déplacements diagonal gauche
      if(board.isValidPosition(x-1, y + directionFactor) && !board.isAllySpot(p.getColor(), x-1, y + directionFactor))
      {
         adjacentPawn = board.getPiece(x-1, y);
         //Vérification de la prise en passant.
         if(adjacentPawn != null && adjacentPawn.getType() == PieceType.PAWN && !board.isAllySpot(p.getColor(), x-1, y) && adjacentPawn == board.getLastMovedPiece() && ((Pawn)adjacentPawn).getMoved2())
            movements.add(new EnPassantMovement(board, p, x-1, y + directionFactor, (Pawn)board.getPiece(x-1 , y)));
         else if(!board.isFreeSpot(x-1, y + directionFactor))
            movements.add(new Movement(board, p, x-1, y + directionFactor));
      }

      //Déplacements diagonal droite
      if(board.isValidPosition(x+1, y + directionFactor) && !board.isAllySpot(p.getColor(), x+1, y + directionFactor))
      {
         //Vérification de la prise en passant.
         adjacentPawn = board.getPiece(x+1, y);
         if(adjacentPawn != null && adjacentPawn.getType() == PieceType.PAWN && !board.isAllySpot(p.getColor(), x+1, y) && adjacentPawn == board.getLastMovedPiece() && ((Pawn)adjacentPawn).getMoved2())
            movements.add(new EnPassantMovement(board, p,  x+1, y + directionFactor, (Pawn)board.getPiece(x+1, y)));
         else if(!board.isFreeSpot(x+1, y + directionFactor))
            movements.add(new Movement(board, p, x+1, y + directionFactor));
      }

      return  movements;
   }
}
