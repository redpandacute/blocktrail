package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrail.pathing.CostHelper;

import java.util.ArrayList;

public interface INode {

    double STRAIGHT_COST = 1;
    double DIAGONAL_COST = 1.4; //Actually sqrt(2) but it makes sense to just approximate it
    double VERTICAL_COST = 1; //for now i consider going up as efficient as going down

    double INFINITY =   Double.POSITIVE_INFINITY;

    double heuristic();
    default double moveCost(INode node) {
        return new CostHelper(getHashMap()).move(this, node).getCost();
    }

    int getX();
    int getY();
    int getZ();

    double getCordX();
    double getCordY();  //Position of a node is determined by the block the player is standing INSIDE of with his legs which means it is +0.5y
    double getCordZ();  //above the block hes standing on and also apart from his own location.


    double getG();
    void setG(double g);

    double getRHS();

    double calculateRHS();
    Key calculateKey();

    Key getKey();
    String getHashKey();

    boolean isLocallyConsistent();

    ArrayList<INode> getPredecessors();
    ArrayList<INode> getSuccessors();

    IPositionHashMap getHashMap();
    void setHashMap(IPositionHashMap map);

    BetterBlockPos getBlockPos();
}
