package scripts;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSTile;

/**
 * @author Starfox
 * @version 12/15/2013
 * 
 * Houses general methods.
 */
public class RSUtil {
    
    /**
     * Gets all of the tile adjacent to the specified tile.
     * @param tile The tile to get the adjacent tiles of.
     * @return An array of tiles containing the adjacent tiles.
     */
    public static RSTile[] getAdjacentTiles(final RSTile tile) {
        return new RSTile[]{ tile,
            new RSTile(tile.getX() + 1, tile.getY(), tile.getPlane()),
            new RSTile(tile.getX(), tile.getY() + 1, tile.getPlane()),
            new RSTile(tile.getX() + 1, tile.getY() + 1, tile.getPlane()),
            new RSTile(tile.getX() - 1, tile.getY(), tile.getPlane()),
            new RSTile(tile.getX(), tile.getY() - 1, tile.getPlane()),
            new RSTile(tile.getX() - 1, tile.getY() - 1, tile.getPlane()),
            new RSTile(tile.getX() + 1, tile.getY() - 1, tile.getPlane()),
            new RSTile(tile.getX() - 1, tile.getY() + 1, tile.getPlane())
        };
    }
    
    /**
     * Checks to see whether or not the second tile is adjacent to the first.
     * @param tile1 The tile that will be the basis for the second tile.
     * @param tile2 The tile to be checked.
     * @return true if the second tile is adjacent to the first; false otherwise.
     */
    public static boolean isAdjacentTile(final RSTile tile1, final RSTile tile2) {
        if(tile1 == null || tile2 == null) {
            return false;
        }
        for(RSTile t : getAdjacentTiles(tile1)) {
            if(t.equals(tile2)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks to see whether or not the current game destination is an
     * acceptable destination based on the destination specified.
     * @param dest The destination to be checked.
     * @return true if the destination is acceptable; false otherwise.
     */
    public static boolean isAcceptableDestination(final RSTile dest) {
        return Game.getDestination() != null && isAdjacentTile(dest, Game.getDestination());
    }
    
    /**
     * Gets the per hour value for the specified value.
     * @param value The value to evaluate.
     * @param startTime The time in ms when the script started.
     * @return The per hour value.
     */
    public static long getPerHour(final int value, final long startTime) {
        return (long)(value * 3600000D / (System.currentTimeMillis() - startTime));    
    }

}
