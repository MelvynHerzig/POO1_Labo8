package engine;

import chess.*;

public class ChessGame implements chess.ChessController
{
    private Board board;
    private ChessView view;

    private PlayerColor turn;

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
        Board tmp = board.clone();

        if (p == null ||(fromX == toX && fromY == toY)) //  p.color != turn ||
        {
            return false;
        }

        for (Movement m : p.canMove(board, toX, toY))
        {
            applyMovement(tmp, m);
            if (checkMate(tmp, m))
            {
                return false;
            }
        }

        board = tmp;
        updateView();

        turn = turn == PlayerColor.BLACK ? PlayerColor.WHITE :
                PlayerColor.BLACK;

        return true;

    }

    @Override
    public void newGame()
    {
        turn = PlayerColor.WHITE;
        resetBoard();
    }

    public void resetBoard()
    {
        board.reset();
        showBoard();
    }

    private void showBoard()
    {
        for (int y = 0; y < board.getSize(); y++)
        {
            for (int x = 0; x < board.getSize(); x++)
            {
                Piece p = board.getPiece(x, y);

                if (p == null) continue;

                view.putPiece(p.type, p.color, x, y);
            }
        }
    }

    private boolean applyMovement(Board b, Movement m)
    {
        Piece toMove = m.getPieceToMove();
        Piece toKill = m.getPieceToKill();
        int realToX = m.getToX();
        int realToY = m.getToY();

        // check sur un autre board si move ok


        if (toKill != null)
        {
            b.killPiece(toKill.getX(), toKill.getY());
        }

        if (toMove instanceof PieceSpecialFirstMove)
        {
            ((PieceSpecialFirstMove) toMove).hasMoved = true;
            if (toMove instanceof Pawn)
            {
                ((Pawn) toMove).moved2Cases =
                        Math.abs(toMove.getY() - realToY) == 2;
            }
        }

        b.movePiece(toMove.getX(), toMove.getY(), realToX, realToY);

        return true;
    }

    private boolean checkMate(Board b, Movement m)
    {

        return false;
    }

    private void updateView()
    {
        for (int y = 0; y < board.getSize(); ++y)
        {
            for (int x = 0; x < board.getSize(); ++x)
            {
                Piece p = board.getPiece(x,y);
                if(p == null)
                {
                    view.removePiece(x, y);
                }
                else
                {
                    view.putPiece(p.type, p.color, x,y);
                }
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
