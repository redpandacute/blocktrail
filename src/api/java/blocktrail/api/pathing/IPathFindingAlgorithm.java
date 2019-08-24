package blocktrail.api.pathing;

import blocktrail.api.pathing.LPA.PositionHashMap;

public interface IPathFindingAlgorithm extends Runnable {

    PositionHashMap getPositionHashMap();

    void start();
    void end();
    void kill();
    void pause();
    void resume();

}
