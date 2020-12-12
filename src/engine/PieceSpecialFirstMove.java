package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

abstract class PieceSpecialFirstMove extends Piece
{
    protected boolean hasMoved;

    PieceSpecialFirstMove(PlayerColor aColor, PieceType aType, int x,
                                 int y)
    {
        super(aColor, aType, x, y);
        hasMoved = false;
    }

    protected boolean hasMoved()
    {
        return hasMoved;
    }

    protected boolean checkSpecialFirstMove()
    {
        return !hasMoved;
    }

}
