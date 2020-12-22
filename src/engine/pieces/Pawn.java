package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.rules.Rule;
import engine.rules.PawnAttackRule;
import engine.rules.PawnForwardRule;

import java.util.LinkedList;

/**
 * Classe modélisant une tour.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class Pawn extends PieceSpecialFirstMove
{
    private int directionFactor;
    private boolean moved2boxes;
    private int promotionLine;

    /**
     * Constructeur.
     * @param color Couleur de la pièce.
     * @param x Position x de la pièce.
     * @param y Position y de la pièce.
     * @param promotion No de ligne de promotion.
     * @param board Échiquier sur lequel elle se trouve.
     */
    public Pawn(PlayerColor color, int x, int y, int promotion, Board board)
    {
        super(color, PieceType.PAWN, x, y, board);
        directionFactor = this.color == PlayerColor.BLACK ? -1 : 1;
        moved2boxes = false;
        promotionLine = promotion;
        rules = new LinkedList<Rule>();
        rules.add(new PawnAttackRule(board, this));
        rules.add(new PawnForwardRule(board, this));
    }

    /**
     * Retourne si la pièce a déjà effectué son bond de 2 case
     * @return Retourne vrai si la pièce a sauté de deux cases.
     */
    public boolean getMoved2()
    {
        return moved2boxes;
    }

    /**
     * Indique à la pièce qu'elle a sauté de deux cases.
     */
    public void setMoved2()
    {
        moved2boxes = true;
    }

    /**
     * Retourne la ligne de promotion de la pièce.
     * @return Le numero de ligne de promotion.
     */
    public int getPromotionLine()
    {
        return promotionLine;
    }

    /**
     * Retourne la direction de déplacement de la pièce.
     * @return 1 si la pièce monte sur l'échiquer sinon -1.
     */
    public int getDirectionFactor()
    {
        return directionFactor;
    }

    /**
     * Copie l'état d'une pièce other. Si la pièce n'est pas une instance de Pawn
     * la fonction est sans effet.
     * @param other Pièce à copier.
     */
    public void copyState(Piece other)
    {
        if(!(other instanceof Pawn)) return;

        super.copyState(other);
        moved2boxes = ((Pawn) other).moved2boxes;
    }

}
