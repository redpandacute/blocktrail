package bbs.december.blocktrailAPI.pathing.algorithms.DLite;

public class StartNode implements INode {

    public final int x, y, z;

    public StartNode(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double heuristic(int sx, int sy, int sz) {

        return 0; //since in our approach we go from goal to start, the heuristic value of the startnode has to be 0;
    }
}
