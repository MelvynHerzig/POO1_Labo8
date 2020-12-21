package engine.movements;

import engine.Board;
import engine.pieces.Pawn;
import engine.pieces.Piece;
import engine.pieces.PieceSpecialFirstMove;

/**
 * Classe modélisant les movements normaux des pièces
 * roi, reine, fou, cavalier, tour, pion. Un mouvement dit normal,
 * est un mouvement ou une pièce se déplace sur l'échiquier en prenant
 * ou non une pièce adverse qui se situe sur la case de destination.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 13.12.2020
 */
public class Movement
{
    Board board;

    private final Piece toMove;
    private final Piece toMoveCopy;

    private Piece lastMovedPieceSave;

    private Piece killed;   // Initialisée quand apply est appelé.

    private final int toX;
    private final int toY;

    public Movement(Board board, Piece toMove, int toX, int toY)
    {
        this.board = board;

        this.toMove = toMove;
        this.toMoveCopy = toMove.clone();

        this.toX = toX;
        this.toY = toY;

        lastMovedPieceSave = null;
    }

    public Piece getToMove()
    {
        return toMove;
    }

    public int getToX()
    {
        return toX;
    }

    public int getToY()
    {
        return toY;
    }

    public void apply()
    {
        killed = board.getPiece(toX, toY);
        board.movePiece(toMove.getX(), toMove.getY(), toX, toY);
        board.killPiece(toMoveCopy.getX(), toMoveCopy.getY());

        //Mise à jour des pièces avec détection de déplacement.
        if(toMove instanceof PieceSpecialFirstMove)
            ((PieceSpecialFirstMove) toMove).setMoved();

        //Mise à jour d'un pion si se déplace de 2 cases en avant.
        if(toMove instanceof Pawn && Math.abs(toY - toMoveCopy.getY()) == 2)
            ((Pawn) toMove).setMoved2();

        lastMovedPieceSave = board.getLastMovedPiece();
        board.setLastMovedPiece(toMove);
    }

    public void undo()
    {
        board.setPiece(toMoveCopy.getX(), toMoveCopy.getY(), toMove);
        board.killPiece(toX, toY);
        if(killed != null)
            board.setPiece(killed.getX(), killed.getY(), killed);
        board.setLastMovedPiece(lastMovedPieceSave);

        toMove.copyState(toMoveCopy);
    }

}
