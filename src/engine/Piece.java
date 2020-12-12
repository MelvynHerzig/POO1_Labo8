package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

abstract class Piece
{
    protected PlayerColor color;
    protected PieceType type;

    private int x;
    private int y;

    Piece(PlayerColor aColor, PieceType aType, int x, int y)
    {
        this.color = aColor;
        this.type = aType;

        this.x = x;
        this.y = y;
    }

    int getX()
    {
        return x;
    }

    int getY()
    {
        return y;
    }

    void setX(int x)
    {
        this.x = x;
    }

    void setY(int y)
    {
        this.y = y;
    }

    abstract ArrayList<Movement> canMove(Board board, int toX, int toY);

  }
