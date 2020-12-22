package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.rules.Rule;
import engine.rules.JumpingHorseRule;

import java.util.LinkedList;

public class Knight extends Piece implements ChessView.UserChoice
{
    public Knight(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.KNIGHT, x, y, board);
        rules = new LinkedList<Rule>();
        rules.add( new JumpingHorseRule(board, this));
    }

    @Override
    public String textValue()
    {
        return toString();
    }
}
