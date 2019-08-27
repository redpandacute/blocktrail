package bbs.december.blocktrail.movement;

public enum Directions {

    NORTH(0,-1), NORTHEAST(1, -1), EAST(1, 0), SOUTHEAST(1, 1), SOUTH(0, 1), SOUTHWEST(-1,1), WEST(-1,0), NORTHWEST(-1,-1);

    public final int x, z;

    private Directions(int x, int z) {
        this.x = x;
        this.z = z;
    }
}
