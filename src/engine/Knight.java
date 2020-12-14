package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Knight extends Piece implements ChessView.UserChoice
{
    Knight(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.KNIGHT, x, y);
    }

    /**
     * Calcule et retourne tous les déplacements du fou égocentriquement.
     * @param board Echiquier sur lequel évaluer les déplacements possibles.
     * @return Retourne la liste des déplacements possibles.
     */
    ArrayList<Movement> possibleMovements(Board board)
    {
        ArrayList<Movement> movements = new ArrayList<Movement>();

        //Deux cases au dessus et en dessous
        for(int offsetX = -1; offsetX <= 1; offsetX +=2)
        {
            if(baseCheck(board, x+offsetX, y+2))
                movements.add(new Movement(this, x+offsetX, y+2));
            if(baseCheck(board, x+offsetX, y-2))
                movements.add(new Movement(this, x+offsetX, y-2));
        }

        //Deux cases à droite et à gauche
        for(int offsetY = -1; offsetY <= 1; offsetY +=2)
        {
            if(baseCheck(board, x+2, y+offsetY))
                movements.add(new Movement(this, x+2, y+offsetY));
            if(baseCheck(board, x-2, y-offsetY))
                movements.add(new Movement(this, x-2, y-offsetY));
        }


        return  movements;

    }

    @Override
    public String textValue()
    {
        return "Knight";
    }
}
