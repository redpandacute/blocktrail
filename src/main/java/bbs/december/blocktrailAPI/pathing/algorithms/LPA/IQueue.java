package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

public interface IQueue  {

    void add(INode node);
    INode get(int x, int y, int z);

    INode pop();

    INode lowest();
}
