package engine;

import chess.PieceType;
import chess.PlayerColor;

class Queen extends Piece implements AngularMovement, LinearMovement
{
    Queen(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.QUEEN, x, y);
    }

    @Override
    boolean canMove(Piece[][] board, int toX, int toY)
    {
        return super.canMove(board, toX, toY) &&
                (
                 checkAngularMovement(board, this.x, this.y, toX, toY) ||
                 checkLinearMovement(board, this.x, this.y, toX, toY)
                );
    }
}
