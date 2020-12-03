package engine;

import chess.PieceType;
import chess.PlayerColor;

class Pawn extends PieceSpecialFirstMove
{
    private boolean moved2Cases;

    Pawn(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.PAWN, x, y);
    }

    void setMoved2Cases(boolean hasMoved2Cases)
    {
        this.moved2Cases = hasMoved2Cases;
    }

    boolean hasMoved2Cases()
    {
        return moved2Cases;
    }


    @Override
    boolean canMove(Piece[][] board, int toX, int toY)
    {

        return false;
    }
}
