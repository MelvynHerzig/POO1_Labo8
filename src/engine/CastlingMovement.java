package engine;

/**
 * Classe modélisant le roque d'un roi
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class CastlingMovement extends Movement
{

   int rX;
   int rY;
   /**
    * Effectue le roque pour un roi kX,kY avec une tour rX, rY, en le déplacant
    * en toX toY.
    * @param kX Position x du roi.
    * @param kY Position y du roi.
    * @param toX Position de destionation x du roi.
    * @param toY Position de destionation y du roi.
    * @param rX Position x de la tour à roquer.
    * @param rY Position y de la tour à roquer.
    */
   CastlingMovement(int kX, int kY, int toX, int toY, int rX, int rY)
   {
      super(kX, kY, toX, toY);
      this.rX = rX;
      this.rY = rY;
   }

   int getrX()
   {
      return rX;
   }

   int getrY()
   {
      return rY;
   }


}
