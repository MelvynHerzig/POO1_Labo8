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

    PlayerColor getColor()
    {
        return color;
    }

    PieceType getPieceType()
    {
        return type;
    }

    boolean canMove(Piece[][] board, int toX, int toY)
    {

        return board[toY][toX] == null || board[toY][toX].color != this.color;
    }

    void move(Piece[][] board, ChessView view, int toX, int toY)
    {
        board[toY][toX] = this;
        board[this.y][this.x] = null;

        view.removePiece(this.x, this.y);

        this.x = toX;
        this.y = toY;

        view.putPiece(this.type, this.color, this.x, this.y);
    }

}
