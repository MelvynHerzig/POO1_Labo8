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

        if (p != null &&  p.color == playerTurn)
        {
            Movement movement = p.canMove(board, toX, toY);

            // Si auncun mouvement possible, return pour ne pas reprint
            // un board identique.
            if(movement != null && applyMovement(board, movement))
            {
                if (!isCheck(board, playerTurn))
                {
                    // Promotion
                    checkAndAskPawnPromotion(board.getLastMovedPiece());
                    updateView();
                    playerTurn = opponent(playerTurn);

                    isValid = true;
                }
                else
                {
                    board.undo();
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
            if(isCheck(board, playerTurn))
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

    Boolean applyMovement(Board b, Movement m)
    {
        if(m.getClass() == PawnMovement.class)
        {
            if(!applyPawnMovement(b, (PawnMovement)m)) return false;
        }
        else if(m.getClass() == CastlingMovement.class)
        {
            if(!applyCastling(b, (CastlingMovement)m)) return false;
        }
        else
        {
            b.movePiece(m.getFromX(), m.getFromY(), m.getToX(), m.getToY());
        }

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
        Pawn movedPawn = (Pawn)b.getPiece(pm.getFromX(), pm.getFromY());
        Pawn enPassantPawn = null;
        if(b.isValidPosition(pm.getToKillX(), pm.getToKillY()))
            enPassantPawn = (Pawn)b.getPiece(pm.getToKillX(), pm.getToKillY());
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
            if(b.isValidPosition(pm.getToKillX(), pm.getToKillY()) && enPassantPawn.equal(b.getLastMovedPiece()) && movedPawn.getY() == noLinePassant && enPassantPawn.getMoved2())
            {
                b.killPiece(enPassantPawn.getX(), enPassantPawn.getY());
            }
            else if( b.isFreeSpot(pm.getToX(), pm.getToY()) || b.isAllySpot(movedPawn.getColor(), pm.getToX(), pm.getToY()))
            {
                return false;
            }
        }

        b.movePiece(pm.getFromX(), pm.getFromY(),pm.getToX(), pm.getToY());
        movedPawn.setMoved();

        return true;
    }

    private boolean applyCastling(Board b, CastlingMovement cm)
    {
        Rook r = (Rook) b.getPiece(cm.getrX(), cm.getrY());
        King k = (King) b.getPiece(cm.getFromX(), cm.getFromY());
        int direction = r.getX() > k.getX() ? 1 : -1;

        if(r.hasMoved || k.hasMoved || isCheck(b, playerTurn)) return false;

        // On test les deux cases sur lequels le roi va se déplacer
        ArrayList<Piece> pieces = b.getPieces(opponent(playerTurn));
        for(int i = 0; i < 2; ++i)
        {
            for(Piece p : pieces)
            {
                if(p.canMove(b, k.getX()+i*direction, k.getY()) != null)
                {
                    return false;
                }
            }
        }

        b.movePiece(k.getX(), k.getY(), cm.getToX(), cm.getToY());
        b.movePiece(r.getX(), r.getY(), k.getX() - direction, k.getY());
        
        return true;
    }

    /**
     * Vérifie si la couleur du joueur playerColor est en échec sur le board b.
     * @param b Board sur lequel vérifier.
     * @param playerColor joueur à vérifier la mise en échec.
     * @return Vrai si le joueur est en échec.
     */
    private boolean isCheck(Board b, PlayerColor playerColor)
    {
        ArrayList<Piece> enemies = b.getPieces(opponent(playerColor));
        King k = b.getKing(playerColor);

        for(Piece p: enemies)
        {
            if(p.canMove(b, k.getX(), k.getY()) != null) return true;
        }

        return false;
    }

    private boolean canPlayerPlay()
    {
        for(Piece p : board.getPieces(playerTurn))
        {
            for (Movement m : p.possibleMovements(board))
            {
                if(applyMovement(board, m))
                {
                    if(!isCheck(board, playerTurn))
                    {
                        board.undo();
                        return true;
                    }
                }
                board.undo();
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
                    new Queen(c,x,y) ,
                    new Rook(c,x,y)  ,
                    new Bishop(c,x,y),
                    new Knight(c,x,y));
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
                    view.putPiece(p.type, p.color, x,y);
                }
            }
        }
    }
}
