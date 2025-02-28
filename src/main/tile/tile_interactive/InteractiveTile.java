package tile.tile_interactive;

import application.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends Entity {

    public InteractiveTile(GamePanel gp) {
        super(gp);
    }

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
    }

    public void update() {


    }

    public void draw(Graphics2D g2) {

        BufferedImage image;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        boolean offCenter = false;

        if (getScreenX() > worldX) {
            screenX = worldX;
            offCenter = true;
        }
        if (getScreenY() > worldY) {
            screenY = worldY;
            offCenter = true;
        }

        // FROM PLAYER TO RIGHT-EDGE OF SCREEN
        int rightOffset = gp.screenWidth - getScreenX();

        // FROM PLAYER TO RIGHT-EDGE OF WORLD
        if (rightOffset > gp.worldWidth - worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
            offCenter = true;
        }

        // FROM PLAYER TO BOTTOM-EDGE OF SCREEN
        int bottomOffSet = gp.screenHeight - getScreenY();

        // FROM PLAYER TO BOTTOM-EDGE OF WORLD
        if (bottomOffSet > gp.worldHeight - worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
            offCenter = true;
        }

        image = switch (direction) {
            case "up", "upleft", "upright" -> up1;
            case "down", "downleft", "downright" -> down1;
            case "left" -> left1;
            case "right" -> right1;
            default -> null;
        };

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, null);
        } else if (offCenter) {
            g2.drawImage(image, screenX, screenY, null);
        }

        if (gp.keyH.debug) {
            g2.setColor(Color.RED);
            g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
        }
    }
}