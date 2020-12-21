package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.rules.Rule;
import engine.Board;

/**
 * Classe modélisant une pièce avec un premier mouvement spécial.
 * Elle comprend le roi, la tour et le pion.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 12.12.2020
 */
public abstract class PieceSpecialFirstMove extends Piece implements Cloneable
{
    protected boolean hasMoved;

    /**
     * Constructeur
     * @param color Couleur de la pièce.
     * @param type Type de la pièce.
     * @param x Position x.
     * @param y Position y.
     */
    protected PieceSpecialFirstMove(PlayerColor color, PieceType type, int x, int y, Board board, Rule ... rules)
    {
        super(color, type, x, y, board, rules);
        hasMoved = false;
    }

    /**
     * @return Vrai si la pièce s'est déjà déplacée sinon false
     */
    public boolean hasMoved()
    {
        return hasMoved;
    }

    /**
     * Indique que la pièce s'est déplacée au moins une fois.
     */
    public void setMoved()
    {
        hasMoved = true;
    }

    @Override
    public void copyState(Piece other)
    {
        super.copyState(other);

        if(other instanceof PieceSpecialFirstMove)
            hasMoved = ((PieceSpecialFirstMove) other).hasMoved;
    }
}
