package bbs.december.blocktrail.movement;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.AirNode;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.Node;
import net.minecraft.world.World;

public class Drop implements IMove {

    private INode destinationNode;
    private INode originNode;

    public Drop(INode originNode, INode destinationNode) {
        this.originNode = originNode;
        this.destinationNode = destinationNode;
    }


    @Override
    public double getCost() {
        if(requiresBreaking()) {
            World world = destinationNode.getHashMap().getWorld();
            return Costs.BREAKFACTOR.cost * world.getBlockState(destinationNode.getBlockPos().down()).getBlockHardness(world, destinationNode.getBlockPos().down());
        } else {
            if(destinationNode instanceof AirNode) {
                return Costs.DROP.cost;
            } else {
                return Costs.LAND.cost;
            }
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
        return false;
    }

    @Override
    public boolean isWalk() {
        return false;
    }

    @Override
    public boolean isDrop() {
        return true;
    }

    @Override
    public boolean requiresBreaking() {
        if(originNode instanceof Node && destinationNode instanceof AirNode) {
            return true;
        }
        return false;
    }

    @Override
    public boolean requiresPlacement() {
        if(destinationNode instanceof Node) {
            return true;
        }

        return false;
    }
}
