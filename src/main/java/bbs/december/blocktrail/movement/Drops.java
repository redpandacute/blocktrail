package bbs.december.blocktrail.movement;

public enum Drops {

    DOWN_DIG(0), DROP(-1), STOP_DROP(0);

    public final int yDiff;

    Drops(int yDiff) {
        this.yDiff = yDiff;
    }
}
