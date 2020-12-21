package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.rules.LinearRule;
import engine.rules.AngularRule;

public class King extends PieceSpecialFirstMove
{
    public King(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.KING, x, y, board, new AngularRule(board, true), new LinearRule(board, true));
    }
}
