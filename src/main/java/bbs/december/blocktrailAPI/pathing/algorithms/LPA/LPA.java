package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import bbs.december.blocktrailAPI.IPathFindingAlgorithm;

public class LPA implements IPathFindingAlgorithm {

    private Thread thread;
    private String threadName;

    PriorityQueue priorityQueue;
    IPositionHashMap positionHashMap;

    boolean active = false;
    private Path path = null;

    public LPA(IPositionHashMap positionHashMap, String threadName) {
        this.threadName = threadName;

        this.positionHashMap = positionHashMap;
    }

    private void initialize() {
        priorityQueue = new PriorityQueue();

        priorityQueue.add(positionHashMap.getStartNode());
    }

    public void updateNode(INode node) {
        System.out.println("updating node at: " + node.getX() + " " + node.getY() + " " + node.getZ());

        if(!node.getClass().isInstance(StartNode.class)) {
            node.calculateRHS();

            if (priorityQueue.containsKey(node.getHashKey())) {
                priorityQueue.remove(node);
            }

            if (node.getG() != node.getRHS()) {
                priorityQueue.add(node);
            }
        }
    }

    private void computeShortestPath() {
        while((priorityQueue.lowestKey().isLowerThan(positionHashMap.getGoalNode().calculateKey()) || !positionHashMap.getGoalNode().isLocallyConsistent())) {
            //unsure if the algorithm could get stuck here at the comparison of "isLowerThan" since it actually means <= and not <

            INode node = priorityQueue.pop();

            if(node.getG() > node.getRHS()) {
                node.setG(node.getRHS());


                for(INode curr : positionHashMap.getSuccessors(node)) {
                    updateNode(curr);
                }
                 //todo: determine how i want this to be done

                /**
                for(int i =0; i < 3;i++) {
                    for(int l =0; l < 3;l++) {
                        for(int f =0; f < 3;f++) {
                            Node curr = positionHashMap.get(node.getX() + l - 1,node.getY()+ i - 1,node.getZ() + f - 1);
                            updateNode(curr);
                            if(i==1 && l==1 && f==0) f++ ; //skip one in order to not get the node itself

                        }
                    }
                }
                 **/
            } else {
                node.setG(Double.POSITIVE_INFINITY);

                updateNode(node); //in this case, the node itself should also get updated

                for(INode curr : positionHashMap.getSuccessors(node)) {
                    updateNode(curr);
                }



                /**
                for(int i =0; i < 3;i++) {
                    for(int l =0; l < 3;l++) {
                        for(int f =0; f < 3;f++) {
                            Node curr = positionHashMap.get(node.getX() + l - 1,node.getY()+ i - 1,node.getZ() + f - 1);
                            updateNode(curr); //in this specific case, we want the node itself also to get updated
                        }
                    }
                }
                 **/
            }
        }
    }

    public void updateGrid() {
         computeShortestPath();

         //todo update path
    }

    public void updatePath() {
            this.path = new Path(this);
            path.display();
    }

    public void run() {

        computeShortestPath();
        updatePath();

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
    public IPositionHashMap getPositionHashMap() {
        return positionHashMap;
    }

    @Override
    public void start() {
        if(thread == null) {
            this.active = true;
            initialize();

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
