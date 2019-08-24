package blocktrail.api.pathing.DLite;

public class GoalNode implements INode {

    public final int x, y, z;

    public GoalNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //exactly the same heuristic approach as for every other node

    @Override
    public double heuristic(int sx, int sy, int sz) {
        double h_heuristic = getHorizontalHeuristic(sx, sz);
        double v_heuristic = getVerticalHeuristic(sy);

        return v_heuristic + h_heuristic;
    }


    private double getVerticalHeuristic(int sy) {

        int y_diff = Math.abs(sy - y);
        return y_diff * VERTICAL_COST;
    }

    private double getHorizontalHeuristic(int sx, int sz) {

        int x_diff = Math.abs(sx - x);
        int z_diff = Math.abs(sz - z);

        double heuristic = 0;

        while(x_diff > 0 && z_diff > 0) {
            heuristic += DIAGONAL_COST;
            x_diff--;
            z_diff--;
        }

        heuristic =+ (x_diff + z_diff) * STRAIGHT_COST;

        return heuristic;
    }
}
