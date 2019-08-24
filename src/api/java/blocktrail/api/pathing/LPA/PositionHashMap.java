package blocktrail.api.pathing.LPA;

import java.util.HashMap;

public class PositionHashMap  extends HashMap<String, Node> {

    public final StartNode startNode;
    public final GoalNode goalNode;

    public PositionHashMap(StartNode startNode, GoalNode goalNode) {
        this.startNode = startNode;
        this.goalNode = goalNode;
    }

    public void add(Node node) {
        put(""+node.x+""+node.y+""+node.z, node);   //the key seems a little off but i guess it should work like that
                                                    //I am not sure if without the "" the coordinates would just get added together.
    }

    public Node get(int x, int y, int z) {
        return get(""+x+""+y+""+z);
    }
}
