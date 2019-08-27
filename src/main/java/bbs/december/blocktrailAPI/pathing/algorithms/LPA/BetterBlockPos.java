package bbs.december.blocktrailAPI.pathing.algorithms.LPA;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

//the idea to creating as well as many parts of the code in this class are copied from baritone but it makes a lot of sense to do this, since apparently, blockPos is not a very well designed class itself

public class BetterBlockPos extends BlockPos {

    public final int x;
    public final int y;
    public final int z;

    public BetterBlockPos(int x, int y, int z) {
        super(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BetterBlockPos(double x, double y, double z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

    public BetterBlockPos(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public int hashCode() {
        return (int) longHash(x, y, z);
    }

    public static long longHash(BetterBlockPos pos) {
        return longHash(pos.x, pos.y, pos.z);
    }

    public static long longHash(int x, int y, int z) {

        // This comment was not made by me but by @author leijurv, who also programmed most of this class
        // invertibility would be incredibly useful
        /*
         *   This is the hashcode implementation of Vec3i (the superclass of the class which I shall not name)
         *
         *   public int hashCode() {
         *       return (this.getY() + this.getZ() * 31) * 31 + this.getX();
         *   }
         *
         *   That is terrible and has tons of collisions and makes the HashMap terribly inefficient.
         *
         *   That's why we grab out the X, Y, Z and calculate our own hashcode
         */
        long hash = 3241;
        hash = 3457689L * hash + x;
        hash = 8734625L * hash + y;
        hash = 2873465L * hash + z;
        return hash;
    }

    @Override
    public BetterBlockPos up() {
        return new BetterBlockPos(x, y + 1, z);
    }

    @Override
    public BetterBlockPos up(int amt) {
        return new BetterBlockPos(x, y + amt, z);
    }

    @Override
    public BetterBlockPos down() {
        return new BetterBlockPos(x, y - 1, z);
    }

    @Override
    public BetterBlockPos down(int amt) {
        return new BetterBlockPos(x, y - amt, z);
    }
}
