package engine;

import chess.*;

public class Board implements chess.ChessController
{
    private Piece[][] board;
    private ChessView view;

    private final int size = 8;

    @Override
    public void start(ChessView view)
    {
        this.view = view;
        this.view.startView();

    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY)
    {
        Piece p = board[fromY][fromX];

        if (p != null && p.canMove(board, toX, toY))
        {
            // on doit encore check si ça met en echec
            p.move(board, view, toX, toY);

            return true;
        }


        return false;
    }

    @Override
    public void newGame()
    {
        resetBoard();
    }

    public void resetBoard()
    {
        board = new Piece[size][size];

        // Black pieces on top
        createBackLine(PlayerColor.BLACK, 7);
        createFrontLine(PlayerColor.BLACK, 6);


        // White pieces on bottom
        createBackLine(PlayerColor.WHITE, 0);
        createFrontLine(PlayerColor.WHITE, 1);

        showBoard();
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
    }

    private void showBoard()
    {
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                Piece p = board[y][x];

                if (p == null) continue;

                view.putPiece(p.type, p.color, x, y);
            }
        }
    }
}
