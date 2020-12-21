package engine;

import chess.*;
import engine.movements.*;
import engine.pieces.*;

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

    PlayerColor checkedPlayer;
    boolean canPlayerMove;

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
        boolean isValid = false;
        Piece p = board.getPiece(fromX, fromY);

        if (p != null &&  p.getColor() == playerTurn)
        {
            Movement movement = p.canMove(toX, toY);

            // Si auncun mouvement possible, return pour ne pas reprint
            // un board identique.
            if(movement != null && isRespectingBeforeApplicationRules(movement))
            {
                movement.apply();

                if (!isCheck(playerTurn))
                {
                    // Promotion
                    checkAndAskPawnPromotion(board.getLastMovedPiece());
                    updateView();
                    playerTurn = opponent(playerTurn);

                    isValid = true;
                }
                else
                {
                    movement.undo();
                }
            }
        }

        checkGameStatus();
        return isValid;
    }

    void checkGameStatus()
    {
        if(checkedPlayer != playerTurn)
        {
            //Est-ce que l'adversaire est en échec
            if(isCheck( playerTurn))
            {
                checkedPlayer = playerTurn;
            }
            else
            {
                checkedPlayer = null;
            }

            canPlayerMove = canPlayerPlay();
        }

        if(checkedPlayer != null)
        {
            if(canPlayerMove)
                view.displayMessage("Check " + checkedPlayer);
            else
                view.displayMessage("Checkmate " + checkedPlayer);
        }
        else
        {
            if(!canPlayerMove) view.displayMessage("Pat, game over!");
        }
    }

    boolean isRespectingBeforeApplicationRules(Movement m)
    {
        if(m instanceof CastlingMovement)
        {
            // Vérifier que le roi n'est pas en échec sur les cases nécessaires au déplacement
            CastlingMovement cm = (CastlingMovement) m;
            King k = (King)cm.getToMove();
            Rook r = (Rook)cm.getRook();

            int direction = k.getX() < r.getX() ? 1 : -1;

            ArrayList<Piece> pieces = board.getPieces(opponent(playerTurn));
            for(int i = 0; i <= 2; ++i)
            {
                for(Piece p : pieces)
                {
                    if(p.canMove( k.getX()+i*direction, k.getY()) != null)
                    {
                        view.displayMessage("Roi en échec durant le roque");
                        return false;
                    }
                }
            }
        }

        //Sinon le mouvement n'a pas de prérequis
        return true;
    }

    @Override
    /**
     * Lance une nouvelle partie
     */
    public void newGame()
    {
        board.reset();
        playerTurn = PlayerColor.WHITE;
        checkedPlayer = null;
        canPlayerMove = true;
        updateView();
    }

    /**
     * Vérifie si la couleur du joueur playerColor est en échec sur le board b.
     * @param playerColor joueur à vérifier la mise en échec.
     * @return Vrai si le joueur est en échec.
     */
    private boolean isCheck(PlayerColor playerColor)
    {
        ArrayList<Piece> enemies = board.getPieces(opponent(playerColor));
        King k = board.getKing(playerColor);

        for(Piece p: enemies)
        {
            if(p.canMove( k.getX(), k.getY()) != null) return true;
        }

        return false;
    }

    private boolean canPlayerPlay()
    {
        for(Piece p : board.getPieces(playerTurn))
        {
            for (Movement m : p.possibleMovements())
            {
                m.apply();

                if(!isCheck(playerTurn))
                {
                    m.undo();
                    return true;
                }

               m.undo();
            }
        }
        return false;
    }

    /**
     * Verifie si la dernière pièce bougée peut être promue.
     * @param lastMovedPiece Dernière pièce déplacée.
     */
    private void checkAndAskPawnPromotion(Piece lastMovedPiece)
    {
        if(lastMovedPiece == null || lastMovedPiece.getClass() != Pawn.class) return;

        if(lastMovedPiece.getY() != ((Pawn) lastMovedPiece).getPromotionLine()) return;

        int x = lastMovedPiece.getX(), y = lastMovedPiece.getY();
        PlayerColor c = lastMovedPiece.getColor();

        Piece p = null;
        while(p == null)
        {
            p = view.askUser("Pawn promoted", "What upgrade do you chose ?",
                    new Queen(c,x,y, board) ,
                    new Rook(c,x,y, board)  ,
                    new Bishop(c,x,y, board),
                    new Knight(c,x,y, board));
        }

        board.setPiece(x,y,p);
    }

    /**
     * Calcule et retourne la couleur opposée au joueur actuel.
     * @param currentPlayer Joueur actuel
     * @return Retourne PlayerColor.BLACK si currentPlayer = PlayerColor.WHITE
     *                  PlayerColor.WHITE si currentPlayer = PlayerColor.BLACK
     */
    private PlayerColor opponent(PlayerColor currentPlayer)
    {
        return currentPlayer == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
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
                    view.putPiece(p.getType(), p.getColor(), x,y);
                }
            }
        }
    }
}
