package engine;

import chess.*;

public class ChessGame implements chess.ChessController
{
    private Board board;
    private ChessView view;

    private final int size = 8;

    @Override
    public void start(ChessView view)
    {
        this.view = view;
        this.view.startView();
        board = new Board();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY)
    {
        Piece p = board.getPiece(fromX, fromY);
        boolean validMove =false;

        if (p == null)
        {
            return false;
        }

        for (Movement m : p.canMove(board, toX, toY))
        {
            Piece toMove = m.getPieceToMove();
            Piece toKill = m.getPieceToKill();
            int realToX = m.getToX();
            int realToY = m.getToY();

            if (toKill != null)
            {
                board.killPiece(toKill.getX(), toKill.getY());
                view.removePiece(toKill.getX(), toKill.getY());
            }

            view.removePiece(toMove.getX(), toMove.getY());

            board.movePiece(toMove.getX(), toMove.getY(), realToX, realToY);
            view.putPiece(toMove.type, toMove.color, realToX, realToY);

            validMove = true;
            if(p instanceof PieceSpecialFirstMove)
            {
                ((PieceSpecialFirstMove)p).hasMoved = true;
                if(p instanceof Pawn)
                {
                    ((Pawn)p).moved2Cases = Math.abs(p.getY() - realToY) == 2;
                }
            }
        }

        return validMove;

    }

    @Override
    public void newGame()
    {
        resetBoard();
    }

    public void resetBoard()
    {
        board.reset();
        showBoard();
    }

    private void showBoard()
    {
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                Piece p = board.getPiece(x, y);

                if (p == null) continue;

                view.putPiece(p.type, p.color, x, y);
            }
        }
    }



  /*  private void movePiece(Piece p, int toX, int toY)
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
*/
}
