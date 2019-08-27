package bbs.december.blocktrail.movement;

import bbs.december.blocktrail.pathing.PositionHashMap;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.BetterBlockPos;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.INode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MovementHelper {

    private final PositionHashMap map;
    private final World world;

    public MovementHelper(PositionHashMap map) {
        this.map = map;
        this.world = map.getWorld();
    }

    public boolean isPlacableBlock(BetterBlockPos pos) {

        if(world.getBlockState(pos).isSolid()) {
            return false;
        }

        if(world.getBlockState(pos.down()).isSolid()) {
            return true;
        }

        if(world.getBlockState(new BlockPos(pos.x + 1, pos.y, pos.z)).isSolid()) {
            return true;
        }

        if(world.getBlockState(new BlockPos(pos.x - 1, pos.y, pos.z)).isSolid()) {
            return true;
        }

        if(world.getBlockState(new BlockPos(pos.x, pos.y, pos.z + 1)).isSolid()) {
            return true;
        }

        if(world.getBlockState(new BlockPos(pos.x, pos.y, pos.z - 1)).isSolid()) {
            return true;
        }


        //we dont check the block above it since it is irrelevant for movementpurposes.
        return false;
    }

    public boolean isPossibleJump(INode node, Jumps jump, Directions direction) {
        if(jump.diagonal) {
            //check diagonal jump
            for(int i = 0; i <= jump.radius; i++) {
                BetterBlockPos pos1 = new BetterBlockPos(node.getCordX() + direction.x * i, node.getCordY(), node.getCordZ() + direction.x * i); //could probably be optimized with node.getX()

                if(i != jump.radius || jump == Jumps.DJ_PURPLE) {
                    if(!world.isAirBlock(pos1)) {
                        return false;
                    }
                }

                if(i != 0) {
                    //diagonal movement requires the adjecent blocks also to be air
                    BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * (i - 1), node.getCordY(), node.getCordZ() + direction.x * i);
                    BetterBlockPos pos3 = new BetterBlockPos(node.getCordX() + direction.x * i, node.getCordY(), node.getCordZ() + direction.x * (i - 1));

                    if(!world.isAirBlock(pos2.up()) || !world.isAirBlock(pos2.up(2))) {
                        return false;
                    }

                    if(!world.isAirBlock(pos3.up()) || !world.isAirBlock(pos3.up(2))) {
                        return false;
                    }
                }

                if(!world.isAirBlock(pos1.up()) || !world.isAirBlock(pos1.up(2))) {
                    return false;
                }
            }

            if(jump == Jumps.DJ_YELLOW ||jump == Jumps.DJ_RED || jump == Jumps.DJ_PURPLE) {
                BetterBlockPos pos1 = new BetterBlockPos(node.getCordX() + direction.x, node.getCordY() + 3, node.getCordZ() + direction.x);
                BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * 2, node.getCordY() + 3, node.getCordZ() + direction.x * 2);

                if(!world.isAirBlock(pos1) || !world.isAirBlock(pos2)) {
                    return false;
                }
            }

            if(jump == Jumps.DJ_RED) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * 3, node.getCordY() , node.getCordZ() + direction.x * 3);
                if (!isPlacableBlock(pos) && !world.getBlockState(pos).isSolid()) {
                    return false;
                }
            }

            return true;

        } else {
            //check straight jump
            for(int i = 0; i <= jump.radius; i++) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * i, node.getCordY(), node.getCordZ() + direction.x * i); //could probably be optimized with node.getX()

                if(i != jump.radius || jump == Jumps.SJ_PURPLE) {
                    if(!world.isAirBlock(pos)) {
                        return false;
                    }
                }

                if(!world.isAirBlock(pos.up()) || !world.isAirBlock(pos.up(2))) {
                    return false;
                }
            }

            if(jump == Jumps.SJ_YELLOW ||jump == Jumps.SJ_RED || jump == Jumps.SJ_PURPLE) {
                BetterBlockPos pos1 = new BetterBlockPos(node.getCordX() + direction.x * 2, node.getCordY() + 3, node.getCordZ() + direction.x * 2);
                BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * 3, node.getCordY() + 3, node.getCordZ() + direction.x * 3);

                if(!world.isAirBlock(pos1) || !world.isAirBlock(pos2)) {
                    return false;
                }
            }

            if(jump == Jumps.SJ_RED) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * 4, node.getCordY(), node.getCordZ() + direction.x * 4);
                if (!isPlacableBlock(pos) && !world.getBlockState(pos).isSolid()) {
                    return false;
                }
            }
        }

        return true;
    }
}
