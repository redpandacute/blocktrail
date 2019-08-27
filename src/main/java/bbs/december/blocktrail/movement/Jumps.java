package bbs.december.blocktrail.movement;

public enum Jumps {
    SJ_WHITE(1, true, false),
    SJ_BLUE(2, true, false),
    SJ_YELLOW(3, true, false),
    SJ_RED(4, false, false),
    SJ_PURPLE(4, true, false),

    DJ_WHITE(1, true, true),
    DJ_YELLOW(2, true, true),
    DJ_RED(3, false, true),
    DJ_PURPLE(3, true, true);

    public final int radius;
    public final boolean airable, diagonal;

    Jumps(int r, boolean airable, boolean diagonal) {
        this.radius = r;
        this.airable = airable;
        this.diagonal = diagonal;
    }
}
