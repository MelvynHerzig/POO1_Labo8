package engine;

interface AngularMovement
{
    default boolean checkAngularMovement(Board board, int fromX, int fromY, int toX, int toY)
    {
        int diffX = fromX - toX;
        int diffY = fromY - toY;

        int factorX;
        int factorY;

        if (Math.abs(diffX) != Math.abs(diffY)) return false;

        factorX = diffX < 0 ? 1 : -1;
        factorY = diffY < 0 ? 1 : -1;

        for(int i = 1; i < Math.abs(diffX); ++i)
        {
            Piece p = board.getPiece(fromX + (i*factorX), fromY + (i*factorY));

            if(p != null) return false;
        }

        return true;
    }
}
