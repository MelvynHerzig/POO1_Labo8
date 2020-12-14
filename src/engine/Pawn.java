package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Pawn extends PieceSpecialFirstMove
{
    private int directionFactor;

    Pawn(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.PAWN, x, y);
        directionFactor = this.color == PlayerColor.BLACK ? -1 : 1;
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
        if(board.isFreeSpot(x, y + 1 * directionFactor))
        {
            movements.add(new PawnMovement(this, null, x, y + 1 * directionFactor));

            //Avancer de 2 cases
            if(board.isValidPosition(x, y + 2 * directionFactor) && !hasMoved() && board.isFreeSpot(x, y + 2 * directionFactor))
                movements.add(new PawnMovement(this, null, x, y + 2 * directionFactor));
        }

        //Déplacements diagonal gauche
        if(board.isValidPosition(x-1, y + 1 * directionFactor) && !board.isAllySpot(color, x-1, y + 1 * directionFactor))
        {
            //Vérification de la prise en passant.
            if(!board.isFreeSpot(x-1, y) && board.getPiece(x-1, y).type == type)
                movements.add(new PawnMovement(this, (Pawn)board.getPiece(x-1, y), x-1, y + 1 * directionFactor));
            else if (!board.isFreeSpot(x-1, y + 1 * directionFactor) && !board.isAllySpot(color,x-1, y + 1 * directionFactor))
                movements.add(new PawnMovement(this, null, x-1, y + 1 * directionFactor));
        }

        //Déplacements diagonal droite
        if(board.isValidPosition(x+1, y + 1 * directionFactor) && !board.isAllySpot(color, x+1, y + 1 * directionFactor))
        {
            //Vérification de la prise en passant.
            if(!board.isFreeSpot(x+1, y) && board.getPiece(x+1, y).type == type)
                movements.add(new PawnMovement(this, (Pawn)board.getPiece(x+1, y), x+1, y + 1 * directionFactor));
            else if (!board.isFreeSpot(x+1, y + 1 * directionFactor) && !board.isAllySpot(color,x+1, y + 1 * directionFactor))
                movements.add(new PawnMovement(this, null, x+1, y + 1 * directionFactor));
        }

        return  movements;
    }
}
