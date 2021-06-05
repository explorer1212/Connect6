package g07.player;

import core.board.PieceColor;

import java.util.Comparator;

public class roadScore {
    private int row;
    private int col;
    private int dir;
    private PieceColor color;
    private int score;

    public int getDir() {
        return dir;
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public int getScore() {
        return score;
    }


    roadScore(int row, int col, int dir, PieceColor color, int score) {
        this.row = row;
        this.col = col;
        this.dir = dir;
        this.color = color;
        this.score = score;
    }
}

class CMP implements Comparator {
    public int compare(Object o1, Object o2) {
        roadScore score = (roadScore) o1;
        roadScore score2 = (roadScore) o2;
        return -(score.getScore() - score2.getScore());
//        if (score.getScore() < score2.getScore())
//            return 1;
//        else if (score.getScore() == score2.getScore())
//            return 0;
//        return -1;
    }
}