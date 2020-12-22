package engine.rules;

import chess.PieceType;
import engine.Board;
import engine.movements.EnPassantMovement;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.Pawn;

import java.util.LinkedList;

/**
 * Classe modélisant une règle d'attaque pour un pawn.
 * La pièce liée doit hériter de pawn.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class PawnAttackRule extends Rule
{
   /**
    * Constructeur.
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    */
   public PawnAttackRule(Board board, Piece piece)
   {
      super(board, piece);
   }

   /**
    * Pour la règle calcul les movements possibles de la pièce piece dans
    * l'échiquier board.
    * @return Retourne la liste des déplacements possibles.
    */
   @Override
   public LinkedList<Movement> possibleMovements()
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();

      if(!(piece instanceof Pawn)) return movements;

      int x = piece.getX();
      int y = piece.getY();
      int directionFactor = ((Pawn)piece).getDirectionFactor();
      Piece adjacentPawn;

      //Déplacements diagonal gauche
      if(board.isValidPosition(x-1, y + directionFactor) && !board.isAllySpot(piece.getColor(), x-1, y + directionFactor))
      {
         adjacentPawn = board.getPiece(x-1, y);

         //Vérification de la prise en passant.
         if(adjacentPawn != null && adjacentPawn.getType() == PieceType.PAWN && !board.isAllySpot(piece.getColor(), x-1, y)
                 && adjacentPawn == board.getLastMovedPiece() && ((Pawn)adjacentPawn).getMoved2())
         {
            movements.add(new EnPassantMovement(board, piece, x-1, y + directionFactor, (Pawn)board.getPiece(x-1 , y)));
         }
         else if(!board.isFreeSpot(x-1, y + directionFactor))
         {
            movements.add(new Movement(board, piece, x-1, y + directionFactor));
         }
      }

      //Déplacements diagonal droite
      if(board.isValidPosition(x+1, y + directionFactor) && !board.isAllySpot(piece.getColor(), x+1, y + directionFactor))
      {
         //Vérification de la prise en passant.
         adjacentPawn = board.getPiece(x+1, y);
         if(adjacentPawn != null && adjacentPawn.getType() == PieceType.PAWN && !board.isAllySpot(piece.getColor(), x+1, y)
                 && adjacentPawn == board.getLastMovedPiece() && ((Pawn)adjacentPawn).getMoved2())
         {
            movements.add(new EnPassantMovement(board, piece,  x+1, y + directionFactor, (Pawn)board.getPiece(x+1, y)));
         }
         else if(!board.isFreeSpot(x+1, y + directionFactor))
         {
            movements.add(new Movement(board, piece, x+1, y + directionFactor));
         }
      }

      return  movements;
   }
}
