package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrailAPI.pathing.algorithms.DLite.DLite;
import bbs.december.blocktrailAPI.IPathFindingAlgorithm;

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
        if(algorithm.getClass().isInstance(LPA.class)) {
            findLPAPath();
        } else if(algorithm.getClass().isInstance(DLite.class)) {
            findDLitePath();
        }
    }

    private void findDLitePath() {

    }

    private boolean findLPAPath() {
        LPA lpa = (LPA) algorithm;

        add(lpa.goalNode); //LPA starts its search for the optimal path from the goalNode

        INode node = lpa.goalNode;

        do { //run until the StartNode has been reached
            node = findNextNode(node);
            add(node);
        } while(!node.getClass().isInstance(StartNode.class));

        return true;
    }


    private INode findNextNode(INode node) {

        INode next = null;
        double smallest = Double.POSITIVE_INFINITY;

        for(int i = 0; i<3;i++) {
            for(int l = 0; l<3; l++) {
                for(int f = 0; f<3;f++) {
                    INode curr = algorithm.getPositionHashMap().get(node.getX() + i - 1,node.getY() + l - 1, node.getZ() + f - 1);

                    if(curr.moveCost(node) + curr.getG() < smallest) {
                        next = curr;
                        smallest = curr.moveCost(node) + curr.getG();
                    }

                    if(i == 1 && l ==1 && f == 0) f++; //again, not selecting the node itself

                }
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
}
