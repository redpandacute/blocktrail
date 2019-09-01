package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import net.minecraft.world.World;

import java.util.ArrayList;

public interface IPositionHashMap {

    //INode get(int x, int y, int z);
    INode get(int x, int y, int z, boolean airnode);
    void add(INode node);

    ICostHelper getCostHelper();

    StartNode getStartNode();
    GoalNode getGoalNode();

    void setStartNode(StartNode node);
    void setGoalNode(GoalNode node);


    World getWorld();

    ArrayList<INode> getPredecessors(INode node);
    ArrayList<INode> getSuccessors(INode node);
}
