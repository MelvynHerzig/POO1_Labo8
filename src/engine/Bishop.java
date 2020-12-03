package engine;

import chess.PieceType;
import chess.PlayerColor;

class Bishop extends Piece implements AngularMovement
{
    Bishop(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.BISHOP, x, y);
    }

    @Override
    boolean canMove(Piece[][] board, int toX, int toY)
    {



        return super.canMove(board, toX, toY) &&
                checkAngularMovement(board, this.x, this.y, toX, toY);
    }
}
