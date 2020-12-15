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
    private final int fromX;
    private final int fromY;

    private final int toX;
    private final int toY;

    Movement(int fromX, int fromY, int toX, int toY)
    {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    int getFromX()
    {
        return fromX;
    }

    int getFromY()
    {
        return fromY;
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
