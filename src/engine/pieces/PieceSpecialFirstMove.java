package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

/**
 * Classe modélisant une pièce avec un premier mouvement spécial. Elle doit
 * donc garder une trace du fait qu'elle ait déjà été déplacée.
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
     * @param board Board sur lequel la pièce se trouve.
     */
    protected PieceSpecialFirstMove(PlayerColor color, PieceType type, int x, int y, Board board)
    {
        super(color, type, x, y, board);
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

    /**
     * Pour une pièce donnée, copie son état. Si other n'est pas du même type
     * que PieceSpecialFirstMove, copyState est sans effet.
     * @param other Pièce à copier.
     */
    @Override
    public void copyState(Piece other)
    {
        if(!(other instanceof PieceSpecialFirstMove)) return;

        super.copyState(other);
        hasMoved = ((PieceSpecialFirstMove) other).hasMoved;
    }
}
