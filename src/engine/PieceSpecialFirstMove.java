package engine;

import chess.PieceType;
import chess.PlayerColor;

/**
 * Classe modélisant une pièce avec un premier mouvement spécial.
 * Elle comprend le roi, la tour et le pion.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 12.12.2020
 */
abstract class PieceSpecialFirstMove extends Piece implements Cloneable
{
    protected boolean hasMoved;

    /**
     * Constructeur
     * @param color Couleur de la pièce.
     * @param type Type de la pièce.
     * @param x Position x.
     * @param y Position y.
     */
    PieceSpecialFirstMove(PlayerColor color, PieceType type, int x, int y)
    {
        super(color, type, x, y);
        hasMoved = false;
    }

    /**
     * @return Vrai si la pièce s'est déjà déplacée sinon false
     */
    protected boolean hasMoved()
    {
        return hasMoved;
    }

    /**
     * Indique que la pièce s'est déplacée au moins une fois.
     */
    protected void setMoved()
    {
        hasMoved = true;
    }
}
