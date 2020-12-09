package engine;

import chess.PieceType;
import chess.PlayerColor;

class Rook extends PieceSpecialFirstMove implements LinearMovement
{

    Rook(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.ROOK, x, y);
    }

    @Override
    Movement canMove(Piece[][] board, int toX, int toY)
    {
        return (super.canMove(board, toX, toY) == Movement.STANDARD
                && checkLinearMovement(board, this.x, this.y, toX, toY)) ?
                Movement.STANDARD : Movement.IMPOSSIBLE;
    }

}
