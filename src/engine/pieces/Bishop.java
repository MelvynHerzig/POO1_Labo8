package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.rules.AngularRule;
import engine.Board;


public class Bishop extends Piece implements ChessView.UserChoice
{
    public Bishop(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.BISHOP, x, y, board, new AngularRule(board, false));
    }

    @Override
    public String textValue()
    {
        return toString();
    }
}
