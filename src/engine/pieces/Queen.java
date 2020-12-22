package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.rules.LinearRule;
import engine.rules.AngularRule;
import engine.rules.Rule;
import engine.Board;

import java.util.LinkedList;

/**
 * Classe modélisant une reine.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class Queen extends Piece implements ChessView.UserChoice
{
   /**
    * Constructeur.
    * @param color Couleur de la pièce.
    * @param x Position x de la pièce.
    * @param y Position y de la pièce.
    * @param board Échiquier sur lequel elle se trouve.
    */
    public Queen(PlayerColor color, int x, int y, Board board)
    {
        super(color, PieceType.QUEEN, x, y, board);
        rules = new LinkedList<Rule>();
        rules.add(new LinearRule(board, this, false));
        rules.add(new AngularRule(board, this, false));
    }

   /**
    * Indique le nom de la pièce. Appel à toString.
    * @return Retourne le nom de l'objet.
    */
   @Override
   public String textValue()
   {
      return toString();
   }
}
