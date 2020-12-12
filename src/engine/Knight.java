package engine;

import chess.PieceType;
import chess.PlayerColor;

class Knight extends Piece
{
    Knight(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.KNIGHT, x, y);
    }

    @Override
    Movement canMove(Piece[][] board, int toX, int toY)
    {
        int x = Math.abs(this.x - toX);
        int y = Math.abs(this.y - toY);

        return (super.canMove(board, toX, toY) == Movement.STANDARD && x * y == 2) ? Movement.STANDARD : Movement.IMPOSSIBLE;

    }
}
