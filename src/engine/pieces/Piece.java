package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.movements.Movement;
import engine.rules.Rule;
import java.util.LinkedList;

/**
 * Classe modélisant une pièce de base d'un jeu d'échec.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public abstract class Piece implements Cloneable
{
    protected PlayerColor color;
    protected PieceType type;

    protected int x;
    protected int y;

    protected Board board;

    protected LinkedList<Rule> rules;

    /**
     * Constructeur d'une pièce
     * @param aColor Couleur de la pièce.
     * @param aType Type de la pièce.
     * @param x Position x.
     * @param y Position y.
     * @param board Board sur lequel la pièce se trouve.
     */
    protected Piece(PlayerColor aColor, PieceType aType, int x, int y, Board board)
    {
        this.color = aColor;
        this.type = aType;

        this.x = x;
        this.y = y;

        this.board = board;
    }

    /**
     * @return Retourne la couleur de la pièce.
     */
    public PlayerColor getColor()
    {
        return color;
    }

    /**
     * @return Retourne le type de pièce.
     */
    public PieceType getType()
    {
        return type;
    }

    /**
     * @return Retourne la position X.
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return Retourne la position Y.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Initialise la valeur X d'une pièce.
     * @param x Position x à assigner.
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * Initialise la valeur Y d'une pièce.
     * @param y Position y à assigner.
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * Demande à la pièce de fournir une liste de ses mouvement possible.
     * Elle ne tient pas compte d'une éventuelle mise en échec de son roi.
     * @return Retourne une liste des movements faisables par la pièce "égoistement".
     */
    public LinkedList<Movement> possibleMovements()
    {
        LinkedList<Movement> movements = new LinkedList<Movement>();

        for(Rule r : rules)
        {
            movements.addAll(r.possibleMovements());
        }

        return movements;
    }

    /**
     * Demande à la pièce si elle peut se déplacer aux coordonnées toX toY
     * @param toX Destination x.
     * @param toY Destination y.
     * @return Retourne le mouvement correspondant si faisable sinon null.
     */
    public Movement canMove(int toX, int toY)
    {
        LinkedList<Movement> movements = possibleMovements();

        for(Movement m: movements)
        {
            if(m.getToX() == toX && m.getToY() == toY) return m;
        }

        return null;
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

    /**
     * Pour une pièce donnée, copie son état.
     * @param other Pièce à copier.
     */
    public void copyState(Piece other)
    {
        x = other.x;
        y = other.y;
        rules = other.rules;
    }

    /**
     * Utilise getSimpleName pour retourner le nom de l'objet.
     * @return Retourne le nom de l'objet.
     */
    public String toString()
    {
        return getClass().getSimpleName();
    }
}
