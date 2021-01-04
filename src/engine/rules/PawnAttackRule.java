package engine.rules;

import chess.PieceType;
import chess.PlayerColor;
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
   private int enPassantLine;
   /**
    * Constructeur.
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    */
   public PawnAttackRule(Board board, Piece piece)
   {
      super(board, piece);
      enPassantLine = piece.getColor() == PlayerColor.BLACK ? 3 : 4;
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
      boolean isEnPassantLine = piece.getY() == enPassantLine;
      Piece adjacentPawn;

      for(int i = -1; i <= 1; i += 2)
      {
         //Pièce à gauche ou à droite du pion
         int offsetX = x + i;

         //Déplacements diagonal gauche
         if(isValidDestination(offsetX, y + directionFactor))
         {
            adjacentPawn = board.getPiece(offsetX, y);

            //Vérification de la prise en passant.
            if(isEnPassantLine && canBeEnPassantAttacked(adjacentPawn))
            {
               movements.add(new EnPassantMovement(board, piece, offsetX, y + directionFactor, (Pawn)board.getPiece(offsetX , y)));
            }
            else if(!board.isFreeSpot(offsetX, y + directionFactor))
            {
               movements.add(new Movement(board, piece, offsetX, y + directionFactor));
            }
         }
      }

      return  movements;
   }

   private boolean canBeEnPassantAttacked(Piece adjacentPawn)
   {
      if(adjacentPawn == null) return false;

      int apX = adjacentPawn.getX();
      int apY = adjacentPawn.getY();

      return adjacentPawn.getType() == PieceType.PAWN && !board.isAllySpot(piece.getColor(), apX, apY)
              && adjacentPawn == board.getLastMovedPiece() && ((Pawn)adjacentPawn).getMoved2();
   }
}
