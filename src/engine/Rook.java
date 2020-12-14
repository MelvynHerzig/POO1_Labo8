package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

/**
 * Classe modélisant une tour.
 * Inclus deux fonctions "controller" qui permettent de
 * calculer les movements possibles.
 */
class Rook extends PieceSpecialFirstMove
{

    /**
     * Constructeur.
     * @param color Couleur de la tour.
     * @param x Position x.
     * @param y Position y.
     */
    Rook(PlayerColor color, int x, int y)
    {
        super(color, PieceType.ROOK, x, y);
    }

    /**
     * Calcule et retourne tous les déplacements de la tour égocentriquement.
     * @param board Echiquier sur lequel évaluer les déplacements possibles.
     * @return Retourne la liste des déplacements possibles.
     */
    ArrayList<Movement> possibleMovements(Board board)
    {
        ArrayList<Movement> movements = new ArrayList<Movement>();
        int offset = 1;
        //En haut
       while(baseCheck( board, x, y+offset))
       {
           movements.add(new Movement(this, x, y+offset));
           if(!board.isFreeSpot(x, y+offset) && !board.isAllySpot(color,x, y+offset)) break;
           ++offset;
       }

        //En bas
        offset = 1;
        while(baseCheck( board, x, y-offset))
        {
            movements.add(new Movement(this, x, y-offset));
            if(!board.isFreeSpot(x, y-offset) && !board.isAllySpot(color, x, y-offset)) break;
            ++offset;
        }

        //A droite
        offset = 1;
        while(baseCheck( board, x+offset, y))
        {
            movements.add(new Movement(this, x+offset, y));
            if(!board.isFreeSpot(x+offset, y) && !board.isAllySpot(color, x+offset, y)) break;
            ++offset;
        }

        //A gauche
        offset = 1;
        while(baseCheck( board, x-offset, y))
        {
            movements.add(new Movement(this, x-offset, y));
            if(!board.isFreeSpot(x-offset, y) && !board.isAllySpot(color, x-offset, y)) break;
            ++offset;
        }

        return  movements;

    }
}
