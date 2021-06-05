//package test01.player;
//
//import core.board.Board;
//import core.game.Game;
//import core.game.Move;
//import core.player.AI;
//
//import java.util.Random;
//
//import static core.board.PieceColor.EMPTY;
//import static core.game.Move.SIDE;
//
//public class SixChuan extends AI {
//
//
//    /** Return a move for me from the current position, assuming there
//     *  is a move. */
////    @Override
////    public Move findMove(Move opponentMove) {
////        if (opponentMove == null) {
////            Move move = firstMove();
////            board.makeMove(move);
////            return move;
////        }
////        else {
////            board.makeMove(opponentMove);
////        }
////
////        Random rand = new Random();
////        while (true) {
////            int index1 = rand.nextInt(SIDE * SIDE);
////            int index2 = rand.nextInt(SIDE * SIDE);
////
////            if (index1 != index2 && board.get(index1) == EMPTY && board.get(index2) == EMPTY) {
////                Move move = new Move(index1, index2);
////                board.makeMove(move);
////                return move;
////            }
////        }
////    }
////
////    @Override
////    public String name() {
////        // TODO Auto-generated method stub
////        return "G01-SixKiller";  //组编号-为自己的AI所取的名字
////    }
////
////    Board board = new Board();
////
////    /* (non-Javadoc)
////     * @see core.player.Player#setBoard(core.board.Board)
////     */
////
////    public Board setBoard(Board board) {
////        // TODO Auto-generated method stub
////        return null;
////    }
////
////    /* (non-Javadoc)
////     * @see core.player.Player#getBoard()
////     */
////
////    public Board getBoard() {
////        // TODO Auto-generated method stub
////        return null;
////    }
//    @Override
//    public String name() {
//        return "我爱六个串";
//    }
//
//
///** Return a move for me from the current position, assuming there
// *  is a move. */
//    @Override
//    public Move findMove(Move opponentMove) {
//        if (opponentMove == null) {
//            Move move = firstMove();
//            board.makeMove(move);
//            return move;
//        }
//        else {
//            board.makeMove(opponentMove);
//        }
//
//        Random rand = new Random();
//        while (true) {
//            int index1 = rand.nextInt(SIDE * SIDE);
//            int index2 = rand.nextInt(SIDE * SIDE);
//
//            if (index1 != index2 && board.get(index1) == EMPTY && board.get(index2) == EMPTY) {
//                Move move = new Move(index1, index2);
//                board.makeMove(move);
//                return move;
//            }
//        }
//    }
//
//    @Override
//    public void playGame(Game game) {
//        super.playGame(game);
//        board = new Board();
//    }
//    //使用框架提供的Board
//    private Board board = new Board();
//}
package test01.player;

import core.board.Board;
import core.game.Game;
import core.game.Move;
import core.player.AI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static core.board.PieceColor.EMPTY;
import static core.game.Move.SIDE;

public class SixChuan extends core.player.AI {
    @Override
    public String name() {
        return "走法二";
    }

    //    static List<Integer> list =
//            List.of(-1, 1, -20, -19, -18, 18, 19, 20);

    public List<Integer> generateMove(int k) {
        List<Integer> list = new ArrayList<>();

        if (!(0 == k % 19)) list.add(-1); // 不是最上方的点，向上
        if (!(18 == k % 19)) list.add(1); // 不是最下方的点，向下
        if (!(k < 19)) { // 不是最左侧的点
            list.add(-19); // 向左
            if (!(0 == k % 19)) list.add(-20); // 不是最上方的点，左上角
            if (!(18 == k % 19)) list.add(-18); // 不是最下方的点，左下角
        }
        if (!(k > 341)){ // 不是最右侧的点
            list.add(19); // 向右
            if (!(0 == k % 19)) list.add(18); // 不是最上方的点，右上角
            if (!(18 == k % 19)) list.add(20); // 不是最下方的点，右下角
        }
        return list;
    }

    /**
     * Return a move for me from the current position, assuming there
     * is a move.
     */
    static int cnt = 0;
    @Override
    public Move findMove(Move opponentMove) {
        if (opponentMove == null) {
            Move move = firstMove();
            board.makeMove(move);
            return move;
        } else {
            board.makeMove(opponentMove);
        }

        Random rand = new Random();
        int index1, index2;
        while (true) {
            do {
                index1 = rand.nextInt(SIDE * SIDE);
            } while (board.get(index1) != EMPTY);
            List<Integer> offsets = generateMove(index1);
            while (true) {
                int pos = rand.nextInt(offsets.size());
                index2 = index1 + offsets.get(pos);
                if (board.get(index2) == EMPTY) break;
                else {
                    offsets.remove(pos);
                    if (0 == offsets.size()) {
                        while (true) {
                            index2 = rand.nextInt(SIDE * SIDE);
                            if (index1 != index2 && board.get(index2) == EMPTY)
                                break;
                        }
                        break;
                    }
                }
            }
//            index1 = cnt;
//            cnt += 2;
//            index2 = cnt;
//            cnt += 2;
            Move move = new Move(index1, index2);
            board.makeMove(move);
            return move;
        }
    }

    @Override
    public void playGame(Game game) {
        super.playGame(game);
        board = new Board();
    }

    private Board board = new Board();
}
