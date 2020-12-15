package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Pawn extends PieceSpecialFirstMove
{
    private int directionFactor;
    private boolean moved2boxes;
    private int promotionLine;

    Pawn(PlayerColor aColor, int x, int y, int promotion)
    {
        super(aColor, PieceType.PAWN, x, y);
        directionFactor = this.color == PlayerColor.BLACK ? -1 : 1;
        moved2boxes = false;
        promotionLine = promotion;
    }

    /**
     * Calcule et retourne tous les déplacements du fou égocentriquement.
     * @param board Echiquier sur lequel évaluer les déplacements possibles.
     * @return Retourne la liste des déplacements possibles.
     */
    ArrayList<Movement> possibleMovements(Board board)
    {
        ArrayList<Movement> movements = new ArrayList<Movement>();

        //Avancer de 1 case
        if(board.isFreeSpot(x, y + directionFactor))
        {
            movements.add(new PawnMovement(x, y, x, y + directionFactor, -1,-1));

            //Avancer de 2 cases
            if(board.isValidPosition(x, y + 2 * directionFactor) && !hasMoved() && board.isFreeSpot(x, y + 2 * directionFactor))
                movements.add(new PawnMovement(x, y, x, y + 2 * directionFactor, -1,-1));
        }

        //Déplacements diagonal gauche
        if(board.isValidPosition(x-1, y + directionFactor) && !board.isAllySpot(color, x-1, y + directionFactor))
        {
            //Vérification de la prise en passant.
            if(!board.isFreeSpot(x-1, y) && board.getPiece(x-1, y).type == type)
                movements.add(new PawnMovement(x, y, x-1, y + directionFactor, x-1 , y));
            else if (!board.isFreeSpot(x-1, y + directionFactor) && !board.isAllySpot(color,x-1, y + directionFactor))
                movements.add(new PawnMovement(x, y, x-1, y + directionFactor, -1, -1));
        }

        //Déplacements diagonal droite
        if(board.isValidPosition(x+1, y + directionFactor) && !board.isAllySpot(color, x+1, y + directionFactor))
        {
            //Vérification de la prise en passant.
            if(!board.isFreeSpot(x+1, y) && board.getPiece(x+1, y).type == type)
                movements.add(new PawnMovement(x, y,  x+1, y + directionFactor, x+1, y));
            else if (!board.isFreeSpot(x+1, y + directionFactor) && !board.isAllySpot(color,x+1, y + directionFactor))
                movements.add(new PawnMovement(x, y, x+1, y + directionFactor, -1, -1));
        }

        return  movements;
    }

    boolean getMoved2()
    {
        return moved2boxes;
    }

    void setMoved2()
    {
        moved2boxes = true;
    }

    int getPromotionLine()
    {
        return promotionLine;
    }

}
