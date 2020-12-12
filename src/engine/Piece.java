package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

abstract class Piece implements Cloneable
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

    public Piece clone()
    {
        Piece p = null;
        try
        {
            p = (Piece) super.clone();
        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        return p;
    }

    /**
     * Vérifie si deux pièces sont égales.
     * @param p Pièce à comparer.
     * @return Vrai en cas d'égalité sinon faux.
     */
    public boolean equal(Piece p)
    {
        if(p == null) return false;

        return p.x == x && p.y == y && p.type == type && p.color == color;
    }
}
