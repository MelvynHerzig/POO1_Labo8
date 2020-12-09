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

        if (p == null)
        {
            return false;
        }

        switch (p.canMove(board, toX, toY))
        {
            case IMPOSSIBLE:
                return false;
            case STANDARD:
                movePiece(p, toX, toY);
                break;
            case PASSANT:
                passant((Pawn)p, toX, toY);
                break;
            case CASTLING:
                castling((King)p, toX, toY);
                break;
        }

        return true;

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

    private void movePiece(Piece p, int toX, int toY)
    {
        if(p instanceof PieceSpecialFirstMove)
        {
            ((PieceSpecialFirstMove)p).hasMoved = true;
        }

        board[toY][toX] = p;
        board[p.y][p.x] = null;

        view.removePiece(p.x, p.y);

        p.x = toX;
        p.y = toY;

        view.putPiece(p.type, p.color, p.x, p.y);
    }

    private void castling(King k, int toX, int toY)
    {
        Rook r = k.rookCastled;

        int rookX = k.x < r.x ? toX-1 : toX+1;

        movePiece(r, rookX, toY);
        movePiece(k, toX, toY);
    }

    private void passant(Pawn p, int toX, int toY)
    {
        Pawn pToDel = p.pawnPassant;

        board[pToDel.y][pToDel.x] = null;
        view.removePiece(pToDel.x, pToDel.y);

        movePiece(p, toX, toY);
    }

}
