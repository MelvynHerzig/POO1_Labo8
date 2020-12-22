package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.rules.Rule;
import engine.rules.CastlingRule;
import engine.rules.LinearRule;
import engine.rules.AngularRule;

import java.util.LinkedList;

/**
 * Classe modélisant un cavalier.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class King extends PieceSpecialFirstMove
{
    /**
     * Constructeur.
     * @param color Couleur de la pièce.
     * @param x Position x de la pièce.
     * @param y Position y de la pièce.
     * @param board Échiquier sur lequel elle se trouve.
     */
    public King(PlayerColor color, int x, int y, Board board)
    {
        super(color, PieceType.KING, x, y, board);
        rules = new LinkedList<Rule>();
        rules.add(new AngularRule(board, this,true));
        rules.add(new LinearRule(board, this, true));
        rules.add(new CastlingRule(board, this));
    }
}
