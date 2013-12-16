package scripts;


import java.awt.Point;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

/**
 * @author Starfox
 * @version 12/15/13
 * Houses methods for picking flax.
 * 
 * NOTE TO SELF:
 * This class is poor convention, but was created for
 * hack organizing due to my inability to visualize large classes.
 */
public class PickMethods {
    
    private static boolean shouldMove = true;
    private static final RSTile FLAX_TILE = new RSTile(2740, 3447, 0);
    private static final RSTile BANK_TILE = new RSTile(2727, 3493, 0);
    
    public static void pickFlax() {
        RSObject flax = null;
        if(Objects.findNearest(5, "Flax").length > 0) {
            flax = Objects.findNearest(5, "Flax")[0];
        }
        if(flax != null) {
            if(flax.isOnScreen()) {
                int rand = General.random(flax.getModel().getPoints().length / 2 - 5, flax.getModel().getPoints().length / 2 + 5);
                for(final Point p : flax.getModel().getPoints()) {
                    if(Mouse.getPos().equals(p)) {
                        shouldMove = false;
                        break;
                    }
                }
                if(shouldMove) {
                    Mouse.hop(flax.getModel().getPoints()[rand]);
                }
                if(!Game.getUptext().contains("Pick")) {
                    flax.click("Pick");
                } else {
                    Mouse.click(1);
                }
            } else {
                Camera.turnToTile(flax);
            }
        }
        shouldMove = true;
    }
    
    public static void walkToBank() {
        if(Game.getDestination() == null || !RSUtil.isAcceptableDestination(BANK_TILE)) {
            PathFinding.aStarWalk(BANK_TILE);
        }
    }
    
    public static void walkToFlax() {
        if(Game.getDestination() == null || !RSUtil.isAcceptableDestination(FLAX_TILE)) {
            WebWalking.walkTo(FLAX_TILE);
        }
    }
    
    public static void depositItems() {
        if(Banking.depositAll() > 0) {
            final int count = Inventory.getAll().length;
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return Inventory.getAll().length < count;
                }
            }, 1500);
        }
    }
    
    public static boolean isAtBank() {
        return Player.getPosition().distanceTo(BANK_TILE) <= 4;
    }
    
    public static boolean isAtFlax() {
        return Player.getPosition().distanceTo(FLAX_TILE) <= 4;
    }

}
