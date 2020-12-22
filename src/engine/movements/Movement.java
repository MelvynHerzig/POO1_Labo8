package engine.movements;

import chess.ChessView;
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
 * Un mécanisme est mis en place pour faire que plusieurs apply
 * n'aient aucun effet tant que le mouvement n'a pas été annulé.
 * De même pour les undo.
 *
 * Si l'état de l'échiquier est modifié entre la création de l'objet et
 * son utilisation, le comportement est indefini.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class Movement
{
    Board board;

    private final Piece toMove;
    private final Piece toMoveCopy;

    private Piece lastMovedPieceSave;

    private Piece killed;

    private final int toX;
    private final int toY;

    boolean hasBeenApplied;

    /**
     * Constructeur.
     * @param board Echiquier sur lequel le déplacement est fait
     * @param toMove Pièce à déplacer.
     * @param toX Position x de destionation.
     * @param toY Position y de destination.
     */
    public Movement(Board board, Piece toMove, int toX, int toY)
    {
        this.board = board;

        this.toMove = toMove;
        this.toMoveCopy = toMove.clone();

        this.toX = toX;
        this.toY = toY;

        killed = board.getPiece(toX, toY);
        lastMovedPieceSave = null;
        hasBeenApplied = false; // Permet d'éviter la duplication d'undo et apply.
    }

    /**
     * Retourne la pièce déplacée.
     * @return Retourne la pièce déplacée.
     */
    public Piece getToMove()
    {
        return toMove;
    }

    /**
     * Renvoie la position X de destionation.
     * @return Retourne la position X de destination.
     */
    public int getToX()
    {
        return toX;
    }

    /**
     * Renvoie la position Y de destionation.
     * @return Retourne la position Y de destination.
     */
    public int getToY()
    {
        return toY;
    }

    /**
     * Applique le nouvement sur l'échiquier.
     */
    public void apply()
    {
        if(hasBeenApplied) return;

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

        hasBeenApplied = true;
    }

    /**
     * Annule le mouvement sur l'échiquier.
     */
    public void undo()
    {
        if(!hasBeenApplied) return;

        board.movePiece(toMove.getX(), toMove.getY(), toMoveCopy.getX(), toMoveCopy.getY());
        if(killed != null)
            board.setPiece(killed.getX(), killed.getY(), killed);
        board.setLastMovedPiece(lastMovedPieceSave);

        toMove.copyState(toMoveCopy);

        hasBeenApplied = false;
    }

    /**
     * Met à jour la vue passé en paramètre en positionnant la pièce déplacée.
     * @param view Vue à mettre à jour.
     */
    public void updateView(ChessView view)
    {
        if(!hasBeenApplied) return;

        // On interroge le board sur la pièce qui se trouve en toX toY
        // au cas où une promotion est survenue.
        Piece p = board.getPiece(toMove.getX(), toMove.getY());
        view.removePiece(toMoveCopy.getX(), toMoveCopy.getY());
        view.putPiece(p.getType(), p.getColor(), toMove.getX(), toMove.getY());
    }

}
