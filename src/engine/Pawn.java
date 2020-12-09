package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

class Pawn extends PieceSpecialFirstMove
{
    private int directionFactor;

    Pawn pawnPassant;

    boolean moved2Cases;

    Pawn(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.PAWN, x, y);
        directionFactor = this.color == PlayerColor.BLACK ? -1 : 1;
        moved2Cases = false;
    }

    @Override
    Movement canMove(Piece[][] board, int toX, int toY)
    {
        int diffX = Math.abs(this.x - toX);
        int diffY = toY - this.y;

        Piece pDest = board[toY][toX];

        if (diffX == 1 && diffY * directionFactor == 1)
        {
            if (pDest == null)
            {
                return enPassant(board, toX);
            }

            return super.canMove(board, toX, toY);
        }

        diffY = Math.abs(diffY);

        if (board[toY][toX] != null || diffY > 2 || diffX != 0)
        {
            return Movement.IMPOSSIBLE;
        }

        if(hasMoved && diffY == 2)
        {
            return Movement.IMPOSSIBLE;
        }

        moved2Cases = diffY == 2;

        return Movement.STANDARD;
    }

    Movement enPassant(Piece[][] board, int toX)
    {
        Piece pDest = board[this.y][toX];

        if (pDest.type == PieceType.PAWN && pDest.color != this.color && ((Pawn) pDest).moved2Cases)
        {
            pawnPassant = (Pawn) pDest;
            return Movement.PASSANT;
        }
        return Movement.IMPOSSIBLE;
    }
}
