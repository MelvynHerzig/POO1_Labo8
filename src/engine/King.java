package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

class King extends PieceSpecialFirstMove implements LinearMovement
{
    private boolean castling;

    King(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.KING, x, y);
        castling = false;
    }

    @Override
    boolean canMove(Piece[][] board, int toX, int toY)
    {

        if (castling = checkForCastling(board, toX))
        {
            return true;
        }

        int x = Math.abs(this.x - toX);
        int y = Math.abs(this.y - toY);

        return super.canMove(board, toX, toY) && x < 2 && y < 2;
    }

    void move(Piece[][] board, ChessView view, int toX, int toY)
    {
        if (castling)
        {
            Rook r = (Rook)board[toY][toX];
            if(r.x < this.x)
            {
                doCastling(board, view, r, -1);
            }
            else
            {
                doCastling(board, view, r, 1);
            }
        }
        else
        {
            super.move(board, view, toX, toY);
        }
    }

    private boolean checkForCastling(Piece[][] board, int toX)
    {
        Piece pDest = board[this.y][toX];
        return super.checkSpecialFirstMove() && pDest != null && pDest.type == PieceType.ROOK && !((Rook) pDest).hasMoved() && checkLinearMovement(board, this.x, this.y, toX, this.y);
    }

    private void doCastling(Piece[][] board, ChessView view, Rook r, int factor)
    {
        super.move(board, view, this.x + (2*factor), this.y);
        r.move(board, view, this.x - factor, r.y);
    }

}
