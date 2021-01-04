package engine.rules;

import engine.Board;
import engine.movements.CastlingMovement;
import engine.movements.Movement;
import engine.pieces.Piece;
import engine.pieces.PieceSpecialFirstMove;
import engine.pieces.Rook;

import java.util.LinkedList;

/**
 * Classe modélisant la règle des mouvements de type roque.
 * Les deux pièces qui roques doivent hériter de PieceSpecialFirstMove. La pièce
 * complémentaire au roque doit hériter de Rook.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class CastlingRule extends Rule
{
   /**
    * Constructeur
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    */
   public CastlingRule(Board board, Piece piece)
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

      if(!(piece instanceof PieceSpecialFirstMove) || ((PieceSpecialFirstMove) piece).hasMoved())
         return movements;

      // la tour devrait se situer en -4 par rapport au roi ou en +3
      // soit en premier lieu grand roque et ensuite petit roque
      for(int rookOffsetX = -4; rookOffsetX <= 3; rookOffsetX += 7)
      {
         boolean castlingPossible = true;
         if(board.isValidPosition(x + rookOffsetX, y) && board.isAllySpot(piece.getColor(),x + rookOffsetX, y))
         {
            Piece p = board.getPiece(x + rookOffsetX, y);
            if(p != null && p.getClass() == Rook.class && !((PieceSpecialFirstMove)p).hasMoved())
            {
               for (int offsetX = 1; offsetX <Math.abs(rookOffsetX); ++offsetX)
               {
                  if (!board.isFreeSpot(x - offsetX, y))
                  {
                     castlingPossible = false;
                     break;
                  }
               }
               if (castlingPossible)
               {
                  int xDestination = (rookOffsetX < 0) ? x - 2 : x + 2;
                  movements.add(new CastlingMovement(board, piece, xDestination, y, (Rook)board.getPiece(x + rookOffsetX, y)));
               }
            }
         }
      }

      return  movements;
   }
}
