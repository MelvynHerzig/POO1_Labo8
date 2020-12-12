package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Queen extends Piece implements AngularMovement, LinearMovement
{
    Queen(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.QUEEN, x, y);
    }

    @Override
    ArrayList<Movement> canMove(Board board, int toX, int toY)
    {
        ArrayList<Movement> moves = new ArrayList<>();
        Piece pDest = board.getPiece(toX, toY);

        if((board.freeBox(toX, toY) || !board.alliesBox(this.color, toX,toY)) &&
                (
                        checkLinearMovement(board, getX(), getY(), toX, toY)
                     || checkAngularMovement(board, getX(), getY(), toX, toY))
                )
        {
            moves.add(new Movement(this, pDest, toX, toY));
        }

        return moves;
    }
}
