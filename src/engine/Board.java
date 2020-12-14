package engine;

import chess.PieceType;
import chess.PlayerColor;
import java.util.ArrayList;

/**
 * Classe modélisant le board d'une partie d'échec.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 12.12.2020
 */
class Board implements Cloneable
{
    // Références sur les différentes pièces en fonctions des cases du board.
    private Piece[][] board;

    private final int size = 8;

    private Piece lastMovedPiece;

    /**
     * Constructeur
     */
    Board()
    {
        reset();
    }

    /**
     * @param x Position x.
     * @param y Position y.
     * @return Retourne une référence sur la pièce en position x y.
     */
    Piece getPiece(int x, int y)
    {
        if(!isValidPosition(x, y)) throw new IllegalArgumentException("Positions inconnues");

        return board[y][x];
    }

    /**
     * Déplace une pièce sur l'échiquier.
     * Si la source est nulle, la fonction est sans effet.
     * @param fromX position x initiale.
     * @param fromY position y initiale.
     * @param toX position x de destination.
     * @param toY position y de destination.
     */
    void movePiece(int fromX, int fromY, int toX, int toY)
    {
        if(!isValidPosition(fromX,fromY)) throw new IllegalArgumentException("Positions from inconnues");
        if(!isValidPosition(toX  ,toY  )) throw new IllegalArgumentException("Positions to inconnues");

        Piece p = getPiece(fromX, fromY);

        //Si la pièce est nulle, sans effet.
        if(p == null) return;

        board[toY][toX] = p;
        p.setX(toX);
        p.setY(toY);
        lastMovedPiece = p;

        killPiece(fromX, fromY);
    }

    /**
     * @param currentPlayer Couleur du joueur qui joue.
     * @return Retourne les pièces opposées à currentPlayer
     */
    ArrayList<Piece> getEnnemyPieces(PlayerColor currentPlayer)
    {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for(int y = 0; y < getSize(); ++y)
            for(int x = 0; x < getSize(); ++x)
            {
                if(!isFreeSpot(x,y) && !isAllySpot(currentPlayer,x ,y)) pieces.add(getPiece(x,y));
            }
        return pieces;
    }

    /**
     *
     * @param currentPlayer Couleur du joueur qui joue.
     * @return Retourne une référence sur le roi de currentPlayer
     */
    King getKing(PlayerColor currentPlayer)
    {
        for(int y = 0; y < getSize(); ++y)
            for(int x = 0; x < getSize(); ++x)
            {
                Piece p;
                if(isAllySpot(currentPlayer,x,y) && (p = getPiece(x,y)).type == PieceType.KING)
                    return (King) p;
            }

        return null;
    }

    /**
     * Réinitialise l'échiquier dans sa position de départ.
     */
    void reset()
    {
        board = new Piece[size][size];

        // Les noirs en haut
        createBackLine(PlayerColor.BLACK, 7);
        createFrontLine(PlayerColor.BLACK, 6);


        // Les blancs en bas
        createFrontLine(PlayerColor.WHITE, 1);
        createBackLine(PlayerColor.WHITE, 0);
    }

    /**
     * @return Retourne la largeur/hauteur de l'échiquier.
     */
    int getSize()
    {
        return size;
    }

    /**
     * Crée les pions d'une couleur sur la ligne donnée.
     * @param color Couleur des pions.
     * @param noLine Numéro de ligne où les positionner.
     */
    private void createFrontLine(PlayerColor color, int noLine)
    {
        for (int i = 0; i < size; i++)
            board[noLine][i] = new Pawn(color, i, noLine);
    }

    /**
     * Crée les tours, fous, cavaliers, roi et reine de couleur color à la ligne
     * noLine.
     * @param color Couleurs à affecter aux pièces.
     * @param noLine Numéro de ligne où les positionner.
     */
    private void createBackLine(PlayerColor color, int noLine)
    {
        board[noLine][0] = new Rook(color, 0, noLine);
        board[noLine][1] = new Knight(color, 1, noLine);
        board[noLine][2] = new Bishop(color, 2, noLine);
        board[noLine][3] = new Queen(color, 3, noLine);
        board[noLine][4] = new King(color, 4, noLine);

        board[noLine][5] = new Bishop(color, 5, noLine);
        board[noLine][6] = new Knight(color, 6, noLine);
        board[noLine][7] = new Rook(color, 7, noLine);
    }

    /**
     * Supprime une pièce du plateau.
     * @param x Position x.
     * @param y Position y.
     */
    void killPiece(int x, int y)
    {
        board[y][x] = null;
    }

    /**
     * Crée une copie pronfonde de l'échiquier.
     * @return Retourne la copie.
     */
    public Board clone()
    {
        Board b = null;
        try
        {
            b = (Board) super.clone();
            b.board = new Piece[size][size];
            for (int y = 0; y < size; ++y)
            {
                for (int x = 0; x < size; ++x)
                {
                    b.board[y][x] = board[y][x] == null ? null : board[y][x].clone();
                }
            }
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * Vérifie si la case en position x et y est occupée.
     * @param x Position x.
     * @param y Position y.
     * @return Vrai si la case est libre sinon faux.
     */
    boolean isFreeSpot(int x, int y)
    {
        if(!isValidPosition(x,y)) throw new IllegalArgumentException("Position inconnue");
        return getPiece(x, y) == null;
    }

    /**
     * Vérifie si la case en position x et y est occupée par une
     * pièce de même couleur que color.
     * @param color Couleur à vérifier l'appartenance.
     * @param x Position x.
     * @param y Position y,
     * @return Retourne vrai si la case est occupée par un allié de color.
     */
    boolean isAllySpot(PlayerColor color, int x, int y)
    {
        if(!isValidPosition(x,y)) throw new IllegalArgumentException("Position inconnue");
        return !isFreeSpot(x, y) && getPiece(x, y).color == color;
    }

    /**
     * Vérifie si la position x y demandée se trouve sur le plateau.
     * @param x Position x.
     * @param y Position y.
     * @return Vrai si la position est sur le plateau sinon faux.
     */
    boolean isValidPosition(int x, int y)
    {
        return x >= 0 && y >= 0 && x < getSize() && y < getSize();
    }

    /**
     * Retourne la dernière pièce déplacée.
     * @return Retourne la dernière pièce déplacée.
     */
    Piece getLastMovedPiece()
    {
        return lastMovedPiece;
    }

    /**
     * Remplece une pièce à la position X Y par une nouvelle pièce p.
     * @param x Position x.
     * @param y Position y
     * @param p Nouvelle pièce
     */
    void setPiece(int x, int y, Piece p)
    {
        if(!isValidPosition(x,y)) throw new IllegalArgumentException("Position invalide");

        board[y][x] = p;
    }

}
