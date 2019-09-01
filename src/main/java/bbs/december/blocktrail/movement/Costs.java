package bbs.december.blocktrail.movement;

public enum Costs {
    S_WALK(1),
    D_WALK(1.4),
    DROP(0.3),
    BLACK(1.5),
    WHITE(1.3),
    BLUE(1.4),
    YELLOW(1.6),
    RED(1.6),
    PURPLE(1.8),
    BLOCKPLACEMENT(0.2),
    BREAKFACTOR(2), //todo really look into cost values
    LAND(0);

    public final double cost;

    Costs(double cost) {
        this.cost = cost;
    }
}
