package scripts;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Constants;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;

/**
 * @author Starfox
 * @version 12/15/13
 * Test script 1, no framework.
 */
@ScriptManifest(name = "SF Flax", authors = "Starfox", category = "Money Making", 
        description = "Picks flax at seers village.")
public class SFFlax extends Script implements MessageListening07, Painting {
    
    private int flaxCount = 0;
    private long startTime = 0;

    @Override
    public void run() {
        Mouse.setSpeed(145);
        println("Thank you for choosing SF Flax.");
        startTime = System.currentTimeMillis();
        WebWalking.setUseRun(true);
        loop(25);
    }
    
    public int loop(final int speed) {
        if(Inventory.isFull() && PickMethods.isAtBank() && !Banking.isBankScreenOpen()) {
            if(Objects.find(10, Constants.IDs.Objects.bankBooth).length > 0) {
                final RSObject bankBooth = Objects.findNearest(10, Constants.IDs.Objects.bankBooth)[0];
                if(bankBooth.isOnScreen()) {
                    Banking.openBank();
                } else {
                    Camera.turnToTile(bankBooth);
                }
            } 
        } else if(Inventory.isFull() && Banking.isBankScreenOpen()) {
            PickMethods.depositItems();
        } else if(!Inventory.isFull() && !PickMethods.isAtFlax()) {
            PickMethods.walkToFlax();
        } else if(Inventory.isFull() && !PickMethods.isAtBank()) {
            PickMethods.walkToBank();
        } else {
            PickMethods.pickFlax();
        }
        sleep(speed);
        return loop(speed);
    }

    @Override
    public void serverMessageReceived(String string) {
        if(string.contains("You pick")) {
            flaxCount++;
        }
    }

    @Override
    public void playerMessageReceived(String string, String string1) {
    }

    @Override
    public void personalMessageReceived(String string, String string1) {
    }

    @Override
    public void tradeRequestReceived(String string) {
    }

    @Override
    public void clanMessageReceived(String string, String string1) {
    }

    @Override
    public void onPaint(Graphics gr) {
        Graphics2D g = (Graphics2D)gr;
        g.setColor(Color.BLACK);
        g.drawRect(4, 4, 120, 60);
        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(4, 4, 120, 60);
        g.setColor(new Color(247, 247, 247, 190));
        g.drawString("SF Flax", 42, 20);
        g.drawString("_________", 30, 23);
        g.drawString("Time ran: " + Timing.msToString(System.currentTimeMillis() - startTime), 8, 40);
        g.drawString("Picked (/h): " + flaxCount + " (" + RSUtil.getPerHour(flaxCount, startTime) + ")", 8, 55);
    }
}
