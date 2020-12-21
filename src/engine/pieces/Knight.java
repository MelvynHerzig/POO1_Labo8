package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.rules.JumpingHorseRule;

public class Knight extends Piece implements ChessView.UserChoice
{
    public Knight(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.KNIGHT, x, y, board, new JumpingHorseRule(board));
    }

    @Override
    public String textValue()
    {
        return toString();
    }
}
