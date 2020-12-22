package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.rules.LinearRule;
import engine.rules.AngularRule;
import engine.rules.Rule;
import engine.Board;

import java.util.LinkedList;

public class Queen extends Piece implements ChessView.UserChoice
{
    public Queen(PlayerColor aColor, int x, int y, Board board)
    {
        super(aColor, PieceType.QUEEN, x, y, board);
        rules = new LinkedList<Rule>();
        rules.add(new LinearRule(board, this, false));
        rules.add(new AngularRule(board, this, false));
    }

   @Override
   public String textValue()
   {
      return toString();
   }
}
