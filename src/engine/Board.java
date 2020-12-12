package engine;

import chess.PlayerColor;

import java.util.ArrayList;

class Board
{
    private Piece[][] board;
    private King black;
    private King white;

    private ArrayList<Piece> blackPieces;
    private ArrayList<Piece> whitePieces;

    private final int size = 8;

    Board()
    {
        reset();
    }

    Piece getPiece(int x, int y)
    {
        return board[y][x];
    }

    void movePiece(int fromX, int fromY, int toX, int toY)
    {
        Piece p = getPiece(fromX, fromY);
        board[toY][toX] = p;
        board[fromY][fromX] = null;
        p.setX(toX);
        p.setY(toY);
    }

    ArrayList<Piece> getBlackPieces()
    {
        return blackPieces;
    }

    ArrayList<Piece> getWhitePieces()
    {
        return whitePieces;
    }

    King getKing(PlayerColor color)
    {
        return color == PlayerColor.BLACK ? black : white;
    }

    void reset()
    {
        board = new Piece[size][size];

        // Black pieces on top
        createBackLine(PlayerColor.BLACK, 7);
        createFrontLine(PlayerColor.BLACK, 6);


        // White pieces on bottom
        createBackLine(PlayerColor.WHITE, 0);
        createFrontLine(PlayerColor.WHITE, 1);
    }

    private void createFrontLine(PlayerColor color, int noLine)
    {
        for (int i = 0; i < size; i++)
        {
            board[noLine][i] = new Pawn(color, i, noLine);
        }
    }

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

        if(color == PlayerColor.BLACK)
        {
            black = (King)getPiece(4, noLine);
        }
        else
        {
            white = (King)getPiece(4, noLine);
        }
    }

    void killPiece(int x, int y)
    {
        board[y][x] = null;
    }

    boolean freeCase(int x, int y)
    {
        return getPiece(x, y) == null;
    }

    boolean alliesCase(PlayerColor color, int x, int y)
    {
        return getPiece(x,y).color == color;
    }
}
