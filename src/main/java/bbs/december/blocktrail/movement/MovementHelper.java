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

    public boolean isPlaceableBlock(BetterBlockPos pos) {

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

    public Jumps getBestPossibleJump(INode node, Directions direction) {
        //this method does not check for black jumps, they should be checked before this method is called for a direction

        int r = 1;

        if(direction.diagonal) {

            while (r <= 3) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY(), node.getCordZ() + direction.z * r);

                if (!world.isAirBlock(pos.up(2)) && !world.isAirBlock(pos.up())) {
                    //the jump that is being checked is not possible
                    r--;
                    break;
                }


                //diagonal movement requires the adjecent blocks also to be air
                BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * (r - 1), node.getCordY(), node.getCordZ() + direction.z * r);
                BetterBlockPos pos3 = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY(), node.getCordZ() + direction.z * (r - 1));

                if (!world.isAirBlock(pos2) || !world.isAirBlock(pos2.up()) || !world.isAirBlock(pos2.up(2))) {
                    r--;
                    break;
                }

                if (!world.isAirBlock(pos3) || !world.isAirBlock(pos3.up()) || !world.isAirBlock(pos3.up(2))) {
                    r--;
                    break;
                }


                //diagonal jumps for ranges 2 and 3 require the ceiling to be 4 high at 2 and 3
                if (r == 2) {
                    BetterBlockPos ceilingPos1 = new BetterBlockPos(node.getCordX() + direction.x * 2, node.getCordY() + 3, node.getCordZ() + direction.z * 2);
                    BetterBlockPos ceilingPos2 = new BetterBlockPos(node.getCordX() + direction.x * 3, node.getCordY() + 3, node.getCordZ() + direction.z * 3);

                    BetterBlockPos dceilingPos1 = new BetterBlockPos(node.getCordX() + direction.x * 2, node.getCordY() + 3, node.getCordZ() + direction.z * 3);
                    BetterBlockPos dceilingPos2 = new BetterBlockPos(node.getCordX() + direction.x * 3, node.getCordY() + 3, node.getCordZ() + direction.z * 2);

                    if (!world.isAirBlock(ceilingPos1) || !world.isAirBlock(ceilingPos2) || !world.isAirBlock(dceilingPos1) || !world.isAirBlock(dceilingPos2)) {
                        r--;
                        break;
                    }
                }

                //checking if maxjump has been found
                if(!world.isAirBlock(pos)) {
                    break;
                }

                r++;
            }

            switch (r) {
                case 1:
                    return Jumps.DJ_WHITE;

                case 2:
                    return Jumps.DJ_YELLOW;

                case 3:
                    return Jumps.DJ_RED;

                case 4:
                    return Jumps.DJ_PURPLE;

                default:
                    return null; //jumping in this direction isnt possible

            }

        } else {
            while (r <= 4) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY(), node.getCordZ() + direction.z * r);

                if (!world.isAirBlock(pos.up(2)) && !world.isAirBlock(pos.up())) {
                    //the jump that is being checked is not possible
                    r--;
                    break;
                }

                //higher ceiling for long jumps
                if (r == 3) {
                    BetterBlockPos pos1 = new BetterBlockPos(node.getCordX() + direction.x * 2, node.getCordY() + 3, node.getCordZ() + direction.z * 2);
                    BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * 3, node.getCordY() + 3, node.getCordZ() + direction.z * 3);

                    if (!world.isAirBlock(pos1) || !world.isAirBlock(pos2)) {
                        r--;
                        break;
                    }
                }

                //checking if we have found our maxJump
                if (!world.isAirBlock((pos))) {
                    break;
                }

                r++;
            }

            switch (r) {
                case 1:
                    return Jumps.SJ_WHITE;

                case 2:
                    return Jumps.SJ_BLUE;

                case 3:
                    return Jumps.SJ_YELLOW;

                case 4:
                    return Jumps.SJ_RED;

                case 5:
                    return Jumps.SJ_PURPLE;

                default:
                    return null; //jumping in this direction isnt possible

            }
        }
    }

    //this is essentially just the inversion of the getBestPossibleJump-function
    public Jumps getBestPossibleInvertedJump(INode node, Directions direction) {

        int r = 1;

        if(direction.diagonal) {

            while (r < 3) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY() - 1, node.getCordZ() + direction.z * r);

                if (!world.isAirBlock(pos.up(2)) || !world.isAirBlock(pos.up()) || !world.isAirBlock(pos)) {
                    //the jump that is being checked is not possible
                    r--;
                    break;
                }


                //diagonal movement requires the adjecent blocks also to be air
                BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * (r - 1), node.getCordY() - 1, node.getCordZ() + direction.z * r);
                BetterBlockPos pos3 = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY() - 1, node.getCordZ() + direction.z * (r - 1));

                if (!world.isAirBlock(pos2) || !world.isAirBlock(pos2.up()) || !world.isAirBlock(pos2.up(2))) {
                    r--;
                    break;
                }

                if (!world.isAirBlock(pos3) || !world.isAirBlock(pos3.up()) || !world.isAirBlock(pos3.up(2))) {
                    r--;
                    break;
                }


                //diagonal jumps for ranges 2 and 3 require the ceiling to be 4 high at 2 and 3
                if (r == 2) {
                    BetterBlockPos ceilingPos1 = new BetterBlockPos(node.getCordX() + direction.x, node.getCordY() + 2, node.getCordZ() + direction.z);
                    BetterBlockPos ceilingPos2 = new BetterBlockPos(node.getCordX(), node.getCordY() + 2, node.getCordZ());

                    BetterBlockPos ceilingDPos1 = new BetterBlockPos(node.getCordX(), node.getCordY() + 2, node.getCordZ() + direction.z);
                    BetterBlockPos ceilingDPos2 = new BetterBlockPos(node.getCordX() + direction.x, node.getCordY() + 2, node.getCordZ());


                    if (!world.isAirBlock(ceilingPos1) || !world.isAirBlock(ceilingPos2) || !world.isAirBlock(ceilingDPos1) || !world.isAirBlock(ceilingDPos2)) {
                        r--;
                        break;
                    }
                }

                //the red jump does not have to be checked, if it gets to three, we directly move on to purple

                r++;
            }

            /**
            //checking the purple jump
            if(r == 3) {

                for(int i = 1; i < 4; i++) {
                    BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * i, node.getCordY(), node.getCordZ() + direction.z * i);
                    BetterBlockPos dpos1 = new BetterBlockPos(node.getCordX() + direction.x * (i - 1), node.getCordY(), node.getCordZ() + direction.z * i);
                    BetterBlockPos dpos2 = new BetterBlockPos(node.getCordX() + direction.x * i, node.getCordY(), node.getCordZ() + direction.z * (i - 1));

                    if(i == 1 || i == 2) {
                        if(!world.isAirBlock(pos.up(3)) || !world.isAirBlock(dpos1.up(3)) || !world.isAirBlock(dpos2.up(3))) {
                            r--;
                            break;
                        }
                    } else if(i == 0) {
                        if(!world.isAirBlock(pos.up(2)) || !world.isAirBlock(dpos1.up(2)) || !world.isAirBlock(dpos2.up(2))) {
                            r--;
                            break;
                        }
                    } else {
                        if(!world.isAirBlock(pos.up(2))) {
                            r--;
                            break;
                        }
                    }
                }
            }

            **/
            switch (r) {
                case 1:
                    return Jumps.DJ_WHITE;

                case 2:
                    return Jumps.DJ_YELLOW; //has to be checked if the block y+2 above the node isAir.

                case 3:
                    return Jumps.DJ_PURPLE;

                default:
                    return null; //jumping in this direction isnt possible

            }

        } else {
            while (r < 4) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY() - 1, node.getCordZ() + direction.z * r);

                if (!world.isAirBlock(pos.up(2)) || !world.isAirBlock(pos.up()) || !world.isAirBlock(pos)) {
                    //the jump that is being checked is not possible
                    r--;
                    break;
                }

                //higher ceiling for long jumps
                if (r == 3) {

                    BetterBlockPos ceilingPos1 = new BetterBlockPos(node.getCordX() + direction.x, node.getCordY() + 2, node.getCordZ() + direction.z);
                    BetterBlockPos ceilingPos2 = new BetterBlockPos(node.getCordX(), node.getCordY() + 2, node.getCordZ());

                    if (!world.isAirBlock(ceilingPos1) || !world.isAirBlock(ceilingPos2)) {
                        r--;
                        break;
                    }
                }

                //red jump gets skipped, since it cant be an origin

                r++;
            }

            /**
            //checking the purple jump
            if(r == 4) {

                for(int i = 1; i < 5; i++) {
                    BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * i, node.getCordY(), node.getCordZ() + direction.z * i);

                    if(i == 1 || i == 2) {
                        if(!world.isAirBlock(pos.up(3))) {
                            r--;
                            break;
                        }
                    } else {
                        if(!world.isAirBlock(pos.up(2))) {
                            r--;
                            break;
                        }
                    }
                }
            }
            **/

            switch (r) {
                case 1:
                    return Jumps.SJ_WHITE;


                case 2:
                    return Jumps.SJ_BLUE; //has to be checked by calling method, if block y+2 of node is air

                case 3:
                    return Jumps.SJ_YELLOW;

                case 4:
                    return Jumps.DJ_RED;

                default:
                    return null; //jumping in this direction isnt possible

            }
        }
    }

    public boolean isPurplePredecessorPossible(INode node, Directions direction) {

        int r = 1;

        if(direction.diagonal) {

            BetterBlockPos ceilingPos1 = new BetterBlockPos(node.getCordX() + direction.x, node.getCordY() + 3, node.getCordZ() + direction.z);
            BetterBlockPos ceilingPos2 = new BetterBlockPos(node.getCordX(), node.getCordY() + 3, node.getCordZ());

            BetterBlockPos ceilingDPos1 = new BetterBlockPos(node.getCordX(), node.getCordY() + 3, node.getCordZ() + direction.z);
            BetterBlockPos ceilingDPos2 = new BetterBlockPos(node.getCordX() + direction.x, node.getCordY() + 3, node.getCordZ());


            if (!world.isAirBlock(ceilingPos1) || !world.isAirBlock(ceilingPos2) || !world.isAirBlock(ceilingDPos1) || !world.isAirBlock(ceilingDPos2)) {
                return false;
            }

            while (r <= 3) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY(), node.getCordZ() + direction.z * r);

                if (!world.isAirBlock(pos.up(2)) && !world.isAirBlock(pos.up())) {
                    //the jump that is being checked is not possible
                    return false;
                }


                //diagonal movement requires the adjecent blocks also to be air
                BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * (r - 1), node.getCordY(), node.getCordZ() + direction.z * r);
                BetterBlockPos pos3 = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY(), node.getCordZ() + direction.z * (r - 1));

                if (!world.isAirBlock(pos2) || !world.isAirBlock(pos2.up()) || !world.isAirBlock(pos2.up(2))) {
                    return false;
                }

                if (!world.isAirBlock(pos3) || !world.isAirBlock(pos3.up()) || !world.isAirBlock(pos3.up(2))) {
                    return false;
                }


                //checking if maxjump has been found
                if(!world.isAirBlock(pos) && r != 3) {
                    return false;
                }

                r++;
            }

            BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * 3, node.getCordY(), node.getCordZ() + direction.z * 3);

            if(!isPlaceableBlock(pos) && world.isAirBlock(pos)) {
                return false;
            }
            return true;

        } else {

            BetterBlockPos pos1 = new BetterBlockPos(node.getCordX() + direction.x * 1, node.getCordY() + 3, node.getCordZ() + direction.z * 1);
            BetterBlockPos pos2 = new BetterBlockPos(node.getCordX() + direction.x * 2, node.getCordY() + 3, node.getCordZ() + direction.z * 2);

            if (!world.isAirBlock(pos1) || !world.isAirBlock(pos2)) {
                return false;
            }

            while (r <= 4) {
                BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * r, node.getCordY(), node.getCordZ() + direction.z * r);

                if (!world.isAirBlock(pos.up(2)) && !world.isAirBlock(pos.up())) {
                    //the jump that is being checked is not possible
                    return false;
                }

                //checking if we have found our maxJump
                if (!world.isAirBlock(pos) && r != 4) {
                    return false;
                }

                r++;
            }
        }

        BetterBlockPos pos = new BetterBlockPos(node.getCordX() + direction.x * 4, node.getCordY(), node.getCordZ() + direction.z * 4);

        if(!isPlaceableBlock(pos) && world.isAirBlock(pos)) {
            return false;
        }
        return true;
    }

    public Walks isWalkable(INode node, Directions direction) {
        BetterBlockPos pos = new BetterBlockPos(node.getX() + direction.x, node.getY(), node.getZ() + direction.z);

        if(direction.diagonal) {
            BetterBlockPos dpos1 = new BetterBlockPos(node.getX() + direction.x, node.getY(), node.getZ());
            BetterBlockPos dpos2 = new BetterBlockPos(node.getX(), node.getY(), node.getZ() + direction.z);

            if(!world.isAirBlock(dpos1) || !world.isAirBlock(dpos1.up())) {
                return null;
            }

            if(!world.isAirBlock(dpos2) || !world.isAirBlock(dpos2.up())) {
                return null;
            }

            if(!world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
                return null;
            }

            if(world.isAirBlock(pos.down()) && !isPlaceableBlock(pos)) {
                //return Walks.D_DROP; //we are not implementing drops just now
                return null;
            }

            return Walks.D_WALK;

        } else {
            return Walks.S_WALK;
        }

    }
}
