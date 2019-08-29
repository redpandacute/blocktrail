package bbs.december.blocktrail.pathing;

import bbs.december.blocktrailAPI.pathing.algorithms.LPA.ICostHelper;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.IPositionHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class CostHelper implements ICostHelper {

    private PositionHashMap map;

    public CostHelper(PositionHashMap map) {
        this.map = map;
    }
    @Override
    public double moveCost(INode originNode, INode destinationNode) {
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

            if(diffX == 0 && diffZ == 0) {
                if(diffY < 0) {

                } else {

                }
            }
        }

        return 0;
    }

    @Override
    public IPositionHashMap getPositionHashMap() {
        return null;
    }

    @Override
    public void setPositionHashMap(IPositionHashMap positionHashMap) {

    }
}
