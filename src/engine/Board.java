package engine;

import chess.PlayerColor;
import engine.pieces.*;

import java.util.LinkedList;

/**
 * Classe modélisant un échiquier.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class Board
{
    // Références sur les différentes pièces en fonctions des cases du board.
    private Piece[][] board;

    private final int SIZE = 8;

    private Piece lastMovedPiece;

    private King whiteKing;
    private King blackKing;

    private LinkedList<Piece> whitePieces;
    private LinkedList<Piece> blackPieces;

    /**
     * Constructeur
     */
    public Board()
    {
        reset();
    }

    /**
     * Déplace une pièce sur l'échiquier.
     * Si les positions ne sont pas valides, la fonction est sans effet.
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

        killPiece(toX, toY);

        board[toY][toX] = p;
        p.setX(toX);
        p.setY(toY);

        board[fromY][fromX] = null;
    }

    /**
     * Retourne la pièce sur le plateau à la position x et y.
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
     * Ajoute une pièce p à la position x y.
     * @param x Position x.
     * @param y Position y
     * @param p Nouvelle pièce
     */
    public void setPiece(int x, int y, Piece p)
    {
        if(!isValidPosition(x,y)) return;

        if(p != null)
        {
            if(p.getColor() == PlayerColor.BLACK && !blackPieces.contains(p))
                blackPieces.add(p);
            else if(p.getColor() == PlayerColor.WHITE && !whitePieces.contains(p))
                whitePieces.add(p);
        }

        board[y][x] = p;
    }

    /**
     * Supprime une pièce du plateau.
     * Si les positions ne sont pas valides, la fonction est sans effet.
     * @param x Position x.
     * @param y Position y.
     */
    public void killPiece(int x, int y)
    {
        if(!isValidPosition(x,y)) return;

        if(board[y][x] != null)
        {
            if(board[y][x].getColor() == PlayerColor.BLACK)
                blackPieces.remove(board[y][x]);
            else
                whitePieces.remove(board[y][x]);
        }

        board[y][x] = null;
    }

    /**
     * Recherche les pièces d'une couleur données
     * @param piecesColor Couleur du joueur a récupérer les pièces
     * @return Retourne les pièces opposées à currentPlayer
     */
    public LinkedList<Piece> getPieces(PlayerColor piecesColor)
    {
        return (piecesColor == PlayerColor.BLACK) ? blackPieces : whitePieces;
    }

    /**
     * Retourne le roi d'une couleur donnée.
     * @param kingColor Couleur du roi recherché.
     * @return Retourne une référence sur le roi.
     */
    public King getKing(PlayerColor kingColor)
    {
       return (kingColor == PlayerColor.BLACK) ? blackKing : whiteKing;
    }

    /**
     * Réinitialise l'échiquier dans sa position de départ.
     */
    public void reset()
    {
        board = new Piece[SIZE][SIZE];
        blackPieces = new LinkedList<Piece>();
        whitePieces = new LinkedList<Piece>();

        // Les noirs en haut
        createBackLine(PlayerColor.BLACK, 7);
        createFrontLine(PlayerColor.BLACK, 6);


        // Les blancs en bas
        createFrontLine(PlayerColor.WHITE, 1);
        createBackLine(PlayerColor.WHITE, 0);
    }

    /**
     * Donne les dimensions de l'échiquier.
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
     * Crée les pions d'une couleur sur la ligne donnée.
     * @param color Couleur des pions.
     * @param noLine Numéro de ligne où les positionner.
     */
    private void createFrontLine(PlayerColor color, int noLine)
    {
        int promotionOnLine = color == PlayerColor.WHITE ? getSize()-1 : 0;
        for (int i = 0; i < SIZE; i++)
        {
            board[noLine][i] = new Pawn(color, i, noLine, promotionOnLine, this);


            if (color == PlayerColor.BLACK)
                blackPieces.add( board[noLine][i]);
            else
                whitePieces.add( board[noLine][i]);
        }
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
        if(color == PlayerColor.BLACK)
        {
            blackKing = (King)board[noLine][4];
        }
        else
        {
            whiteKing = (King)board[noLine][4];
        }


        board[noLine][5] = new Bishop(color, 5, noLine, this);
        board[noLine][6] = new Knight(color, 6, noLine, this);
        board[noLine][7] = new Rook(color, 7, noLine, this);

        for(int i = 0; i < getSize(); ++i)
        {
            if (color == PlayerColor.BLACK)
                blackPieces.add( board[noLine][i]);
            else
                whitePieces.add( board[noLine][i]);
        }
    }

    /**
     * Fixe la valeur de la dernière pièce déplacée.
     * @param piece Pièce étant la dernière pièce déplacée.
     */
    public void setLastMovedPiece(Piece piece)
    {
        lastMovedPiece = piece;
    }

    /**
     * Retourne la dernière pièce déplacée.
     * @return Retourne la dernière pièce déplacée.
     */
    public Piece getLastMovedPiece()
    {
        return lastMovedPiece;
    }
}
