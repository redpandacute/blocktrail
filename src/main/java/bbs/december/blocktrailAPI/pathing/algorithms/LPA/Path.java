package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrailAPI.pathing.algorithms.DLite.DLite;
import bbs.december.blocktrailAPI.IPathFindingAlgorithm;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;


public class Path implements Iterable<INode> {

    private List<INode> list = new ArrayList<>();

    IPathFindingAlgorithm algorithm;

    public Path(IPathFindingAlgorithm algorithm) {
        this.algorithm = algorithm;

        findPath();
    }

    private void findPath() {
        if(algorithm instanceof LPA) {
            findLPAPath();
        } else if(algorithm instanceof DLite) {
            findDLitePath();
        }
    }

    private void findDLitePath() {

    }

    private boolean findLPAPath() {
        LPA lpa = (LPA) algorithm;

        add(lpa.getPositionHashMap().getGoalNode()); //LPA starts its search for the optimal path from the goalNode

        INode node = lpa.getPositionHashMap().getGoalNode();

        do { //run until the StartNode has been reached
            node = findNextNode(node);
            add(node);
        } while(!(node instanceof StartNode));

        return true;
    }


    private INode findNextNode(INode node) {


        INode next = null;
        double smallest = Double.POSITIVE_INFINITY;

        for(INode curr : node.getPredecessors()) {
            if(curr.getG() + curr.heuristic() < smallest) {
                smallest = curr.getG() + curr.heuristic();
                next = curr;
            }
        }

        return next;
    }


    public void add(INode node) {
        list.add(list.size(), node);
    }


    @Override
    public Iterator<INode> iterator() {
        return list.iterator();
    }

    @Override
    public void forEach(Consumer<? super INode> action) {

    }

    public void display() {
        for(INode node : this) {
            World world = node.getHashMap().getWorld();
            world.setBlockState(node.getBlockPos(), Blocks.MELON.getDefaultState());
        }
    }
}
