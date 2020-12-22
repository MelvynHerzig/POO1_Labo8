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
    * @param board Echiquier sur lequel appliquer la règle.
    */
   public CastlingRule(Board board, Piece piece)
   {
      super(board, piece);
   }

   /**
    * Calcule les mouvements possibles pour la pièce p.
    * @return Retourne la liste des mouvements possibles.
    */
   public LinkedList<Movement> possibleMovements()
   {
      LinkedList<Movement> movements = new LinkedList<Movement>();


      int x = piece.getX();
      int y = piece.getY();

      if(!(piece instanceof PieceSpecialFirstMove) || ((PieceSpecialFirstMove) piece).hasMoved())
         return movements;

      // Grand Roques
      boolean castlingPossible = true;
      if(board.isValidPosition(x-4, y) && board.isAllySpot(piece.getColor(),x-4, y))
      {
         if(board.getPiece(x-4,y).getClass() == Rook.class)
         {
            for (int offsetX = 1; offsetX < 4; ++offsetX)
            {
               if (!board.isFreeSpot(x - offsetX, y))
               {
                  castlingPossible = false;
                  break;
               }
            }
            if (castlingPossible)
               movements.add(new CastlingMovement(board, piece, x - 2, y, (Rook)board.getPiece(x - 4, y)));
         }
      }

      castlingPossible = true;
      // Petit Roques
      if(board.isValidPosition(x+3, y) && board.isAllySpot(piece.getColor(),x+3, y))
      {
         if (board.getPiece(x+3, y).getClass() == Rook.class)
         {
            for (int offsetX = 1; offsetX < 3; ++offsetX)
            {
               if (!board.isFreeSpot(x + offsetX, y))
               {
                  castlingPossible = false;
                  break;
               }
            }
            if (castlingPossible)
               movements.add(new CastlingMovement(board, piece, x + 2, y,(Rook)board.getPiece(x + 3, y)));
         }
      }
      return  movements;
   }
}
