package engine.rules;

import engine.Board;
import engine.movements.Movement;
import engine.pieces.Piece;
import java.util.LinkedList;

/**
 * Classe modélisant une règle de base.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public abstract class Rule
{
   protected Board board;
   protected Piece piece;

   /**
    * Constructeur.
    * @param board Échiquier sur lequel la règle va s'appliquer.
    * @param piece Pièce liée à la règle.
    */
   public Rule(Board board, Piece piece)
   {
      this.board = board;
      this.piece = piece;
   }

   /**
    * Pour la règle calcul les movements possibles de la pièce piece dans
    * l'échiquier board.
    * @return Retourne la liste des déplacements possibles.
    */
   public abstract LinkedList<Movement> possibleMovements();
}
