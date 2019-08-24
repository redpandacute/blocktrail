package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

public interface IPositionHashMap {

    Node get(int x, int y, int z);
    void add(Node node);

    ICostHelper getCostHelper();

    StartNode getStartNode();
    GoalNode getGoalNode();
}
