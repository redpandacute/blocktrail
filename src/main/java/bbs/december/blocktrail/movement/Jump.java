package bbs.december.blocktrail.movement;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import net.minecraft.world.World;

public class Jump implements IMove {

    private INode originNode;
    private INode destinationNode;

    private Jumps jump;

    public Jump(INode originNode, INode destinationNode) {
        this.originNode = originNode;
        this.destinationNode = destinationNode;

        jump = determineJump();
    }

    private Jumps determineJump() {
        int xDiff = destinationNode.getX() - originNode.getX();
        int yDiff = destinationNode.getY() - originNode.getY();
        int zDiff = destinationNode.getZ() - originNode.getZ();

        Directions direction = getDirection();

        if(direction == Directions.UP) {
            return Jumps.J_BLACK;
        }

        if(direction.diagonal) {
            if(yDiff == 0) {
                return Jumps.DJ_PURPLE;
            }

            int dist = xDiff * xDiff + zDiff * zDiff;

            switch(dist) {
                case 2: return Jumps.DJ_WHITE;
                case 8: return Jumps.DJ_YELLOW;
                case 18: return Jumps.DJ_RED;
            }

        } else {
            if(yDiff == 0) {
                return Jumps.SJ_PURPLE;
            }

            switch(Math.abs(xDiff + zDiff)) {
                case 1: return Jumps.SJ_WHITE;
                case 2: return Jumps.SJ_BLUE;
                case 3: return Jumps.SJ_YELLOW;
                case 4: return Jumps.SJ_RED;
            }
        }

        return null;
    }

    @Override
    public double getCost() {

        if(requiresPlacement()) {
            return Costs.RED.cost + Costs.BLOCKPLACEMENT.cost;
        }

        if(requiresBreaking()) {
            World world = destinationNode.getHashMap().getWorld();
            return Costs.BLACK.cost + Costs.BREAKFACTOR.cost * world.getBlockState(destinationNode.getBlockPos()).getBlockHardness(world, destinationNode.getBlockPos());
        }

        switch(jump) {
            case SJ_WHITE: return Costs.WHITE.cost;
            case SJ_BLUE: return Costs.BLUE.cost;
            case SJ_YELLOW: return Costs.YELLOW.cost;
            case SJ_RED: return Costs.RED.cost;
            case SJ_PURPLE: return Costs.PURPLE.cost;

            case DJ_WHITE: return Costs.WHITE.cost;
            case DJ_YELLOW: return Costs.YELLOW.cost;
            case DJ_RED: return Costs.RED.cost;
            case DJ_PURPLE: return Costs.PURPLE.cost;

            default: J_BLACK: return Costs.BLACK.cost;
        }
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
        return true;
    }

    @Override
    public boolean isWalk() {
        return false;
    }

    @Override
    public boolean isDrop() {
        return false;
    }

    @Override
    public boolean requiresBreaking() {
        if(jump == Jumps.J_BLACK && !destinationNode.getHashMap().getWorld().isAirBlock(destinationNode.getBlockPos())) {
            return true;
        } else {
            return false;
        }
    }
}
