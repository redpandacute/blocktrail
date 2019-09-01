package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrail.pathing.PositionHashMap;

import java.util.ArrayList;

public class Node implements INode {

    public final int x, y, z;

    private double g = INFINITY;
    private double rhs = INFINITY;

    public Key key;

    private IPositionHashMap map;

    public Node(int x, int y, int z, IPositionHashMap map) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.map = map;
    }

    public Node(int x, int y, int z, int g, IPositionHashMap map) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.map = map;
        this.g = g;
    }


    /*
    lpa* starts its calculation estimates its heuristic values according to the position of the goal_node
    */

    @Override
    public double heuristic() {
        double h_heuristic = getHorizontalHeuristic(map.getGoalNode().getX(), map.getGoalNode().getZ());
        double v_heuristic = getVerticalHeuristic(map.getGoalNode().getY());

        return v_heuristic + h_heuristic;
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
    public Key getKey() {
        return key;
    }

    @Override
    public String getHashKey() {
        return x + "" + y + "" + z; //I am not sure if the "" are necessary
    }



    /*
    * The when the RHS value is calculated, the node checks all adjecent Nodes and their g values and the cost to traverse
    * the node border. The lowest found formula g(s') + c(s',s) = rhs .
    * */

    public double calculateRHS() {
        rhs = INFINITY;


        for(INode curr : map.getPredecessors(this)) {
            if(curr.getG() + curr.moveCost(this) < rhs) { //Todo implement cost function
                rhs = curr.getG() + curr.moveCost(this);
            }
        }
          //todo: implement this

        /** this is not up to date anymore
        for(int i =0; i < 3;i++) {
            for(int l =0; l < 3;l++) {
                for(int f =0; f < 3;f++) {
                    INode curr = map.get(x + l - 1,y + i - 1,z + f - 1);
                    if(i==1 && l==1 && f==0) f++ ; //skip one in order to not get the node itself
                    if(curr.getG() + curr.moveCost(this) < rhs) { //Todo implement cost function
                        rhs = curr.getG() + curr.moveCost(this);
                    }
                }
            }
        }
         **/

        return rhs;
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
        return map.getSuccessors(this);
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

    @Override
    public Key calculateKey() {
        key = new Key(this);
        return key;
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
