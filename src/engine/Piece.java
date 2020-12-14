package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import java.util.ArrayList;

/**
 * Classe modélisant une pièce de base du jeu d'un jeu d'échec.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
abstract class Piece implements Cloneable
{
    protected PlayerColor color;
    protected PieceType type;

    protected int x;
    protected int y;

    /**
     * Constructeur d'une pièce
     * @param aColor Couleur de la pièce.
     * @param aType Type de la pièce.
     * @param x Position x.
     * @param y Position y.
     */
    Piece(PlayerColor aColor, PieceType aType, int x, int y)
    {
        this.color = aColor;
        this.type = aType;

        this.x = x;
        this.y = y;
    }

    /**
     * @return Retourne la couleur de la pièce.
     */
    PlayerColor getColor()
    {
        return color;
    }

    /**
     * @return Retourne le type de pièce.
     */
    PieceType getType()
    {
        return type;
    }

    /**
     * @return Retourne la position X.
     */
    int getX()
    {
        return x;
    }

    /**
     * @return Retourne la position Y.
     */
    int getY()
    {
        return y;
    }

    /**
     * Initialise la valeur Y d'une pièce.
     * @param x Position x à assigner.
     */
    void setX(int x)
    {
        this.x = x;
    }

    /**
     * Initialise la valeur Y d'une pièce.
     * @param y Position y à assigner.
     */
    void setY(int y)
    {
        this.y = y;
    }

    /**
     * Vérification puisse se déplacer
     * @param board Echiquier sur lequel vérifier la condition.
     * @param toX Position x de destination.
     * @param toY Position y de destination.
     * @return Retourne faux si la case est occupée par un allié sinon vrai.
     */
    boolean baseCheck(Board board, int toX, int toY)
    {
        return board.isValidPosition(toX, toY) && (board.getPiece(toX, toY) == null || board.getPiece(toX, toY).color != color);
    }

    /**
     * Demande à la pièce de fournir une liste de ses mouvement possible.
     * Elle ne tient pas compte d'une éventuelle mise en échec de son roi.
     * @param board Echiquier sur lequel évaluer les déplacements possibles.
     * @return Retourne une liste des movements faisable par la pièce "égoistement".
     */
    abstract ArrayList<Movement> possibleMovements(Board board);

    /**
     * Demande à la pièce si elle peut se déplacer aux coordonnées toX toY
     * @param board Board sur lequel évaluer la faisabilité du déplacement
     * @param toX Destination x.
     * @param toY Destination y.
     * @return Retourne le mouvement correspondant si faisable sinon null.
     */
    Movement canMove(Board board, int toX, int toY)
    {
        ArrayList<Movement> movements = possibleMovements(board);

        for(Movement m: movements)
        {
            if(m.getToX() == toX && m.getToY() == toY) return m;
        }

        return null;
    }
    /**
     * Vérifie si deux pièces sont égales.
     * @param p Pièce à comparer.
     * @return Vrai en cas d'égalité sinon faux.
     */
    public boolean equal(Piece p)
    {
        if(p == null) return false;

        return p.x == x && p.y == y && p.type == type && p.color == color;
    }

    /**
     * Copie une pièce.
     * @return Retourne la copie d'une pièce.
     */
    public Piece clone()
    {
        Piece p = null;
        try
        {
            p = (Piece) super.clone();
        }
        catch (CloneNotSupportedException e){ e.printStackTrace(); }

        return p;
    }
}
