package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrail.movement.IMove;

public interface ICostHelper {

    IMove move(INode originNode, INode destinationNode);

    IPositionHashMap getPositionHashMap();
    void setPositionHashMap(IPositionHashMap positionHashMap);
}
