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
    Movement canMove(Piece[][] board, int toX, int toY)
    {
        return (super.canMove(board, toX, toY) == Movement.STANDARD &&
                checkAngularMovement(board, this.x, this.y, toX, toY)) ? Movement.STANDARD : Movement.IMPOSSIBLE;
    }
}
