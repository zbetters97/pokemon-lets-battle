package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door extends Entity {

    public static final String objName = "Door";
    private boolean open = false;
    private boolean closing = false;
    private int openTimer = 0;

    public OBJ_Door(GamePanel gp, int worldX, int worldY, int door) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;

        type = type_obstacle;
        name = objName;
        direction = "down";
        hasShadow = false;
        this.power = door;

        if (door == 0) getStoreImage();
        else if (door == 1) getGymImage();
    }

    public OBJ_Door(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;

        type = type_obstacle;
        name = objName;
        direction = "down";
        hasShadow = false;
    }

    public void setImage(int door) {
        if (door == 0) getStoreImage();
        else if (door == 1) getGymImage();
    }

    public void getStoreImage() {
        down1 = setup("/objects/door_store_1");
        down2 = setup("/objects/door_store_2");
        down3 = setup("/objects/door_store_3");
    }

    public void getGymImage() {
        down1 = setup("/objects/door_gym_1");
        down2 = setup("/objects/door_gym_2");
        down3 = setup("/objects/door_gym_3");
    }

    public void update() {

        if (opening) {
            open();
        } else if (open) {
            checkClose();
        } else if (closing) {
            close();
        }
    }

    public void interact() {
        if (!opening) {
            playOpenSE();
            opening = true;
            gp.player.moving = true;
        }
    }

    private void open() {
        spriteCounter++;
        if (spriteCounter < 5) {
            spriteNum = 2;
        } else if (spriteCounter < 10) {
            spriteNum = 3;
        } else {
            spriteNum = -1;
            spriteCounter = 0;
            opening = false;
            open = true;
        }
    }

    private void checkClose() {
        if (gp.player.worldX == worldX && gp.player.worldY == worldY) {
            openTimer = 0;
        } else {
            openTimer++;
            if (openTimer >= 60) {
                closing = true;
                open = false;
                openTimer = 0;
            }
        }
    }

    private void close() {

        spriteCounter++;
        if (spriteCounter < 5) {
            spriteNum = 3;
        } else if (spriteCounter < 10) {
            spriteNum = 2;
        } else {
            spriteNum = 1;
            spriteCounter = 0;
            closing = false;
        }
    }

    public void playOpenSE() {
        gp.playSE(gp.world_SE, "door-shop");
    }

    public void playCloseSE() {
        gp.playSE(gp.world_SE, "exit");
    }
}