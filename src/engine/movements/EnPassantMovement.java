package engine.movements;

import chess.ChessView;
import engine.Board;
import engine.pieces.Pawn;
import engine.pieces.Piece;

/**
 * Classe modélisant la prise en passant.
 *
 * Un mécanisme est mis en place pour faire que plusieurs apply
 * n'aient aucun effet tant que le mouvement n'a pas été annulé.
 * De même pour les undo.
 *
 * Si l'état de l'échiquier est modifié entre la création de l'objet et
 * son utilisation, le comportement est indefini.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class EnPassantMovement extends Movement
{
   Pawn toKill;

   /**
    * Constructeur.
    * @param board Echiquier.
    * @param toMove Pièce à déplacer.
    * @param toX Position x de destination.
    * @param toY Position y de destination.
    * @param toKill Pion pris en passant.
    */
   public EnPassantMovement(Board board, Piece toMove, int toX, int toY, Pawn toKill)
   {
      super(board, toMove, toX, toY);
      this.toKill = toKill;
   }

   /**
    * Renvoie le pion à tuer lors de la prise.
    * @return Retourne le pion tué lors de la prise.
    */
   public Pawn getToKill()
   {
      return toKill;
   }

   /**
    * Applique la prise en passant sur l'échiquier.
    */
   @Override
   public void apply()
   {
      if(super.hasBeenApplied) return;

      super.apply();
      board.killPiece(toKill.getX(), toKill.getY());
   }

   /**
    * Annule la prise en passant sur l'échiquier.
    */
   @Override
   public void undo()
   {
      if(!super.hasBeenApplied) return;

      super.undo();
      board.setPiece(toKill.getX(), toKill.getY(), toKill);
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
      if(board.getPiece(toKill.getX(), toKill.getY()) == null)
         view.removePiece(toKill.getX(), toKill.getY());
   }

}
