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
        Piece p = getPiece(fromX, fromY);

        //Si la pièce est nulle, sans effet.
        if(p == null) return;

        board[toY][toX] = p;
        board[fromY][fromX] = null;
        p.setX(toX);
        p.setY(toY);
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
                Piece p = getPiece(x,y);
                if(p.color != currentPlayer) pieces.add(p);
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
                Piece p = getPiece(x,y);
                if(p.color == currentPlayer && p.type == PieceType.KING)
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
        createBackLine(PlayerColor.WHITE, 0);
        createFrontLine(PlayerColor.WHITE, 1);
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

        King king = new King(color, 4, noLine);
        board[noLine][4] = king;

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
            for (int y = 0; y < size; ++y)
                for (int x = 0; x < size; ++x)
                    b.board[y][x] = board[y][x] == null ? null : board[y][x].clone();


        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }

        return b;
    }

    // TODO déplacer
    boolean freeBox(int x, int y)
    {
        return getPiece(x, y) == null;
    }

    // TODO déplacer
    boolean alliesBox(PlayerColor color, int x, int y)
    {
        return getPiece(x, y).color == color;
    }
}
