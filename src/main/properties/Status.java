package properties;

import application.GamePanel;

import java.awt.*;

/*** STATUS CLASS ***/
public enum Status {
    /*** STATUS CHART REFERENCE: https://pokemon.fandom.com/wiki/Status_Effects ***/

    PARALYZE("Paralyze", "paralyzed", "PAR"),
    POISON("Poison", "poisoned", "PSN"),
    BADPOISON("Bad Poison", "poisoned", "PSN"),
    CONFUSE("Confuse", "confused", "CNF"),
    BURN("Burn", "burned", "BRN"),
    FREEZE("Freeze", "frozen", "FRZ"),
    SLEEP("Sleep", "asleep", "SLP");

    private final String name;
    private final String status;
    private final String abr;

    /**
     * CONSTRUCTOR
     **/
    Status(String name, String status, String abr) {
        this.name = name;
        this.status = status;
        this.abr = abr;
    }
    /** END CONSTRUCTOR **/

    /**
     * GETTERS
     **/
    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    public String getAbbreviation() {
        return this.abr;
    }

    public Color getColor() {

        Color color = switch (this) {
            case Status.PARALYZE -> new Color(253, 174, 16);
            case Status.POISON, Status.BADPOISON -> new Color(188, 82, 231);
            case Status.CONFUSE -> new Color(226, 196, 116);
            case Status.BURN -> new Color(249, 78, 0);
            case Status.FREEZE -> new Color(98, 204, 212);
            case Status.SLEEP -> new Color(125, 125, 125);
            default -> Color.BLACK;
        };

        return color;
    }

    public static Status getStatus(String name) {

        for (Status s : Status.values()) {
            if (s.getName().equals(name)) {
                return s;
            }
        }

        return null;
    }

    /**
     * END GETTERS
     **/

    public void printStatus(GamePanel gp, String fighter) throws InterruptedException {

        switch (this) {
            case Status.PARALYZE:
                gp.btlManager.typeDialogue(fighter + " is paralyzed\nand unable to move!");
                break;
            case Status.POISON, Status.BADPOISON:
                gp.btlManager.typeDialogue(fighter + " is hurt\nfrom the poison!");
                break;
            case Status.CONFUSE:
                gp.btlManager.typeDialogue(fighter + " hurt\nitself in confusion!");
                break;
            case Status.BURN:
                gp.btlManager.typeDialogue(fighter + " is hurt\nfrom the burn!");
                break;
            case Status.FREEZE:
                gp.btlManager.typeDialogue(fighter + " is frozen\nsolid!");
                break;
            case Status.SLEEP:
                gp.btlManager.typeDialogue(fighter + " is fast\nasleep!");
                break;
        }
    }

    public String printCondition() {
        String recover = switch (this) {
            case Status.PARALYZE -> " grew\nparalyzed!";
            case Status.POISON -> " became\npoisoned!";
            case Status.BADPOISON -> " became\nbadly poisoned!";
            case Status.CONFUSE -> " became\nconfused!";
            case Status.BURN -> " suffered\na burn!";
            case Status.FREEZE -> " froze\nin ice!";
            case Status.SLEEP -> " fell\nasleep!";
            default -> "";
        };

        return recover;
    }

    public String printRecover() {
        String recover = switch (this) {
            case Status.PARALYZE -> " healed\nfrom paralysis!";
            case Status.POISON, Status.BADPOISON -> " healed\nfrom the poison!";
            case Status.CONFUSE -> " snapped\nout of confusion!";
            case Status.BURN -> " healed\nfrom the burn!";
            case Status.FREEZE -> " thawed\nfrom the ice!";
            case Status.SLEEP -> " woke up!";
            default -> "";
        };

        return recover;
    }
}
/*** END STATUS CLASS ***/