package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

class Rook extends PieceSpecialFirstMove implements LinearMovement
{

    Rook(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.ROOK, x, y);
    }

    @Override
    boolean canMove(Piece[][] board, int toX, int toY)
    {
        return super.canMove(board, toX, toY)
                && checkLinearMovement(board, this.x, this.y, toX, toY);
    }

}
