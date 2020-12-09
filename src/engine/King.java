package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

class King extends PieceSpecialFirstMove implements LinearMovement
{
    Rook rookCastled;

    King(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.KING, x, y);
    }

    @Override
    Movement canMove(Piece[][] board, int toX, int toY)
    {

        int diffX = Math.abs(this.x - toX);
        int diffY = Math.abs(this.y - toY);

        Piece pDest = this.x < toX ? board[this.y][7] : board[this.y][0];

        if (diffX == 2 && !hasMoved && toY == this.y && checkForCastling(board, pDest))
        {
            rookCastled = (Rook) pDest;
            return Movement.CASTLING;
        }

        return (super.canMove(board, toX, toY) == Movement.STANDARD && diffX < 2 && diffY < 2) ? Movement.STANDARD : Movement.IMPOSSIBLE;
    }

    private boolean checkForCastling(Piece[][] board, Piece pDest)
    {

        return super.checkSpecialFirstMove() && pDest != null && pDest.color == this.color && pDest.type == PieceType.ROOK && !((Rook) pDest).hasMoved() && checkLinearMovement(board, this.x, this.y, pDest.x, pDest.y);
    }

}
