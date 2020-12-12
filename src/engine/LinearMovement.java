package engine;

interface LinearMovement
{
    default boolean checkLinearMovement(Board board, int fromX, int fromY, int toX, int toY)
    {
        int from;
        int to;

        if(fromX != toX && fromY != toY) return false;

        if (fromX == toX)
        {
            from = Math.min(fromY, toY);
            to = Math.max(fromY, toY);
        }
        else
        {
            from = Math.min(fromX, toX);
            to = Math.max(fromX, toX);
        }


        for (from += 1; from < to; ++from)
        {
            Piece p = fromX == toX ? board.getPiece(fromX, from) : board.getPiece(from, fromY);

            if(p != null) return false;
        }

        return true;
    }
}
