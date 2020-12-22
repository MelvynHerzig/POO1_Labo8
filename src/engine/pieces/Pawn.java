package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.rules.Rule;
import engine.rules.PawnAttackRule;
import engine.rules.PawnForwardRule;

import java.util.LinkedList;

public class Pawn extends PieceSpecialFirstMove
{
    private int directionFactor;
    private boolean moved2boxes;
    private int promotionLine;

    public Pawn(PlayerColor aColor, int x, int y, int promotion, Board board)
    {
        super(aColor, PieceType.PAWN, x, y, board);
        directionFactor = this.color == PlayerColor.BLACK ? -1 : 1;
        moved2boxes = false;
        promotionLine = promotion;
        rules = new LinkedList<Rule>();
        rules.add(new PawnAttackRule(board, this));
        rules.add(new PawnForwardRule(board, this));
    }

    public boolean getMoved2()
    {
        return moved2boxes;
    }

    public void setMoved2()
    {
        moved2boxes = true;
    }

    public int getPromotionLine()
    {
        return promotionLine;
    }

    public int getDirectionFactor()
    {
        return directionFactor;
    }

    public void copyState(Piece other)
    {
        super.copyState(other);

        if(other instanceof Pawn)
            moved2boxes = ((Pawn) other).moved2boxes;
    }

}
