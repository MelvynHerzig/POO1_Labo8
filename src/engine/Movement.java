package engine;


class Movement
{
    private final Piece toMove;
    private final Piece toKill;

    private final int toX;
    private final int toY;

    Movement(Piece toMove, Piece toKill, int toX, int toY)
    {
        this.toMove = toMove;
        this.toKill = toKill;

        this.toX = toX;
        this.toY = toY;
    }

    Piece getPieceToKill()
    {
        return toKill;
    }

    Piece getPieceToMove()
    {
        return toMove;
    }

    int getToX()
    {
        return toX;
    }

    int getToY()
    {
        return toY;
    }

}
