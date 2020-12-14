package engine;

/**
 * Classe modélisant le roque d'un roi
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class CastlingMovement extends Movement
{
   // Tour avec laquelle le roque est fait
   final Rook rookSwapped;

   /**
    * Constructeur.
    * @param toMove Roi à roquer.
    * @param rook Tour complémentaire au movement.
    * @param toX Position d'arrivée X du roi.
    * @param toY Position d'arrivée Y du roi.
    */
   CastlingMovement(Piece toMove, Rook rook, int toX, int toY)
   {
      super(toMove, toX, toY);
      rookSwapped = rook;
   }

   /**
    * @return Retourne la tour complémentaire au mouvement.
    */
   Rook getRook()
   {
      return rookSwapped;
   }
}
