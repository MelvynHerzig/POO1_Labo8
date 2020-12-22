package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.rules.LinearRule;
import engine.Board;
import engine.rules.Rule;

import java.util.LinkedList;

/**
 * Classe modélisant une tour.
 * Inclus deux fonctions "controller" qui permettent de
 * calculer les movements possibles.
 */
public class Rook extends PieceSpecialFirstMove implements ChessView.UserChoice
{

    /**
     * Constructeur.
     * @param color Couleur de la tour.
     * @param x Position x.
     * @param y Position y.
     */
    public Rook(PlayerColor color, int x, int y, Board board)
    {
        super(color, PieceType.ROOK, x, y, board);
        rules = new LinkedList<Rule>(){};
        rules.add(new LinearRule(board, this, false));
    }

   @Override
   public String textValue()
   {
      return toString();
   }
}
