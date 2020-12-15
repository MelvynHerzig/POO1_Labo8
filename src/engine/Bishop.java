package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Bishop extends Piece implements ChessView.UserChoice
{
    Bishop(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.BISHOP, x, y);
    }


    /**
     * Calcule et retourne tous les déplacements du fou égocentriquement.
     * @param board Echiquier sur lequel évaluer les déplacements possibles.
     * @return Retourne la liste des déplacements possibles.
     */
    ArrayList<Movement> possibleMovements(Board board)
    {
        ArrayList<Movement> movements = new ArrayList<Movement>();
        int offset = 1;
        //En haut-droite
        while(baseCheck( board, x+offset, y+offset))
        {
            movements.add(new Movement(x, y, x+offset, y+offset));
            if(!board.isFreeSpot(x+offset, y+offset) && !board.isAllySpot(color,x+offset, y+offset)) break;
            ++offset;
        }

        //En haut-gauche
        offset = 1;
        while(baseCheck( board, x-offset, y+offset))
        {
            movements.add(new Movement(x, y, x-offset, y+offset));
            if(!board.isFreeSpot(x-offset, y+offset) && !board.isAllySpot(color,x-offset, y+offset)) break;
            ++offset;
        }

        //En bas-droite
        offset = 1;
        while(baseCheck( board, x+offset, y-offset))
        {
            movements.add(new Movement(x, y, x+offset, y-offset));
            if(!board.isFreeSpot(x+offset, y-offset) && !board.isAllySpot(color,x+offset, y-offset)) break;
            ++offset;
        }

        //En bas-gauche
        offset = 1;
        while(baseCheck( board, x-offset, y-offset))
        {
            movements.add(new Movement(x, y, x-offset, y-offset));
            if(!board.isFreeSpot(x-offset, y-offset) && !board.isAllySpot(color,x-offset, y-offset)) break;
            ++offset;
        }

        return  movements;

    }

    @Override
    public String textValue()
    {
        return toString();
    }
}
