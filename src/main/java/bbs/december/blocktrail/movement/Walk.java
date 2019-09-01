package bbs.december.blocktrail.movement;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.BetterBlockPos;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import net.minecraft.world.World;

public class Walk implements IMove {

    public INode destinationNode;
    public INode originNode;

    public Walk(INode originNode, INode destinationNode) {
        this.originNode = originNode;
        this.destinationNode = destinationNode;

    }
    @Override
    public double getCost() {
        Directions direction = getDirection();

        double placementCost = 0;


        if(requiresPlacement()) {
            placementCost =+ Costs.BLOCKPLACEMENT.cost;
        }

        if(direction.diagonal) {
            return Costs.D_WALK.cost + placementCost;
        } else {
            return Costs.S_WALK.cost + placementCost + getBreakingCost();
        }
    }

    private double getBreakingCost() {

        double out = 0;

        BetterBlockPos pos = destinationNode.getBlockPos();
        World world = destinationNode.getHashMap().getWorld();

        if(!world.isAirBlock(pos)) {
            out =+ world.getBlockState(pos).getBlockHardness(world, pos) * Costs.BREAKFACTOR.cost;
        }

        if(!world.isAirBlock(pos.up())) {
            out =+ world.getBlockState(pos.up()).getBlockHardness(world, pos.up()) * Costs.BREAKFACTOR.cost;
        }

        return out;
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
        BetterBlockPos pos = destinationNode.getBlockPos();
        World world = destinationNode.getHashMap().getWorld();

        if(!world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
            return true;
        }

        return false;
    }
}
