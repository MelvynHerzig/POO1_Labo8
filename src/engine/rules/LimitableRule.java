package engine.rules;

import engine.Board;
import engine.pieces.Piece;

/**
 * Classe abstraite modélisant des règles de mouvements dont la profondeur
 * de sondage peut être limitée.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public abstract class LimitableRule extends Rule
{
   protected boolean limitToOne;

   /**
    * Constructeur
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    * @param limitToOne Si vrai, le sondage dans les différentes directions
    *                   est limité à 1 de distance.
    */
   protected LimitableRule(Board board, Piece piece, boolean limitToOne)
   {
      super(board, piece);
      this.limitToOne = limitToOne;
   }

   /**
    * Vérifie si le sondage doit s'arrêter.
    * @param x position x de destination sondée jusque là.
    * @param y position y de destination sondée jusque là.
    * @return Retourne vrai si le sondage doit s'arrêter.
    */
   protected boolean mustStop(int x, int y)
   {
      return limitToOne || (isValidDestination(x,y) && !board.isFreeSpot(x, y));
   }
}
