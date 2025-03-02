package data;

import pokemon.Pokemon.Pokedex;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {

    @Serial
    private static final long serialVersionUID = -5842400749008672573L;

    // FILE INFO
    String file_date;
    long playtime;
    boolean gameCompleted;

    // PROGRESS

    // PLAYER DATA
    String pName, pDirection;
    int cMap, cArea, pWorldX, pWorldY;
    int pMoney, pSteps, pDexSeen, pDexOwn;

    // PLAYER BAG
    ArrayList<String> pKeyItemNames = new ArrayList<>();
    ArrayList<String> pItemNames = new ArrayList<>();
    ArrayList<Integer> pItemAmounts = new ArrayList<>();

    ArrayList<String> pPokeballNames = new ArrayList<>();
    ArrayList<Integer> pPokeballAmounts = new ArrayList<>();

    // PLAYER ITEM
    String pKeyItem;

    // PLAYER POKEPARTY
    ArrayList<Pokedex> pPokePartyID = new ArrayList<>();
    ArrayList<Character> pPokePartySex = new ArrayList<>();
    ArrayList<int[]> pPokePartyStats = new ArrayList<>();
    ArrayList<String> pPokePartyNature = new ArrayList<>();
    ArrayList<List<String>> pPokePartyMoveNames = new ArrayList<>();
    ArrayList<List<Integer>> pPokePartyMovePP = new ArrayList<>();
    ArrayList<String> pPokePartyStatus = new ArrayList<>();
    ArrayList<String> pPokePartyItem = new ArrayList<>();
    ArrayList<String> pPokePartyBall = new ArrayList<>();
    ArrayList<Boolean> pPokePartyAlive = new ArrayList<>();

    // PLAYER PC PARTY
    ArrayList<Pokedex> pPCPartyID = new ArrayList<>();
    ArrayList<Character> pPCPartySex = new ArrayList<>();
    ArrayList<int[]> pPCPartyStats = new ArrayList<>();
    ArrayList<String> pPCPartyNature = new ArrayList<>();
    ArrayList<List<String>> pPCPartyMoveNames = new ArrayList<>();
    ArrayList<List<Integer>> pPCPartyMovePP = new ArrayList<>();
    ArrayList<String> pPCPartyStatus = new ArrayList<>();
    ArrayList<String> pPCPartyItem = new ArrayList<>();
    ArrayList<String> pPCPartyBall = new ArrayList<>();
    ArrayList<Boolean> pPCPartyAlive = new ArrayList<>();
    ArrayList<Integer> pPCPartyIndexBox = new ArrayList<>();
    ArrayList<Integer> pPCPartyIndexSlot = new ArrayList<>();

    // NPC DATA
    String[][] npcNames, npcDirections;
    int[][] npcWorldX, npcWorldY, npcDialogueSet, npcSteps;
    boolean[][] npcHasBattle;

    // NPC POKE PARTY
    ArrayList<ArrayList<Pokedex>> nPokePartyID = new ArrayList<>();
    ArrayList<ArrayList<Character>> nPokePartySex = new ArrayList<>();
    ArrayList<ArrayList<int[]>> nPokePartyStats = new ArrayList<>();
    ArrayList<ArrayList<String>> nPokePartyNature = new ArrayList<>();
    ArrayList<ArrayList<List<String>>> nPokePartyMoves = new ArrayList<>();
    ArrayList<ArrayList<String>> nPokePartyItem = new ArrayList<>();
    ArrayList<ArrayList<String>> nPokePartyBall = new ArrayList<>();

    // MAP OBJECTS
    String[][] mapObjectNames, mapObjectDirections;
    int[][] mapObjectWorldX, mapObjectWorldY, mapObjectDoors, mapObjectLedges;

    // MAP iTILES
    String[][] iTileNames, iTileDirections;
    int[][] iTileWorldX, iTileWorldY;

    public String toString() {
        return "[" + pName + "]  " + file_date;
    }
}