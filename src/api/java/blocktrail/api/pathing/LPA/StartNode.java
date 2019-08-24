package blocktrail.api.pathing.LPA;

public class StartNode implements INode {

    private final int x, y, z;

    private double rhs = 0, g = INFINITY;

    private Key key;

    private PositionHashMap map;

    public StartNode(int x, int y, int z, PositionHashMap map) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.map = map;
    }

    //exactly the same heuristic approach as for every other node

    @Override
    public double heuristic() {
        double h_heuristic = getHorizontalHeuristic(map.goalNode.getX(), map.goalNode.getZ());
        double v_heuristic = getVerticalHeuristic(map.goalNode.getY());

        return v_heuristic + h_heuristic;
    }

    @Override
    public double moveCost(INode node) {

        //todo implement movement cost

        return 0;
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


    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public double getG() {
        return g;
    }

    @Override
    public void setG(double g) {
        this.g = g;
    }

    @Override
    public double getRHS() {
        return rhs;
    }

    @Override
    public double calculateRHS() {
        return -1;
    }

    @Override
    public Key calculateKey() {
        key = new Key(this);
        return key;
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public String getHashKey() {
        return x + "" + y + "" + z;
    }

    @Override
    public boolean isLocallyConsistent() {
        return rhs == g;
    }
}
