package blocktrail.api.pathing.LPA;

import java.util.HashMap;

public interface IQueue  {

    void add(INode node);
    INode get(int x, int y, int z);

    INode pop();

    INode lowest();
}
