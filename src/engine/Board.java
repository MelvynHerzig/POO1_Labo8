package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.pieces.*;
import engine.rules.*;

import java.util.ArrayList;

/**
 * Classe modélisant le board d'une partie d'échec.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 12.12.2020
 */
public class Board
{
    // Références sur les différentes pièces en fonctions des cases du board.
    private Piece[][] board;

    private final int SIZE = 8;

    private Piece lastMovedPiece;

    /**
     * Constructeur
     */
    public Board()
    {
        reset();
    }

    /**
     * @param x Position x.
     * @param y Position y.
     * @return Retourne une référence sur la pièce en position x y.
     */
    public Piece getPiece(int x, int y)
    {
        if(!isValidPosition(x, y)) return null;

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
    public void movePiece(int fromX, int fromY, int toX, int toY)
    {
        if(!isValidPosition(fromX,fromY) || !isValidPosition(toX  ,toY  )) return;


        Piece p = getPiece(fromX, fromY);

        //Si la pièce est nulle, sans effet.
        if(p == null) return;

        board[toY][toX] = p;
        p.setX(toX);
        p.setY(toY);

        killPiece(fromX, fromY);
    }

    /**
     * Supprime une pièce du plateau.
     * @param x Position x.
     * @param y Position y.
     */
    public void killPiece(int x, int y)
    {
        if(!isValidPosition(x,y)) return;
        board[y][x] = null;
    }

    /**
     * @param piecesColor Couleur du joueur qui joue.
     * @return Retourne les pièces opposées à currentPlayer
     */
    public ArrayList<Piece> getPieces(PlayerColor piecesColor)
    {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for(int y = 0; y < getSize(); ++y)
            for(int x = 0; x < getSize(); ++x)
            {
                if(!isFreeSpot(x,y) && isAllySpot(piecesColor,x ,y)) pieces.add(getPiece(x,y));
            }
        return pieces;
    }

    /**
     *
     * @param kingColor Couleur du roi recherché.
     * @return Retourne une référence sur le roi.
     */
    public King getKing(PlayerColor kingColor)
    {
        for(int y = 0; y < getSize(); ++y)
            for(int x = 0; x < getSize(); ++x)
            {
                Piece p;
                if(isAllySpot(kingColor,x,y) && (p = getPiece(x,y)).getType() == PieceType.KING)
                    return (King) p;
            }

        return null;
    }

    /**
     * Réinitialise l'échiquier dans sa position de départ.
     */
    public void reset()
    {
        board = new Piece[SIZE][SIZE];

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
    public int getSize()
    {
        return SIZE;
    }


    /**
     * Vérifie si la case en position x et y est occupée.
     * @param x Position x.
     * @param y Position y.
     * @return Vrai si la case est libre sinon faux.
     */
    public boolean isFreeSpot(int x, int y)
    {
        if(!isValidPosition(x,y)) return false;
        return getPiece(x, y) == null;
    }

    /**
     * Vérifie si la case en position x et y est occupée par une
     * pièce de même couleur que color.
     * @param color Couleur à vérifier l'appartenance.
     * @param x Position x.
     * @param y Position y,
     * @return Retourne vrai si la case est occupée par un allié de couleur color.
     */
    public boolean isAllySpot(PlayerColor color, int x, int y)
    {
        if(!isValidPosition(x,y)) return false;
        return !isFreeSpot(x, y) && getPiece(x, y).getColor() == color;
    }

    /**
     * Vérifie si la position x y demandée se trouve sur le plateau.
     * @param x Position x.
     * @param y Position y.
     * @return Vrai si la position est sur le plateau sinon faux.
     */
    public boolean isValidPosition(int x, int y)
    {
        return x >= 0 && y >= 0 && x < getSize() && y < getSize();
    }

    /**
     * Remplece une pièce à la position X Y par une nouvelle pièce p.
     * @param x Position x.
     * @param y Position y
     * @param p Nouvelle pièce
     */
    public void setPiece(int x, int y, Piece p)
    {
        if(!isValidPosition(x,y)) return;

        board[y][x] = p;
    }

    /**
     * Crée les pions d'une couleur sur la ligne donnée.
     * @param color Couleur des pions.
     * @param noLine Numéro de ligne où les positionner.
     */
    private void createFrontLine(PlayerColor color, int noLine)
    {
        int promotionOnLine = color == PlayerColor.WHITE ? getSize()-1 : 0;
        for (int i = 0; i < SIZE; i++)
            board[noLine][i] = new Pawn(color, i, noLine, promotionOnLine, this);
    }

    /**
     * Crée les tours, fous, cavaliers, roi et reine de couleur color à la ligne
     * noLine.
     * @param color Couleurs à affecter aux pièces.
     * @param noLine Numéro de ligne où les positionner.
     */
    private void createBackLine(PlayerColor color, int noLine)
    {
        board[noLine][0] = new Rook(color, 0, noLine, this);
        board[noLine][1] = new Knight(color, 1, noLine, this);
        board[noLine][2] = new Bishop(color, 2, noLine, this);
        board[noLine][3] = new Queen(color, 3, noLine, this);
        board[noLine][4] = new King(color, 4, noLine, this);

        board[noLine][5] = new Bishop(color, 5, noLine, this);
        board[noLine][6] = new Knight(color, 6, noLine, this);
        board[noLine][7] = new Rook(color, 7, noLine, this);
    }

    public void setLastMovedPiece(Piece piece)
    {
        lastMovedPiece = piece;
    }

    public Piece getLastMovedPiece()
    {
        return lastMovedPiece;
    }
}
