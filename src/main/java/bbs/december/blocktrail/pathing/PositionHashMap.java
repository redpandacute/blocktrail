package bbs.december.blocktrail.pathing;

import bbs.december.blocktrail.movement.Directions;
import bbs.december.blocktrail.movement.Jumps;
import bbs.december.blocktrail.movement.MovementHelper;
import bbs.december.blocktrailAPI.pathing.algorithms.LPA.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public ArrayList<INode> findSuccessors(INode node) {

        ArrayList<INode> suc = new ArrayList<>();

        if(node.getClass().isInstance(AirNode.class)) {
            if(world.getBlockState(new BlockPos(node.getCordX(), node.getCordY() - 1, node.getZ())).isSolid()) { //todo get a better blockhelper for solid check
                suc.add(this.get(node.getX(),node.getY(), node.getZ(), false)); //migth create a problem with the reaction time when it comes to placing the water but it should be okey for now
                return suc;
            } else {
                suc.add(this.get(node.getX(), node.getY() - 1, node.getZ(), true)); //todo implement liquid management, for now it should just avoid liquids at all cost
                return suc;
            }
        }


        //getting all possible successors for a regular node

        //white traverse movement -- these always get added since the algorithm could be bridging
        addStraightMovementRadius(suc, node, 1, 0, false);
        addDiagonalMovementRadius(suc, node, 1, 0, false);

        //white drop movement
        addStraightMovementRadius(suc, node, 1, -1, true);
        addDiagonalMovementRadius(suc, node, 1, -1, true); //leaving this out for the moment to avoid problems with the

        //question is if the possible movments should already be evaluated here or in the movecost

    }


    //these two function seem very scary and they probably are, i really need to talk to somebody about this.

    @Override
    public ArrayList<INode> getPredecessors(INode node) {
        ArrayList<INode> predecessors = new ArrayList<>();

        //this is not here to stay, its just for the sake of testing here
        //todo remove options from here and add enum for movementtypes

        boolean parcour = false;

        /* there will be 5 categories for movement:

            walk/sprint (t1)
            short jump (t2)
            medium jump (t3)
            long jump (t4)
            parcour (t5)


            movement is split into 4 layers:

            up (x, +1, z)
            even (x, 0, z)
            down (x, -1, z)
            parcour (x, -drop, z)
         */

        //walking predecessors

        for(int i =0; i < 3;i++) {
            for(int l =0; l < 3;l++) {
                for(int f =0; f < 3;f++) {
                    predecessors.add(get(node.getX() + l - 1,node.getY() + i - 1,node.getZ() + f - 1));
                    if(i==1 && l==1 && f==0) f++ ; //skip one in order to not get the node itself
                }
            }
        }

        //add additional y+1 predecessors
        addStraightMovementRadius(predecessors, node, 2, 1);
        addDiagonalMovementRadius(predecessors, node, 2, 1);

        //jump predecessors

        //short jump even
        addStraightMovementRadius(predecessors, node, 2, 0);
        addDiagonalMovementRadius(predecessors, node, 2, 0);

        //shortjump up
        addStraightMovementRadius(predecessors, node, 2, -1);
        addDiagonalMovementRadius(predecessors, node, 2, -1);

        //shortjump down
        addStraightMovementRadius(predecessors, node, 3, 1);

        //mediumjump even
        addStraightMovementRadius(predecessors, node, 3, 0);

        //mediumjump up
        addStraightMovementRadius(predecessors, node, 3, -1);

        //mediumjump down
        addStraightMovementRadius(predecessors, node, 4, 1);
        addStraightMovementRadius(predecessors, node, 3, 1);

        //longjump even
        addStraightMovementRadius(predecessors, node, 4, 0);
        addDiagonalMovementRadius(predecessors, node, 3, 0);

        //longjump up
        addStraightMovementRadius(predecessors, node, 4, -1);

        //longjump down
        addStraightMovementRadius(predecessors, node, 5, 1);
        addStraightMovementRadius(predecessors, node, 4, 1);

        //todo add parcour predecessors when implemented

        return predecessors;
    }

    //essentially the same as the predecessors only upside down
    @Override
    public ArrayList<INode> getSuccessors(INode node) {

        ArrayList<INode> successors = new ArrayList<>();

        //white

        //blue

        //yellow

        //red

        //purple

        /**
        for(int i =0; i < 3;i++) {
            for(int l =0; l < 3;l++) {
                for(int f =0; f < 3;f++) {
                    successors.add(get(node.getX() + l - 1,node.getY() + i - 1,node.getZ() + f - 1));
                    if(i==1 && l==1 && f==0) f++ ; //skip one in order to not get the node itself
                }
            }
        }


        //add additional down walk successors
        addStraightMovementRadius(successors, node, 2, -1);
        addDiagonalMovementRadius(successors, node, 2, -1);

        //jump predecessors

        //short jump even
        addStraightMovementRadius(successors, node, 2, 0);
        addDiagonalMovementRadius(successors, node, 2, 0);

        //shortjump up
        addStraightMovementRadius(successors, node, 2, 1);
        addDiagonalMovementRadius(successors, node, 2, 1);

        //shortjump down
        addStraightMovementRadius(successors, node, 3, -1);

        //mediumjump even
        addStraightMovementRadius(successors, node, 3, 0);

        //mediumjump up
        addStraightMovementRadius(successors, node, 3, 1);

        //mediumjump down
        addStraightMovementRadius(successors, node, 4, -1);
        addStraightMovementRadius(successors, node, 3, -1);

        //longjump even
        addStraightMovementRadius(successors, node, 4, 0);
        addDiagonalMovementRadius(successors, node, 3, 0);

        //longjump up
        addStraightMovementRadius(successors, node, 4, 1);

        //longjump down
        addStraightMovementRadius(successors, node, 5, -1);
        addStraightMovementRadius(successors, node, 4, -1);

        //todo add parcour predecessors when implemented
    **/
        return successors;
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

    private void addMovementRadius(ArrayList<INode>, Jumps jump,) {

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
