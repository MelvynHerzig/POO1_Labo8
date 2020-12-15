package engine;

/**
 * Classe modélisant le movement d'un pion
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class PawnMovement extends Movement
{
   int toKillX;
   int toKillY;

   PawnMovement(int fromX, int fromY, int toX, int toY, int toKillX, int toKillY)
   {
      super(fromX, fromY, toX, toY);
      this.toKillX = toKillX;
      this.toKillY = toKillY;
   }

   int getToKillX()
   {
      return toKillX;
   }

   int getToKillY()
   {
      return toKillY;
   }
}
