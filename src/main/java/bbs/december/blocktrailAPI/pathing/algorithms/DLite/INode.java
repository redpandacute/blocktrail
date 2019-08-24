package bbs.december.blocktrailAPI.pathing.algorithms.DLite;

public interface INode {



    double STRAIGHT_COST = 1;
    double DIAGONAL_COST = 1.4; //Actually sqrt(2) but it makes sense to just approximate it
    double VERTICAL_COST = 1; //for now i consider going up as efficient as going down

    double heuristic(int x, int y, int z);
}
