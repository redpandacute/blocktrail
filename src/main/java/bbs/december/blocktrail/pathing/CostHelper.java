package bbs.december.blocktrail.pathing;

import bbs.december.blocktrail.movement.Drop;
import bbs.december.blocktrail.movement.IMove;
import bbs.december.blocktrail.movement.Jump;
import bbs.december.blocktrail.movement.Walk;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class CostHelper implements ICostHelper {

    private IPositionHashMap map;

    public CostHelper(IPositionHashMap map) {
        this.map = map;
    }

    @Override
    public IMove move(INode originNode, INode destinationNode) {
        if(
                !map.getWorld().getChunkProvider().isChunkLoaded(new ChunkPos((int) Math.floor(originNode.getX()/16), (int) Math.floor(originNode.getZ()/16))) ||
                !map.getWorld().getChunkProvider().isChunkLoaded(new ChunkPos((int) Math.floor(destinationNode.getX()/16), (int) Math.floor(destinationNode.getZ()/16)))
        ) {
            //todo calculate ideal cost (if diagonal or not)
        } else {
            //todo calculate regular cost based on the position and the difficulty of the movement itself

            int diffX = Math.abs(originNode.getX() - destinationNode.getX());
            int diffY = destinationNode.getY() - originNode.getY();
            int diffZ = Math.abs(originNode.getZ() - destinationNode.getZ());

            if (originNode instanceof AirNode || (diffX == 0 && diffY == 0 && diffZ == 0)) {
                return new Drop(originNode, destinationNode);
            }

            if(destinationNode instanceof Node && diffY == 0) {
                return new Walk(originNode, destinationNode);
            } else {
                return new Jump(originNode, destinationNode);
            }
        }

        return null; //never really gets reached
    }

    @Override
    public IPositionHashMap getPositionHashMap() {
        return this.map;
    }

    @Override
    public void setPositionHashMap(IPositionHashMap positionHashMap) {
        this.map = positionHashMap;
    }
}
