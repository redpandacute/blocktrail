package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import java.util.HashMap;
import java.util.Map;

public class PriorityQueue extends HashMap<String, INode> implements IQueue  {

    @Override
    public void add(INode node) {

        if(node instanceof StartNode) {
            node.calculateKey(); //Update Key
        }

        put(""+node.getX()+""+node.getY()+""+node.getZ(), node);   //the key seems a little off but i guess it should work like that
        //I am not sure if without the "" the coordinates would just get added together.
    }

    @Override
    public INode get(int x, int y, int z) {
        return get(""+x+""+y+""+z);
    }

    @Override
    public INode pop() {
        try {
            INode node = lowest();
            remove(node.getHashKey());
            return node;
        } catch(Exception e) {}
        return null;
    }

    @Override
    public Key lowestKey() {

        Key lowest = null;

        if(this.isEmpty()) {
            lowest = new Key();
            return lowest;
        }

        for(INode curr : values()) {


            if(lowest == null) {
                lowest = curr.calculateKey();
            } else if(curr.calculateKey().isLowerThan(lowest)) {
                lowest = curr.calculateKey();
            }
        }

        return lowest;
    }

    private INode lowest() {

        INode lowest = null;
        if (isEmpty()) {
            return null;
        }

        for (INode curr : values()) {

            if (lowest == null) {
                lowest = curr;
            } else if (curr.calculateKey().isLowerThan(lowest.calculateKey())) {
                lowest = curr;
            }
        }

        return lowest;
    }
}
