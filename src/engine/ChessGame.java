package engine;

import chess.*;

import java.util.ArrayList;

/**
 * Classe controllant le déroulement d'une partie d'échec
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 12.12.2020
 */
public class ChessGame implements chess.ChessController
{
    private Board board;
    private ChessView view;

    private PlayerColor playerTurn;

    @Override
    /**
     * Enclanche la vue.
     */
    public void start(ChessView view)
    {
        this.view = view;
        this.view.startView();
        board = new Board();
    }

    @Override
    /**
     * Vérifie si une pièce à la position fromX fromY peut aller en toX toY.
     * Si le déplacement est légal, return true et met à jour la vue, sinon
     * retourne false.
     */
    public boolean move(int fromX, int fromY, int toX, int toY)
    {
        // Modèle temporaire du board sur lequel tester les déplacements
        Board tmp = board.clone();
        Piece p = tmp.getPiece(fromX, fromY);

        if (p == null || p.color != playerTurn)
        {
            return false;
        }

        Movement movement = p.canMove(tmp, toX, toY);

        // Si auncun mouvement possible, return pour ne pas reprint
        // un board identique.
        if(movement == null) return false;

        if(movement.getClass() == PawnMovement.class)
        {
           if(!applyPawnMovement(tmp, (PawnMovement)movement)) return false;
        }
        else if(movement.getClass() == CastlingMovement.class)
        {
            if(!applyCastling(tmp, (CastlingMovement)movement)) return false;
        }
        tmp.movePiece(movement.getPieceToMove().getX(), movement.getPieceToMove().getY(), movement.getToX(), movement.getToY());

        if (checkMate(tmp, playerTurn)) return false;

        board = tmp;
        updateView();

        // Changement de tour
        playerTurn = playerTurn == PlayerColor.BLACK ? PlayerColor.WHITE : PlayerColor.BLACK;

        return true;
    }

    @Override
    /**
     * Lance une nouvelle partie
     */
    public void newGame()
    {
        playerTurn = PlayerColor.WHITE;
        resetGame();
    }

    /**
     * Réinitialisation de la partie
     */
    public void resetGame()
    {
        board.reset();
        updateView();
    }


    private boolean applyPawnMovement(Board b, PawnMovement pm)
    {
        Pawn movedPawn = (Pawn)pm.getPieceToMove();
        Pawn enPassantPawn = pm.getPieceToKill();
        int noLinePassant = (playerTurn == PlayerColor.BLACK) ? 3 : 4;

        // Déplacement de 2 cases impossible si déjà déplacée.
        if(Math.abs(pm.getToY() - movedPawn.getY()) == 2)
        {
            if(movedPawn.hasMoved()) return false;
            movedPawn.setMoved2();
        }

        // Vérification en passant
        if(pm.getToX() != movedPawn.getX())
        {
            if(enPassantPawn != null && enPassantPawn.equal(b.getLastMovedPiece()) && movedPawn.getY() == noLinePassant && enPassantPawn.getMoved2())
            {
                b.killPiece(enPassantPawn.getX(), enPassantPawn.getY());
            }
            else if( b.isFreeSpot(pm.getToX(), pm.getToY()) || b.isAllySpot(movedPawn.getColor(), pm.getToX(), pm.getToY()))
            {
                return false;
            }
        }

        movedPawn.setMoved();

        return true;
    }

    private boolean applyCastling(Board b, CastlingMovement cm)
    {


        return true;
    }

    /**
     * Vérifie si la couleur du joueur playerColor est en échec sur le board b.
     * @param b Board sur lequel vérifier.
     * @param playerColor joueur à vérifier la mise en échec.
     * @return
     */
    private boolean checkMate(Board b, PlayerColor playerColor)
    {
        ArrayList<Piece> enemies = b.getEnnemyPieces(playerColor);
        King k = b.getKing(playerColor);

        for(Piece p: enemies)
        {
            if(p.canMove(b, k.getX(), k.getY()) != null) return true;
        }

        return false;
    }

    /**
     * Met à jour la vue.
     * Optimisation possible: détecter les changements entre le modèle
     * actuel et précédent pour mettre à jour les différences uniquement.
     */
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
}
