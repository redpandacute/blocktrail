package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrail.pathing.PositionHashMap;
import bbs.december.blocktrailAPI.IPathFindingAlgorithm;

public class LPA implements IPathFindingAlgorithm {

    private Thread thread;
    private String threadName;

    PriorityQueue priorityQueue;
    PositionHashMap positionHashMap;

    StartNode startNode;
    GoalNode goalNode;

    boolean active = false;

    public LPA(StartNode startNode, GoalNode goalNode, String threadName) {
        this.startNode = startNode;
        this.goalNode = goalNode;
        this.threadName = threadName;
    }

    private void initialize(StartNode startNode, GoalNode goalNode) {
        priorityQueue = new PriorityQueue();
        positionHashMap = new PositionHashMap(startNode, goalNode);

        priorityQueue.add(startNode);
    }

    public void updateNode(INode node) {
        if(!node.getClass().isInstance(StartNode.class)) {
            node.calculateRHS();

            if (priorityQueue.containsKey(node.getHashKey())) {
                priorityQueue.remove(node);
            }

            if (node.getG() != node.getRHS()) {
                priorityQueue.add(node);
                //node.calculateKey(); //is already implemented into add function of the priority queue
            }
        }
    }

    private void computeShortestPath() {
        while((priorityQueue.lowest().getKey().isLowerThan(positionHashMap.goalNode.calculateKey()) || !positionHashMap.goalNode.isLocallyConsistent()) && active) {
            //unsure if the algorithm could get stuck here at the comparison of "isLowerThan" since it actually means <= and not <

            INode node = priorityQueue.pop();

            if(node.getG() > node.getRHS()) {
                node.setG(node.getRHS());

                for(int i =0; i < 3;i++) {
                    for(int l =0; l < 3;l++) {
                        for(int f =0; f < 3;f++) {
                            Node curr = positionHashMap.get(node.getX() + l - 1,node.getY()+ i - 1,node.getZ() + f - 1);
                            updateNode(curr);
                            if(i==1 && l==1 && f==0) f++ ; //skip one in order to not get the node itself

                        }
                    }
                }
            } else {
                node.setG(Double.POSITIVE_INFINITY);

                for(int i =0; i < 3;i++) {
                    for(int l =0; l < 3;l++) {
                        for(int f =0; f < 3;f++) {
                            Node curr = positionHashMap.get(node.getX() + l - 1,node.getY()+ i - 1,node.getZ() + f - 1);
                            updateNode(curr); //in this specific case, we want the node itself also to get updated
                        }
                    }
                }
            }
        }
    }

    public void updatePath() {
         computeShortestPath();

         //todo update path
    }

    public void run() {

        computeShortestPath();

        // todo needs a listener that updates the algorithm on a mapupdate

        /**
        while(active) {
            while(!hasChanges && active) {
                wait(TIMEOUT);
            }
            if(active) {
                for (changedNode : changedNodes) {
                    updateNode(changedNode);
                }
            }
        }
         **/



        /*forever
            {19} ComputeShortestPath();
            {20} Wait for changes in edge costs;
            {21} for all directed edges (u, v) with changed edge costs
            {22} Update the edge cost c(u, v);
            {23} UpdateVertex(v);
        */
    }

    @Override
    public PositionHashMap getPositionHashMap() {
        return positionHashMap;
    }

    @Override
    public void start() {
        if(thread == null) {
            initialize(startNode, goalNode);

            this.active = true;

            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    @Override
    public void end() {
        this.active = false;
    }

    @Override
    public void kill() {
        thread.interrupt();
    }

    @Override
    public void pause() {
        try {
            thread.wait(); //unsure if this works
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resume() {
        thread.notify(); //unsure if this works but i dont see a reason why i really need this anyways
    }


}