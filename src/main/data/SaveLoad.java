package data;

import application.GamePanel;
import entity.Entity;
import moves.Move;
import pokemon.Pokemon;
import pokemon.Pokemon.Pokedex;
import properties.Nature;
import properties.Status;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveLoad {

    public String[] saveFiles = {
            File.separator + "save_1.dat",
            File.separator + "save_2.dat",
            File.separator + "save_3.dat",
    };

    private final GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save(int saveSlot) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(gp.saveDir + saveFiles[saveSlot]));

            // SAVE DATA TO DS
            DataStorage ds = new DataStorage();

            // CURRENT DATE/TIME
            ds.file_date = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));

            // PLAY TIME
            ds.playtime = gp.playtime;

            // PROGRESS DATA
            ds.gameCompleted = Progress.gameCompleted;

            // PLAYER DATA
            ds.pName = gp.player.name;
            ds.pDirection = gp.player.direction;
            ds.cMap = gp.currentMap;
            ds.cArea = gp.currentArea;
            ds.pWorldX = gp.player.worldX;
            ds.pWorldY = gp.player.worldY;
            ds.pMoney = gp.player.money;
            ds.pSteps = gp.player.steps;
            ds.pDexSeen = gp.player.dexSeen;
            ds.pDexOwn = gp.player.dexOwn;

            // PLAYER BAG
            for (Entity kItem : gp.player.inventory_keyItems) {
                ds.pKeyItemNames.add(kItem.name);
            }
            for (Entity item : gp.player.inventory_items) {
                ds.pItemNames.add(item.name);
                ds.pItemAmounts.add(item.amount);
            }
            for (Entity ball : gp.player.inventory_pokeballs) {
                ds.pPokeballNames.add(ball.name);
                ds.pPokeballAmounts.add(ball.amount);
            }

            // PLAYER ITEM
            if (gp.player.keyItem != null) {
                ds.pKeyItem = gp.player.keyItem.name;
            }
            else {
                ds.pKeyItem = "NULL";
            }

            // PLAYER POKE PARTY
            ArrayList<String> moveNames = new ArrayList<>();
            ArrayList<Integer> movePP = new ArrayList<>();
            for (Pokemon p : gp.player.pokeParty) {

                ds.pPokePartyID.add(p.getID());
                ds.pPokePartySex.add(p.getSex());
                ds.pPokePartyStats.add(new int[]{
                        p.getLevel(), p.getXP(), p.getEV(),
                        p.getHPIV(), p.getAttackIV(), p.getDefenseIV(),
                        p.getSpAttackIV(), p.getSpDefenseIV(), p.getSpeedIV()
                });
                ds.pPokePartyNature.add(p.getNature().getName());

                for (Move m : p.getMoveSet()) {
                    moveNames.add(m.getName());
                    movePP.add(m.getPP());
                }
                ds.pPokePartyMoveNames.add(new ArrayList<>(moveNames));
                ds.pPokePartyMovePP.add(new ArrayList<>(movePP));

                if (p.getStatus() != null) {
                    ds.pPokePartyStatus.add(p.getStatus().getName());
                }
                else {
                    ds.pPokePartyStatus.add("NULL");
                }

                if (p.getItem() != null) {
                    ds.pPokePartyItem.add(p.getItem().name);
                }
                else {
                    ds.pPokePartyItem.add("NULL");
                }

                if (p.getBall() != null) {
                    ds.pPokePartyBall.add(p.getBall().name);
                }
                else {
                    ds.pPokePartyBall.add("NULL");
                }

                ds.pPokePartyAlive.add(p.isAlive());

                moveNames.clear();
                movePP.clear();
            }

            // PLAYER PC PARTY
            Pokemon p;
            for (int i = 0; i < gp.player.pcParty.length; i++) {

                for (int c = 0; c < gp.player.pcParty[i].length; c++) {

                    p = gp.player.pcParty[i][c];

                    if (p != null) {

                        ds.pPCPartyID.add(p.getID());
                        ds.pPCPartySex.add(p.getSex());
                        ds.pPCPartyStats.add(new int[]{
                                p.getLevel(), p.getXP(), p.getEV(),
                                p.getHPIV(), p.getAttackIV(), p.getDefenseIV(),
                                p.getSpAttackIV(), p.getSpDefenseIV(), p.getSpeedIV()
                        });
                        ds.pPCPartyNature.add(p.getNature().getName());

                        for (Move m : p.getMoveSet()) {
                            moveNames.add(m.getName());
                            movePP.add(m.getPP());
                        }
                        ds.pPCPartyMoveNames.add(new ArrayList<>(moveNames));
                        ds.pPCPartyMovePP.add(new ArrayList<>(movePP));

                        if (p.getStatus() != null) {
                            ds.pPCPartyStatus.add(p.getStatus().getName());
                        }
                        else {
                            ds.pPCPartyStatus.add("NULL");
                        }

                        if (p.getItem() != null) {
                            ds.pPCPartyItem.add(p.getItem().name);
                        }
                        else {
                            ds.pPCPartyItem.add("NULL");
                        }

                        if (p.getBall() != null) {
                            ds.pPCPartyBall.add(p.getBall().name);
                        }
                        else {
                            ds.pPCPartyBall.add("NULL");
                        }

                        ds.pPCPartyAlive.add(p.isAlive());
                        ds.pPCPartyIndexBox.add(i);
                        ds.pPCPartyIndexSlot.add(c);

                        moveNames.clear();
                        movePP.clear();
                    }
                }
            }

            // NPCs
            ds.npcNames = new String[gp.maxMap][gp.npc[1].length];
            ds.npcDirections = new String[gp.maxMap][gp.npc[1].length];
            ds.npcWorldX = new int[gp.maxMap][gp.npc[1].length];
            ds.npcWorldY = new int[gp.maxMap][gp.npc[1].length];
            ds.npcSteps = new int[gp.maxMap][gp.npc[1].length];
            ds.npcDialogueSet = new int[gp.maxMap][gp.npc[1].length];
            ds.npcHasBattle = new boolean[gp.maxMap][gp.npc[1].length];

            // MAP OBJECTS
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectDirections = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectDoors = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLedges = new int[gp.maxMap][gp.obj[1].length];

            // MAP iTILES
            ds.iTileNames = new String[gp.maxMap][gp.iTile[1].length];
            ds.iTileWorldX = new int[gp.maxMap][gp.iTile[1].length];
            ds.iTileWorldY = new int[gp.maxMap][gp.iTile[1].length];
            ds.iTileDirections = new String[gp.maxMap][gp.iTile[1].length];

            // NPC POKE PARTY
            ArrayList<Pokedex> id = new ArrayList<>();
            ArrayList<Character> sex = new ArrayList<>();
            ArrayList<int[]> stats = new ArrayList<>();
            ArrayList<String> nature = new ArrayList<>();
            ArrayList<List<String>> moves = new ArrayList<>();
            ArrayList<String> item = new ArrayList<>();
            ArrayList<String> ball = new ArrayList<>();

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                // NPCs
                for (int i = 0; i < gp.npc[1].length; i++) {

                    if (gp.npc[mapNum][i] == null) {
                        ds.npcNames[mapNum][i] = "NULL";
                    }
                    else {
                        ds.npcNames[mapNum][i] = gp.npc[mapNum][i].name;
                        ds.npcDirections[mapNum][i] = gp.npc[mapNum][i].direction;
                        ds.npcWorldX[mapNum][i] = gp.npc[mapNum][i].worldX;
                        ds.npcWorldY[mapNum][i] = gp.npc[mapNum][i].worldY;
                        ds.npcSteps[mapNum][i] = gp.npc[mapNum][i].steps;

                        // Fix NPC X position if not on grid
                        if (ds.npcWorldX[mapNum][i] % 48.0 != 0) {
                            int difference = (int) (ds.npcWorldX[mapNum][i] % 48.0);
                            String direction = ds.npcDirections[mapNum][i];

                            if (direction.equals("right")) {
                                ds.npcWorldX[mapNum][i] -= difference;
                            }
                            else if (direction.equals("left")) {
                                ds.npcWorldX[mapNum][i] += (48 - difference);
                            }
                        }
                        // Fix NPC Y position if not on grid
                        if (ds.npcWorldY[mapNum][i] % 48.0 != 0) {
                            int difference = (int) (ds.npcWorldY[mapNum][i] % 48.0);
                            String direction = ds.npcDirections[mapNum][i];

                            if (direction.equals("up")) {
                                ds.npcWorldY[mapNum][i] += (48 - difference);
                            }
                            else if (direction.equals("down")) {
                                ds.npcWorldY[mapNum][i] -= difference;
                            }
                        }

                        ds.npcDialogueSet[mapNum][i] = gp.npc[mapNum][i].dialogueSet;
                        ds.npcHasBattle[mapNum][i] = gp.npc[mapNum][i].hasBattle;

                        if (!gp.npc[mapNum][i].pokeParty.isEmpty()) {

                            ArrayList<String> npcMoveNames = new ArrayList<>();
                            for (Pokemon pk : gp.npc[mapNum][i].pokeParty) {

                                id.add(pk.getID());
                                sex.add(pk.getSex());
                                stats.add(new int[]{
                                        pk.getLevel(), pk.getXP(), pk.getEV(),
                                        pk.getHPIV(), pk.getAttackIV(), pk.getDefenseIV(),
                                        pk.getSpAttackIV(), pk.getSpDefenseIV(), pk.getSpeedIV()
                                });
                                nature.add(pk.getNature().getName());

                                for (Move m : pk.getMoveSet()) {
                                    npcMoveNames.add(m.getName());
                                }
                                moves.add(new ArrayList<>(npcMoveNames));

                                if (pk.getItem() != null) {
                                    item.add(pk.getItem().name);
                                }
                                else {
                                    item.add("NULL");
                                }

                                if (pk.getBall() != null) {
                                    ball.add(pk.getBall().name);
                                }
                                else {
                                    ball.add("NULL");
                                }

                                npcMoveNames.clear();
                            }

                            ds.nPokePartyID.add(new ArrayList<>(id));
                            ds.nPokePartySex.add(new ArrayList<>(sex));
                            ds.nPokePartyStats.add(new ArrayList<>(stats));
                            ds.nPokePartyNature.add(new ArrayList<>(nature));
                            ds.nPokePartyMoves.add(new ArrayList<>(moves));
                            ds.nPokePartyItem.add(new ArrayList<>(item));
                            ds.nPokePartyBall.add(new ArrayList<>(ball));

                            id.clear();
                            sex.clear();
                            stats.clear();
                            nature.clear();
                            moves.clear();
                            item.clear();
                            ball.clear();
                        }
                        else {
                            ds.nPokePartyID.add(null);
                            ds.nPokePartySex.add(null);
                            ds.nPokePartyStats.add(null);
                            ds.nPokePartyNature.add(null);
                            ds.nPokePartyMoves.add(null);
                            ds.nPokePartyItem.add(null);
                            ds.nPokePartyBall.add(null);
                        }
                    }
                }

                // MAP OBJECTS
                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NULL";
                    }
                    else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        ds.mapObjectDirections[mapNum][i] = gp.obj[mapNum][i].direction;

                        if (gp.obj[mapNum][i].name.equals("Door")) {
                            ds.mapObjectDoors[mapNum][i] = gp.obj[mapNum][i].power;
                        }
                        else if (gp.obj[mapNum][i].name.equals("Ledge")) {
                            ds.mapObjectLedges[mapNum][i] = gp.obj[mapNum][i].power;
                        }
                        else {
                            ds.mapObjectDoors[mapNum][i] = -1;
                            ds.mapObjectLedges[mapNum][i] = -1;
                        }

                    }
                }

                // MAP iTILES
                for (int i = 0; i < gp.iTile[1].length; i++) {

                    if (gp.iTile[mapNum][i] == null) {
                        ds.iTileNames[mapNum][i] = "NULL";
                    }
                    else {
                        ds.iTileNames[mapNum][i] = gp.iTile[mapNum][i].name;
                        ds.iTileWorldX[mapNum][i] = gp.iTile[mapNum][i].worldX;
                        ds.iTileWorldY[mapNum][i] = gp.iTile[mapNum][i].worldY;
                        ds.iTileDirections[mapNum][i] = gp.iTile[mapNum][i].direction;
                    }
                }
            }

            // WRITE THE DS OBJECT
            oos.writeObject(ds);
            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(int saveSlot) {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(gp.saveDir + saveFiles[saveSlot]));

            // LOAD DATA FROM DS
            DataStorage ds = (DataStorage) ois.readObject();

            // PLAY TIME
            gp.playtime = ds.playtime;

            // FILE DATA
            Progress.gameCompleted = ds.gameCompleted;

            // PLAYER DATA
            gp.player.name = ds.pName;
            gp.player.direction = ds.pDirection;
            gp.currentMap = ds.cMap;
            gp.currentArea = ds.cArea;
            gp.player.worldX = ds.pWorldX;
            gp.player.worldY = ds.pWorldY;
            gp.player.steps = ds.pSteps;
            gp.player.money = ds.pMoney;
            gp.player.dexSeen = ds.pDexSeen;
            gp.player.dexOwn = ds.pDexOwn;
            gp.player.inGrass = false;

            // PLAYER BAG
            gp.player.inventory_keyItems.clear();
            for (int i = 0; i < ds.pKeyItemNames.size(); i++) {
                gp.player.inventory_keyItems.add(gp.eGenerator.getItem(ds.pKeyItemNames.get(i)));
            }
            gp.player.inventory_items.clear();
            for (int i = 0; i < ds.pItemNames.size(); i++) {
                gp.player.inventory_items.add(gp.eGenerator.getItem(ds.pItemNames.get(i)));
                gp.player.inventory_items.get(i).amount = ds.pItemAmounts.get(i);
            }
            gp.player.inventory_pokeballs.clear();
            for (int i = 0; i < ds.pPokeballNames.size(); i++) {
                gp.player.inventory_pokeballs.add(gp.eGenerator.getItem(ds.pPokeballNames.get(i)));
                gp.player.inventory_pokeballs.get(i).amount = ds.pPokeballAmounts.get(i);
            }

            // PLAYER ITEM
            if (!ds.pKeyItem.equals("NULL")) {
                for (int i = 0; i < gp.player.inventory_keyItems.size(); i++) {
                    if (gp.player.inventory_keyItems.get(i).name.equals(ds.pKeyItem)) {
                        gp.player.keyItem = gp.player.inventory_keyItems.get(i);
                        break;
                    }
                }
            }

            // PLAYER POKE PARTY
            gp.player.pokeParty.clear();
            Pokemon p;
            char sex;
            int level, cxp, ev;
            int hpIV, attackIV, defenseIV, spAttackIV, spDefenseIV, speedIV;
            Nature nature;
            Status status;
            List<Move> moves = new ArrayList<>();
            List<Move> moveset = new ArrayList<>();
            Entity item;
            Entity ball;
            boolean isAlive;
            for (int i = 0; i < ds.pPokePartyID.size(); i++) {

                p = Pokemon.get(ds.pPokePartyID.get(i), 1, null);

                sex = ds.pPokePartySex.get(i);
                level = ds.pPokePartyStats.get(i)[0];
                cxp = ds.pPokePartyStats.get(i)[1];
                ev = ds.pPokePartyStats.get(i)[2];
                hpIV = ds.pPokePartyStats.get(i)[3];
                attackIV = ds.pPokePartyStats.get(i)[4];
                defenseIV = ds.pPokePartyStats.get(i)[5];
                spAttackIV = ds.pPokePartyStats.get(i)[6];
                spDefenseIV = ds.pPokePartyStats.get(i)[7];
                speedIV = ds.pPokePartyStats.get(i)[8];

                nature = Nature.getNature(ds.pPokePartyNature.get(i));

                if (!ds.pPokePartyStatus.get(i).equals("NULL")) {
                    status = Status.getStatus(ds.pPokePartyStatus.get(i));
                }
                else {
                    status = null;
                }

                for (int c = 0; c < ds.pPokePartyMoveNames.get(i).size(); c++) {
                    moves.add(Move.getMove(ds.pPokePartyMoveNames.get(i).get(c), ds.pPokePartyMovePP.get(i).get(c)));
                }
                moveset.addAll(moves);

                isAlive = ds.pPokePartyAlive.get(i);

                if (!ds.pPokePartyBall.get(i).equals("NULL")) {
                    ball = gp.eGenerator.getItem(ds.pPokePartyBall.get(i));
                }
                else {
                    ball = null;
                }
                if (!ds.pPokePartyItem.get(i).equals("NULL")) {
                    item = gp.eGenerator.getItem(ds.pPokePartyItem.get(i));
                }
                else {
                    item = null;
                }

                if (p != null) {
                    p.create(sex, level, cxp, ev,
                            hpIV, attackIV, defenseIV, spAttackIV, spDefenseIV, speedIV,
                            nature, status, moveset, item, ball, isAlive);
                }

                gp.player.pokeParty.add(p);
                moves.clear();
                moveset.clear();
            }

            // PLAYER PC PARTY
            Arrays.stream(gp.player.pcParty).forEach(o -> Arrays.fill(o, null));
            for (int i = 0; i < ds.pPCPartyID.size(); i++) {

                p = Pokemon.get(ds.pPCPartyID.get(i), 1, null);

                sex = ds.pPCPartySex.get(i);
                level = ds.pPCPartyStats.get(i)[0];
                cxp = ds.pPCPartyStats.get(i)[1];
                ev = ds.pPCPartyStats.get(i)[2];
                hpIV = ds.pPCPartyStats.get(i)[3];
                attackIV = ds.pPCPartyStats.get(i)[4];
                defenseIV = ds.pPCPartyStats.get(i)[5];
                spAttackIV = ds.pPCPartyStats.get(i)[6];
                spDefenseIV = ds.pPCPartyStats.get(i)[7];
                speedIV = ds.pPCPartyStats.get(i)[8];
                nature = Nature.getNature(ds.pPCPartyNature.get(i));

                if (!ds.pPCPartyStatus.get(i).equals("NULL")) {
                    status = Status.getStatus(ds.pPCPartyStatus.get(i));
                }
                else {
                    status = null;
                }

                for (int c = 0; c < ds.pPCPartyMoveNames.get(i).size(); c++) {
                    moves.add(Move.getMove(ds.pPCPartyMoveNames.get(i).get(c), ds.pPCPartyMovePP.get(i).get(c)));
                }
                moveset = new ArrayList<>(moves);

                isAlive = ds.pPCPartyAlive.get(i);

                if (!ds.pPCPartyBall.get(i).equals("NULL")) {
                    ball = gp.eGenerator.getItem(ds.pPCPartyBall.get(i));
                }
                else {
                    ball = null;
                }
                if (!ds.pPCPartyItem.get(i).equals("NULL")) {
                    item = gp.eGenerator.getItem(ds.pPCPartyItem.get(i));
                }
                else {
                    item = null;
                }

                if (p != null) {
                    p.create(sex, level, cxp, ev,
                            hpIV, attackIV, defenseIV, spAttackIV, spDefenseIV, speedIV,
                            nature, status, moveset, item, ball, isAlive);

                    gp.player.pcParty[ds.pPCPartyIndexBox.get(i)][ds.pPCPartyIndexSlot.get(i)] = p;
                }

                moves.clear();
                moveset.clear();
            }

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                // NPCs
                for (int i = 0; i < gp.npc[1].length; i++) {

                    if (ds.npcNames[mapNum][i].equals("NULL")) {
                        gp.npc[mapNum][i] = null;
                    }
                    else if (gp.npc[mapNum][i] != null) {
                        gp.npc[mapNum][i].direction = ds.npcDirections[mapNum][i];
                        gp.npc[mapNum][i].worldX = ds.npcWorldX[mapNum][i];
                        gp.npc[mapNum][i].worldY = ds.npcWorldY[mapNum][i];
                        gp.npc[mapNum][i].dialogueSet = ds.npcDialogueSet[mapNum][i];
                        gp.npc[mapNum][i].steps = ds.npcSteps[mapNum][i];
                        gp.npc[mapNum][i].hasBattle = ds.npcHasBattle[mapNum][i];

                        // NPC POKE PARTY
                        if (!gp.npc[mapNum][i].pokeParty.isEmpty()) {

                            gp.npc[mapNum][i].pokeParty.clear();

                            for (int c = 0; c < ds.nPokePartyID.get(i).size(); c++) {

                                p = Pokemon.get(ds.nPokePartyID.get(i).get(c), 1, null);

                                sex = ds.nPokePartySex.get(i).get(c);
                                level = ds.nPokePartyStats.get(i).get(c)[0];
                                cxp = ds.nPokePartyStats.get(i).get(c)[1];
                                ev = ds.nPokePartyStats.get(i).get(c)[2];
                                hpIV = ds.nPokePartyStats.get(i).get(c)[3];
                                attackIV = ds.nPokePartyStats.get(i).get(c)[4];
                                defenseIV = ds.nPokePartyStats.get(i).get(c)[5];
                                spAttackIV = ds.nPokePartyStats.get(i).get(c)[6];
                                spDefenseIV = ds.nPokePartyStats.get(i).get(c)[7];
                                speedIV = ds.nPokePartyStats.get(i).get(c)[8];

                                nature = Nature.getNature(ds.nPokePartyNature.get(i).get(c));

                                ds.nPokePartyMoves.get(i).get(c).forEach(m -> moves.add(Move.getMove(m)));
                                moveset = new ArrayList<>(moves);

                                if (!ds.nPokePartyBall.get(i).get(c).equals("NULL")) {
                                    ball = gp.eGenerator.getItem(ds.nPokePartyBall.get(i).get(c));
                                }
                                else {
                                    ball = null;
                                }
                                if (!ds.nPokePartyItem.get(i).get(c).equals("NULL")) {
                                    item = gp.eGenerator.getItem(ds.nPokePartyItem.get(i).get(c));
                                }
                                else {
                                    item = null;
                                }

                                if (p != null) {
                                    p.create(sex, level, cxp, ev,
                                            hpIV, attackIV, defenseIV, spAttackIV, spDefenseIV, speedIV,
                                            nature, null, moveset, item, ball, true);
                                }

                                gp.npc[mapNum][i].pokeParty.add(p);
                                moves.clear();
                                moveset.clear();
                            }
                        }
                    }
                }

                // MAP OBJECTS
                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (ds.mapObjectNames[mapNum][i].equals("NULL")) {
                        gp.obj[mapNum][i] = null;
                    }
                    else if (gp.obj[mapNum][i] != null) {

                        if (gp.obj[mapNum][i].name.equals("Door")) {
                            gp.obj[mapNum][i] = gp.eGenerator.getDoor(
                                    ds.mapObjectWorldX[mapNum][i] / gp.tileSize,
                                    ds.mapObjectWorldY[mapNum][i] / gp.tileSize,
                                    ds.mapObjectDoors[mapNum][i]
                            );
                        }
                        else if (gp.obj[mapNum][i].name.equals("Ledge")) {
                            gp.obj[mapNum][i] = gp.eGenerator.getLedge(
                                    ds.mapObjectWorldX[mapNum][i] / gp.tileSize,
                                    ds.mapObjectWorldY[mapNum][i] / gp.tileSize,
                                    ds.mapObjectDirections[mapNum][i],
                                    ds.mapObjectLedges[mapNum][i]
                            );
                        }
                        else {
                            gp.obj[mapNum][i] = gp.eGenerator.getObject(
                                    ds.mapObjectNames[mapNum][i],
                                    ds.mapObjectWorldX[mapNum][i] / gp.tileSize,
                                    ds.mapObjectWorldY[mapNum][i] / gp.tileSize
                            );
                        }

                        gp.obj[mapNum][i].direction = ds.mapObjectDirections[mapNum][i];
                    }
                }

                // MAP iTILES
                for (int i = 0; i < gp.iTile[1].length; i++) {

                    if (ds.iTileNames[mapNum][i].equals("NULL")) {
                        gp.iTile[mapNum][i] = null;
                    }
                    else if (gp.iTile[mapNum][i] != null) {
                        gp.iTile[mapNum][i] = gp.iGenerator.getTile(ds.iTileNames[mapNum][i]);
                        gp.iTile[mapNum][i].worldX = ds.iTileWorldX[mapNum][i];
                        gp.iTile[mapNum][i].worldY = ds.iTileWorldY[mapNum][i];
                        gp.iTile[mapNum][i].direction = ds.iTileDirections[mapNum][i];
                    }
                }
            }

            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFighterData(int saveSlot) {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(gp.saveDir + saveFiles[saveSlot]));

            // LOAD DATA FROM DS
            DataStorage ds = (DataStorage) ois.readObject();

            // PLAYER DATA
            gp.player_2.name = ds.pName;

            // PLAYER BAG
            gp.player_2.inventory_keyItems.clear();
            for (int i = 0; i < ds.pKeyItemNames.size(); i++) {
                gp.player_2.inventory_keyItems.add(gp.eGenerator.getItem(ds.pKeyItemNames.get(i)));
            }
            gp.player_2.inventory_items.clear();
            for (int i = 0; i < ds.pItemNames.size(); i++) {
                gp.player_2.inventory_items.add(gp.eGenerator.getItem(ds.pItemNames.get(i)));
                gp.player_2.inventory_items.get(i).amount = ds.pItemAmounts.get(i);
            }
            gp.player_2.inventory_pokeballs.clear();
            for (int i = 0; i < ds.pPokeballNames.size(); i++) {
                gp.player_2.inventory_pokeballs.add(gp.eGenerator.getItem(ds.pPokeballNames.get(i)));
                gp.player_2.inventory_pokeballs.get(i).amount = ds.pPokeballAmounts.get(i);
            }

            // player_2 POKE PARTY
            gp.player_2.pokeParty.clear();
            Pokemon p;
            char sex;
            int level, cxp, ev;
            int hpIV, attackIV, defenseIV, spAttackIV, spDefenseIV, speedIV;
            Nature nature;
            Status status;
            List<Move> moves = new ArrayList<>();
            List<Move> moveset = new ArrayList<>();
            Entity item;
            Entity ball;
            boolean isAlive;
            for (int i = 0; i < ds.pPokePartyID.size(); i++) {

                p = Pokemon.get(ds.pPokePartyID.get(i), 1, null);

                sex = ds.pPokePartySex.get(i);
                level = ds.pPokePartyStats.get(i)[0];
                cxp = ds.pPokePartyStats.get(i)[1];
                ev = ds.pPokePartyStats.get(i)[2];
                hpIV = ds.pPokePartyStats.get(i)[3];
                attackIV = ds.pPokePartyStats.get(i)[4];
                defenseIV = ds.pPokePartyStats.get(i)[5];
                spAttackIV = ds.pPokePartyStats.get(i)[6];
                spDefenseIV = ds.pPokePartyStats.get(i)[7];
                speedIV = ds.pPokePartyStats.get(i)[8];

                nature = Nature.getNature(ds.pPokePartyNature.get(i));

                if (!ds.pPokePartyStatus.get(i).equals("NULL")) {
                    status = Status.getStatus(ds.pPokePartyStatus.get(i));
                }
                else {
                    status = null;
                }

                for (int c = 0; c < ds.pPokePartyMoveNames.get(i).size(); c++) {
                    moves.add(Move.getMove(ds.pPokePartyMoveNames.get(i).get(c), ds.pPokePartyMovePP.get(i).get(c)));
                }
                moveset.addAll(moves);

                isAlive = ds.pPokePartyAlive.get(i);

                if (!ds.pPokePartyBall.get(i).equals("NULL")) {
                    ball = gp.eGenerator.getItem(ds.pPokePartyBall.get(i));
                }
                else {
                    ball = null;
                }
                if (!ds.pPokePartyItem.get(i).equals("NULL")) {
                    item = gp.eGenerator.getItem(ds.pPokePartyItem.get(i));
                }
                else {
                    item = null;
                }

                if (p != null) {
                    p.create(sex, level, cxp, ev,
                            hpIV, attackIV, defenseIV, spAttackIV, spDefenseIV, speedIV,
                            nature, status, moveset, item, ball, isAlive);
                }

                gp.player_2.pokeParty.add(p);
                moves.clear();
                moveset.clear();
            }

            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPlayerData(int saveSlot) {

        try {
            File saveFile = new File(gp.saveDir + saveFiles[saveSlot]);

            if (saveFile.exists()) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile));
                DataStorage ds = (DataStorage) ois.readObject();
                ois.close();

                return ds.pName;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String loadFileData(int saveSlot) {

        try {
            File saveFile = new File(gp.saveDir + saveFiles[saveSlot]);

            if (saveFile.exists()) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile));
                DataStorage ds = (DataStorage) ois.readObject();
                ois.close();

                return ds.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}