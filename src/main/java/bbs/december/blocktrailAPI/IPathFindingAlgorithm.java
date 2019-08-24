package bbs.december.blocktrailAPI;

import bbs.december.blocktrail.pathing.PositionHashMap;

public interface IPathFindingAlgorithm extends Runnable {

    PositionHashMap getPositionHashMap();

    void start();
    void end();
    void kill();
    void pause();
    void resume();

}
