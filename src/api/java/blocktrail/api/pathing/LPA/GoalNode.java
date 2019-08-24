package blocktrail.api.pathing.LPA;

public class GoalNode implements INode {

    private final int x, y, z;

    private PositionHashMap map;

    private double g = INFINITY, rhs = INFINITY;

    private Key key;

    public GoalNode(int x, int y, int z, PositionHashMap map) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.map = map;
    }

    @Override
    public double heuristic() {

        return 0; //LAP expands from the startnode towards the goalnode, thus the heuristic value of the goalnode has to be 0;
    }

    @Override
    public double moveCost(INode node) {

        //todo implement moveCost

        return 0;
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
        return null;
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