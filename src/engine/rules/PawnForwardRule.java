package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.Pawn;
import engine.pieces.PieceSpecialFirstMove;

import java.util.LinkedList;

/**
 * Classe modélisant une règle de déplacement pour un pawn.
 * La pièce liée doit hériter de pawn.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class PawnForwardRule extends Rule
{
   /**
    * Constructeur.
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    */
   public PawnForwardRule(Board board, Piece piece)
   {
      super(board, piece);
   }

   /**
    * Pour la règle calcul les movements possibles de la pièce piece dans
    * l'échiquier board.
    * @return Retourne la liste des déplacements possibles.
    */
   public LinkedList<Movement> possibleMovements()
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();

      int x = piece.getX();
      int y = piece.getY();

      if(!(piece instanceof Pawn)) return movements;
      int directionFactor = ((Pawn)piece).getDirectionFactor();

      // Vérification avancer d'une case
      if(board.isValidPosition(x, y + 1 * directionFactor) && board.isFreeSpot(x, y + 1 * directionFactor))
      {
         movements.add(new Movement(board, piece, x, y + 1 * directionFactor));

         // Vérification avancer de 2 cases
         if( !((PieceSpecialFirstMove) piece).hasMoved() && board.isValidPosition(x, y + 2 * directionFactor) && board.isFreeSpot(x, y + 2 * directionFactor))
         {
            movements.add(new Movement(board, piece, x, y + 2 * directionFactor));
         }
      }

      return movements;
   }
}
