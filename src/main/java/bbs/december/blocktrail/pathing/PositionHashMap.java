package bbs.december.blocktrail.pathing;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.*;
import net.minecraft.world.World;

import java.util.HashMap;

public class PositionHashMap extends HashMap<String, Node> implements IPositionHashMap {

    public final StartNode startNode;
    public final GoalNode goalNode;

    private final World world;

    public static CostHelper costHelper;


    public PositionHashMap(StartNode startNode, GoalNode goalNode, World world) {
        this.startNode = startNode;
        this.goalNode = goalNode;

        costHelper = new CostHelper(this);

        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void add(Node node) {
        put(node.getHashKey(), node);
    }

    @Override
    public ICostHelper getCostHelper() {
        return costHelper;
    }

    @Override
    public StartNode getStartNode() {
        return startNode;
    }

    @Override
    public GoalNode getGoalNode() {
        return goalNode;
    }

    @Override
    public Node get(int x, int y, int z) {
        return get(x+""+y+""+z);
    }
}
