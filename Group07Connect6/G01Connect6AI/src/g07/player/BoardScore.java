package g07.player;

import core.board.PieceColor;

import java.util.ArrayList;

public class BoardScore {
    private long score;
    private PieceColor color;
    public long getScore() { return score; }

    BoardScore(PieceColor p) {
        this.color = p;
        this.score = 0;
    }

    //x925
    final private long[][] valuation = {
            {-891453125,1,925,855625,791453125,732094140625L,677187080078125L},
            {0,-1,-1925,-1855625,-891453125,-832094140625L,-677187080078125L}
    };
    /**
     * 统计完路数使用
     */
    public void calcScore(RoadList roadLists) {
        // roadLists.getRoads();
        score = 0;
        //我方有i个子，对方j个子
        for(int i = 0; i < 7; ++i) {
            for(int j = 0; j < 7; ++j) {
                ArrayList<Road> roads = roadLists.getRoad(i, j);
                int len = roads.size();
                if(PieceColor.BLACK == color) {
                    score += (len * (valuation[0][i] + valuation[1][j]));
                }
                else{
                    score += (len * (valuation[1][i] + valuation[0][j]));
                }
            }
        }
    }
}
