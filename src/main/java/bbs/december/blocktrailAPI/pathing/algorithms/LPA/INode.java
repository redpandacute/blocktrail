package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

public interface INode {



    double STRAIGHT_COST = 1;
    double DIAGONAL_COST = 1.4; //Actually sqrt(2) but it makes sense to just approximate it
    double VERTICAL_COST = 1; //for now i consider going up as efficient as going down

    double INFINITY =   Double.POSITIVE_INFINITY;

    double heuristic();
    double moveCost(INode node);

    int getX();
    int getY();
    int getZ();

    double getG();
    void setG(double g);

    double getRHS();

    double calculateRHS();
    Key calculateKey();

    Key getKey();
    String getHashKey();

    boolean isLocallyConsistent();

}
