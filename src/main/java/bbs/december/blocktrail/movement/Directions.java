package bbs.december.blocktrail.movement;

public enum Directions {

    NORTH(0,-1, false),
    NORTHEAST(1, -1, true),
    EAST(1, 0, false),
    SOUTHEAST(1, 1, true),
    SOUTH(0, 1, false),
    SOUTHWEST(-1,1, true),
    WEST(-1,0, false),
    NORTHWEST(-1,-1, true),

    UP(1),
    DOWN(-1);

    public final int x, z;
    public final boolean diagonal;

    public int yDiff = 0;

    private Directions(int x, int z, boolean diagonal) {
        this.x = x;
        this.z = z;
        this.diagonal = diagonal;
    }

    private Directions(int yDiff) {
        this.yDiff = yDiff;
        this.x = 0;
        this.z = 0;
        this.diagonal = false;
    }
}
