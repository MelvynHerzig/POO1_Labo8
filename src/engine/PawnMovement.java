package engine;

/**
 * Classe modélisant le movement d'un pion
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class PawnMovement extends Movement
{
   // Un pion peut tuer sur la prise en passant, la cible ne se situe donc pas
   // Sur la case de destination.
   private final Pawn toKill;

   PawnMovement(Piece toMove, Pawn toKill, int toX, int toY)
   {
      super(toMove, toX, toY);
      this.toKill = toKill;
   }

   /**
    * @return Retourne le pion tué par une prise en passant sinon null.
    */
   Pawn getPieceToKill()
   {
      return toKill;
   }
}
