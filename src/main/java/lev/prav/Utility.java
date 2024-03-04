package lev.prav;

public final class Utility {
    public static Quarter find(double x, double y) {
        if (x > 0) {
            return y > 0 ? Quarter.NORTH_EAST : Quarter.SOUTH_EAST;
        }
        return y > 0 ? Quarter.NORTH_WEST : Quarter.SOUTH_WEST;
    }
}
