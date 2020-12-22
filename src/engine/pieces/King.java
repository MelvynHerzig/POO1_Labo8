package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.rules.Rule;
import engine.rules.CastlingRule;
import engine.rules.LinearRule;
import engine.rules.AngularRule;

import java.util.LinkedList;

public class King extends PieceSpecialFirstMove
{
    public King(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.KING, x, y, board);
        rules = new LinkedList<Rule>();
        rules.add(new AngularRule(board, this,true));
        rules.add(new LinearRule(board, this, true));
        rules.add(new CastlingRule(board, this));
    }
}
