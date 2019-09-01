package bbs.december.blocktrail.movement;

public enum Walks {

    S_WALK(false, false),
    D_WALK(false, true),

    S_DROP(true, false),
    D_DROP(true, true);

    boolean drop, diagonal;

    Walks(boolean drop ,boolean diagonal) {
        this.drop = drop;
        this.diagonal = diagonal;
    }
}
