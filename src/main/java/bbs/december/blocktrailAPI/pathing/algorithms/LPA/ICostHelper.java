package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

public interface ICostHelper {

    double moveCost(INode originNode, INode destinationNode);

    IPositionHashMap getPositionHashMap();
    void setPositionHashMap(IPositionHashMap positionHashMap);
}
