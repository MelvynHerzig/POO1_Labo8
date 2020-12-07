package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

class Pawn extends PieceSpecialFirstMove
{
    private boolean moved2Cases;
    private int directionFactor;

    Pawn(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.PAWN, x, y);
        directionFactor = this.color == PlayerColor.BLACK ? -1 : 1;
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
        int diffX = Math.abs(toX - this.x);
        int diffY = toY - this.y;

        Piece pDest = board[toY][toX];

        if (diffX == 1 && diffY * directionFactor == 1 && pDest != null && pDest.color != this.color)
        {
            return true;
        }
        else if (diffX == 0 && pDest == null && (this.y + 2 * directionFactor == toY && super.checkSpecialFirstMove() || this.y + directionFactor == toY))
        {
            return true;
        }
        return false;
    }

    void move(Piece[][] board, ChessView view, int toX, int toY)
    {
        if(board[this.y - directionFactor][this.x] == this) board[this.y - directionFactor][this.x] = null;
        int diffX = Math.abs(toX - this.x);
        Piece pDest = board[toY][toX];


        // Prise en passant
        if (diffX == 1 && pDest.type == PieceType.PAWN)
        {
            ((Pawn)pDest).kill(board, view);
        }
        else
        {
            board[this.y + directionFactor][this.x] = this;
        }


        super.move(board, view, toX, toY);
    }

    void kill(Piece[][] board, ChessView view)
    {
        board[this.y][this.x] = null;
        board[this.y - directionFactor][this.x] = board[this.y - directionFactor][this.x] == this ? null : board[this.y - directionFactor][this.x];

        view.removePiece(this.x, this.y);
    }
}
