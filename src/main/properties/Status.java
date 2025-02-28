package properties;

import application.GamePanel;

import java.awt.*;

/*** STATUS CLASS ***/
public enum Status {
    /*** STATUS CHART REFERENCE: https://pokemon.fandom.com/wiki/Status_Effects ***/

    PARALYZE("Paralyze", "paralyzed", "PAR"),
    POISON("Poison", "poisoned", "PSN"),
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

        Color color = switch (this.abr) {
            case ("PAR") -> new Color(253, 174, 16);
            case ("PSN") -> new Color(188, 82, 231);
            case ("CNF") -> new Color(226, 196, 116);
            case ("BRN") -> new Color(249, 78, 0);
            case ("FRZ") -> new Color(98, 204, 212);
            case ("SLP") -> new Color(125, 125, 125);
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

        switch (this.abr) {
            case ("PAR"):
                gp.btlManager.typeDialogue(fighter + " is paralyzed\nand unable to move!");
                break;
            case ("PSN"):
                gp.btlManager.typeDialogue(fighter + " is hurt\nfrom the poison!");
                break;
            case ("CNF"):
                gp.btlManager.typeDialogue(fighter + " hurt\nitself in confusion!");
                break;
            case ("BRN"):
                gp.btlManager.typeDialogue(fighter + " is hurt\nfrom the burn!");
                break;
            case ("FRZ"):
                gp.btlManager.typeDialogue(fighter + " is frozen\nsolid!");
                break;
            case ("SLP"):
                gp.btlManager.typeDialogue(fighter + " is fast\nasleep!");
                break;
        }
    }

    public String printCondition() {
        String recover = switch (this.abr) {
            case ("PAR") -> " grew\nparalyzed!";
            case ("PSN") -> " became\npoisoned!";
            case ("CNF") -> " became\nconfused!";
            case ("BRN") -> " suffered\na burn!";
            case ("FRZ") -> " froze\nin ice!";
            case ("SLP") -> " fell\nasleep!";
            default -> "";
        };

        return recover;
    }

    public String printRecover() {
        String recover = switch (this.abr) {
            case ("PAR") -> " healed\nfrom paralysis!";
            case ("PSN") -> " healed\nfrom the poison!";
            case ("CNF") -> " snapped\nout of confusion!";
            case ("BRN") -> " healed\nfrom the burn!";
            case ("FRZ") -> " thawed\nfrom the ice!";
            case ("SLP") -> " woke up!";
            default -> "";
        };

        return recover;
    }
}
/*** END STATUS CLASS ***/