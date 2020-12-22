package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.rules.Rule;
import engine.rules.AngularRule;
import engine.Board;

import java.util.LinkedList;


public class Bishop extends Piece implements ChessView.UserChoice
{
    public Bishop(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.BISHOP, x, y, board);
        rules = new LinkedList<Rule>();
        rules.add(new AngularRule(board, this, false));

    }

    @Override
    public String textValue()
    {
        return toString();
    }
}
