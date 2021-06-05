package g07.player;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

import java.util.ArrayList;

import static core.board.PieceColor.EMPTY;


public class BoardG07 extends Board {
    private RoadList roadLists;
    private BoardScore boardScore;

    public BoardG07(PieceColor color) {
        super();
        roadLists = new RoadList(get_board());
        boardScore = new BoardScore(color);
        boardScore.calcScore(roadLists);
    }

    public RoadList getRoadLists() {
        return roadLists;
    }

    public BoardScore getBoardScore() {
        return boardScore;
    }

    /**
     * 对pos1做操作，撤销落子或者落子
     * @param pos 要操作的点的位置
     * @param color 要把pos1变成color色
     */
    public void changePosColor(int pos, PieceColor color) {
        //System.out.println("pos = " + pos);
        PieceColor[] board = get_board();
        PieceColor v = board[pos]; // 当前位置的颜色
        // 找到包含这个点的所有路
        ArrayList<Road> roads = roadLists.findRoads(pos);
        for (Road r : roads) {
            // 删除旧路
            roadLists.delRoad(r);
            // 添加新路
            if (PieceColor.EMPTY == color) { // 撤销落子，v是当前位置的颜色
                if (PieceColor.BLACK == v) r.setNumOfBlack(-1);
                else if (PieceColor.WHITE == v) r.setNumOfWhite(-1);
            } else if (PieceColor.BLACK == color) {
                r.setNumOfBlack(1);
            } else if (PieceColor.WHITE == color) {
                r.setNumOfWhite(1);
            }
            roadLists.addRoad(r);
        }
        board[pos] = color;
    }

    public void unmake(int pos1, int pos2) {
        changePosColor(pos1, PieceColor.EMPTY);
        changePosColor(pos2, PieceColor.EMPTY);
    }

    /**
     * 修改路表
     * @param pos1 第一个棋子的坐标
     * @param pos2 第二个棋子的坐标
     * @param v 棋子的颜色
     */
    public void make(int pos1, int pos2, PieceColor v) {
        changePosColor(pos1, v);
        changePosColor(pos2, v);
    }

    public void updateRoadLists(Move move, PieceColor color) {
        int index1 = move.index1();
        int index2 = move.index2();
        make(index1, index2, color);
    }

    /**
     * 判断落子是否可行
     * @param col // 列
     * @param row // 行
     * @return
     */
    boolean isLegalAndEmpty(int col, int row) {
        return (0 <= row)
                && (19 >= row)
                && (0 <= col)
                && (19 >= col)
                && (PieceColor.EMPTY == get(row * 19 + col));
    }
}
