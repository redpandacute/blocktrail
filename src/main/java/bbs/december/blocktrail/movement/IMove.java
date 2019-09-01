package bbs.december.blocktrail.movement;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.AirNode;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.Node;
import net.minecraft.util.Direction;

public interface IMove {

    double getCost();

    void setOriginNode(INode node);
    void setDestinationNode(INode node);

    INode getOriginNode();
    INode getDestinationNode();

    boolean isJump();
    boolean isWalk();
    boolean isDrop();

    boolean requiresBreaking();

    default boolean requiresPlacement() {
        INode dNode = getDestinationNode();

        if(dNode instanceof Node && dNode.getHashMap().getWorld().isAirBlock(dNode.getBlockPos().down())) {
            return true;
        }
        return false;
    }

    default Directions getDirection() {
        INode oNode = getOriginNode();
        INode dNode = getDestinationNode();

        int xDiff = dNode.getX() - oNode.getX();
        int zDiff = dNode.getZ() - oNode.getZ();

        if(xDiff == 0 && zDiff == 0) {
            int yDiff = dNode.getY() - oNode.getY();
            int y = yDiff/Math.abs(yDiff);

            if(y > 0) {
                return Directions.UP;
            }

            if(y < 0) {
                return Directions.DOWN;
            }

            if(y == 0) {
                if(oNode instanceof AirNode) {
                    return Directions.DOWN;
                }

                if(dNode instanceof AirNode) {
                    return Directions.DOWN;
                }

                return Directions.UP;
            }
        }

        //directionPropeties
        int x = 0, z = 0;

        if(xDiff != 0) {
            x = xDiff/Math.abs(xDiff);
        }
        if(zDiff != 0) {
            z = zDiff/Math.abs(zDiff);
        }




        for(Directions direction : Directions.values()) {
            if(direction.x == x && direction.z == z) {
                return direction;
            }
        }

        return null; //should never get called
    }


}
