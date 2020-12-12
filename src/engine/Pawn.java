package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Pawn extends PieceSpecialFirstMove
{
    private int directionFactor;

    boolean moved2Cases;

    Pawn(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.PAWN, x, y);
        directionFactor = this.color == PlayerColor.BLACK ? -1 : 1;
        moved2Cases = false;
    }

    @Override
    ArrayList<Movement> canMove(Board board, int toX, int toY)
    {
        ArrayList<Movement> moves = new ArrayList<>();

        int diffX = Math.abs(getX() - toX);
        int diffY = toY - getY();

        Piece pDest = board.getPiece(toX, toY);

        if (diffX == 1 && diffY * directionFactor == 1)
        {
            if (pDest == null)
            {
                return enPassant(board, toX, toY);
            }
            else if(!board.alliesBox(this.color, toX, toY))
            {
                moves.add(new Movement(this, pDest, toX, toY));
            }

            return moves;
        }

        if(diffX == 0 && diffY * directionFactor > 0 && board.getPiece(toX, toY) == null)
        {
            if(hasMoved && Math.abs(diffY) == 2)
            {
                return moves;
            }

            moves.add(new Movement(this, pDest, toX, toY));
        }

        return moves;
    }

    ArrayList<Movement> enPassant(Board board, int toX, int toY)
    {
        ArrayList<Movement> moves = new ArrayList<>();
        Piece pDest = board.getPiece(toX, getY());

        if (pDest.type == PieceType.PAWN && pDest.color != this.color && ((Pawn) pDest).moved2Cases)
        {
            moves.add(new Movement(this, pDest, toX, toY));
            return moves;
        }
        return moves;
    }
}
