package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import java.util.HashMap;
import java.util.Map;

public class PriorityQueue extends HashMap<String, INode> implements IQueue  {

    @Override
    public void add(INode node) {
        node.calculateKey(); //Update Key
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
    public INode lowest() {

        INode lowest = null;

        for(Map.Entry<String, INode> entry : entrySet()) {
            if(lowest == null) {
                lowest = entry.getValue();
            } else if(entry.getValue().getKey().isLowerThan(lowest.getKey())) {
                lowest = entry.getValue();
            }
        }

        return lowest;
    }
}
