package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.movements.*;
import engine.pieces.*;

import java.util.LinkedList;

/**
 * Classe controllant le déroulement d'une partie d'échec.
 *
 * @author Forestier Quentin, Herzig Melvyn
 * @version 22.12.2020
 */
public class ChessGame implements chess.ChessController
{
   private Board board;
   private ChessView view;

   private PlayerColor playerTurn;

   private PlayerColor checkedPlayer;
   private boolean canPlayerMove;

   /**
    * Démarre la vue et initialise le modèle de l'échiquier.
    * @param view la vue à utiliser
    */
   @Override
   public void start(ChessView view)
   {
      board = new Board();
      this.view = view;
      this.view.startView();
   }

   /**
    * Démarre une nouvelle partie.
    */
   @Override
   public void newGame()
   {
      playerTurn = PlayerColor.WHITE;
      checkedPlayer = null;
      canPlayerMove = true;
      board.reset();

      if(board.getKing(playerTurn) == null || board.getKing(opponent()) == null)
         throw new RuntimeException("At least, chess needs two kings, one for each player.");

      updateView();
   }

   /**
    * Vérifie si une pièce à la position fromX fromY peut aller en toX toY.
    * Si le déplacement est légal, return true et met à jour la vue, sinon
    * @param fromX Position x de départ.
    * @param fromY Position y de départ.
    * @param toX Position x d'arrivée.
    * @param toY Position y d'arrivée.
    * @return Si le déplacement est légal retourne vrai et met à jour la vue, sinon
    *         retourne false.
    */
   @Override
   public boolean move(int fromX, int fromY, int toX, int toY)
   {
      boolean isValid = false;
      Piece p = board.getPiece(fromX, fromY);

      if (p != null && p.getColor() == playerTurn)
      {
         Movement movement = p.canMove(toX, toY);

         if (movement != null && isRespectingBeforeApplicationRules(movement))
         {
            movement.apply();

            if (!isCheck())
            {

               checkAndAskPawnPromotion(board.getLastMovedPiece());

               playerTurn = opponent();
               movement.updateView(view);
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

   /**
    * Vérifie si le joueur courrant est en échec, échec et mat ou pat.
    * La fonction ne refait pas tous les calculs tant que le tour
    * n'a pas changé.
    */
   private void checkGameStatus()
   {
      if (checkedPlayer != playerTurn)
      {
         if (isCheck())
            checkedPlayer = playerTurn;
         else
            checkedPlayer = null;

         canPlayerMove = canPlayerPlay();
      }

      if (checkedPlayer != null)
      {
         if (canPlayerMove)
            view.displayMessage("Check! " + checkedPlayer);
         else
            view.displayMessage("Checkmate! " + checkedPlayer);
      }
      else
      {
         if (!canPlayerMove) view.displayMessage("Pat!");
      }
   }

   /**
    * Vérifie si les conditions préliminaires à un mouvement sont respectées.
    * Pour un castling, le roi et les cases de déplacement ne doivent pas être en échec.
    * Sinon aucune condition n'est nécessaire.
    * @param m Mouvement à vérifier
    * @return Vrai si les conditions sont bonnes faux sinon.
    */
   private boolean isRespectingBeforeApplicationRules(Movement m)
   {
      if (m instanceof CastlingMovement)
      {
         // Vérifier que le roi n'est pas en échec sur les cases nécessaires au déplacement
         CastlingMovement cm = (CastlingMovement) m;
         King k = (King) cm.getToMove();
         Rook r = cm.getRook();

         int direction = k.getX() < r.getX() ? 1 : -1;

         //Vérification de la case initiale et de transit.
         LinkedList<Piece> pieces = board.getPieces(opponent());
         for (int i = 0; i < 2; ++i)
         {
            for (Piece p : pieces)
            {
               if (p.canMove(k.getX() + i * direction, k.getY()) != null)
               {
                  return false;
               }
            }
         }
      }

      //Sinon le mouvement n'a pas de prérequis
      return true;
   }

   /**
    * Vérifie si le joueur courant est en échec sur le board.
    * @return Vrai si le joueur est en échec.
    */
   private boolean isCheck()
   {
      LinkedList<Piece> enemies = board.getPieces(opponent());
      King k = board.getKing(playerTurn);

      for (Piece p : enemies)
      {
         if (p.canMove(k.getX(), k.getY()) != null) return true;
      }

      return false;
   }


   /**
    * Vérifie si le joueur courrant à une possiblité de déplacement.
    * @return Vrai si le joueur a un moinns un déplacement faisable.
    */
   private boolean canPlayerPlay()
   {
      for (Piece p : board.getPieces(playerTurn))
      {
         for (Movement m : p.possibleMovements())
         {
            if (isRespectingBeforeApplicationRules(m))
            {
               m.apply();

               if (!isCheck())
               {
                  m.undo();
                  return true;
               }

               m.undo();
            }
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
      if (lastMovedPiece == null || lastMovedPiece.getClass() != Pawn.class) return;

      if (lastMovedPiece.getY() != ((Pawn) lastMovedPiece).getPromotionLine()) return;

      int x = lastMovedPiece.getX(), y = lastMovedPiece.getY();
      PlayerColor c = lastMovedPiece.getColor();

      Piece p = null;
      while (p == null)
      {
         p = view.askUser("Pawn promoted", "What upgrade do you chose ?",
                 new Queen (c, x, y, board),
                 new Rook  (c, x, y, board),
                 new Bishop(c, x, y, board),
                 new Knight(c, x, y, board));
      }

      board.setPiece(x, y, p);
   }

   /**
    * Calcule et retourne la couleur opposée au joueur actuel.
    * @return Retourne PlayerColor.BLACK si currentPlayer = PlayerColor.WHITE
    * PlayerColor.WHITE si currentPlayer = PlayerColor.BLACK
    */
   private PlayerColor opponent()
   {
      return playerTurn == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
   }

   /**
    * Réinitialise la vue.
    */
   private void updateView()
   {
      for (int y = 0; y < board.getSize(); ++y)
      {
         for (int x = 0; x < board.getSize(); ++x)
         {
            Piece p = board.getPiece(x, y);
            if (p == null)
            {
               view.removePiece(x, y);
            } else
            {
               view.putPiece(p.getType(), p.getColor(), x, y);
            }
         }
      }
   }
}
