package application;

import entity.npc.*;
import entity.object.OBJ_Door;
import entity.object.OBJ_Ledge;
import entity.object.object_interactive.OBJ_Boulder;
import entity.object.object_interactive.OBJ_Rock;
import entity.object.object_interactive.OBJ_Tree;

public class AssetSetter {

    private final GamePanel gp;

    protected AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    protected void setNPC() {

        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_Rival(gp, 16, 16);
        i++;
        gp.npc[mapNum][i] = new NPC_Red(gp, 25, 19);
        i++;
        gp.npc[mapNum][i] = new NPC_Steve(gp, 20, 29);
        i++;

        gp.npc[mapNum][i] = new NPC_Sign(gp, 18, 19, 0, "MAY'S HOUSE");
        i++;
        gp.npc[mapNum][i] = new NPC_Sign(gp, 27, 19, 1, "PETALBURG GYM");
        i++;
        gp.npc[mapNum][i] = new NPC_Sign(gp, 27, 25, 0, "WELCOME TO:\nPetalburg Town");
        i++;

        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Nurse(gp, 22, 26);
        i++;
        gp.npc[mapNum][i] = new NPC_Computer(gp, 25, 25);
        i++;

        mapNum = 2;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Clerk(gp, 16, 27);
        i++;
    }

    protected void setObject() {

        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_Ledge(gp, 16, 21, "down", 4);
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp, 30, 25, 0);
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp, 35, 21, 0);
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp, 25, 18, 1);
        i++;
    }

    protected void setInteractiveObjects() {

        int mapNum = 0;
        int i = 0;

        gp.obj_i[mapNum][i] = new OBJ_Tree(gp, 22, 23);
        i++;
        gp.obj_i[mapNum][i] = new OBJ_Rock(gp, 22, 22);
        i++;
        gp.obj_i[mapNum][i] = new OBJ_Boulder(gp, 21, 22);
        i++;
    }

    protected void setInteractiveTiles(boolean reset) {

    }
}