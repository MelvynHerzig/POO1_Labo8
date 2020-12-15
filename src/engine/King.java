package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class King extends PieceSpecialFirstMove
{
    King(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.KING, x, y);
    }

    /**
     * Calcule et retourne tous les déplacements du fou égocentriquement.
     * @param board Echiquier sur lequel évaluer les déplacements possibles.
     * @return Retourne la liste des déplacements possibles.
     */
    ArrayList<Movement> possibleMovements(Board board)
    {
        ArrayList<Movement> movements = new ArrayList<Movement>();

        //Vérifications des 8 cases adjacente
        // 3 supérieures et inférieurs
        for(int offsetX = -1; offsetX <= 1; ++offsetX)
        {
            if(baseCheck(board, x + offsetX, y+1))
                movements.add(new Movement(x, y, x+offsetX, y+1));

            if(baseCheck(board, x + offsetX, y-1))
                movements.add(new Movement(x, y, x+offsetX, y-1));
        }
        // cases gauche et droite
        for(int offsetX = -1; offsetX <= 1; offsetX += 2)
        {
            if (baseCheck(board, x + offsetX, y))
                movements.add(new Movement(x, y, x + offsetX, y));
        }

        boolean castlingPossible = true;
        // Grand Roques
        if(board.isValidPosition(x-4, y) && board.isAllySpot(color,x-4, y))
        {
            if(board.getPiece(x-4,y).getClass() == Rook.class)
            {
                for (int offsetX = 1; offsetX < 4; ++offsetX)
                {
                    if (!board.isFreeSpot(x - offsetX, y))
                    {
                        castlingPossible = false;
                        break;
                    }
                }

                if (castlingPossible)
                    movements.add(new CastlingMovement(x, y, x - 2, y, x - 4, y));
            }
        }
        castlingPossible = true;
        // Grand Roques
        if(board.isValidPosition(x+3, y) && board.isAllySpot(color,x+3, y))
        {
            if (board.getPiece(x+3, y).getClass() == Rook.class)
            {
                for (int offsetX = 1; offsetX < 3; ++offsetX)
                {
                    if (!board.isFreeSpot(x + offsetX, y))
                    {
                        castlingPossible = false;
                        break;
                    }
                }
                if (castlingPossible)
                    movements.add(new CastlingMovement(x, y, x + 2, y,x + 3, y));
            }
        }
        return  movements;
    }
}
