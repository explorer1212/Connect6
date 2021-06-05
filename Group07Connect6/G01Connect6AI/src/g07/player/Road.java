package g07.player;

import core.board.PieceColor;
import java.util.ArrayList;


public class Road {

    public int pos;//起始点
    public int dir;//方向
    public int numOfBlack;//黑子数目
    public int numOfWhite;//白子数目

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Road){
            Road road = (Road) obj;
            if ((road.pos == pos)
                    &&(road.dir == dir)
                    &&(road.numOfBlack == numOfBlack)
                    &&(road.numOfWhite == numOfWhite)){
                return true;
            }
            return false;
        }
        return false;
    }

    Road(int pos, int dir, int numOfWhite, int numOfBlack) {
        this.pos = pos;
        this.dir = dir;
        this.numOfWhite = numOfWhite;
        this.numOfBlack = numOfBlack;
    }

    public void setNumOfBlack(int num) { numOfBlack += num; }

    public void setNumOfWhite(int num) { numOfWhite += num; }


    public int getNumOfColor(PieceColor Color) {
        return (PieceColor.BLACK == Color) ? numOfBlack : numOfWhite;
    }

    public int getPos() {
        return pos;
    }

    public int getDir() {
        return dir;
    }

    public int getNumOfWhite() {
        return numOfWhite;
    }

    public int getNumOfBlack() { return numOfBlack; }

    /**
     * 判断这条路有没有包含pos1这个点
     * @param pos1 点的位置
     * @return 包含的话返回true，否则返回false
     */
    public boolean hasPos(int pos1) {
        // 右 19
        // 下 1
        // 右上 18
        // 右下 20
        int[] d = {0, 19, 1, 18, 20};
        for (int i = 0; i <= 5; ++i) {
            if (pos + i * d[dir] == pos1) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return pos + " " +
                dir + " " +
                numOfWhite + " " +
                numOfBlack;
    }

    public static void main(String[] args) {
        Road road = new Road(0, 0, 1, 1);
        ArrayList<Road> roads1 = new ArrayList<>();
        ArrayList<Road> roads2 = new ArrayList<>();
        roads1.add(road);
        for (Road r : roads1) {
            roads2.add(r);
        }
        for (Road r : roads2) {
            r.setNumOfWhite(-1);
        }
        for (Road r : roads1) System.out.println(r);
        for (Road r : roads1) System.out.println(r);
    }
}
