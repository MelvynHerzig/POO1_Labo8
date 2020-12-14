package engine;

/**
 * Classe modélisant le movement normaux des pièces
 * roi, reine, fou, cavalier, tour.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
class Movement
{
    private final Piece toMove;

    private final int toX;
    private final int toY;

    Movement(Piece toMove, int toX, int toY)
    {
        this.toMove = toMove;

        this.toX = toX;
        this.toY = toY;
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
