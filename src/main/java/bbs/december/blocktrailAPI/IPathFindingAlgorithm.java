package bbs.december.blocktrailAPI;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.IPositionHashMap;

public interface IPathFindingAlgorithm extends Runnable {

    IPositionHashMap getPositionHashMap();

    void start();
    void end();
    void kill();
    void pause();
    void resume();

}
