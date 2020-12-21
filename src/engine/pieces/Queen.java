package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.rules.LinearRule;
import engine.rules.AngularRule;
import engine.Board;

public class Queen extends Piece implements ChessView.UserChoice
{
    public Queen(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.QUEEN, x, y, board, new LinearRule(board, false), new AngularRule(board, false));
    }

   @Override
   public String textValue()
   {
      return toString();
   }
}
