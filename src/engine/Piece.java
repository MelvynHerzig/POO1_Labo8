package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

abstract class Piece
{
    protected PlayerColor color;
    protected PieceType type;

    protected int x;
    protected int y;

    Piece(PlayerColor aColor, PieceType aType, int x, int y)
    {
        this.color = aColor;
        this.type = aType;

        this.x = x;
        this.y = y;

    }

    Movement canMove(Piece[][] board, int toX, int toY)
    {

        return (board[toY][toX] == null || board[toY][toX].color != this.color) ? Movement.STANDARD : Movement.IMPOSSIBLE;
    }

}
