package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrail.pathing.PositionHashMap;

import java.util.ArrayList;

public class GoalNode implements INode {

    private final int x, y, z;

    private IPositionHashMap map;

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
        return rhs;
    }

    @Override
    public double calculateRHS() {
        rhs = INFINITY;

        for(INode curr : map.getPredecessors(this)) {
            if(curr.getG() + curr.moveCost(this) < rhs) { //Todo implement cost function
                rhs = curr.getG() + curr.moveCost(this);
            }
        }

        return rhs;
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

    @Override
    public ArrayList<INode> getPredecessors() {
        return map.getPredecessors(this);
    }

    @Override
    public ArrayList<INode> getSuccessors() {
        return null; //goalnode shouldnt have any successors
    }

    @Override
    public IPositionHashMap getHashMap() {
        return map;
    }

    @Override
    public void setHashMap(IPositionHashMap map) {
        this.map = map;
    }

    @Override
    public BetterBlockPos getBlockPos() {
        return new BetterBlockPos(x, y, z);
    }
}
