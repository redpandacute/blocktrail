package bbs.december.blocktrail.movement;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.BetterBlockPos;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import net.minecraft.world.World;

public class UnloadedMove implements IMove {

    private INode originNode, destinationNode;

    public UnloadedMove(INode originNode, INode destinationNode) {
        this.originNode = originNode;
        this.destinationNode = destinationNode;

    }
    @Override
    public double getCost() {
       return 1; //todo improve cost for unloaded chunk
    }

    private double getBreakingCost() {
        return 0;
    }

    @Override
    public void setOriginNode(INode node) {
        this.originNode = node;
    }

    @Override
    public void setDestinationNode(INode node) {
        this.destinationNode = node;
    }

    @Override
    public INode getOriginNode() {
        return originNode;
    }

    @Override
    public INode getDestinationNode() {
        return destinationNode;
    }

    @Override
    public boolean isJump() {
        return false;
    }

    @Override
    public boolean isWalk() {
        return true;
    }

    @Override
    public boolean isDrop() {
        return false;
    }

    @Override
    public boolean requiresBreaking() {
        return false;
    }
}
