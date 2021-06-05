package g07.player;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

import java.util.ArrayList;
import java.util.Iterator;

public class RoadList {
    private ArrayList<Road>[][] roads = new ArrayList[7][7];

    public RoadList(PieceColor[] boardG07) {
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                roads[i][j] = new ArrayList<Road>();
            }
        }
        initAllRoads(boardG07);
    }

    public void initAllRoads(PieceColor[] boardG07) {
        for (int i = 0; i < 360; ++i) { // i是Road的起点
            initAllRoads(boardG07, i);
        }
    }

    public void initAllRoads(PieceColor[] boardG07, int start) {
        int cnt = 0;
        int col = start % 19; // 列
        int row = start / 19; // 行
        int numOfWhite, numOfBlack;
        PieceColor[][] board = new PieceColor[19][19];
        for (int x = 0; x < 19; x++) {
            for (int y = 0; y < 19; y++) {
                board[y][x] = boardG07[cnt];
                cnt++;
            }
        }
        for (int dir = 1; dir < 5; ++dir) {
            if ((1 == dir) && (row + 5 < 19)) { // 向右并且路合法
                numOfBlack = 0;
                numOfWhite = 0;
                for (int i = 0; i < 6; ++i) {
                    if (PieceColor.BLACK == board[col][row + i])
                        ++numOfBlack;
                    else if (PieceColor.WHITE == board[col][row + i])
                        ++numOfWhite;
                }
                addRoad(new Road(start, dir, numOfWhite, numOfBlack));
            }
            else if ((2 == dir) && (col + 5 < 19)) { // 向下并且路合法
                numOfBlack = 0;
                numOfWhite = 0;
                for (int i = 0; i < 6; ++i) {
                    if (PieceColor.BLACK == board[col + i][row])
                        ++numOfBlack;
                    else if (PieceColor.WHITE == board[col + i][row])
                        ++numOfWhite;
                }
                addRoad(new Road(start, dir, numOfWhite, numOfBlack));
            }
            else if ((3 == dir) && (col - 5 >= 0) && (row + 5 < 19)) { // 右上并且路合法
                numOfBlack = 0;
                numOfWhite = 0;
                for (int i = 0; i < 6; ++i) {
                    if (PieceColor.BLACK == board[col - i][row + i])
                        ++numOfBlack;
                    else if (PieceColor.WHITE == board[col - i][row + i])
                        ++numOfWhite;
                }
                addRoad(new Road(start, dir, numOfWhite, numOfBlack));
            }
            else if ((4 == dir) && (row + 5 < 19) && (col + 5 < 19)) { // 右下并且合法
                numOfBlack = 0;
                numOfWhite = 0;
                for (int i = 0; i < 6; ++i) {
                    if (PieceColor.BLACK == board[col + i][row + i])
                        ++numOfBlack;
                    else if (PieceColor.WHITE == board[col + i][row + i])
                        ++numOfWhite;
                }
                addRoad(new Road(start, dir, numOfWhite, numOfBlack));
            }
        }
    }

    /**
     * 添加一条road
     *
     * @param road
     */
    public void addRoad(Road road) {

        int numB = road.getNumOfBlack();
        int numW = road.getNumOfWhite();
//        System.out.println(numW + " " + numB);
        ArrayList<Road> curRoads = roads[numB][numW];
//        System.out.println("add road: " + road);
        curRoads.add(road);
    }

    public void delRoad(Road road) {
        //System.out.println("find the deleteable road");
        int numB = road.getNumOfBlack();
        int numW = road.getNumOfWhite();
        // if (numB > 6 || numW > 6 || numB < 0 || numW < 0) return;
        ArrayList<Road> curRoads = roads[numB][numW];
        Iterator<Road> iterator = curRoads.iterator();
        while (iterator.hasNext()) {
            Road road1 = iterator.next();
            if (road1.equals(road)) {
                //    System.out.println("delRoad: " + road);
                iterator.remove();
                return; // I find this bug for long long time
            }
        }
    }

    public ArrayList<Road>[][] getRoads() {
        return roads;
    }

    public ArrayList<Road> findRoads(int pos) {
        ArrayList<Road> ans = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                ArrayList<Road> curRoads = roads[i][j];
                for (Road road : curRoads) {
                    if (road.hasPos(pos)) {
//                        System.out.println(road);
                        ans.add(road);
                    }
                }
            }
        }
        return ans;
    }

    public ArrayList<Road> getRoad(int numB, int numW) {
        return roads[numB][numW];
    }

    public void draw() {
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 6; ++j) {
                ArrayList<Road> curRoads = roads[i][j];
                for (Road road : curRoads) {
                    System.out.println(road);
                }
            }
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        RoadList roadList = new RoadList(board.get_board());
        roadList.draw();
    }


}
