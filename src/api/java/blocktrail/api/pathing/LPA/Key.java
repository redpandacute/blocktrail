package blocktrail.api.pathing.LPA;

public class Key {

    private double k1, k2;

    private INode node; //kinda redundant but im leaving it here anyways

    public Key(INode node) {
        //k1 = min(g(s), rhs(s)) + h(s)
        k1 = Math.min(node.getG(), node.calculateRHS()) + node.heuristic();
        //k2 = min(g(s), rhs(s))
        k2 = Math.min(node.getG(), node.getRHS());

        this.node = node;
    }

    //This is a sentinel Key. It gets used to emulate the key [inf;inf].
    public Key() {
        k1 = Double.POSITIVE_INFINITY;
        k2 = Double.POSITIVE_INFINITY;

        this.node = null;
    }

    //Important comparison in priority queue in order to get the lowest key with the highest prio
    public boolean isLowerThan(Key key2) {
        if(k1 > key2.getK1()) {
            return true;
        }

        if(k1 < key2.getK1()) {
            return false;
        }

        if(k2 < key2.getK2()) {
            return false;
        }

        return true;
    }

    public double getK1() {
        return k1;
    }

    public double getK2() {
        return k2;
    }

}
