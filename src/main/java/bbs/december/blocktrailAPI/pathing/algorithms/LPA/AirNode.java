package bbs.december.blocktrailAPI.pathing.algorithms.LPA;


//the idea behind an airnode is that it has only limited successors. for now, the only successor for an airnode is the node directly beneath it.
//this means that also digging down actually isnt a move that modifies the location of the path but instead turns the node the path is in into an
//airnode which then offers different successors than before.

//this also offers the opportunity to implement mid air block placment which is a counterpart to digging down that turns an airnode into a regular node.

//the airnode approach offers the possibility to implement fallingcontroll apart from just falling straight.

import java.util.ArrayList;

public class AirNode implements INode {

    private final int x, y, z;

    //private final double velocity; maybe requried in the future.

    private double g = INFINITY;
    private double rhs = INFINITY;

    public Key key;

    private IPositionHashMap map;

    public AirNode(int x, int y, int z, IPositionHashMap map) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.map = map;
        //this.velocity = velocity;
    }

    @Override
    public double heuristic() {
        double h_heuristic = getHorizontalHeuristic(map.getGoalNode().getX(), map.getGoalNode().getZ());
        double v_heuristic = getVerticalHeuristic(map.getGoalNode().getY());
        return v_heuristic + h_heuristic;
    }

    @Override
    public double moveCost(INode node) {

        //todo implement movecost

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
    public double getCordX() {
        return x + 0.5;
    }

    @Override
    public double getCordY() {
        return y + 0.5;
    }

    @Override
    public double getCordZ() {
        return z + 0.5;
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
        return 0;
    }

    @Override
    public double calculateRHS() {
        return 0;
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
        return x + "" + y + "" + z + "A"; //since airnodes can be on top of other nodes, they have to have a different hashkey for identification
    }

    @Override
    public boolean isLocallyConsistent() {
        return rhs == g;
    }

    @Override
    public ArrayList<INode> getPredecessors() {
        return null;
    }

    @Override
    public ArrayList<INode> getSuccessors() {
        ArrayList<INode> list= new ArrayList<>();

        if(map.containsNode())

        list.add(map.get(x,y -1,z, true));
        return list;
    }

    private double getVerticalHeuristic(int gy) {

        int y_diff = Math.abs(gy - y);
        return y_diff * VERTICAL_COST;
    }

    private double getHorizontalHeuristic(int gx, int gz) {

        int x_diff = Math.abs(gx - x);
        int z_diff = Math.abs(gz - z);

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
