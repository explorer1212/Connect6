package g07.player;

import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;

public class AI extends core.player.AI {

    private Board board;
    private Searcher searcher;

    @Override
    public Move findMove(Move opponentMove) {
        BoardG07 boardG07 = (BoardG07) board;
//        boardG07.getRoadLists().draw();
        if (opponentMove == null) {
            Move move = firstMove();
            board.makeMove(move);
            boardG07.updateRoadLists(move, getColor().opposite());
            return move;
        }
        else {
            board.makeMove(opponentMove);
            boardG07.updateRoadLists(opponentMove, getColor().opposite());
        }

        int[] index;

        PieceColor color = getColor();
        BoardScore boardScore = boardG07.getBoardScore();

        Step win = searcher.willWin(boardG07, color);

        //判断我方有没有必胜棋
        if (null == win) {
            // 判断对手有没有必胜棋
            Step lose = searcher.needStop(boardG07, color, boardScore);
            if (null == lose) {
                //对手没有必胜棋，走当前我方的最优棋
                searcher.alphabeta(0, -Long.MAX_VALUE, Long.MAX_VALUE, boardG07);
                index = searcher.getStep().toArrayInt();
            } else {
                index = lose.toArrayInt();
            }
        } else {
            index = win.toArrayInt();
            System.out.println("find the kill method");
        }

        Move move = new Move(index[0], index[1]);
        board.makeMove(move);
         boardG07.updateRoadLists(move, getColor()); // 更新路表
        System.out.println(index[0] + " " + index[1]);
        return move;
    }



    @Override
    public String name() {
        return "Group07";
    }

    @Override
    public void playGame(Game game) {
        super.playGame(game);
        board = new BoardG07(getColor());
        searcher = new Searcher(getColor());
    }
}
