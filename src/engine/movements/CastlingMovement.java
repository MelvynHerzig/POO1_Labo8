package engine.movements;

import chess.ChessView;
import engine.Board;
import engine.pieces.Piece;
import engine.pieces.Rook;

/**
 * Classe modélisant le roque d'un roi.
 *
 * Un mécanisme est mis en place pour faire que plusieurs apply
 * n'aient aucun effet tant que le mouvement n'a pas été annulé.
 * De même pour les undo.
 *
 * Si l'état de l'échiquier est modifié entre la création de l'objet et
 * son utilisation, le comportement est indefini.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class CastlingMovement extends Movement
{
   private final Rook rook;
   private final Rook rookCopy;

   /**
    * Constructeur.
    * @param board Echiquier sur lequel effectuer le mouvement.
    * @param king Roi à roquer.
    * @param toX Destination x du roque.
    * @param toY Destination y du roque.
    * @param rook Tour à roquer.
    */
   public CastlingMovement(Board board, Piece king, int toX, int toY, Rook rook)
   {
      super(board, king, toX, toY);
      this.rook = rook;
      this.rookCopy = (Rook) rook.clone();
   }

   /**
    * Retourne la tour qui roque avec le roi.
    * @return Retourne la tour roquée.
    */
   public Rook getRook()
   {
      return rook;
   }

   /**
    * Applique la prise en passant sur l'échiquier.
    */
   @Override
   public void apply()
   {
      if(super.hasBeenApplied) return;
      super.apply();

      int relativeX = super.getToMove().getX() < rook.getX() ? -1 : 1;
      board.movePiece(rook.getX(), rook.getY(), super.getToMove().getX() + relativeX, rook.getY());

      rook.setMoved();
   }

   /**
    * Annule la prise en passant sur l'échiquier.
    */
   @Override
   public void undo()
   {
      if(!super.hasBeenApplied) return;
      super.undo();
      board.killPiece(rook.getX(), rook.getY());
      board.setPiece(rookCopy.getX(), rookCopy.getY(), rook);

      rook.copyState(rookCopy);
   }

   /**
    * Met à jour la vue avec la prise en passant.
    * @param view Vue à mettre à jour.
    */
   @Override
   public void updateView(ChessView view)
   {
      if(!super.hasBeenApplied) return;
      super.updateView(view);
      view.putPiece(rook.getType(), rook.getColor(), rook.getX(), rook.getY());
      view.removePiece(rookCopy.getX(), rookCopy.getY());
   }
}
