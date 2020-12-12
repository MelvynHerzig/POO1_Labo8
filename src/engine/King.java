package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class King extends PieceSpecialFirstMove implements LinearMovement
{
    King(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.KING, x, y);
    }

    @Override
    ArrayList<Movement> canMove(Board board, int toX, int toY)
    {
        ArrayList<Movement> moves = new ArrayList<>();

        int diffX = Math.abs(getX() - toX);
        int diffY = Math.abs(getY() - toY);

        Piece pDest = getX() < toX ? board.getPiece(7, getY()) :
                board.getPiece(0, getY());

        if (diffX == 2 && !hasMoved && toY == getY() && checkForCastling(board, pDest))
        {
            int factor = toX < getX() ? -1 : 1;

            hasMoved = true;
            moves.add(new Movement(this, null, getX() + factor, toY));
            moves.add(new Movement(this, null, toX, toY));
            moves.add(new Movement(pDest, null, toX - factor, toY));
        }
        else if ((board.freeBox(toX, toY) || !board.alliesBox(this.color,
                toX, toY)) && diffX < 2 && diffY < 2)
        {
            hasMoved = true;
            pDest = board.getPiece(toX, toY);
            moves.add(new Movement(this, pDest, toX, toY));
        }
        return moves;
    }

    private boolean checkForCastling(Board board, Piece pDest)
    {

        return  !super.hasMoved()
                && pDest != null && pDest.color == this.color
                && pDest.type == PieceType.ROOK
                && !((Rook) pDest).hasMoved()
                && checkLinearMovement(board, getX(), getY(), pDest.getX(), pDest.getY());
    }

}
