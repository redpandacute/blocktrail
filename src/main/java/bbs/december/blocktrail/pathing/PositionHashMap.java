package bbs.december.blocktrail.pathing;

import bbs.december.blocktrail.movement.Directions;
import bbs.december.blocktrail.movement.Jumps;
import bbs.december.blocktrail.movement.MovementHelper;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.*;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class PositionHashMap extends HashMap<String, INode> implements IPositionHashMap {

    public final StartNode startNode;
    public final GoalNode goalNode;

    private final World world;

    public static CostHelper costHelper;
    public static MovementHelper movementHelper;


    public PositionHashMap(StartNode startNode, GoalNode goalNode, World world) {
        this.startNode = startNode;
        this.goalNode = goalNode;

        costHelper = new CostHelper(this);
        movementHelper = new MovementHelper(this);

        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void add(INode node) {
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

    //finding all possible airnodes
    @Override
    public ArrayList<INode> getSuccessors(INode node) {

        ArrayList<INode> suc = new ArrayList<>();

        if(node.getClass().isInstance(AirNode.class)) {
            if(world.getBlockState(new BlockPos(node.getCordX(), node.getCordY() - 1, node.getZ())).isSolid()) { //todo get a better blockhelper for solid check
                suc.add(get(node.getX(),node.getY(), node.getZ(), false)); //migth create a problem with the reaction time when it comes to placing the water but it should be okey for now
                return suc;
            } else {
                suc.add(get(node.getX(), node.getY() - 1, node.getZ(), true)); //todo implement liquid management, for now it should just avoid liquids at all cost
                return suc;
            }
        }

        suc.add(get(node.getX(), node.getY(), node.getZ(), true)); //adding the dig move, which essentially just turns the current node into an airnode


        //getting all possible successors for a regular node
        if(world.isAirBlock(new BetterBlockPos(node.getCordX(), node.getCordY() + 1, node.getCordZ()))) {
            //Blackjump is possible
            suc.add(get(node.getX(), node.getY() + 1, node.getZ(), true));

            for(Directions direction : Directions.values()) { //adding all the possible jumpsuccessors in only 2 lines :) suck it baritone
                addJumpSuccessors(node, suc, movementHelper.getBestPossibleJump(node, direction), direction);
            }
        }
        //white traverse movement -- these always get added since the algorithm could be bridging
        addStraightMovementRadius(suc, node, 1, 0, false);
        addDiagonalMovementRadius(suc, node, 1, 0, false);

        //white drop movement todo: add dropmovement (left out in order to finally make some progress :) )
        //addStraightMovementRadius(suc, node, 1, -1, true);
        //addDiagonalMovementRadius(suc, node, 1, -1, true); //leaving this out for the moment to avoid problems with the
        return suc;
    }

    @Override
    public ArrayList<INode> getPredecessors(INode node) {

        ArrayList<INode> pre = new ArrayList<>();

        if(node.getClass().isInstance(AirNode.class)) {
            add(get(node.getX(), node.getY() + 1, node.getZ(), true));


            //adding all the possible jumps

            for(Directions direction : Directions.values()) { //adding all the possible jumppredecessors in only 2 lines :) suck it baritone
                addJumpPredecessors(node, pre, movementHelper.getBestPossibleJump(node, direction), direction);
            }

        } else {
            //if the current node is a regular node, it can either be reached by a red jump, by an airnode inside the current node or an adjecent block (walking)
            addStraightMovementRadius(pre, node, 1, 0, false);
            addDiagonalMovementRadius(pre, node, 1, 0, false);

            add(get(node.getX(), node.getY(), node.getZ(), true));

            //adding redjumps and placable purplejumps
            for(Directions direction : Directions.values()) {
                if(direction.diagonal) {
                    INode jnode = get(node.getX() + direction.x * 3, node.getY() - 1, node.getZ() + direction.z * 3, false);
                    Jumps bestjump = movementHelper.getBestPossibleJump(jnode, direction);
                    if(bestjump == Jumps.DJ_RED || (bestjump == Jumps.DJ_PURPLE && movementHelper.isPlaceableBlock(new BetterBlockPos(node.getCordX(), node.getCordY(), node.getCordZ())))) {
                        pre.add(jnode);
                    }
                } else {
                    INode jnode = get(node.getX() + direction.x * 4, node.getY() - 1, node.getZ() + direction.z * 4, false);
                    Jumps bestjump = movementHelper.getBestPossibleJump(jnode, direction);
                    if(bestjump == Jumps.SJ_RED || (bestjump == Jumps.SJ_PURPLE && movementHelper.isPlaceableBlock(new BetterBlockPos(node.getCordX(), node.getCordY(), node.getCordZ())))) {
                        pre.add(jnode);
                    }
                }
            }
        }


        return pre;
    }

    private void addJumpSuccessors(INode node ,ArrayList<INode> suc, Jumps bestPossibleJump, Directions direction) {

        if(bestPossibleJump == null) {
            return; //jumps in this direction arent possible
        }

        for(int i = 1; i < bestPossibleJump.radius; i++) {
            suc.add(get(node.getX() + direction.x * i, node.getY() + 1, node.getZ() + direction.z * i, true));
        }

        if (bestPossibleJump == Jumps.SJ_PURPLE || bestPossibleJump == Jumps.DJ_PURPLE) {
            if(movementHelper.isPlaceableBlock(new BetterBlockPos(node.getX() + direction.x * bestPossibleJump.radius, node.getY() + 1, node.getZ() + direction.z * bestPossibleJump.radius))) {
                suc.add(get(node.getX() + direction.x * bestPossibleJump.radius, node.getY() + 1, node.getZ() + direction.z * bestPossibleJump.radius, false));
            }
            suc.add(get(node.getX() + direction.x * bestPossibleJump.radius, node.getY(), node.getZ() + direction.z * bestPossibleJump.radius, true));
        } else {
            suc.add(get(node.getX() + direction.x * bestPossibleJump.radius, node.getY() + 1, node.getZ() + direction.z * bestPossibleJump.radius, bestPossibleJump.airable));
        }
    }

    private void addJumpPredecessors(INode node, ArrayList<INode> pre, Jumps bestPossibleInvertedJump, Directions direction) {

        if(bestPossibleInvertedJump == null) {
            return; //jumps from this direction arent possible
        }

        for(int i = 1; i < bestPossibleInvertedJump.radius; i++) {
            pre.add(get(node.getX() + direction.x * i, node.getY() - 1, node.getZ() + direction.z * i, false));
        }

        if(bestPossibleInvertedJump == Jumps.SJ_PURPLE || bestPossibleInvertedJump == Jumps.DJ_PURPLE) {
            pre.add(get(node.getX() + direction.x * bestPossibleInvertedJump.radius, node.getY(), node.getZ() + direction.z * bestPossibleInvertedJump.radius, false));
        } else {
            pre.add(get(node.getX() + direction.x * bestPossibleInvertedJump.radius, node.getY() - 1, node.getZ() + direction.z * bestPossibleInvertedJump.radius, bestPossibleInvertedJump.airable));
        }
    }




    /**
    @Override
    public INode get(int x, int y, int z) {
        if(containsKey(x+""+y+""+z)) {
            return get(x + "" + y + "" + z);
        } else {
            INode node = createNode(x, y, z, false);

            if(node != null) {
                add(node);
                return node;
            } else {
                return null;
            }

        }
    }
     **/ //this has become redundant

    @Override
    public INode get(int x, int y, int z, boolean airnode) {

        if(airnode) {
            if(containsKey(x+""+y+""+z+"A")) {
                return get(x+""+y+""+z+"A");
            } else {
                INode node = createNode(x, y, z, true); //we want to create the node if it doesnt already exist.
                return node;
            }

        } else {
            if(containsKey(x+""+y+""+z)) {
                return get(x+""+y+""+z);
            } else {
                INode node = createNode(x, y, z, false);
                return node;
            }
        }
    }

    private INode createNode(int x, int y, int z, boolean airnode) {
        INode node = null;
        if(airnode) {
            node = new AirNode(x, y, z, this);
        } else {
            node = new Node(x, y, z, this);
        }

        add(node);
        return node;
    }

    public BlockState getBlock(int x, int y, int z) {
        return world.getBlockState(new BlockPos(x,y,z));
    }

    public BlockState getBlock(INode node) {
        return world.getBlockState(new BetterBlockPos(node.getX(),node.getY(),node.getZ()));
    }


    private void addStraightMovementRadius(ArrayList<INode> list, INode node, int r, int y, boolean airnode) {
        list.add(get(node.getX(),node.getY() + y,node.getZ() - r, airnode));
        list.add(get(node.getX(),node.getY() + y,node.getZ() + r, airnode));

        list.add(get(node.getX() - r,node.getY() + y, node.getZ(), airnode));
        list.add(get(node.getX() + r,node.getY() + y, node.getZ(), airnode));

    }

    private void addDiagonalMovementRadius(ArrayList<INode> list, INode node, int r, int y, boolean airnode) {
        list.add(get(node.getX() + r,node.getY() + y,node.getZ() - r, airnode));
        list.add(get(node.getX() + r,node.getY() + y,node.getZ() + r, airnode));

        list.add(get(node.getX() - r,node.getY() + y,node.getZ() - r, airnode));
        list.add(get(node.getX() - r,node.getY() + y,node.getZ() + r, airnode));
    }
}
