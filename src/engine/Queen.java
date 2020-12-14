package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

class Queen extends Piece implements ChessView.UserChoice
{
    Queen(PlayerColor aColor, int x, int y)
    {
        super(aColor, PieceType.QUEEN, x, y);
    }

    /**
     * Calcule et retourne tous les déplacements de la reine égocentriquement.
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
            movements.add(new Movement(this, x+offset, y+offset));
            if(!board.isFreeSpot(x+offset, y+offset) && !board.isAllySpot(color,x+offset, y+offset)) break;
            ++offset;
        }

        //En haut-gauche
        offset = 1;
        while(baseCheck( board, x-offset, y+offset))
        {
            movements.add(new Movement(this, x-offset, y+offset));
            if(!board.isFreeSpot(x-offset, y+offset) && !board.isAllySpot(color,x-offset, y+offset)) break;
            ++offset;
        }

        //En bas-droite
        offset = 1;
        while(baseCheck( board, x+offset, y-offset))
        {
            movements.add(new Movement(this, x+offset, y-offset));
            if(!board.isFreeSpot(x+offset, y-offset) && !board.isAllySpot(color,x+offset, y-offset)) break;
            ++offset;
        }

        //En bas-gauche
        offset = 1;
        while(baseCheck( board, x-offset, y-offset))
        {
            movements.add(new Movement(this, x-offset, y-offset));
            if(!board.isFreeSpot(x-offset, y-offset) && !board.isAllySpot(color,x-offset, y-offset)) break;
            ++offset;
        }

       offset = 1;
        //En haut
        while(baseCheck( board, x, y+offset))
        {
            movements.add(new Movement(this, x, y+offset));
            if(!board.isFreeSpot( x, y+offset) && !board.isAllySpot(color, x, y+offset)) break;
            ++offset;
        }

        //En bas
        offset = 1;
        while(baseCheck( board, x, y-offset))
        {
            movements.add(new Movement(this, x, y-offset));
            if(!board.isFreeSpot( x, y-offset) && !board.isAllySpot(color, x, y-offset)) break;
            ++offset;
        }

        //A droite
        offset = 1;
        while(baseCheck( board, x+offset, y))
        {
            movements.add(new Movement(this, x+offset, y));
            if(!board.isFreeSpot( x+offset, y) && !board.isAllySpot(color, x+offset, y)) break;
            ++offset;
        }

        //A gauche
        offset = 1;
        while(baseCheck( board, x-offset, y))
        {
            movements.add(new Movement(this, x-offset, y));
            if(!board.isFreeSpot( x-offset, y) && !board.isAllySpot(color, x-offset, y)) break;
            ++offset;
        }

        return  movements;
    }

   @Override
   public String textValue()
   {
      return "Queen";
   }
}
