package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Knight extends Piece
{
    Knight(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.KNIGHT, x, y);
    }

    @Override
    ArrayList<Movement> canMove(Board board, int toX, int toY)
    {
        ArrayList<Movement> moves = new ArrayList<>();
        Piece pDest = board.getPiece(toX, toY);

        int x = Math.abs(this.getX() - toX);
        int y = Math.abs(this.getY() - toY);

        if((board.freeCase(toX, toY) || !board.alliesCase(this.color, toX, toY)) && x * y == 2)
        {
            moves.add(new Movement(this, pDest, toX, toY));
        }

        return moves;

    }
}
