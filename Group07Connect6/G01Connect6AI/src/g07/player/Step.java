package g07.player;

public class Step {
    private int[] first;
    private int[] second;

    Step(int f, int s) {
        first = new int[2];
        second = new int[2];

        first[0] = f % 19; first[1] = f / 19; // 0是列，1是行
        second[0] = s % 19; second[1] = s / 19; // 0是列，1是行
    }
    Step(int[] f,int[] s) {
        this.first = f;
        this.second = s;
    }
    public int[] toArrayInt() {
        int[] ans = new int[]{getFirst(), getSecond()};
        return ans;
    }
    public int getFirst() {
        return first[0] + first[1] * 19;
    } // 0是列，1是行

    public int getSecond() { return second[0] + second[1] * 19; } // 0是列，1是行
}
