package g07.player;

import core.board.PieceColor;

import java.util.ArrayList;

import static core.board.PieceColor.*;


/**
 * Group07的搜索引擎
 * 思路：
 * 有没有连4，连5
 * 有没有双三
 */
public class Searcher {
    private ArrayList<Step> solution = new ArrayList<>();
    private PieceColor myColor;
    private Step step;
    private int searchDepth = 3;
    private int[][] dir = { // dir[col][row]
            {0, 1}, // 右
            {1, 0}, // 下
            {-1, 1}, // 右上
            {1, 1} // 右下
    };
    public Searcher(PieceColor myColor) {
        this.myColor = myColor;
    }


    public Step getStep() {
        return step;
    }


    /**
     * 对于一条路，查找它连成6个点所需要的其他位置。
     *
     * @param pos     路的起点
     * @param board   棋盘
     * @param road    要遍历的路
     * @param myColor 己方棋子颜色
     */
    private void findWin(int pos, BoardG07 board, Road road, PieceColor myColor) {
        int oriCol = pos % 19;
        int oriRow = pos / 19;
        //沿着路的方向搜六步
        int d = road.getDir() - 1;//方向坐标
        int dirCol = dir[d][0]; // 列
        int dirRow = dir[d][1]; // 行
        ArrayList<int[]> step = new ArrayList<>();
        int num = road.getNumOfColor(myColor);

        for (int k = 0; k < 6; ++k) {
            int col = oriCol + dirCol * k;
            int row = oriRow + dirRow * k;

            if (board.isLegalAndEmpty(col, row)) {
                int[] arr = {col, row};
                step.add(arr);
            }
        }
        // 如果这一条线上不能连起来6个棋子，就证明这条路没有必胜棋
        if (6 != num + step.size()) step.clear();
        //如果只下一步就能赢，另一个找空白位置下
        if (1 == step.size()) {
            for (int col = 0; col < 19; ++col) {
                for (int row = 0; row < 19; ++row) {
                    if (EMPTY == board.get(row * 19 + col)) {
                        int[] arr = {col, row};
                        step.add(arr);
                        row = 20;
                        col = 20;//跳出两层循环
                    }
                }
            }
        }
//        if (false) { // 替换下行
//            int index[] = step.get(0);
//            int index2[] = step.get(1);
//            Move move = new Move(index[0] + index[1] * 19, index2[0] + index2[1] * 19);
//        }
        if (step.size() != 0)
            solution.add(new Step(step.get(0), step.get(1)));
    }

    /**
     * 判断color方有没有必胜棋，有无双三，连4,连5
     *
     * @param board
     * @param color
     * @return 如果有必胜棋，返回，否则返回出界的值代表没有必胜棋
     */
    public Step willWin(BoardG07 board, PieceColor color) {
        solution.clear();
        // 得到所有有4个或5个己方棋子的路
        RoadList roadLists = board.getRoadLists();
        ArrayList<Road> roads = new ArrayList<>();

        if (BLACK == color) {
            roads.addAll(roadLists.getRoad(4, 0));
            roads.addAll(roadLists.getRoad(5, 0));
        } else {
            roads.addAll(roadLists.getRoad(0, 4));
            roads.addAll(roadLists.getRoad(0, 5));
        }
        // System.out.println(roads.size());
        for (Road road : roads) {
            findWin(road.getPos(), board, road, color);
            if (0 != solution.size()) {
                System.out.println("find the answer");
                break;
            }
        }
        if (solution.size() != 0)
            return solution.get(0);
        else
            return null;
    }

    /**
     * @param roads   路的列表
     * @param pieces  可在ar上落子的集合
     * @param board   棋盘
     * @param mychess 我方棋子颜色
     */
    public void findStop(ArrayList<Road> roads, ArrayList<Integer> pieces, BoardG07 board, PieceColor mychess) {
        ArrayList<Road> stopRoads = new ArrayList<>();
        for (int i = 0; i < pieces.size(); ++i) {
            for (int j = i + 1; j < pieces.size(); ++j) {
                boolean ok = true;
                stopRoads.clear();
                stopRoads.addAll(board.getRoadLists().findRoads(pieces.get(i)));
                stopRoads.addAll(board.getRoadLists().findRoads(pieces.get(j)));

                for (Road r : roads) {
                    if (!stopRoads.contains(r)) {
                        ok = false;
                        break;
                    }
                }
                if (true == ok) {
                    solution.add(new Step(pieces.get(i), pieces.get(j)));
                    return;
                }
            }
        }
//        if (false == ok) {
//            solution.add(new Step(pieces.get(tmp1), pieces.get(tmp2)));
//        }
    }

    // 判断对手有没有杀棋
    public Step needStop(BoardG07 board, PieceColor color, BoardScore BS) {
        //四五子必堵
        solution.clear();

        RoadList roadLists = board.getRoadLists();
        ArrayList<Road> roads = new ArrayList<>();
        ArrayList<Road> roads2 = new ArrayList<>();
        if (BLACK == color) {
            roads.addAll(roadLists.getRoad(0, 4));
            roads.addAll(roadLists.getRoad(0, 5));
            roads2.addAll(roadLists.getRoad(0, 3));
        } else {
            roads.addAll(roadLists.getRoad(4, 0));
            roads.addAll(roadLists.getRoad(5, 0));
            roads2.addAll(roadLists.getRoad(3, 0));
        }

        ArrayList<Integer> positions = new ArrayList<>();
        ArrayList<Integer> positions2 = new ArrayList<>();
        for (Road road : roads) {
            int d = road.getDir() - 1;//方向坐标
            int oriCol = road.getPos() % 19;
            int oriRow = road.getPos() / 19;
            for (int k = 0; k < 6; ++k) {
                int col = oriCol + dir[d][0] * k;
                int row = oriRow + dir[d][1] * k;
                if (board.isLegalAndEmpty(col, row)) {
                    if (!positions.contains(row * 19 + col))
                        positions.add(row * 19 + col);
                }
            }
        }
        this.findStop(roads, positions, board, color);
        if (0 != solution.size()) {
            return solution.get(0);
        } else {
            //三三必堵
            for (Road road : roads2) {
                int d = road.getDir() - 1;//方向坐标
                int oriCol = road.getPos() % 19;
                int oriRow = road.getPos() / 19;
                for (int k = 0; k < 6; k++) {
                    int col = oriCol + dir[d][0] * k;
                    int row = oriRow + dir[d][1] * k;

                    if (board.isLegalAndEmpty(col, row)) {
                        if (!positions2.contains(row * 19 + col))
                            positions2.add(row * 19 + col);
                    }
                }
            }
            this.findStop(roads2, positions2, board, color);
            if (solution.size() != 0) {
                return solution.get(0);
            } else
                return null;
        }
    }

    // 这里需要改
    public long alphabeta(int depth, long alpha, long beta, BoardG07 board) {
        BoardScore boardScore = board.getBoardScore();
        // 达到最大深度，计算叶子结点的值，返回
        if (depth == searchDepth) {
            boardScore.calcScore(board.getRoadLists());
            return boardScore.getScore();
        }

        long tmp;
        long maxValue = -Long.MAX_VALUE, minValue = Long.MAX_VALUE;
        int choose = 0;
        PieceColor nowChess =
                (0 == depth % 2) ? this.myColor : this.myColor.opposite();
        GenerateCandidates generateCandts = new GenerateCandidates();
        generateCandts.generateStep(nowChess, board);
        //取出所有落子
        ArrayList<Step> steps = generateCandts.getAllStep();

        for (int i = 0; i < steps.size(); i++) {
            Step step = steps.get(i);
            board.make(step.getFirst(), step.getSecond(), nowChess);
            tmp = alphabeta(depth + 1, alpha, beta, board);
            board.unmake(step.getFirst(), step.getSecond());
            // 获取最佳着子
            if (depth == 0) {
                if (tmp > maxValue) {
                    choose = i;
                }
            }
            if (tmp > maxValue) {
                maxValue = tmp;
            }
            if (tmp < minValue) {
                minValue = tmp;
            }
            // 剪枝
            if (nowChess != this.myColor) {
                if (tmp < alpha) break;
                if (tmp < beta) beta = tmp;
            }
            else {
                if (beta < tmp) break;
                if (alpha < tmp) alpha = tmp;
            }

        }
        //当前层数，输出最佳方案
        if (depth == 0) {
            System.out.println("steps.size = " + steps.size() + " choose =  " + choose);
            this.step = steps.get(choose);
        }
        return (nowChess == this.myColor) ? maxValue : minValue;
    }
}
