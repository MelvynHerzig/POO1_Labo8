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

        ArrayList<Movement> movements = p.canMove(tmp, toX, toY);
        // Si auncun mouvmeent possible, return pour ne pas reprint
        // un board identique.
        if(movements.isEmpty()) return false;

        for (Movement movement : movements)
        {
            applyMovement(tmp, movement);
            if (checkMate(tmp, playerTurn))
            {
                return false;
            }
        }

        board = tmp;
        updateView();

        // Changement de tour
        playerTurn = playerTurn == PlayerColor.BLACK ? PlayerColor.WHITE :
                                                       PlayerColor.BLACK;

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

    /**
     * Applique le movement m sur le board b.
     * @param b Modèle du board sur lequel appliquer le mouvement.
     * @param m Le movement à appliquer.
     * @return Retourne vrai le pouvement peut être fait sans mettre en échec
     *         son propre roi sinon false.
     */
    private boolean applyMovement(Board b, Movement m)
    {
        Piece toMove = m.getPieceToMove();
        Piece toKill = m.getPieceToKill();

        if (toKill != null)
        {
            b.killPiece(toKill.getX(), toKill.getY());
        }

        b.movePiece(toMove.getX(), toMove.getY(), m.getToX(), m.getToY());

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
