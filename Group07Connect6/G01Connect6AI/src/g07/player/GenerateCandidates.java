package g07.player;

import core.board.PieceColor;

import java.util.ArrayList;


public class GenerateCandidates {

    private int[][] dir = { // dir[col][row]
            {0, 1},
            {1, 0},
            {-1, 1},
            {1, 1}
    };

    private ArrayList<Step> allStep = new ArrayList<>();
    private ArrayList<roadScore> myRoadScore = new ArrayList<roadScore>();
    private ArrayList<roadScore> opponentRoadScore = new ArrayList<roadScore>();
    private ArrayList<Integer> killPos = new ArrayList<>();
    private ArrayList<Integer> stopPos = new ArrayList<>();

    /**
     * 遍历一条路
     * @param board
     */
    private void searchSixStep(BoardG07 board) {
        for(roadScore ps : myRoadScore) {
            int direction = ps.getDir() - 1;
            int oriRow = ps.getRow();
            int oriCol = ps.getCol();
            for (int k = 0 ; k < 6; ++k) {
                int col = oriCol + dir[direction][0] * k;
                int row = oriRow + dir[direction][1] * k;

                if(board.isLegalAndEmpty(col, row)) {
                    if(killPos.size()>=6) break;

                    if(!killPos.contains(row * 19 + col)) {
                        killPos.add(row * 19 + col);
                    }
                }
            }
        }
        for(roadScore ps: opponentRoadScore) {
            int direction = ps.getDir()-1;
            int oriRow = ps.getRow();
            int oriCol = ps.getCol();

            for (int k = 0 ; k < 6;k++) {
                int col = oriCol + dir[direction][0] * k;
                int row = oriRow + dir[direction][1] * k;
                if(board.isLegalAndEmpty(col, row)){
                    if(stopPos.size() >= 6) break;

                    if(!stopPos.contains(row * 19 + col)) {
                        stopPos.add(row * 19 + col);
                    }
                    break;
                }
            }
        }
    }

    public void generateRoadScore(PieceColor myColor, BoardG07 board) {
        RoadList roadLists = board.getRoadLists();
        ArrayList<Road> usefulRoads = new ArrayList<>();
       // System.out.println("myColor = " + myColor);
        PieceColor opponent = myColor.opposite();

        int[] sc = {0, 1, 25, 601, 14425, 346201}; // * 24 + 1

        for(int i = 1; i < 6; ++i) { // 把所有白棋为或黑棋为0的路加入到usefulRoads
            usefulRoads.addAll(roadLists.getRoad(0, i));
            usefulRoads.addAll(roadLists.getRoad(i, 0));
        }

        for(Road road : usefulRoads) { // 遍历计算得分
            int myScore = 0, yourScore = 0;
            int numOfMine = road.getNumOfColor(myColor);
            int numOfOpponent = road.getNumOfColor(myColor.opposite());

            //自己的路
            if(0 == numOfOpponent) {
                myScore += sc[numOfMine];
                myRoadScore.add(
                        new roadScore(road.getPos() / 19,road.getPos() % 19,
                                road.getDir(), myColor, myScore)
                );
            }
            //对手的路
            else if(0 == numOfMine) {
                yourScore += sc[numOfOpponent];
                opponentRoadScore.add(
                        new roadScore(road.getPos() / 19,road.getPos() % 19,
                                road.getDir(),opponent,yourScore)
                );
            }
        }
        myRoadScore.sort(new CMP());
        opponentRoadScore.sort(new CMP());
//        Collections.sort(myRoadScore, new CMP());
//        Collections.sort(opponentRoadScore, new CMP());

        this.searchSixStep(board);
    }

    public void generateStep(PieceColor myChess, BoardG07 board) {
        this.generateRoadScore(myChess,board);
        int size1 = stopPos.size();
        int size2 = killPos.size();
        for(int i = 0; i < size1; i++) {
            for(int j = i + 1; j < size1; j++) {
                if(stopPos.get(i).intValue() != stopPos.get(j).intValue()) {
                    allStep.add(new Step(stopPos.get(i), stopPos.get(j)));
                }
            }
        }
        for(int i = 0; i < size1; ++i) {
            for(int j = 0; j < size2; ++j) {
                if(stopPos.get(i).intValue()!= killPos.get(j).intValue()) {
                    allStep.add(new Step(stopPos.get(i), killPos.get(j)));
                }
            }
        }
        for(int i = 0; i < size2; i++) {
            for(int j = i + 1; j<size2; j++) {
                if(killPos.get(i) != killPos.get(j)) {
                    allStep.add(new Step(killPos.get(i), killPos.get(j)));
                }
            }
        }
    }

    public ArrayList<Step> getAllStep() { return allStep; }

    public static void main(String[] args) {
        int a = 3;
        int b = 3;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        ArrayList<Integer> arr2 = new ArrayList<Integer>();
        arr.add(a * b);
        arr2.add(a * b);
        if (arr.get(0) == arr2.get(0))
            System.out.println("yes");
        else System.out.println("no");
    }

}
