package application;

import application.GamePanel.Weather;
import entity.Entity;
import entity.collectables.items.ITM_EXP_Share;
import moves.Move;
import moves.Move.MoveType;
import moves.Moves;
import pokemon.Pokemon;
import pokemon.Pokemon.Protection;
import properties.Ability;
import properties.Status;
import properties.Type;

import java.util.*;
import java.util.List;

public class BattleManager extends Thread {

    private static final List<Moves> healMoves = Arrays.asList(
            Moves.ROOST,
            Moves.REST,
            Moves.RECOVER,
            Moves.SYNTHESIS,
            Moves.WISH
    );
    private static final List<Moves> skyMoves = Arrays.asList(
            Moves.BOUNCE,
            Moves.FLY,
            Moves.HIGHJUMPKICK,
            Moves.JUMPKICK
    );
    private static final List<Moves> soundMoves = Arrays.asList(
            Moves.GROWL,
            Moves.HOWL,
            Moves.HYPERBEAM,
            Moves.HYPERVOICE,
            Moves.PERISHSONG,
            Moves.SCREECH,
            Moves.SNORE,
            Moves.SUPERSONIC
    );
    private static final Map<Integer, Integer> magnitudeTable = Map.ofEntries(
            Map.entry(4, 5),
            Map.entry(5, 10),
            Map.entry(6, 20),
            Map.entry(7, 30),
            Map.entry(8, 20),
            Map.entry(9, 10),
            Map.entry(10, 5)
    );

    // GENERAL VALUES
    private final GamePanel gp;
    public boolean shift = true;
    public boolean active = false;
    public boolean running = false;
    private int textSpeed = 30;

    // BATTLE INFORMATION
    public boolean cpu = true;
    public boolean pcBattle = false;
    public Entity trainer;
    public Pokemon[] fighter = new Pokemon[2];
    private final Pokemon[] newFighter = new Pokemon[2];
    private final ArrayList<Pokemon> otherFighters = new ArrayList<>();
    private final ArrayList<Moves> playerMoves = new ArrayList<>();
    private final ArrayList<Moves> cpuMoves = new ArrayList<>();
    public Move playerMove, cpuMove;
    private int playerFurryCutterCount = 10, cpuFurryCutterCount = 10;
    public Move newMove = null, oldMove = null;
    private Weather weather = Weather.CLEAR;
    private int weatherDays = -1;
    private int playerPoison = 1, cpuPoison = 1;
    private int playerDamageTaken = 0, cpuDamageTaken = 0;
    private int gravityCounter = 0;
    private int winner = -1, loser = -1;
    private int escapeAttempts = 0;
    public Entity ballUsed;
    public boolean battleEnd = false;

    // BATTLE QUEUE
    public Deque<Integer> battleQueue = new ArrayDeque<>();
    private final int queue_PlayerSwap = 1;
    private final int queue_CPUSwap = 2;
    private final int queue_Rotation = 3;
    private final int queue_PlayerMove = 4;
    private final int queue_CPUMove = 5;
    private final int queue_ActiveMoves = 6;
    private final int queue_StatusDamage = 7;
    private final int queue_WeatherDamage = 8;
    private final int queue_TurnReset = 9;

    // BATTLE STATES
    public int battleMode;
    public final int wildBattle = 1;
    public final int trainerBattle = 2;
    public final int rivalBattle = 3;
    public final int gymBattle = 4;
    public final int eliteBattle = 5;
    public final int championBattle = 6;
    public final int legendaryBattle = 7;

    // FIGHT STAGES
    public int fightStage;
    public final int fight_Encounter = 1;
    public final int fight_Start = 2;
    public final int fight_Capture = 3;
    public final int fight_Run = 4;
    public final int fight_Evolve = 5;

    /**
     * CONSTRUCTOR
     **/
    public BattleManager(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * SETUP METHOD
     **/
    public void setup(int currentBattle, int music, Entity trainer, Pokemon pokemon,
                      String condition, boolean cpu, boolean pcBattle) {

        gp.stopMusic();

        textSpeed = gp.ui.textSpeed == 2 ? 30 : gp.ui.textSpeed == 3 ? 40 : 50;

        battleMode = currentBattle;
        this.trainer = trainer;

        fighter[1] = pokemon;
        weather = condition != null ? Weather.valueOf(condition) : Weather.CLEAR;
        weatherDays = -1;
        this.cpu = cpu;
        this.pcBattle = pcBattle;

        if (gp.player.action == Entity.Action.SURFING) {
            gp.ui.battle_arena = gp.ui.battle_arena_water;
            gp.ui.battle_bkg = gp.ui.battle_bkg_water;
        }
        else {
            gp.ui.battle_arena = gp.ui.battle_arena_grass;
            gp.ui.battle_bkg = gp.ui.battle_bkg_grass;
        }

        gp.startMusic(pcBattle ? 9 : 1, music);

        active = true;
        running = true;
        battleEnd = false;
        fightStage = fight_Encounter;
    }

    /**
     * RUN METHOD
     **/
    public void run() {

        while (running) {
            switch (fightStage) {
                case fight_Encounter:
                    try {
                        setBattle();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case fight_Start:
                    try {
                        runBattle();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case fight_Capture:
                    try {
                        throwPokeball();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case fight_Run:
                    try {
                        escapeBattle();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case fight_Evolve:
                    try {
                        checkEvolve();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * SETUP BATTLE METHODS
     **/
    private void setBattle() throws InterruptedException {

        if (pcBattle) {
            gp.player.healPokemonParty();
            gp.player_2.healPokemonParty();
        }

        switch (battleMode) {
            case wildBattle:

                pause(1400);
                gp.playSE(gp.cry_SE, fighter[1].toString());
                typeDialogue("A wild " + fighter[1].getName() + "\nappeared!", true);

                gp.ui.battleState = gp.ui.battle_Dialogue;

                break;
            case trainerBattle:

                pause(1400);
                typeDialogue("Trainer " + trainer.name + "\nwould like to battle!", true);

                gp.ui.battleState = gp.ui.battle_Dialogue;

                fighter[1] = trainer.pokeParty.getFirst();
                gp.playSE(gp.cry_SE, fighter[1].toString());
                typeDialogue("Trainer " + trainer.name + "\nsent out " + fighter[1].getName() + "!");
                pause(100);

                break;
        }

        // Get player's first available Pokémon
        for (Pokemon p : gp.player.pokeParty) {
            if (p.isAlive()) {
                fighter[0] = p;
                break;
            }
        }

        getOtherFighters();

        gp.playSE(gp.cry_SE, fighter[0].toString());
        typeDialogue("Go, " + fighter[0].getName() + "!");
        pause(100);

        getFighterAbility();

        gp.player.trackSeenPokemon(fighter[1]);

        running = false;
        getCPUMove();
        gp.ui.battleState = gp.ui.battle_Options;
    }

    private void getFighterAbility() throws InterruptedException {

        if (fighter[0].getAbility() == Ability.INTIMIDATE) {
            setAttribute(fighter[1], List.of("attack"), -1);
        }
        if (fighter[1].getAbility() == Ability.INTIMIDATE) {
            setAttribute(fighter[0], List.of("attack"), -1);
        }

        Weather oldWeather = weather;

        // If both fighters have a weather ability
        if (fighter[0].getAbility().getWeather() != null && fighter[1].getAbility().getWeather() != null) {

            // If fighter 1 is faster, fighter 1's weather is chosen
            if (fighter[0].getSpeed() > fighter[1].getSpeed()) {
                weather = fighter[1].getAbility().getWeather();
            }
            // If fighter 2 is faster, fighter 2's weather is chosen
            else if (fighter[0].getSpeed() < fighter[1].getSpeed()) {
                weather = fighter[0].getAbility().getWeather();
            }
            // If both fighters have equal speed, coin flip decides
            else {
                Random r = new Random();
                if (r.nextFloat() <= ((float) 1 / 2)) {
                    weather = fighter[1].getAbility().getWeather();
                }
                else {
                    weather = fighter[0].getAbility().getWeather();
                }
            }
        }
        // Only one fighter has a weather ability
        else if (fighter[0].getAbility().getWeather() != null) {
            weather = fighter[0].getAbility().getWeather();
        }
        else if (fighter[1].getAbility().getWeather() != null) {
            weather = fighter[1].getAbility().getWeather();
        }

        // Weather changed, check for Abilities
        if (oldWeather != weather) {
            weatherDays = -1;
            checkWeatherCondition(oldWeather);
        }
    }

    private void getOtherFighters() {

        // Loop through player's party to find exp share fighters
        for (Pokemon partyMember : gp.player.pokeParty) {

            // If party member is not the current fighter and is not yet in the list
            // and has an EXP Share
            if (partyMember != fighter[0] &&
                    !otherFighters.contains(partyMember) &&
                    partyMember.getItem() != null &&
                    partyMember.getItem().name.equals(ITM_EXP_Share.colName)) {

                // Add party member to list of exp share fighters
                otherFighters.add(partyMember);
            }
        }
    }
    /** END SETUP BATTLE METHODS **/

    /**
     * SWAP FIGHTERS
     **/
    private void swapFighter_Player() throws InterruptedException {

        boolean defeated = false;

        // If player's Pokémon fainted
        if (fighter[0] == null || !fighter[0].isAlive()) {
            fighter[0] = null;
            defeated = true;
        }

        gp.ui.battleState = gp.ui.battle_Dialogue;

        // Remove incoming fighter from EXP share list
        otherFighters.remove(newFighter[0]);


        // Forced swap out for player 1
        if (defeated) {
            fighter[0] = newFighter[0];
            gp.playSE(gp.cry_SE, fighter[0].toString());
            typeDialogue("Go, " + fighter[0].getName() + "!");
            pause(100);

            getFighterAbility();

            getCPUMove();
            running = false;
            gp.ui.battleState = gp.ui.battle_Options;
        }
        // Mid-battle swap out for player 1
        else {
            if (fighter[0].getAbility() == Ability.NATURALCURE) {
                fighter[0].removeStatus();
            }

            Pokemon oldFighter = fighter[0];
            fighter[0] = newFighter[0];

            gp.playSE(gp.cry_SE, fighter[0].toString());
            typeDialogue("Go, " + fighter[0].getName() + "!");
            pause(100);

            // Multi-player battle
            if (!cpu && pcBattle) {
                int delay = getDelay();

                // Player 2 waiting for move
                if (delay == 2) {
                    setQueue();
                    getFighterAbility();
                }
                // Player 2 not waiting
                else {
                    getFighterAbility();

                    gp.ui.player = 1;
                    running = false;
                    gp.ui.battleState = gp.ui.battle_Options;
                }
            }
            // Mid-battle swap out in single player
            else if (newFighter[1] == null) {

                // Add old fighter to EXP share list
                if (!otherFighters.contains(oldFighter)) {
                    otherFighters.add(oldFighter);
                }

                setQueue();
                getFighterAbility();
            }
            // Shift swap out
            else {
                getFighterAbility();
                getCPUMove();
                running = false;
                gp.ui.battleState = gp.ui.battle_Options;
            }
        }

        getOtherFighters();
        playerMoves.clear();

        newFighter[0] = null;
        newFighter[1] = null;
    }

    private void swapFighter_CPU() throws InterruptedException {

        boolean defeated = false;

        // If CPU pokemon fainted
        if (!fighter[1].isAlive()) {
            fighter[1] = null;
            defeated = true;
        }

        if (fighter[0] == null || !fighter[0].isAlive()) {
            fighter[0] = null;
        }

        gp.ui.battleState = gp.ui.battle_Dialogue;

        // CPU swap out or player 2 forced swap out
        if (cpu || defeated) {

            fighter[1] = newFighter[1];
            gp.playSE(gp.cry_SE, fighter[1].toString());
            typeDialogue("Trainer " + trainer.name + "\nsent out " + fighter[1].getName() + "!");
            pause(100);

            // Player 1 not sending new fighter (single-player)
            if (cpu && !battleQueue.contains(queue_PlayerSwap)) {
                newFighter[1] = null;

                getFighterAbility();
                getCPUMove();

                running = false;
                gp.ui.battleState = gp.ui.battle_Options;
            }
            // Player 1 sending new fighter (multi-player)
            else if (pcBattle && !battleQueue.contains(queue_PlayerSwap)) {
                int delay = getDelay();

                // Player 1 waiting for move
                if (delay == 1) {
                    setQueue();
                    getFighterAbility();
                }
                // Player 1 not waiting
                else {
                    getFighterAbility();

                    gp.ui.player = 0;
                    running = false;
                    gp.ui.battleState = gp.ui.battle_Options;
                }
            }
        }
        // Mid-battle swap out for player 2
        else if (pcBattle) {
            if (fighter[1].getAbility() == Ability.NATURALCURE) {
                fighter[1].removeStatus();
            }

            fighter[1] = newFighter[1];
            gp.playSE(gp.cry_SE, fighter[1].toString());
            typeDialogue("Trainer " + trainer.name + "\nsent out " + fighter[1].getName() + "!");
            pause(100);

            setQueue();
            getFighterAbility();

            newFighter[1] = null;
        }

        gp.player.trackSeenPokemon(fighter[1]);
        cpuMoves.clear();
    }

    public boolean swapPokemon(int partySlot) {

        int playerNum = gp.ui.player;

        // Player's Pokemon is alive and can't escape
        if (fighter[playerNum] != null && fighter[playerNum].isAlive() && !fighter[playerNum].getCanEscape()) {
            return false;
        }

        Entity player = playerNum == 0 ? gp.player : trainer;
        Pokemon chosenFighter = player.pokeParty.get(partySlot);

        if (playerNum == 0) {
            playerFurryCutterCount = 10;
        }
        else {
            cpuFurryCutterCount = 10;
        }

        // Player 1 swap or CPU / Player 2 swap
        int queue = playerNum == 0 ? queue_PlayerSwap : queue_CPUSwap;
        battleQueue.add(queue);

        // If same pokemon as current, don't swap
        if (fighter[playerNum] == chosenFighter) {
            return false;
        }

        // If pokemon is alive
        if (chosenFighter.isAlive()) {

            fighter[playerNum].resetValues();
            newFighter[playerNum] = chosenFighter;

            // Swap pokemon if there is more than 1 in party
            if (player.pokeParty.size() > 1) {
                Collections.swap(player.pokeParty, 0, partySlot);
            }

            return true;
        }
        // If pokemon is fainted, don't swap
        else {
            return false;
        }
    }

    /**
     * RUN BATTLE METHOD
     **/
    public void setQueue() {
        battleQueue.addAll(Arrays.asList(
                queue_Rotation,
                queue_ActiveMoves,
                queue_StatusDamage,
                queue_WeatherDamage,
                queue_TurnReset
        ));
    }

    private void runBattle() throws InterruptedException {

        while (!battleQueue.isEmpty()) {

            int action = battleQueue.poll();
            switch (action) {
                case queue_PlayerSwap:
                    swapFighter_Player();
                    break;
                case queue_CPUSwap:
                    swapFighter_CPU();
                    break;
                case queue_Rotation:
                    setRotation();
                    break;
                case queue_PlayerMove:
                    playerMove();
                    break;
                case queue_CPUMove:
                    cpuMove();
                    break;
                case queue_ActiveMoves:
                    checkActiveMoves(fighter[0], fighter[1]);
                    checkActiveMoves(fighter[1], fighter[0]);
                    break;
                case queue_StatusDamage:
                    checkStatusDamage();
                    break;
                case queue_WeatherDamage:
                    checkWeatherDamage();
                    break;
                case queue_TurnReset:
                    getDelayedTurn();
                    break;
            }

            if (hasWinningPokemon()) {
                battleQueue.clear();
                getWinningPokemon();
                getWinningTrainer();
                break;
            }
        }
    }

    /**
     * GET MOVE METHODS
     **/
    public Move getPlayerMove(int selection, int player) {

        Move selectedMove = fighter[player].getMoveSet().get(selection);
        int opponent = player == 0 ? 1 : 0;

        boolean struggle = true;

        // Confirm player has a move with PP
        for (Move m : fighter[player].getMoveSet()) {
            if (m.getPP() > 0) {
                struggle = false;
                break;
            }
        }

        if (struggle) {
            selectedMove = new Move(Moves.STRUGGLE);
        }
        else {
            // Find if player has a move that opponent doesn't during Imprison
            if (fighter[opponent].hasActiveMove(Moves.IMPRISON)) {

                struggle = true;
                for (Move m : fighter[player].getMoveSet()) {
                    if (!fighter[opponent].hasMove(m.getMove())) {
                        struggle = false;
                        break;
                    }
                }
                if (struggle) {
                    selectedMove = new Move(Moves.STRUGGLE);
                }
            }
        }

        if (player == 0) {
            playerMove = selectedMove;
        }
        else {
            cpuMove = selectedMove;
        }

        return selectedMove;
    }

    private void getCPUMove() {

        // CPU Battle
        if (cpu) {

            // GET CPU MOVE IF NO CPU DELAY
            int delay = getDelay();

            if (delay == 0 || delay == 1) {
                cpuMove = chooseCPUMove();
            }
        }
    }

    private int getDelay() {
        // 0: neither is waiting
        // 1: player 1 is waiting
        // 2: cpu/player 2 is waiting
        // 3: both are waiting

        int delay = 0;

        // BOTH MOVES ARE ACTIVE
        if (playerMove != null && cpuMove != null) {

            // both fighters are waiting
            if (playerMove.isWaiting() && cpuMove.isWaiting()) {
                delay = 3;
            }
            // fighter 2 is waiting;
            else if (cpuMove.isWaiting()) {
                delay = 2;
            }
            // fighter 1 is waiting
            else if (playerMove.isWaiting()) {
                delay = 1;
            }
        }
        // CPU MOVE IS ACTIVE AND WAITING
        else if (cpuMove != null && cpuMove.isWaiting()) {
            delay = 2;
        }
        // PLAYER MOVE IS ACTIVE AND WAITING
        else if (playerMove != null && playerMove.isWaiting()) {
            delay = 1;
        }

        return delay;
    }

    public Move chooseCPUMove() {
        /** TRAINER AI LOGIC REFERENCE: https://www.youtube.com/watch?v=apuO7pvmGUo **/

        Move bestMove;

        if (trainer == null || trainer.skillLevel == trainer.skill_rookie) {
            bestMove = chooseCPUMove_Random();
        }
        else if (trainer.skillLevel == trainer.skill_smart) {
            bestMove = chooseCPUMove_Power();
        }
        else if (trainer.skillLevel == trainer.skill_elite) {
            bestMove = chooseCPUMove_Best();
        }
        else {
            bestMove = chooseCPUMove_Random();
        }

        return bestMove;
    }

    private Move chooseCPUMove_Random() {

        Move bestMove;

        int ranMove = (int) (Math.random() * (fighter[1].getMoveSet().size()));
        bestMove = fighter[1].getMoveSet().get(ranMove);

        return bestMove;
    }

    private Move chooseCPUMove_Power() {

        Move bestMove;

        Map<Move, Integer> damageMoves = new HashMap<>();

        for (Move move : fighter[1].getMoveSet()) {

            if (move.getPower() > 0 && move.getPP() != 0) {

                double power = getPower(move, fighter[1], fighter[0]);
                double type = getEffectiveness(fighter[0], move.getType());

                // CALCULATE POWER OF EACH MOVE
                int result = (int) (power * type);

                damageMoves.put(move, result);
            }
        }

        if (damageMoves.isEmpty()) {

            for (Move move : fighter[1].getMoveSet()) {

                if (move.getMType() == MoveType.STATUS) {
                    bestMove = move;
                    return bestMove;
                }
            }

            int ranMove = (int) (Math.random() * (fighter[1].getMoveSet().size()));
            bestMove = fighter[1].getMoveSet().get(ranMove);
        }
        else {

            bestMove = Collections.max(damageMoves.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();

            int val = 1 + (int) (Math.random() * 4);
            if (val == 1) {
                int ranMove = (int) (Math.random() * (fighter[1].getMoveSet().size()));
                bestMove = fighter[1].getMoveSet().get(ranMove);
            }
        }

        return bestMove;
    }

    private Move chooseCPUMove_Best() {

        Move bestMove;

        Map<Move, Integer> damageMoves = new HashMap<>();
        Map<Move, Integer> koMoves = new HashMap<>();

        for (Move move : fighter[1].getMoveSet()) {

            // Get damage-dealing moves with valid PP
            if (move.getPower() > 0 && move.getPP() != 0) {

                // Calculate damage each move deals to opponent
                int damage = calculateDamage(fighter[1], fighter[0], move, 1);
                damageMoves.put(move, damage);

                // If move is KO, add to KO list and track accuracy
                if (damage >= fighter[0].getHP()) {

                    // KO move with high priority, use it
                    if (move.getPriority() > 0) {
                        bestMove = move;
                        return bestMove;
                    }

                    int accuracy = (int) getAccuracy(fighter[1], move);
                    koMoves.put(move, accuracy);
                }
            }
        }

        // Find knockout move with highest accuracy
        if (!koMoves.isEmpty()) {

            // Multiple knockout moves found
            if (koMoves.size() > 1) {
                bestMove = Collections.max(koMoves.entrySet(),
                        Comparator.comparingInt(Map.Entry::getValue)).getKey();

                // If knockout move has delay
                if (bestMove.getTurns() > 0) {

                    // Find best move with no delay
                    for (Move move : koMoves.keySet()) {
                        if (move.getTurns() == 0) {
                            bestMove = move;
                            break;
                        }
                    }
                }
            }
            // Return only knockout move found
            else {
                bestMove = koMoves.keySet().iterator().next();
            }
        }
        // Find most powerful damage-dealing move
        else if (!damageMoves.isEmpty()) {

            // Multiple damage moves found
            if (damageMoves.size() > 1) {
                bestMove = Collections.max(damageMoves.entrySet(),
                        Comparator.comparingInt(Map.Entry::getValue)).getKey();

                // If damage move has delay
                if (bestMove.getTurns() > 0) {

                    // Find best move with no delay
                    for (Move move : damageMoves.keySet()) {
                        if (move.getTurns() == 0) {
                            bestMove = move;
                            break;
                        }
                    }
                }
            }
            // Return only damage move found
            else {
                bestMove = damageMoves.keySet().iterator().next();
            }
        }
        // No knockout or damage moves found
        else {
            // Find move that applies status
            for (Move move : fighter[1].getMoveSet()) {
                if (move.getMType() == MoveType.STATUS) {
                    bestMove = move;
                    return bestMove;
                }
            }

            // No status moves found, choose random
            int ranMove = (int) (Math.random() * (fighter[1].getMoveSet().size()));
            bestMove = fighter[1].getMoveSet().get(ranMove);
        }

        return bestMove;
    }
    /** END GET MOVE METHODS **/

    /**
     * SET ROTATION
     **/
    private void setRotation() {
        // 0: neither goes first
        // 1: player moves first
        // 2: cpu moves first
        // 3: only cpu moves
        // 4: only player moves

        if (fighter[0].isAlive() && fighter[1].isAlive()) {

            int firstTurn = getFirstTurn();

            if (firstTurn == 1) {
                battleQueue.addFirst(queue_CPUMove);
                battleQueue.addFirst(queue_PlayerMove);
            }
            else if (firstTurn == 2) {
                battleQueue.addFirst(queue_PlayerMove);
                battleQueue.addFirst(queue_CPUMove);
            }
            else if (firstTurn == 3) {
                battleQueue.addFirst(queue_CPUMove);
            }
            else if (firstTurn == 4) {
                battleQueue.addFirst(queue_PlayerMove);
            }
        }
    }

    private int getFirstTurn() {

        int first;

        if (playerMove == null && cpuMove == null) {
            first = 0;
        }
        else if (playerMove == null) {
            first = 3;
        }
        else if (cpuMove == null) {
            first = 4;
        }
        else {
            if (playerMove.getPriority() > cpuMove.getPriority()) {
                first = 1;
            }
            else if (playerMove.getPriority() < cpuMove.getPriority()) {
                first = 2;
            }
            else {
                if (fighter[0].getSpeed() > fighter[1].getSpeed()) {
                    first = 1;
                }
                else if (fighter[0].getSpeed() < fighter[1].getSpeed()) {
                    first = 2;
                }
                else {
                    Random r = new Random();
                    first = (r.nextFloat() <= ((float) 1 / 2)) ? 1 : 2;
                }
            }
        }

        return first;
    }

    /**
     * PLAYER MOVE
     **/
    private void playerMove() throws InterruptedException {

        if (canMove(fighter[0], playerMove)) {
            move(fighter[0], fighter[1], playerMove);
        }
        else {
            playerMove.resetMoveTurns();
        }
    }

    /**
     * CPU MOVE
     **/
    private void cpuMove() throws InterruptedException {

        if (canMove(fighter[1], cpuMove)) {
            move(fighter[1], fighter[0], cpuMove);
        }
        else {
            cpuMove.resetMoveTurns();
        }
    }

    /**
     * CAN MOVE METHODS
     **/
    private boolean canMove(Pokemon pkm, Move move) throws InterruptedException {

        // If pokemon has status, check if it can move, else return true
        boolean canMove = pkm.getStatus() == null || switch (pkm.getStatus()) {
            case Status.PARALYZE -> paralyzed(pkm);
            case Status.FREEZE -> frozen(pkm);
            case Status.SLEEP -> asleep(pkm, move);
            case Status.CONFUSE -> confused(pkm);
            default -> true;
        };

        return canMove;
    }

    private boolean paralyzed(Pokemon pkm) throws InterruptedException {

        // 1/4 chance can't move due to PAR
        int val = 1 + (int) (Math.random() * 4);
        if (val == 1) {
            pkm.setStatChanging(true);
            gp.playSE(gp.battle_SE, pkm.getStatus().getStatus());
            pkm.getStatus().printStatus(gp, pkm.getName());
            return false;
        }
        else {
            return true;
        }
    }

    private boolean frozen(Pokemon pkm) throws InterruptedException {

        // 1/4 chance attacker can thaw from ice
        int val = 1 + (int) (Math.random() * 4);
        if (val == 1) {
            removeStatus(pkm);
            return true;
        }
        else {
            pkm.setStatChanging(true);
            gp.playSE(gp.battle_SE, pkm.getStatus().getStatus());
            pkm.getStatus().printStatus(gp, pkm.getName());
            return false;
        }
    }

    private boolean asleep(Pokemon pkm, Move move) throws InterruptedException {

        if (move.getMove() == Moves.SNORE || move.getMove() == Moves.SLEEPTALK) {
            return true;
        }
        // if number of moves under status hit limit, remove status
        else if (recoverStatus(pkm)) {
            return true;
        }
        // pokemon still under status status
        else {
            // increase counter
            pkm.setStatusCounter(pkm.getStatusCounter() + 1);
            pkm.setStatChanging(true);
            gp.playSE(gp.battle_SE, pkm.getStatus().getStatus());
            pkm.getStatus().printStatus(gp, pkm.getName());

            return false;
        }
    }

    private boolean confused(Pokemon pkm) throws InterruptedException {

        pkm.setStatChanging(true);
        gp.playSE(gp.battle_SE, pkm.getStatus().getStatus());
        typeDialogue(pkm.getName() + " is\n" + pkm.getStatus().getStatus() + "...");

        // if number of moves under status hit limit, remove status
        if (recoverStatus(pkm)) {
            return true;
        }
        // pokemon still under status status
        else {
            // increase counter
            pkm.setStatusCounter(pkm.getStatusCounter() + 1);

            // if Pokémon hurt itself in confusion
            return !confusionDamage(pkm);
        }
    }

    private boolean confusionDamage(Pokemon pkm) throws InterruptedException {

        // 1/2 chance of hurting self
        int val = 1 + (int) (Math.random() * 2);

        if (val == 1) {

            int damage = getConfusionDamage(pkm);
            if (damage > pkm.getHP()) {
                damage = pkm.getHP();
            }

            pkm.setHit(true);
            gp.playSE(gp.battle_SE, "hit-normal");
            decreaseHP(pkm, damage);
            pkm.getStatus().printStatus(gp, pkm.getName());

            return true;
        }
        else {
            return false;
        }
    }

    private int getConfusionDamage(Pokemon pkm) {
        /** CONFUSION DAMAGE FORMULA REFERENCE: https://bulbapedia.bulbagarden.net/wiki/Confusion_(status_condition)#Effect **/

        int damage;

        double level = pkm.getLevel();
        double power = 40.0;
        double A = pkm.getAttack();
        double D = pkm.getDefense();

        Random r = new Random();
        double random = (double) (r.nextInt(100 - 85 + 1) + 85) / 100.0;

        damage = (int) ((Math.floor(((((Math.floor((2 * level) / 5)) + 2) *
                power * (A / D)) / 50)) + 2) * random);

        return damage;
    }

    private boolean recoverStatus(Pokemon pkm) throws InterruptedException {

        // if first move under status, set number of moves until free (1-5)
        if (pkm.getStatusLimit() == 0) {
            pkm.setStatusLimit((int) (Math.random() * 5));
        }

        // if number of moves under status hit limit, remove status
        if (pkm.getStatusCounter() >= pkm.getStatusLimit()) {
            removeStatus(pkm);
            return true;
        }
        else {
            return false;
        }
    }
    /** END CAN MOVE METHODS **/

    /**
     * MOVE METHODS
     **/
    private void move(Pokemon atk, Pokemon trg, Move atkMove) throws InterruptedException {

        getWeatherMoveDelay(atkMove);

        // Pokemon can't move if wrapped
        if (atk.hasActiveMove(Moves.WRAP)) {
            gp.playSE(gp.battle_SE, "hit-normal");
            atk.setHit(true);

            int damage = (int) Math.ceil(atk.getHP() * 0.0625);
            if (damage >= atk.getHP()) {
                damage = atk.getHP();
            }
            decreaseHP(atk, damage);
            typeDialogue(atk.getName() + " is\nhurt by Wrap!");
        }
        // Normal move or charging move ready on second turn
        else if (atkMove.isReady()) {
            typeDialogue(atk.getName() + " used\n" + atkMove + "!", false);

            atk.setProtection(Protection.NONE);
            atk.setAttacking(true);
            playSE(gp.moves_SE, atkMove.getName());

            if (validMove(atk, trg, atkMove)) {
                atkMove.resetMoveTurns();
                atkMove.setPP(atkMove.getPP() - 1);
                attack(atk, trg, atkMove);
            }
            else {
                typeDialogue("It had no effect!");
            }
        }
        // Charging move used, needs cooldown
        else if (atkMove.getRecharge()) {
            typeDialogue(atkMove.getDelay(atk.getName()));
            atkMove.setTurnCount(atkMove.getTurns());
        }
        // Charging move with no cooldown, used on first turn
        else {
            typeDialogue(atk.getName() + " used\n" + atkMove + "!");

            if (gravityCounter > 0 && atkMove.getMove() == Moves.FLY || atkMove.getMove() == Moves.BOUNCE) {
                typeDialogue("The move failed!");
            }
            else {
                atk.setProtection(atkMove.getProtection());
                typeDialogue(atkMove.getDelay(atk.getName()));

                atkMove.setTurnCount(atkMove.getTurnCount() - 1);
            }
        }
    }

    private void getWeatherMoveDelay(Move move) {

        switch (weather) {
            case SUNLIGHT:
                if (move.getMove() == Moves.SOLARBEAM) {
                    move.setTurnCount(0);
                }
                break;
            case RAIN:
                break;
            case HAIL:
                break;
            case SANDSTORM:
                break;
            case CLEAR:
                break;
        }
    }

    private boolean validMove(Pokemon atk, Pokemon trg, Move move) {

        // Check if move is used in the correct situation
        boolean isValid = switch (move.getMove()) {
            case Moves.DREAMEATER -> trg.hasStatus(Status.SLEEP);
            case Moves.FEINT -> trg.hasActiveMove(Moves.PROTECT) || trg.hasActiveMove(Moves.DETECT);
            case Moves.LASTRESORT -> {
                ArrayList<Moves> moveSet = atk == fighter[0] ? playerMoves : cpuMoves;
                boolean valid = atk.getMoveSet().stream().anyMatch(m -> moveSet.contains(m.getMove()));
                yield valid;
            }
            case Moves.SNORE -> atk.hasStatus(Status.SLEEP);
            case Moves.SUCKERPUNCH -> !battleQueue.isEmpty() && cpuMove.getPower() > 0;
            default -> true;
        };

        if (trg.hasActiveMove(Moves.HEALBLOCK) && healMoves.contains(move.getMove())) {
            isValid = false;
        }

        if (gravityCounter > 0 && skyMoves.contains(move.getMove())) {
            isValid = false;
        }

        return isValid;
    }
    /** END MOVE METHODS **/

    /**
     * ATTACK METHOD
     **/
    public void attack(Pokemon atk, Pokemon trg, Move move) throws InterruptedException {

        if (atk == fighter[0]) {
            if (!playerMoves.contains(move.getMove())) {
                playerMoves.add(move.getMove());
            }
        }
        else {
            if (!cpuMoves.contains(move.getMove())) {
                cpuMoves.add(move.getMove());
            }
        }

        if (hit(atk, trg, move) || move.isToSelf()) {
            switch (move.getMType()) {
                case STATUS:
                    statusMove(atk, trg, move);
                    break;
                case ATTRIBUTE:
                    attributeMove(atk, trg, move);
                    break;
                case WEATHER:
                    weatherMove(move);
                    break;
                case OTHER:
                    otherMove(atk, trg, move);
                    break;
                default:
                    damageMove(atk, trg, move);
                    break;
            }

            if (trg.getAbility() == Ability.PRESSURE) {
                move.setPP(move.getPP() - 1);
            }
        }
        else {
            typeDialogue("The attack missed!");

            // Reset Furry Cutter counter
            if (atk == fighter[0]) {
                playerFurryCutterCount = 10;
            }
            else {
                cpuFurryCutterCount = 10;
            }

            if (move.getMove() == Moves.JUMPKICK || move.getMove() == Moves.HIGHJUMPKICK) {

                int damage = (int) Math.floor(atk.getHP() * 0.125);
                if (damage >= atk.getHP()) {
                    damage = atk.getHP();
                }

                decreaseHP(atk, damage);
                typeDialogue(atk.getName() + " hurt\n" + damage + " itself!");
            }
        }
    }

    private boolean hit(Pokemon atk, Pokemon trg, Move move) {
        /** PROBABILITY FORMULA REFERENCE: https://monster-master.fandom.com/wiki/Evasion **/
        /** PROTECTED MOVES REFERENCE: https://bulbapedia.bulbagarden.net/wiki/Semi-invulnerable_turn **/

        boolean hit;

        // Mind Reader bypasses accuracy check unless target has Protect
        if (atk.hasActiveMove(Moves.MINDREADER) && !trg.hasActiveMove(Moves.PROTECT)) {
            return true;
        }

        // Check if target's protection can be bypassed
        switch (trg.getProtection()) {
            case BOUNCE, FLY, SKYDROP:
                if (move.getPower() > 0 && move.getMove() != Moves.GUST &&
                        move.getMove() != Moves.SKYUPPERCUT &&
                        move.getMove() != Moves.THUNDER &&
                        move.getMove() != Moves.TWISTER) {
                    return false;
                }
                break;
            case DIG:
                if (move.getMove() != Moves.EARTHQUAKE &&
                        move.getMove() != Moves.FISSURE &&
                        move.getMove() != Moves.MAGNITUDE) {
                    return false;
                }
                break;
            case DIVE:
                if (move.getMove() != Moves.SURF) {
                    return false;
                }
                break;
            default:
                break;
        }

        // Target is protected
        if (trg.hasActiveMove(Moves.PROTECT)) {
            hit = false;
        }
        // Move never misses, always hit
        else if (move.getAccuracy() == -1) {
            hit = true;
        }
        // Attacker has Miracle Eye or Odor Sleuth, always hit
        else if (atk.hasActiveMove(Moves.MIRACLEEYE) || atk.hasActiveMove(Moves.ODORSLEUTH)) {
            hit = true;
        }
        else {
            double accuracy;
            double moveAccuracy = getAccuracy(atk, move);

            // Always hit
            if (moveAccuracy > 100) {
                return true;
            }

            // Ignore evasion if attacker has Foresight
            if (atk.hasActiveMove(Moves.FORESIGHT)) {
                accuracy = moveAccuracy * atk.getAccuracy();
            }
            else {
                accuracy = moveAccuracy * (atk.getAccuracy() / trg.getEvasion());
            }

            Random r = new Random();
            float chance = r.nextFloat();

            // Chance of missing is accuracy / 100
            hit = chance <= ((float) accuracy / 100);
        }

        return hit;
    }

    private double getAccuracy(Pokemon pkm, Move move) {
        /** GRAVITY CALCULATION REFERENCE: https://bulbapedia.bulbagarden.net/wiki/Gravity_(move) **/

        double accuracy = move.getAccuracy();

        switch (weather) {
            case CLEAR:
                break;
            case HAIL:
                if (move.getMove() == Moves.BLIZZARD) {
                    accuracy = 999;
                }
                break;
            case RAIN:
                if (move.getMove() == Moves.THUNDER) {
                    accuracy = 999;
                }
                break;
            case SUNLIGHT, SANDSTORM:
                if (move.getMove() == Moves.THUNDER) {
                    accuracy = 50;
                }
                break;
        }

        if (pkm.getAbility() == Ability.COMPOUNDEYES) {
            accuracy *= 1.3;
        }

        if (gravityCounter > 0) {
            accuracy *= 1.66;
        }

        return accuracy;
    }
    /** END ATTACK METHODS **/

    /**
     * STATUS MOVE METHODS
     **/
    private void statusMove(Pokemon atk, Pokemon trg, Move move) throws InterruptedException {
        if (trg.getStatus() == null) {
            if (trg.hasActiveMove(Moves.SAFEGUARD)) {
                typeDialogue("It had no effect!");
            }
            else {
                setStatus(atk, trg, move.getEffect());
            }
        }
        else {
            typeDialogue(trg.getName() + " is\nalready " +
                    trg.getStatus().getStatus() + "!");
        }
    }

    private void setStatus(Pokemon atk, Pokemon trg, Status status) throws InterruptedException {

        if (trg.getHP() <= 0 || trg.getStatus() != null) {
            return;
        }

        if (trg.getAbility() == Ability.LIMBER && status == Status.PARALYZE) {
            typeDialogue("It had no effect!");
        }
        else {
            trg.setStatus(status);
            trg.setStatChanging(true);

            gp.playSE(gp.battle_SE, status.getStatus());
            typeDialogue(trg.getName() + status.printCondition());

            if (trg.getAbility() == Ability.QUICKFEET && !trg.getAbility().isActive()) {
                trg.getAbility().setActive(true);
                setAttribute(trg, List.of("speed"), 2);
            }
            else if (trg.getAbility() == Ability.GUTS && !trg.getAbility().isActive()) {
                trg.getAbility().setActive(true);
                setAttribute(trg, List.of("attack"), 2);
            }
            else if ((trg.getAbility() == Ability.SYNCHRONIZE) &&
                    (status == Status.BURN || status == Status.PARALYZE || status == Status.POISON)) {
                setStatus(trg, atk, status);
            }
        }
    }

    private void removeStatus(Pokemon pkm) throws InterruptedException {
        Status status = pkm.getStatus();
        pkm.removeStatus();
        typeDialogue(pkm.getName() + status.printRecover());
    }
    /** END STATUS MOVE METHODS **/

    /**
     * ATTRIBUTE MOVE METHODS
     **/
    private void attributeMove(Pokemon atk, Pokemon trg, Move move) throws InterruptedException {

        if (trg.hasActiveMove(Moves.SNATCH)) {
            Pokemon temp = atk;
            atk = trg;
            trg = temp;
            typeDialogue(atk.getName() + " snatched\n" + trg.getName() + "'s move!");

            typeDialogue(atk.getName() + " used\n" + move + "!", false);
            atk.setAttacking(true);
            playSE(gp.moves_SE, move.getName());
        }

        // if move changes own attributes
        if (move.isToSelf()) {

            int level = move.getLevel();

            if (weather == Weather.SUNLIGHT && move.getMove() == Moves.GROWTH) {
                level++;
            }

            setAttribute(atk, move.getStats(), level);
        }
        // if move changes target attributes
        else {
            if (trg.hasActiveMove(Moves.MIST) || trg.getAbility() == Ability.CLEARBODY) {
                typeDialogue("It had no effect!");
            }
            else if (move.getMove() == Moves.CAPTIVATE && trg.getSex() == atk.getSex()) {
                typeDialogue("It had no effect!");
            }
            else {
                setAttribute(trg, move.getStats(), move.getLevel());

                if (move.getMove() == Moves.SWAGGER) {
                    setStatus(atk, trg, Status.CONFUSE);
                }
            }
        }
    }

    private void setAttribute(Pokemon pkm, List<String> stats, int level) throws InterruptedException {

        if (pkm.getHP() <= 0) {
            return;
        }

        // Loop through each specified attribute to be changed
        boolean canChange;
        for (String stat : stats) {
            canChange = pkm.canChangeStat(stat, level);

            if (!canChange) {
                String change = level > 0 ? "higher" : "lower";
                typeDialogue(pkm.getName() + "'s " + stat + "\nwon't go any " + change + "!");
                continue;
            }

            // Set value for UI to animate stat change
            pkm.setStatChanging(true);

            if (level > 0) {
                gp.playSE(gp.battle_SE, "stat-up");
                typeDialogue(pkm.changeStat(stat, level));
            }
            else {
                if (pkm.getAbility() == Ability.KEENEYE && stat.equals("accuracy")) {
                    typeDialogue("It had no effect!");
                }
                else {
                    gp.playSE(gp.battle_SE, "stat-down");
                    typeDialogue(pkm.changeStat(stat, level));
                }
            }
        }
    }
    /** END ATTRIBUTE MOVE METHODS **/

    /**
     * WEATHER MOVE METHODS
     **/
    private void weatherMove(Move move) throws InterruptedException {

        // If the move has the same weather as the current weather
        if (move.getWeather() == weather) {
            typeDialogue("It had no effect!");
        }
        else {
            Weather oldWeather = weather;

            weather = move.getWeather();
            weatherDays = move.getTurns();

            checkWeatherCondition(oldWeather);
        }
    }

    private void checkWeatherCondition(Weather oldWeather) throws InterruptedException {
        switch (weather) {
            case CLEAR:
                break;
            case SUNLIGHT:
                gp.playSE(gp.battle_SE, "sunlight");
                typeDialogue("The sun shines intensely!");
                break;
            case RAIN:
                gp.playSE(gp.battle_SE, "rain");
                typeDialogue("Rain started to fall!");

                // Weather changed, check for Abilities
                if (weather != oldWeather) {
                    if (fighter[0].getAbility() == Ability.SWIFTSWIM) {
                        setAttribute(fighter[0], List.of("speed"), 2);
                    }
                    if (fighter[1].getAbility() == Ability.SWIFTSWIM) {
                        setAttribute(fighter[1], List.of("speed"), 2);
                    }
                }

                break;
            case HAIL:
                gp.playSE(gp.battle_SE, "hail");
                typeDialogue("Hail started to fall!");
                break;
            case SANDSTORM:
                gp.playSE(gp.battle_SE, "sandstorm");
                typeDialogue("A sandstorm started to\n rage!");
                break;
        }
    }
    /** END WEATHER MOVE METHODS **/

    /**
     * OTHER MOVE METHODS
     **/
    private void otherMove(Pokemon atk, Pokemon trg, Move move) throws InterruptedException {

        int gainedHP;

        switch (move.getMove()) {
            case AROMATHERAPY:
                // Player 1
                if (atk == fighter[0]) {
                    for (Pokemon pkm : gp.player.pokeParty) {
                        if (pkm.isAlive()) {
                            pkm.removeStatus();
                        }
                    }
                }
                // Player 2
                else if (gp.ui.player == 1) {
                    for (Pokemon pkm : gp.player_2.pokeParty) {
                        if (pkm.isAlive()) {
                            pkm.removeStatus();
                        }
                    }
                }
                // CPU Trainer
                else if (trainer != null) {
                    for (Pokemon pkm : trainer.pokeParty) {
                        if (pkm.isAlive()) {
                            pkm.removeStatus();
                        }
                    }
                }
                // Wild Pokemon
                else {
                    atk.removeStatus();
                }

                typeDialogue("A soothing aroma wafted\nthrough the air!");
                break;
            case CHARGE:
                if (atk.hasActiveMove(Moves.CHARGE)) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(Moves.CHARGE);
                    typeDialogue(atk.getName() + " began\ncharging power!");
                    setAttribute(atk, List.of("sp. defense"), 1);
                }
                break;
            case DOOMDESIRE:
                if (trg.hasActiveMove(Moves.DOOMDESIRE) || trg.hasActiveMove(Moves.FUTURESIGHT)) {
                    typeDialogue("It had no effect!");
                }
                else {
                    trg.addActiveMove(Moves.DOOMDESIRE);
                    typeDialogue(atk.getName() + " chose\nDoome Desire as its destiny!");
                }
                break;
            case DETECT, PROTECT:
                atk.addActiveMove(Moves.PROTECT);
                typeDialogue(atk.getName() + " protected\nitself!");
                break;
            case ENDURE:
                // Check if next turn is opponent move
                boolean firstTurn = atk == fighter[0] ? battleQueue.contains(queue_CPUMove) : battleQueue.contains(queue_PlayerMove);

                if (!firstTurn || atk.getHP() == 0) {
                    typeDialogue("It had no effect!");
                }
                else {
                    typeDialogue(atk.getName() + " braced\nitself!");
                }
                break;
            case FLING:
                Entity item = atk.getItem();
                if (item != null && item.damage != 0) {
                    typeDialogue(atk.getName() + " flung\nits " + item.name + "!");
                    damageMove(atk, trg, move);
                }
                else {
                    typeDialogue("It had no effect!");
                }
                break;
            case FOCUSENERGY:
                atk.setIsFocusing(true);
                typeDialogue(atk.getName() + " is getting\npumped!");
                break;
            case FUTURESIGHT:
                if (trg.hasActiveMove(move.getMove()) || trg.hasActiveMove(Moves.DOOMDESIRE)) {
                    typeDialogue("It had no effect!");
                }
                else {
                    trg.addActiveMove(move.getMove());
                    typeDialogue(trg.getName() + " has\nforeseen an attack!");
                }
                break;
            case GRAVITY:
                if (gravityCounter > 0) {
                    typeDialogue("It had no effect!");
                }
                else {
                    gravityCounter = 4;
                    typeDialogue("Gravity intensified!");

                    if (trg.getProtection() == Protection.FLY || trg.getProtection() == Protection.BOUNCE) {
                        trg.setProtection(Protection.NONE);
                        cpuMove.resetMoveTurns();
                        playerMove.resetMoveTurns();
                    }

                    trg.removeActiveMove(Moves.MAGNETRISE);
                    atk.removeActiveMove(Moves.MAGNETRISE);
                }
                break;
            case GRUDGE:
                if (atk.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " wants the\nopponent to bear a GRUDGE!");
                }
                break;
            case HAZE:
                atk.resetStats();
                atk.resetStatStages();

                trg.resetStats();
                trg.resetStatStages();

                typeDialogue("All stat changes were\neliminated!");
                break;
            case HEALBELL:
                atk.removeStatus();

                if (atk == fighter[0]) {
                    for (Pokemon p : gp.player.pokeParty) {
                        p.removeStatus();
                    }
                }
                else if (atk == fighter[1] && trainer != null) {
                    for (Pokemon p : trainer.pokeParty) {
                        p.removeStatus();
                    }
                }

                typeDialogue("A bell chimed!");
                break;
            case HEALBLOCK:
                if (trg.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    trg.addActiveMove(move.getMove());
                    typeDialogue(trg.getName() + " was prevented\nfrom healing!");
                }
                break;
            case IMPRISON:
                if (atk.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " sealed the\nopponent's move(s)!");
                }
                break;
            case LEECHSEED:
                if (trg.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    trg.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " planted\na seed on " + trg.getName() + "!");
                }
                break;
            case LIGHTSCREEN, REFLECT:
                if (atk.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + "'s " + move + "\nraised DEFENSE!");
                }
                break;
            case LUCKYCHANT:
                if (atk.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(move.getMove());
                    typeDialogue("The lucky chant shielded\n" + atk.getName() + " from harm!");
                }
                break;
            case MAGNETRISE:
                if (atk.hasActiveMove(move.getMove()) || gravityCounter > 0) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " levitated\non electromagnetism!");
                }
                break;
            case MIST:
                if (trg.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    trg.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " became\nshrouded in MIST!");
                }
                break;
            case MEANLOOK:
                trg.setCanEscape(false);
                typeDialogue(trg.getName() + " can't\nescape now!");
                break;
            case METRONOME:
                do {
                    int randomIndex = new Random().nextInt(Moves.values().length);
                    move = new Move(Moves.values()[randomIndex]);
                }
                while (move.getMove() == Moves.METRONOME || move.getMove() == Moves.SLEEPTALK);

                move(atk, trg, move);
                break;
            case MINDREADER:
                if (atk.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " took aim\nat " + trg.getName() + "!");
                }
                break;
            case ODORSLEUTH, MIRACLEEYE, FORESIGHT:
                if (trg.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    trg.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " identified\n" + trg.getName() + "!");
                }
                break;
            case PERISHSONG:
                if (trg.hasActiveMove(move.getMove()) && atk.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else if (trg.getAbility() == Ability.SOUNDPROOF && atk.getAbility() == Ability.SOUNDPROOF) {
                    typeDialogue("It had no effect!");
                }
                else {
                    if (!trg.hasActiveMove(move.getMove()) && trg.getAbility() != Ability.SOUNDPROOF) {
                        trg.addActiveMove(move.getMove());
                    }
                    if (!atk.hasActiveMove(move.getMove()) && atk.getAbility() != Ability.SOUNDPROOF) {
                        atk.addActiveMove(move.getMove());
                    }

                    typeDialogue("All Pokémon hearing the song\nwill faint in three turns!");
                }
                break;
            case PSYCHOSHIFT:
                if (atk.getStatus() != null) {
                    setStatus(atk, trg, atk.getStatus());
                    atk.removeStatus();
                }
                else {
                    typeDialogue("It had no effect!");
                }

                break;
            case PSYCHUP:
                atk.resetStats();
                atk.resetStatStages();

                atk.changeStat("defense", trg.getDefenseStg());
                atk.changeStat("sp. attack", trg.getSpAttackStg());
                atk.changeStat("sp. defense", trg.getSpDefenseStg());
                atk.changeStat("speed", trg.getSpeedStg());
                atk.changeStat("evasion", trg.getAccuracyStg());
                atk.changeStat("evasion", trg.getEvasionStg());

                typeDialogue(atk.getName() + " copied\n" + trg.getName() + "'s stats!");
                break;
            case RECOVER, ROOST:
                if (atk.getHP() < atk.getBHP()) {

                    gainedHP = atk.getBHP() - atk.getHP();
                    int halfHP = (int) Math.floor(atk.getBHP() / 2.0);
                    if (gainedHP > halfHP) {
                        gainedHP = halfHP;
                    }

                    increaseHP(atk, gainedHP);
                    typeDialogue(atk.getName() + "\nregained health!");
                }
                else {
                    typeDialogue("It had no effect!");
                }
                break;
            case REFRESH:
                if (atk.hasStatus(Status.BURN) || atk.hasStatus(Status.PARALYZE) || atk.hasStatus(Status.POISON)) {
                    typeDialogue(atk.getName() + " status\nreturned to normal!");
                }
                else {
                    typeDialogue("It had no effect!");
                }
                break;
            case REST:
                gainedHP = atk.getBHP() - atk.getHP();
                increaseHP(atk, gainedHP);
                typeDialogue(atk.getName() + " regained\nhealth!");
                setStatus(trg, atk, Status.SLEEP);
                break;
            case SLEEPTALK:
                if (atk.hasStatus(Status.SLEEP)) {
                    do {
                        move = atk.getMoveSet().get(new Random().nextInt(atk.getMoveSet().size()));
                    }
                    while (move.getMove() == Moves.SLEEPTALK);

                    move(atk, trg, move);
                }
                else {
                    typeDialogue("It had no effect!");
                }
                break;
            case SNATCH:
                atk.addActiveMove(move.getMove());
                typeDialogue(atk.getName() + " waits for the target\nto make a move!");
                break;
            case SPITE:
                Move trgMove = move == playerMove ? cpuMove : playerMove;

                if (trgMove.getPP() < 1) {
                    typeDialogue("It had no effect!");
                }
                else {
                    int PP = 4;
                    if (trgMove.getPP() < PP) {
                        PP = trgMove.getPP();
                    }
                    trgMove.setPP(trgMove.getPP() - PP);
                    typeDialogue("It reduced the PP of\n" + trg.getName() + "'s " +
                            trgMove.getName() + " by " + PP + "!");
                }
                break;
            case SPLASH:
                typeDialogue("It had no effect!");
                break;
            case SYNTHESIS:
                if (weather == Weather.SUNLIGHT) {
                    gainedHP = (int) (atk.getBHP() * 0.66);
                }
                else if (weather == null) {
                    gainedHP = (int) (atk.getBHP() * 0.5);
                }
                else {
                    gainedHP = (int) (atk.getBHP() * 0.25);
                }

                increaseHP(atk, gainedHP);
                typeDialogue(atk.getName() + " regained\nhealth!");
                break;
            case TELEPORT:
                if (trainer == null) {
                    typeDialogue(atk.getName() + " teleported\naway!");
                    endBattle();
                }
                else {
                    typeDialogue("It had no effect!");
                }
                break;
            case WISH:
                if (atk.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    atk.addActiveMove(move.getMove());
                    typeDialogue(atk.getName() + " made\na wish!");
                }
                break;
            case WRAP:
                if (trg.hasActiveMove(move.getMove())) {
                    typeDialogue("It had no effect!");
                }
                else {
                    trg.addActiveMove(move.getMove());
                    typeDialogue(trg.getName() + " was\nwrapped by " + atk.getName() + "!");
                }
            default:
                break;
        }
    }
    /** END OTHER MOVE METHODS **/

    /**
     * DAMAGE MOVE METHODS
     **/
    private void damageMove(Pokemon atk, Pokemon trg, Move move) throws InterruptedException {

        // Reset Furry Cutter counter if not Furry Cutter
        if (move.getMove() != Moves.FURYCUTTER) {
            if (atk == fighter[0]) {
                playerFurryCutterCount = 10;
            }
            else {
                cpuFurryCutterCount = 10;
            }
        }

        getDamage(atk, trg, move);

        // Both pokemon alive
        if (trg.getHP() > 0 && atk.getHP() > 0) {
            applyEffect(atk, trg, move);
            if (!battleQueue.isEmpty() && battleQueue.peek() != queue_ActiveMoves && flinched(trg, move)) {
                battleQueue.removeFirst();
            }
        }
        // One or both pokemon fainted
        else {
            if (trg.getHP() > 0 || atk.getHP() > 0) {
                applyEffect(atk, trg, move);
            }
            battleQueue.remove(queue_PlayerMove);
            battleQueue.remove(queue_CPUMove);
        }
    }

    private void getDamage(Pokemon atk, Pokemon trg, Move move) throws InterruptedException {

        double crit = getCritical(atk, trg, move);
        int damage = 0;

        switch (move.getMove()) {
            case COMETPUNCH, FURYATTACK, FURYSWIPES:
                int turns = new Random().nextInt(5 - 2 + 1) + 2;
                int i = 0;
                while (i < turns) {

                    // Only the first hit has a crit chance
                    damage += i != 0 ? dealDamage(atk, trg, move, 1.0) : dealDamage(atk, trg, move, crit);

                    pause(800);

                    i++;

                    if (trg.getHP() <= 0) {
                        break;
                    }
                }

                String times = i == 1 ? "time!" : "times!";
                typeDialogue("Hit " + i + " " + times);

                break;
            default:
                damage = dealDamage(atk, trg, move, crit);
                break;
        }

        if (damage <= 0) {
            typeDialogue("It had no effect!");
        }
        else {
            if (trg == fighter[0]) {
                playerDamageTaken = damage;
            }
            else {
                cpuDamageTaken = damage;
            }

            if (crit >= 1.5) {
                typeDialogue("A critical hit!");
            }

            String hitSE = getHitSE(getEffectiveness(trg, move.getType()));
            if (hitSE.equals("hit-super")) {
                typeDialogue("It's super effective!");
            }
            else if (hitSE.equals("hit-weak")) {
                typeDialogue("It's not very effective...");
            }

            typeDialogue(trg.getName() + " took\n" + damage + " damage!");

            if (move.getMove() == Moves.FEINT) {
                trg.removeActiveMove(Moves.DETECT);
                trg.removeActiveMove(Moves.PROTECT);
                typeDialogue(trg.getName() + " fell for\nthe feint!");
            }
        }

        switch (move.getMove()) {
            case ABSORB, DREAMEATER, GIGADRAIN, LEECHLIFE, MEGADRAIN:
                absorbHP(atk, damage);
                break;
            default:
                break;
        }

        getRecoil(atk, move, damage);
    }

    private int dealDamage(Pokemon atk, Pokemon trg, Move move, double crit) throws InterruptedException {

        double effectiveness = getEffectiveness(trg, move.getType());
        if (move.getMove() == Moves.DOOMDESIRE) {
            effectiveness = 1.0;
        }

        String hitSE = getHitSE(effectiveness);
        gp.playSE(gp.battle_SE, hitSE);
        trg.setHit(true);

        int damage = calculateDamage(atk, trg, move, crit);

        if (trg.hasActiveMove(Moves.REFLECT) && move.getMType() == MoveType.PHYSICAL) {
            damage /= 2;
        }

        if (trg.getAbility() == Ability.FLASHFIRE && move.getType() == Type.FIRE &&
                !trg.getAbility().isActive()) {
            trg.getAbility().setActive(true);
        }

        if (damage >= trg.getHP()) {
            damage = trg.getHP();

            Move otherMove = atk == fighter[1] ? playerMove : cpuMove;
            if (move.getMove() == Moves.FALSESWIPE || (otherMove != null &&
                    otherMove.getMove() == Moves.ENDURE)) {
                damage--;
            }
        }

        decreaseHP(trg, damage);

        return damage;
    }

    private int calculateDamage(Pokemon atk, Pokemon trg, Move move, double crit) {
        /** DAMAGE FORMULA REFERENCE (GEN IV): https://bulbapedia.bulbagarden.net/wiki/Damage **/

        double damage;

        double level = atk.getLevel();
        double power = getPower(move, atk, trg);
        double A = getAttack(move, atk, trg);
        double D = getDefense(move, atk, trg);
        double STAB = atk.checkType(move.getType()) ? 1.5 : 1.0;
        double type = getEffectiveness(trg, move.getType());

        if (move.getMove() == Moves.DOOMDESIRE) {
            STAB = 1.0;
            type = 1.0;
        }

        Random r = new Random();
        double random = (double) (r.nextInt(100 - 85 + 1) + 85) / 100.0;

        damage = ((Math.floor(((((Math.floor((2 * level) / 5)) + 2) *
                power * (A / D)) / 50)) + 2) * crit * STAB * type * random);

        // OHKA moves do not work if the target's level is higher
        if (power >= 999 && atk.getLevel() < trg.getLevel()) {
            damage = 0;
        }

        switch (move.getMove()) {
            case COUNTER:
                // Run Counter if the opponent has already used a damaging physical move
                if (atk == fighter[0]) {
                    if (cpuMove != null && cpuMove.getMove().getMType() == MoveType.PHYSICAL && playerDamageTaken > 0) {
                        damage = playerDamageTaken * 2;
                    }
                    else {
                        damage = 0;
                    }
                }
                else {
                    if (playerMove != null && playerMove.getMove().getMType() == MoveType.PHYSICAL && cpuDamageTaken > 0) {
                        damage = cpuDamageTaken * 2;
                    }
                    else {
                        damage = 0;
                    }
                }
                break;
            case DRAGONRAGE:
                damage = 40;
                break;
            case ENDEAVOR:
                if (trg.getHP() < atk.getHP()) {
                    damage = 0;
                }
                else {
                    damage = trg.getHP() - atk.getHP();
                }
                break;
            case SEISMICTOSS:
                damage = atk.getLevel();
                break;
            default:
                break;
        }

        switch (trg.getAbility()) {
            case FLASHFIRE:
                if (move.getType() == Type.FIRE) {
                    damage = 0;
                }
                break;
            case LEVITATE:
                if (move.getType() == Type.GROUND && gravityCounter == 0) {
                    damage = 0;
                }
                break;
            case SOUNDPROOF:
                if (soundMoves.contains(move.getMove())) {
                    damage = 0;
                }
                break;
            case THICKFAT:
                if (move.getType() == Type.FIRE || move.getType() == Type.ICE) {
                    damage *= 0.5;
                }
                break;
            case WONDERGUARD:
                if (type <= 1.0) {
                    damage = 0;
                }
                break;
            default:
                break;
        }

        if (trg.hasActiveMove(Moves.MAGNETRISE) && gravityCounter <= 0) {
            damage = 0;
        }

        return (int) damage;
    }

    private double getPower(Move move, Pokemon atk, Pokemon trg) {
        /** FLAIL POWER FORMULA REFERENCE (GEN IV): https://bulbapedia.bulbagarden.net/wiki/Flail_(move) **/

        double power = 1.0;

        switch (move.getMove()) {
            case ASSURANCE:
                if (atk == fighter[0] && cpuDamageTaken > 0) {
                    power *= 2;
                }
                else if (atk == fighter[1] && playerDamageTaken > 0) {
                    power *= 2;
                }
                else {
                    power = move.getPower();
                }
                break;
            case ERUPTION:
                power = (atk.getHP() * 150.0) / atk.getBHP();
                break;
            case FURYCUTTER:
                // Track consecutive uses of Furry Cutter for player or CPU
                if (atk == fighter[0]) {
                    power = playerFurryCutterCount;
                    if (playerFurryCutterCount < 160) {
                        playerFurryCutterCount *= 2;
                    }
                }
                else {
                    power = cpuFurryCutterCount;
                    if (cpuFurryCutterCount < 160) {
                        cpuFurryCutterCount *= 2;
                    }
                }
                break;
            case FLAIL, REVERSAL:
                double remainHP = (double) atk.getHP() / atk.getBHP();

                if (remainHP >= 0.672) {
                    power = 20;
                }
                else if (0.672 > remainHP && remainHP >= 0.344) {
                    power = 40;
                }
                else if (0.344 > remainHP && remainHP >= 0.203) {
                    power = 80;
                }
                else if (0.203 > remainHP && remainHP >= 0.094) {
                    power = 100;
                }
                else if (0.094 > remainHP && remainHP >= 0.031) {
                    power = 150;
                }
                else if (0.031 > remainHP) {
                    power = 200;
                }

                break;
            case FLING:
                power = atk.getItem().power;
                atk.giveItem(null);
                break;
            case MAGNITUDE:
                int strength = 4;

                // RANDOM NUM 0-100
                int chance = new Random().nextInt(100);
                int total = 0;

                // FOR EACH MAGNITUDE VALUE
                for (Integer magnitude : magnitudeTable.keySet()) {

                    // GET PROBABILITY OF MOVE MAGNITUDE
                    int rate = magnitudeTable.get(magnitude);
                    total += rate;

                    // MAGNITUDE RANDOMLY SELECTED, ASSIGN TO STRENGTH
                    if (chance <= total) {
                        strength = magnitude;
                        break;
                    }
                }

                if (strength == 4) {
                    power = 10;
                }
                else if (strength == 5) {
                    power = 30;
                }
                else if (strength == 6) {
                    power = 50;
                }
                else if (strength == 7) {
                    power = 70;
                }
                else if (strength == 8) {
                    power = 90;
                }
                else if (strength == 9) {
                    power = 110;
                }
                else if (strength == 10) {
                    power = 150;
                }

                break;
            case PUNISHMENT:

                power = 60.0;

                if (trg.getAttackStg() > 0) {
                    power += 20.0 * trg.getAttackStg();
                }
                if (trg.getDefenseStg() > 0) {
                    power += 20.0 * trg.getDefenseStg();
                }
                if (trg.getSpAttackStg() > 0) {
                    power += 20.0 * trg.getSpAttackStg();
                }
                if (trg.getSpDefenseStg() > 0) {
                    power += 20.0 * trg.getSpDefenseStg();
                }
                if (trg.getAccuracyStg() > 0) {
                    power += 20.0 * trg.getAccuracyStg();
                }
                if (trg.getEvasionStg() > 0) {
                    power += 20.0 * trg.getEvasionStg();
                }
                if (trg.getSpeedStg() > 0) {
                    power += 20.0 * trg.getSpeedStg();
                }

                if (power > 200.0) {
                    power = 200.0;
                }

                break;
            case REVENGE:
                // Player 1 already took damage
                if (atk == fighter[0] && playerDamageTaken > 0) {
                    power = move.getPower() * 2;
                }
                // CPU or Player 2 already took damage
                else if (atk == fighter[1] && cpuDamageTaken > 0) {
                    power = move.getPower() * 2;
                }
                else {
                    power = move.getPower();
                }
                break;
            case WATERSPOUT:
                power = Math.ceil((double) (atk.getHP() * move.getPower()) / atk.getBHP());
                break;
            default:
                if (move.getPower() == -1) {
                    power = atk.getLevel();
                }
                else if (move.getPower() == 1) {
                    power = trg.getLevel();
                }
                else {
                    power = move.getPower();
                }
                break;
        }

        switch (weather) {
            case CLEAR:
                break;
            case SUNLIGHT:
                if (move.getType() == Type.FIRE) {
                    power *= 1.5;
                }
                else if (move.getType() == Type.WATER) {
                    power *= 0.5;
                }
                break;
            case RAIN:
                if (move.getType() == Type.WATER) {
                    power *= 1.5;
                }
                else if (move.getType() == Type.FIRE) {
                    power *= 0.5;
                }

                if (move.getMove() == Moves.SOLARBEAM) {
                    power *= 0.5;
                }
                break;
            case HAIL, SANDSTORM:
                if (move.getMove() == Moves.SOLARBEAM) {
                    power *= 0.5;
                }
                break;
        }

        switch (atk.getAbility()) {
            case BLAZE:
                if (move.getType() == Type.FIRE && ((double) atk.getHP() / (double) atk.getBHP() <= 0.33)) {
                    power *= 1.5;
                }
                break;
            case FLASHFIRE:
                if (atk.getAbility().isActive()) {
                    power *= 1.5;
                }
                break;
            case OVERGROW:
                if (move.getType() == Type.GRASS && ((double) atk.getHP() / (double) atk.getBHP() <= 0.33)) {
                    power *= 1.5;
                }
                break;
            case TORRENT:
                if (move.getType() == Type.WATER && ((double) atk.getHP() / (double) atk.getBHP() <= 0.33)) {
                    power *= 1.5;
                }
                break;
            default:
                break;
        }

        if (move.getMove().getType() == Type.ELECTRIC && atk.hasActiveMove(Moves.CHARGE)) {
            power *= 2.0;
        }

        return power;
    }

    private double getAttack(Move move, Pokemon atk, Pokemon trg) {

        double attack = 1.0;

        if (move.getMType().equals(MoveType.SPECIAL) || move.getMType().equals(MoveType.OTHER)) {
            attack = atk.getSpAttack();

            if (trg.hasActiveMove(Moves.LIGHTSCREEN)) {
                attack /= 2.0;
            }
        }
        else if (move.getMType().equals(MoveType.PHYSICAL)) {
            attack = atk.getAttack();
        }

        switch (weather) {
            case CLEAR:
                break;
            case SUNLIGHT:
                break;
            case RAIN:
                break;
            case HAIL:
                break;
            case SANDSTORM:
                break;
        }

        return attack;
    }

    private double getDefense(Move move, Pokemon atk, Pokemon trg) {

        double defense = 1.0;

        if (move.getMType().equals(MoveType.SPECIAL) || move.getMType().equals(MoveType.OTHER)) {
            defense = trg.getSpDefense();
        }
        else if (move.getMType().equals(MoveType.PHYSICAL)) {
            defense = trg.getDefense();
        }

        switch (weather) {
            case CLEAR, SUNLIGHT, RAIN, HAIL:
                break;
            case SANDSTORM:
                if (trg.checkType(Type.ROCK) && move.getMType().equals(MoveType.SPECIAL)) {
                    defense *= 1.5;
                }
                break;
        }

        return defense;
    }

    public double getEffectiveness(Pokemon pkm, Type type) {

        double effect = 1.0;

        if ((type == Type.NORMAL || type == Type.FIGHTING) &&
                pkm.checkType(Type.GHOST) &&
                (pkm.hasActiveMove(Moves.ODORSLEUTH) || pkm.hasActiveMove(Moves.FORESIGHT))) {
            return effect;
        }
        else if (type == Type.PSYCHIC && pkm.checkType(Type.DARK) &&
                pkm.hasActiveMove(Moves.MIRACLEEYE)) {
            return effect;
        }
        else if (type == Type.GROUND && pkm.checkType(Type.FLYING) && gravityCounter > 0) {
            return effect;
        }

        // if trg is single type
        if (pkm.getTypes() == null) {

            // if vulnerable, retrieve and return vulnerable value
            for (Type vulnType : pkm.getType().getVulnerability().keySet()) {
                if (vulnType.getName().equals(type.getName())) {
                    effect = pkm.getType().getVulnerability().get(vulnType);
                    return effect;
                }
            }
            // if resistant, retrieve and return resistance value
            for (Type resType : pkm.getType().getResistance().keySet()) {
                if (resType.getName().equals(type.getName())) {
                    effect = pkm.getType().getResistance().get(resType);
                    return effect;
                }
            }
        }
        // if pkm is multi type
        else {

            // for each type
            for (Type trgType : pkm.getTypes()) {

                // for each vulnerability
                for (Type vulnType : trgType.getVulnerability().keySet()) {

                    // if found, multiply by effect and move to next loop
                    if (vulnType.getName().equals(type.getName())) {
                        effect *= trgType.getVulnerability().get(vulnType);
                        break;
                    }
                }

                // for each resistance
                for (Type resType : trgType.getResistance().keySet()) {

                    // if found, multiply by effect and move to next loop
                    if (resType.getName().equals(type.getName())) {
                        effect *= trgType.getResistance().get(resType);
                        break;
                    }
                }
            }

            // vulnerable and resistant cancel out
            if (effect == 0.75) {
                effect = 1.0;
            }
        }

        return effect;
    }

    private String getHitSE(double effectiveness) {

        return switch (Double.toString(effectiveness)) {
            case "0.25", "0.5" -> "hit-weak";
            case "1.5", "2.25" -> "hit-super";
            default -> "hit-normal";
        };
    }

    private double getCritical(Pokemon atk, Pokemon trg, Move move) {
        /** CRITICAL HIT REFERENCE: https://www.serebii.net/games/criticalhits.shtml (GEN II-V) **/

        int stage = 0;
        double damage = 1.5;

        if (atk.getAbility() == Ability.SNIPER) {
            damage = 3.0;
        }
        if (trg.getAbility() == Ability.SHELLARMOR || move.getMove() == Moves.DOOMDESIRE) {
            damage = 1.0;
        }
        if (trg.hasActiveMove(Moves.LUCKYCHANT)) {
            damage = 1.0;
        }

        if (move.getCrit() == 1) {
            stage++;
        }
        if (atk.getIsFocusing()) {
            stage += 2;
        }

        float chance = switch (stage) {
            case 0 -> 1f / 16f;
            case 1 -> 1f / 8f;
            case 2 -> 1f / 4f;
            case 3 -> 1f / 3f;
            default -> 0.5f;
        };

        Random r = new Random();
        return (r.nextFloat() <= chance) ? damage : 1.0;
    }
    /** END DAMAGE MOVE METHODS **/

    /**
     * POST-MOVE METHODS
     **/
    private void absorbHP(Pokemon pkm, int damage) throws InterruptedException {

        int gainedHP = (damage / 2);

        if (pkm.getHP() != pkm.getBHP()) {

            if (gainedHP + pkm.getHP() > pkm.getBHP()) {
                gainedHP = pkm.getBHP() - pkm.getHP();
                increaseHP(pkm, gainedHP);
            }
            else {
                increaseHP(pkm, gainedHP);
            }

            typeDialogue(pkm.getName() + "\nabsorbed " + gainedHP + " HP!");
        }
    }

    private void getRecoil(Pokemon pkm, Move move, int damage) throws InterruptedException {

        if (move.getMove() == Moves.EXPLOSION || move.getMove() == Moves.SELFDESTRUCT) {
            decreaseHP(pkm, pkm.getHP());
            typeDialogue(pkm.getName() + " was hit\nwith recoil damage!");
        }
        else if (move.getSelfInflict() != 0.0 && pkm.getAbility() != Ability.ROCKHEAD) {

            int recoilDamage = (int) (Math.ceil(damage * move.getSelfInflict()));
            if (recoilDamage > pkm.getHP()) {
                recoilDamage = pkm.getHP();
            }

            if (recoilDamage > 0) {
                decreaseHP(pkm, recoilDamage);
                typeDialogue(pkm.getName() + " was hit\nwith recoil damage!");
            }
        }
    }

    private void applyEffect(Pokemon atk, Pokemon trg, Move move) throws InterruptedException {

        if (trg.getHP() > 0) {
            switch (move.getMove()) {
                case BRICKBREAK:
                    if (trg.hasActiveMove(Moves.REFLECT)) {
                        trg.removeActiveMove(Moves.REFLECT);
                        typeDialogue(atk.getName() + " broke\nthe foe's shield!");
                    }
                    else if (trg.hasActiveMove(Moves.LIGHTSCREEN)) {
                        trg.removeActiveMove(Moves.LIGHTSCREEN);
                        typeDialogue(atk.getName() + " broke\nthe foe's shield!");
                    }
                    break;
                case OUTRAGE, PETALDANCE, THRASH:
                    if (!move.isWaiting() && !trg.hasStatus(Status.CONFUSE)) {
                        setStatus(trg, atk, Status.CONFUSE);
                    }
                    return;
                case WAKEUPSLAP:
                    if (trg.hasStatus(Status.SLEEP)) {
                        removeStatus(trg);
                    }
                    return;
                default:
                    break;
            }
        }

        if (move.getMove() == Moves.RAPIDSPIN && atk.hasActiveMove(Moves.LEECHSEED)) {
            atk.removeActiveMove(Moves.LEECHSEED);
            typeDialogue(atk.getName() + " broke free\nof LEECH SEED!");
        }

        if (trg.getAbility() == Ability.STATIC && move.getMType() == MoveType.PHYSICAL &&
                atk.getStatus() == null && Math.random() < 0.30) {
            setStatus(trg, atk, Status.PARALYZE);
        }

        // move causes attribute or status effect
        double probability = move.getProbability();
        if (probability != 0.0) {

            // Increase probability if user has Serene Grace
            probability *= atk.getAbility() == Ability.SERENEGRACE ? 2.0 : 1.0;

            // Chance for effect to apply
            if (new Random().nextDouble() <= probability) {
                if (move.getStats() != null) {
                    setAttribute(move.isToSelf() ? atk : trg, move.getStats(), move.getLevel());
                }
                else {
                    setStatus(atk, trg, move.getEffect());
                }
            }
        }
    }

    private boolean flinched(Pokemon pkm, Move move) throws InterruptedException {

        boolean flinched = false;

        if (move.getFlinch() != 0.0 && pkm.getAbility() != Ability.INNERFOCUS) {

            if (new Random().nextDouble() <= move.getFlinch()) {
                typeDialogue(pkm.getName() + " flinched!");
                flinched = true;
            }
        }

        return flinched;
    }
    /** END POST-MOVE METHODS **/

    /**
     * ACTIVE MOVE METHODS
     **/
    private void checkActiveMoves(Pokemon trg, Pokemon atk) throws InterruptedException {

        Iterator<Move> iterator = trg.getActiveMoves().iterator();
        while (iterator.hasNext()) {

            Move move = iterator.next();

            switch (move.getMove()) {
                case CHARGE, MAGNETRISE, MINDREADER:
                    move.setTurnCount(move.getTurnCount() - 1);
                    if (move.getTurnCount() <= 0) {
                        iterator.remove();
                        move.resetMoveTurns();
                    }
                    break;
                case DOOMDESIRE:
                    move.setTurnCount(move.getTurnCount() - 1);
                    if (move.getTurnCount() <= 0) {
                        iterator.remove();
                        move.resetMoveTurns();
                        doomDesire(trg, atk);
                    }
                    break;
                case FUTURESIGHT:
                    move.setTurnCount(move.getTurnCount() - 1);
                    if (move.getTurnCount() <= 0) {
                        iterator.remove();
                        move.resetMoveTurns();
                        futureSight(trg, atk);
                    }
                    break;
                case HEALBLOCK, MIST, REFLECT, SAFEGUARD, WRAP:
                    move.setTurnCount(move.getTurnCount() - 1);
                    if (move.getTurnCount() <= 0) {
                        iterator.remove();
                        move.resetMoveTurns();
                        typeDialogue(move.getDelay(move.getName()));
                    }
                    break;
                case LEECHSEED:
                    leechSeed(trg, atk);
                    break;
                case PERISHSONG:
                    move.setTurnCount(move.getTurnCount() - 1);
                    if (move.getTurnCount() <= 0) {
                        iterator.remove();
                        move.resetMoveTurns();
                        trg.setHP(0);
                    }
                    break;
                case SNATCH:
                    iterator.remove();
                    break;
                case WISH:
                    move.setTurnCount(move.getTurnCount() - 1);
                    if (move.getTurnCount() <= 0) {
                        iterator.remove();
                        move.resetMoveTurns();
                        typeDialogue(trg.getName() + "'s wish\ncame true!");

                        if (trg.getHP() == trg.getBHP()) {
                            typeDialogue("It had no effect!");
                        }
                        else {
                            int gainedHP = trg.getBHP() - trg.getHP();
                            int halfHP = (int) Math.floor(trg.getBHP() / 2.0);
                            if (gainedHP > halfHP) {
                                gainedHP = halfHP;
                            }

                            increaseHP(trg, gainedHP);
                            typeDialogue(trg.getName() + "\nregained health!");
                        }
                    }
                default:
                    break;
            }
        }

        if (trg.getStatus() != null && trg.getAbility() == Ability.SHEDSKIN &&
                Math.random() > 0.66) {
            removeStatus(trg);
        }

        if (trg.getAbility() == Ability.SPEEDBOOST) {
            setAttribute(trg, List.of("speed"), 1);
        }
    }

    private void futureSight(Pokemon trg, Pokemon atk) throws InterruptedException {

        Move move = new Move(Moves.FUTURESIGHT);
        int damage = dealDamage(atk, trg, move, 1);

        String hitSE = getHitSE(getEffectiveness(trg, move.getType()));

        if (hitSE.equals("hit-super")) {
            typeDialogue("It's super effective!");
        }
        else if (hitSE.equals("hit-weak")) {
            typeDialogue("It's not very effective...");
        }

        typeDialogue(trg.getName() + " took\n" + damage + " by Future Sight!");
    }

    private void doomDesire(Pokemon trg, Pokemon atk) throws InterruptedException {

        typeDialogue(trg.getName() + " took the\nDoome Desire attack!");

        Move move = new Move(Moves.DOOMDESIRE);
        int damage = dealDamage(atk, trg, move, 1);
    }

    private void leechSeed(Pokemon trg, Pokemon atk) throws InterruptedException {

        if (trg.isAlive() && atk.isAlive()) {

            int stolenHP = (int) (trg.getHP() * 0.125);
            if (stolenHP > trg.getHP()) {
                stolenHP = trg.getHP();
            }

            decreaseHP(trg, stolenHP);

            if (atk.getHP() != atk.getBHP()) {

                if (stolenHP + atk.getHP() > atk.getBHP()) {
                    stolenHP = atk.getBHP() - atk.getHP();
                    increaseHP(atk, stolenHP);
                }
                else {
                    increaseHP(atk, stolenHP);
                }
            }
            else {
                stolenHP = 0;
            }

            typeDialogue(atk.getName() + "\nabsorbed " + stolenHP + " HP!");
        }
    }
    /** END ACTIVE MOVE METHODS **/

    /**
     * STATUS DAMAGE METHODS
     **/
    private void checkStatusDamage() throws InterruptedException {
        if (fighter[0].isAlive()) {
            setStatusDamage(fighter[0], 0);
        }
        if (fighter[1].isAlive()) {
            setStatusDamage(fighter[1], 1);
        }
    }

    private void setStatusDamage(Pokemon pkm, int player) throws InterruptedException {
        // status effects reference: https://pokemon.fandom.com/wiki/Status_Effects
        // posion reference: https://bulbapedia.bulbagarden.net/wiki/Poison_(status_condition)

        if (pkm.getStatus() != null) {

            if (pkm.getStatus() == Status.POISON ||
                    pkm.getStatus() == Status.BURN) {

                pause(500);

                int damage = (int) Math.ceil((pkm.getBHP() * 0.125));
                if (damage > pkm.getHP()) {
                    damage = pkm.getHP();
                }

                gp.playSE(gp.battle_SE, pkm.getStatus().getStatus());
                pkm.setHit(true);
                decreaseHP(pkm, damage);

                pkm.getStatus().printStatus(gp, pkm.getName());
            }
            else if (pkm.getStatus() == Status.BADPOISON) {

                pause(500);

                int poisonCount = player == 0 ? playerPoison : cpuPoison;

                if (poisonCount > 15) {
                    poisonCount = 15;
                }

                int damage = (int) (poisonCount * (pkm.getBHP() * 0.16));
                if (damage > pkm.getHP()) {
                    damage = pkm.getHP();
                }

                if (damage < 1) {
                    damage = 1;
                }

                gp.playSE(gp.battle_SE, pkm.getStatus().getStatus());
                pkm.setHit(true);
                decreaseHP(pkm, damage);

                pkm.getStatus().printStatus(gp, pkm.getName());

                if (player == 0) {
                    playerPoison++;
                }
                else {
                    cpuPoison++;
                }
            }
        }
    }
    /** END STATUS MOVE METHODS **/

    /**
     * WEATHER DAMAGE METHODS
     **/
    private void checkWeatherDamage() throws InterruptedException {

        if (weatherDays != -1) {

            if (weatherDays == 0) {

                switch (weather) {
                    case CLEAR:
                        break;
                    case SUNLIGHT:
                        typeDialogue("The sunlight grew gentle!");
                        break;
                    case RAIN:
                        typeDialogue("The rain stopped falling!");
                        break;
                    case HAIL:
                        typeDialogue("The hail stopped falling!");
                        break;
                    case SANDSTORM:
                        typeDialogue("The sandstorm subsided!");
                        break;
                }

                weather = Weather.CLEAR;
                weatherDays = -1;
            }
            else {
                weatherDays--;
            }
        }

        switch (weather) {
            case CLEAR:
                break;
            case SUNLIGHT:
                gp.playSE(gp.battle_SE, "sunlight");
                typeDialogue("The sunlight continues\nto shine intensely!");
                break;
            case RAIN:
                gp.playSE(gp.battle_SE, "rain");
                typeDialogue("Rain continues to fall!");
                break;
            case HAIL:
                gp.playSE(gp.battle_SE, "hail");
                typeDialogue("Hail continues to fall!");

                if (fighter[0].isAlive() && !fighter[0].checkType(Type.ICE)) {
                    setWeatherDamage(fighter[0]);
                }
                if (fighter[1].isAlive() && !fighter[1].checkType(Type.ICE)) {
                    setWeatherDamage(fighter[1]);
                }

                break;
            case SANDSTORM:
                gp.playSE(gp.battle_SE, "sandstorm");
                typeDialogue("The sandstorm continues to rage!");

                if (fighter[0].isAlive() &&
                        !fighter[0].checkType(Type.ROCK) &&
                        !fighter[0].checkType(Type.STEEL) &&
                        !fighter[0].checkType(Type.GROUND)) {
                    setWeatherDamage(fighter[0]);
                }
                if (fighter[1].isAlive() &&
                        !fighter[1].checkType(Type.ROCK) &&
                        !fighter[1].checkType(Type.STEEL) &&
                        !fighter[1].checkType(Type.GROUND)) {
                    setWeatherDamage(fighter[1]);
                }

                break;
        }
    }

    private void setWeatherDamage(Pokemon pkm) throws InterruptedException {

        int damage = (int) Math.ceil((pkm.getHP() * 0.0625));
        if (damage > pkm.getHP()) {
            damage = pkm.getHP();
        }

        gp.playSE(gp.battle_SE, "hit-normal");
        pkm.setHit(true);
        decreaseHP(pkm, damage);

        typeDialogue(pkm.getName() + " was hurt\nby the " + weather.toString().toLowerCase() + "!");
    }
    /** END WEATHER DAMAGE METHODS **/

    /**
     * DELAYED TURN
     **/
    private void getDelayedTurn() throws InterruptedException {

        playerDamageTaken = 0;
        cpuDamageTaken = 0;

        if (gravityCounter > 0) {
            gravityCounter--;

            if (gravityCounter == 0) {
                typeDialogue("Intense gravity wore off!");
            }
        }

        // RESET NON-DELAYED MOVES
        int delay = getDelay();

        if (delay == 0) {
            if (playerMove != null) {
                playerMove.resetMoveTurns();
            }
            playerMove = null;

            if (cpuMove != null) {
                cpuMove.resetMoveTurns();
            }
            cpuMove = null;
        }
        else if (delay == 1) {
            if (cpuMove != null) {
                cpuMove.resetMoveTurns();
            }
            cpuMove = null;
        }
        else if (delay == 2) {
            if (playerMove != null) {
                playerMove.resetMoveTurns();
            }
            playerMove = null;
        }

        // Single player
        if (cpu) {
            getCPUMove();

            // Player is waiting, skip option select
            if (delay == 1 || delay == 3) {
                setQueue();
                fightStage = fight_Start;
            }
            else {
                running = false;
                fightStage = fight_Start;
                gp.ui.player = 0;
                gp.ui.battleState = gp.ui.battle_Options;
            }
        }
        // Multiplayer
        else {
            // Both players are waiting, skip option select
            if (delay == 3) {
                setQueue();
                fightStage = fight_Start;
            }
            else {
                running = false;
                fightStage = fight_Start;

                // Player 2 selects option if player 1 is waiting
                gp.ui.player = delay == 1 ? 1 : 0;
                gp.ui.battleState = gp.ui.battle_Options;
            }
        }
    }

    /**
     * CHECK WINNER METHODS
     **/
    private boolean hasWinningPokemon() {

        if (battleQueue.contains(queue_PlayerSwap) || battleQueue.contains(queue_CPUSwap)) {
            return false;
        }

        boolean hasWinningPokemon = false;

        if (fighter[0].getHP() <= 0) {

            // Reduce PP if Grudge is active
            if (fighter[0].hasActiveMove(Moves.GRUDGE)) {
                if (cpuMove != null && cpuMove.getMove() != Moves.STRUGGLE) {
                    cpuMove.setPP(0);
                }
            }

            fighter[0].setAlive(false);
            playerMove = null;
            playerFurryCutterCount = 10;
            playerPoison = 1;
        }
        if (fighter[1].getHP() <= 0) {

            // Reduce PP if Grudge is active
            if (fighter[1].hasActiveMove(Moves.GRUDGE)) {
                if (playerMove != null && playerMove.getMove() != Moves.STRUGGLE) {
                    playerMove.setPP(0);
                }
            }

            fighter[1].setAlive(false);
            cpuMove = null;
            cpuFurryCutterCount = 10;
            cpuPoison = 1;
        }

        // TIE
        if (!fighter[0].isAlive() && !fighter[1].isAlive()) {
            winner = 2;
            loser = 2;
            hasWinningPokemon = true;
        }
        // PLAYER 2 WINS
        else if (!fighter[0].isAlive()) {
            otherFighters.remove(fighter[0]);
            winner = 1;
            loser = 0;
            hasWinningPokemon = true;
        }
        // PLAYER 1 WINS
        else if (!fighter[1].isAlive()) {
            winner = 0;
            loser = 1;
            hasWinningPokemon = true;
        }

        return hasWinningPokemon;
    }

    public void getWinningPokemon() throws InterruptedException {

        // TRAINER 1 WINNER
        if (winner == 0) {
            if (playerMove != null) {
                playerMove.resetMoveTurns();
            }
            playerMove = null;
            fighter[0].clearProtection();
            fighter[0].setCanEscape(true);

            gp.playSE(gp.faint_SE, fighter[1].toString());
            typeDialogue(fighter[1].getName() + " fainted!");

            if (!pcBattle) {
                gainEXP();
            }
        }
        // TRAINER 2 WINNER
        else if (winner == 1) {
            if (cpuMove != null) {
                cpuMove.resetMoveTurns();
            }
            cpuMove = null;
            fighter[1].clearProtection();
            fighter[1].setCanEscape(true);

            gp.playSE(gp.faint_SE, fighter[0].toString());
            typeDialogue(fighter[0].getName() + " fainted!");
        }
        // TIE GAME
        else if (winner == 2) {
            playerMove = null;
            cpuMove = null;

            gp.playSE(gp.faint_SE, fighter[0].toString());
            typeDialogue(fighter[0].getName() + " fainted!");

            gp.playSE(gp.faint_SE, fighter[1].toString());
            typeDialogue(fighter[1].getName() + " fainted!");
        }
    }

    /**
     * EXP METHODS
     **/
    private void gainEXP() throws InterruptedException {

        // WILD BATTLE
        if (trainer == null) {
            gp.stopMusic();
            gp.startMusic(1, 2);
        }

        int gainedXP = (int) Math.ceil((double) calculateEXPGain() / (otherFighters.size() + 1));
        typeDialogue(fighter[0].getName() + " gained\n" + gainedXP + " Exp. Points!", false);

        int xp = fighter[0].getXP() + gainedXP;
        int xpTimer = (int) Math.ceil(2500.0 / (double) gainedXP);
        increaseEXP(fighter[0], xp, xpTimer);

        // Shared EXP
        for (Pokemon p : otherFighters) {
            xp = p.getXP() + gainedXP;
            typeDialogue(p.getName() + " gained\n" + gainedXP + " Exp. Points!", true);
            increaseEXP(p, xp, 0);
        }

        // Clear list of exp share fighters
        otherFighters.clear();
    }

    private int calculateEXPGain() {
        // EXP FORMULA REFERENCE (GEN I-IV): https://bulbapedia.bulbagarden.net/wiki/Experience

        int exp;

        double b = fighter[loser].getXPYield();
        double L = fighter[loser].getLevel();
        double s = 1.0;
        double e = 1.0;
        double a = battleMode == trainerBattle ? 1.5 : 1.0;
        double t = 1.0;

        exp = (int) (Math.floor((b * L) / 7) * Math.floor(1 / s) * e * a * t);

        return exp;
    }

    private void increaseEXP(Pokemon p, int xp, int timer) throws InterruptedException {

        while (p.getXP() < xp) {

            p.setXP(p.getXP() + 1);

            // Pause to show exp gain
            pause(timer);

            // FIGHTER LEVELED UP
            if (p.getXP() >= p.getBXP() + p.getNXP()) {

                gp.pauseMusic();
                gp.playSE(gp.battle_SE, "level-up");

                p.levelUp();
                gp.ui.battleState = gp.ui.battle_LevelUp;

                gp.playSE(gp.battle_SE, "upgrade");
                typeDialogue(p.getName() + " grew to\nLv. " +
                        (p.getLevel()) + "!", true);

                gp.ui.battleState = gp.ui.battle_Dialogue;

                checkNewMove(p);

                gp.playMusic();
            }
        }
    }

    private void checkNewMove(Pokemon pokemon) throws InterruptedException {

        newMove = pokemon.getNewMove();
        if (newMove != null) {

            if (pokemon.learnMove(newMove)) {

                gp.playSE(gp.battle_SE, "upgrade");
                typeDialogue(pokemon.getName() + " learned\n" +
                        newMove.getName() + "!", true);

                newMove = null;
            }
            else {
                gp.playMusic();

                typeDialogue(pokemon.getName() + " is trying to\nlearn " + newMove.getName() + ".", true);
                typeDialogue("But, " + pokemon.getName() + " can't learn\nmore than four moves.", true);
                typeDialogue("Delete a move to make\nroom for " + newMove.getName() + "?", false);

                gp.ui.battleState = gp.ui.battle_Confirm;
                waitForKeyPress();
                getMoveAnswer(pokemon);
            }
        }
    }

    private void getMoveAnswer(Pokemon pokemon) throws InterruptedException {

        if (gp.keyH.aPressed) {
            gp.keyH.aPressed = false;

            if (fightStage == fight_Evolve) {
                gp.ui.battleState = gp.ui.battle_Evolve;
            }
            else {
                gp.ui.battleState = gp.ui.battle_Dialogue;
            }

            if (gp.ui.commandNum == 0) {

                for (int i = 0; i < gp.player.pokeParty.size(); i++) {
                    if (gp.player.pokeParty.get(i) == pokemon) {
                        gp.ui.fighterNum = i;
                        break;
                    }
                }

                gp.gameState = gp.pauseState;
                gp.ui.pauseState = gp.ui.pause_Party;
                gp.ui.partyState = gp.ui.party_MoveSwap;

                while (oldMove == null && !gp.keyH.bPressed) {
                    pause(5);
                }

                if (oldMove != null) {

                    if (playerMove == oldMove) {
                        playerMove = null;
                        fighter[0].clearProtection();
                    }

                    typeDialogue("1, 2, and.. .. ..\nPoof!", true);
                    typeDialogue(pokemon.getName() + " forgot\n" + oldMove.getName() + ".", true);
                    typeDialogue("And...", true);

                    gp.pauseMusic();
                    gp.playSE(gp.battle_SE, "upgrade");

                    typeDialogue(pokemon.getName() + " learned\n" + newMove.getName() + "!", true);

                    gp.playMusic();
                }
                else {
                    typeDialogue(pokemon.getName() + " did not learn\n" + newMove.getName() + ".", true);
                }
            }
            else {
                typeDialogue(pokemon.getName() + " did not learn\n" + newMove.getName() + ".", true);
            }

            newMove = null;
            oldMove = null;
            gp.ui.commandNum = 0;
        }
        else if (gp.keyH.bPressed) {
            gp.keyH.bPressed = false;

            if (fightStage == fight_Evolve) {
                gp.ui.battleState = gp.ui.battle_Evolve;
            }
            else {
                fightStage = fight_Start;
                gp.ui.battleState = gp.ui.battle_Dialogue;
            }

            typeDialogue(pokemon.getName() + " did not learn\n" + newMove.getName() + ".", true);

            newMove = null;
            gp.ui.commandNum = 0;
        }
    }
    /** END GAIN EXP METHODS **/

    /**
     * GET WINNING TRAINER
     **/
    private void getWinningTrainer() throws InterruptedException {

        // WILD BATTLE
        if (trainer == null) {

            // TRAINER 1 WINNER
            if (winner == 0) {
                pause(1000);
                fightStage = fight_Evolve;
            }
            // WILD POKEMON WINNER
            else if (winner == 1) {

                // TRAINER 1 HAS MORE POKÉMON
                if (gp.player.hasPokemon()) {

                    winner = -1;
                    running = false;

                    gp.gameState = gp.pauseState;
                    gp.ui.pauseState = gp.ui.pause_Party;
                    gp.ui.partyState = gp.ui.party_Main_Select;
                }
                // TRAINER 1 OUT OF POKÉMON
                else {
                    announceWinner();
                }
            }
        }
        // TRAINER BATTLE
        else {
            gp.ui.battleState = gp.ui.battle_Dialogue;

            // TRAINER 1 WINNER
            if (winner == 0) {

                // TRAINER 2 HAS MORE POKÉMON
                if (trainer.hasPokemon()) {

                    winner = -1;

                    // CPU battle
                    if (cpu) {

                        // Get new CPU pokemon
                        newFighter[1] = getCPUFighter();
                        battleQueue.add(queue_CPUSwap);

                        // If trainer 1 has more than 1 pokemon, and option set to Shift...
                        // ...ask trainer 1 if they want to swap
                        if (gp.player.getAvailablePokemon() > 1 && shift) {
                            typeDialogue("Trainer " + trainer.name + " is about\nto sent out " +
                                    newFighter[1].getName() + "!", true);

                            typeDialogue("Will " + gp.player.name + " swap\nPokemon?", false);
                            gp.ui.battleState = gp.ui.battle_Confirm;

                            waitForKeyPress();
                            getSwapAnswer();
                        }
                        // Trainer 1 has only 1 pokemon or option not set to Shift
                        else {
                            gp.ui.resetFighterPositions();
                            gp.ui.battleState = gp.ui.battle_Dialogue;
                        }
                    }
                    // Multiplayer battle
                    else {
                        running = false;

                        // Get new trainer 2 pokemon
                        gp.gameState = gp.pauseState;
                        gp.ui.player = 1;
                        gp.ui.partyDialogue = "Choose a POKéMON.";
                        gp.ui.pauseState = gp.ui.pause_Party;
                        gp.ui.partyState = gp.ui.party_Main_Select;
                    }
                }
                // TRAINER 2 OUT OF POKÉMON
                else {
                    announceWinner();
                }
            }
            // TRAINER 2 WINNER
            else if (winner == 1) {

                // TRAINER 1 HAS MORE POKÉMON
                if (gp.player.hasPokemon()) {

                    winner = -1;
                    running = false;

                    // Get new trainer 1 pokemon
                    gp.gameState = gp.pauseState;
                    gp.ui.player = 0;
                    gp.ui.partyDialogue = "Choose a POKéMON.";
                    gp.ui.pauseState = gp.ui.pause_Party;
                    gp.ui.partyState = gp.ui.party_Main_Select;
                }
                // TRAINER 1 OUT OF POKÉMON
                else {
                    announceWinner();
                }
            }
            // TIE GAME
            else if (winner == 2) {

                // TRAINER 1 AND 2 HAVE MORE POKÉMON
                if (gp.player.hasPokemon() && trainer.hasPokemon()) {

                    winner = -1;
                    running = false;
                    gp.ui.player = 1;

                    // Get new CPU pokemon
                    if (cpu) {
                        newFighter[1] = getCPUFighter();
                        battleQueue.add(queue_CPUSwap);
                        gp.ui.player = 0;
                    }

                    gp.gameState = gp.pauseState;
                    gp.ui.partyDialogue = "Choose a POKéMON.";
                    gp.ui.pauseState = gp.ui.pause_Party;
                    gp.ui.partyState = gp.ui.party_Main_Select;
                }
                // ONLY TRAINER 1 HAS MORE POKÉMON
                else if (gp.player.hasPokemon()) {
                    winner = 0;
                    announceWinner();
                }
                // ONLY TRAINER 2 HAS MORE POKÉMON
                else if (trainer.hasPokemon()) {
                    winner = 1;
                    announceWinner();
                }
                // BOTH TRAINERS DEFEATED
                else {
                    typeDialogue("It's a draw!", true);
                    pause(500);
                    endBattle();
                }
            }
        }
    }

    /**
     * GET NEXT CPU FIGHTER METHODS
     **/
    private Pokemon getCPUFighter() {

        Pokemon nextFighter = null;

        if (trainer == null || trainer.skillLevel == trainer.skill_rookie) {
            nextFighter = getCPUFighter_Next();
        }
        else if (trainer.skillLevel == trainer.skill_smart) {
            nextFighter = getCPUFighter_Power();
        }
        else if (trainer.skillLevel == trainer.skill_elite) {
            nextFighter = getCPUFighter_Best();
        }

        return nextFighter;
    }

    private Pokemon getCPUFighter_Next() {

        Pokemon nextFighter;

        if (fighter == null) {
            nextFighter = trainer.pokeParty.getFirst();
        }
        else {
            int index = trainer.pokeParty.indexOf(fighter[1]);
            if (index < 0 || index + 1 == trainer.pokeParty.size()) {
                nextFighter = null;
            }
            else {
                nextFighter = trainer.pokeParty.get(index + 1);
            }
        }

        return nextFighter;
    }

    private Pokemon getCPUFighter_Power() {

        Pokemon bestFighter;

        Map<Pokemon, Integer> fighters = new HashMap<>();
        Map<Move, Integer> powerMoves = new HashMap<>();

        for (Pokemon p : trainer.pokeParty) {

            if (p.isAlive()) {

                for (Move m : p.getMoveSet()) {

                    if (m.getPower() > 0 && m.getPP() != 0) {

                        double power = getPower(m, p, fighter[0]);
                        double type = getEffectiveness(fighter[0], m.getType());

                        // CALCULATE POWER OF EACH MOVE
                        int result = (int) (power * type);

                        powerMoves.put(m, result);
                    }
                }

                if (!powerMoves.isEmpty()) {

                    int power = Collections.max(powerMoves.entrySet(),
                            Comparator.comparingInt(Map.Entry::getValue)).getValue();

                    fighters.put(p, power);
                }

                powerMoves.clear();
            }
        }

        if (fighters.isEmpty()) {

            // loop through party to find highest level Pokémon
            for (Pokemon p : trainer.pokeParty) {
                fighters.put(p, p.getLevel());
            }
        }

        bestFighter = Collections.max(fighters.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();

        return bestFighter;
    }

    private Pokemon getCPUFighter_Best() {

        Pokemon bestFighter;

        Map<Pokemon, Integer> fighters = new HashMap<>();
        Map<Move, Integer> damageMoves = new HashMap<>();

        for (Pokemon p : trainer.pokeParty) {

            if (p.isAlive()) {

                for (Move m : p.getMoveSet()) {

                    if (m.getPower() > 0 && m.getPP() != 0) {
                        int damage = calculateDamage(p, fighter[0], m, 1);
                        damageMoves.put(m, damage);
                    }
                }

                if (!damageMoves.isEmpty()) {

                    int damage = Collections.max(damageMoves.entrySet(),
                            Comparator.comparingInt(Map.Entry::getValue)).getValue();

                    fighters.put(p, damage);
                }

                damageMoves.clear();
            }
        }

        if (fighters.isEmpty()) {
            // loop through party to find highest level Pokémon
            for (Pokemon p : trainer.pokeParty) {
                fighters.put(p, p.getLevel());
            }
        }

        bestFighter = Collections.max(fighters.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();

        return bestFighter;
    }

    /**
     * END GET NEXT CPU FIGHTER METHODS
     **/

    private void getSwapAnswer() {

        if (gp.keyH.aPressed) {
            gp.keyH.aPressed = false;

            gp.ui.resetFighterPositions();

            gp.ui.battleState = gp.ui.battle_Dialogue;

            if (gp.ui.commandNum == 0) {
                running = false;
                gp.gameState = gp.pauseState;
                gp.ui.pauseState = gp.ui.pause_Party;
                gp.ui.partyState = gp.ui.party_Main_Select;
            }

            gp.ui.commandNum = 0;
        }
    }

    /**
     * ANNOUNCE WINNER
     **/
    private void announceWinner() throws InterruptedException {

        // PC BATTLE
        if (pcBattle) {

            if (winner == 0) {
                typeDialogue("Player defeated\nTrainer " + trainer.name + "!", true);
            }
            else {
                typeDialogue("Player defeated\nTrainer " + gp.player.name + "!", true);
            }

            gp.player.healPokemonParty();
            gp.player_2.healPokemonParty();

            endBattle();
        }
        // CPU BATTLE
        else {

            // TRAINER 1 VICTORY
            if (winner == 0) {

                gp.ui.fighter_two_X = gp.screenWidth + gp.tileSize;

                gp.ui.battleState = gp.ui.battle_End;

                gp.stopMusic();
                gp.startMusic(1, 5);

                typeDialogue("Player defeated\nTrainer " + trainer.name + "!", true);

                trainer.battleIconTimer = 0;
                trainer.speed = trainer.defaultSpeed;
                trainer.isDefeated = true;
                trainer.hasBattle = false;

                int dialogueSet = trainer.dialogueSet + 1;
                typeDialogue(trainer.dialogues[dialogueSet][0], true);

                int moneyEarned = calculateMoneyEarned();
                gp.player.money += moneyEarned;
                typeDialogue(gp.player.name + " got $" + moneyEarned + "\nfor winning!", true);

                fightStage = fight_Evolve;
            }
            // TRAINER 2 VICTORY
            else {
                typeDialogue(gp.player.name + " is out\nof Pokemon!", true);
                pause(1000);

                endBattle();
            }
        }
    }

    /**
     * CALCULATE MONEY
     **/
    private int calculateMoneyEarned() {
        /** MONEY EARNED FORMULA REFERENCE (ALL GEN): https://bulbapedia.bulbagarden.net/wiki/Prize_money **/

        int payout;

        int level = trainer.pokeParty.getLast().getLevel();
        int base = trainer.trainerClass;
        payout = base * level;

        return payout;
    }

    /**
     * CAPTURE METHODS
     **/
    private void throwPokeball() throws InterruptedException {

        if (trainer == null) {

            typeDialogue(gp.player.name + " used a\n" + ballUsed.name + "!", false);
            playSE(gp.battle_SE, "ball-throw");
            gp.ui.isFighterCaptured = true;
            playSE(gp.battle_SE, "ball-open");
            playSE(gp.battle_SE, "ball-bounce");

            for (int i = 0; i < 3; i++) {
                playSE(gp.battle_SE, "ball-shake");
                Thread.sleep(800);
            }

            if (isCaptured()) {
                capturePokemon();
            }
            else {
                gp.playSE(gp.battle_SE, "ball-open");
                gp.ui.isFighterCaptured = false;

                typeDialogue("Oh no!\n" + fighter[1].getName() + " broke free!", true);
                setQueue();
                fightStage = fight_Start;
            }
        }
        else {
            typeDialogue("You can't use\nthat here!", true);
            gp.ui.battleState = gp.ui.battle_Options;
            running = false;
        }

        ballUsed = null;
    }

    private boolean isCaptured() {
        /** CATCH RATE FORMULA REFERENCE (GEN IV): https://bulbapedia.bulbagarden.net/wiki/Catch_rate#Capture_method_(Generation_III-IV) **/

        boolean isCaptured = false;

        double catchOdds;
        double maxHP;
        double hp;
        double catchRate;
        double statusBonus = 1.0;

        maxHP = fighter[1].getBHP();
        if (maxHP == 0.0) {
            maxHP = 1.0;
        }
        hp = fighter[1].getHP();
        if (hp == 0.0) {
            hp = 1.0;
        }
        catchRate = fighter[1].getCatchRate();

        if (fighter[1].getStatus() != null) {
            switch (fighter[1].getStatus()) {
                case Status.PARALYZE, Status.POISON, Status.BADPOISON, Status.BURN:
                    statusBonus = 1.5;
                    break;
                case Status.FREEZE, Status.SLEEP:
                    statusBonus = 2.0;
                    break;
                case Status.CONFUSE:
                    break;
            }
        }

        catchOdds = ((((3 * maxHP) - (2 * hp)) / (3 * maxHP)) * catchRate * statusBonus);

        Random r = new Random();
        int roll = r.nextInt(ballUsed.catchProbability + 1);

        if (roll <= catchOdds) {
            isCaptured = true;
        }

        return isCaptured;
    }

    private void capturePokemon() throws InterruptedException {

        gp.stopMusic();
        gp.startMusic(1, 3);

        fighter[1].resetStats();
        fighter[1].resetStatStages();
        fighter[1].resetMoveTurns();
        fighter[1].clearActiveMoves();
        fighter[1].resetMoves();
        fighter[1].setBall(ballUsed);
        typeDialogue("Gotcha!\n" + fighter[1].getName() + " was caught!", true);

        if (!gp.player.ownsPokemon(fighter[1].getIndex())) {
            gp.player.dexOwn++;
        }

        if (gp.player.pokeParty.size() < 6) {
            gp.player.pokeParty.add(fighter[1]);
            typeDialogue(fighter[1].getName() + " was added\nto your party!", true);
        }
        else {
            boolean found = false;
            for (int i = 0; i < gp.player.pcParty.length; i++) {
                for (int c = 0; c < gp.player.pcParty[i].length; c++) {
                    if (gp.player.pcParty[i][c] != null) {
                        gp.player.pcParty[i][c] = fighter[1];
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                typeDialogue(fighter[1].getName() + " was sent\nto your PC!", true);
            }
            else {
                typeDialogue("There is no more room in your PC!\n" + fighter[1].getName() + " was released!", true);
            }
        }

        endBattle();
    }
    /** END CAPTURE METHODS **/

    /**
     * ESCAPE BATTLE
     **/
    public void escapeBattle() throws InterruptedException {
        /** ESCAPE FORMULA REFERENCE: https://bulbapedia.bulbagarden.net/wiki/Escape **/

        boolean escape = false;

        // WILD BATTLE
        if (trainer == null) {
            if (fighter[0].isAlive() && !fighter[0].getCanEscape()) {
                typeDialogue("Oh no!\nYou can't escape!", true);
                setQueue();
                fightStage = fight_Start;
                return;
            }

            double playerSpeed = fighter[0].getSpeed();
            double wildSpeed = fighter[1].getSpeed();

            if (playerSpeed > wildSpeed) {
                escape = true;
            }
            else {
                escapeAttempts++;
                double attempts = escapeAttempts;
                double escapeOdds = ((((playerSpeed * 128) / wildSpeed) + 30) * attempts);

                Random r = new Random();
                int roll = r.nextInt(255 + 1);

                if (roll <= escapeOdds) {
                    escape = true;
                }
            }

            if (escape) {
                gp.playSE(gp.battle_SE, "run");
                typeDialogue("Got away safely!", true);
                endBattle();
            }
            else {
                typeDialogue("Oh no!\nYou can't escape!", true);
                setQueue();
                fightStage = fight_Start;
            }
        }
        // PC BATTLE
        else if (pcBattle) {
            if (gp.ui.player == 0) {
                typeDialogue("Trainer " + gp.player.name + " has fled\nthe battle!", true);
            }
            else {
                typeDialogue("Trainer " + trainer.name + " has fled\nthe battle!", true);
            }

            gp.player.healPokemonParty();
            trainer.healPokemonParty();

            endBattle();
        }
        // CPU TRAINER BATTLE
        else {
            typeDialogue("You can't flee\na trainer battle!", true);
            gp.ui.battleState = gp.ui.battle_Options;
            running = false;
        }
    }

    /**
     * EVOLVE METHODS
     **/
    private void checkEvolve() throws InterruptedException {

        gp.stopMusic();

        for (int i = 0; i < gp.player.pokeParty.size(); i++) {
            if (gp.player.pokeParty.get(i).canEvolve()) {

                Pokemon oldEvolve = gp.player.pokeParty.get(i);
                Pokemon newEvolve = oldEvolve.evolve();

                gp.ui.evolvePokemon = oldEvolve;
                gp.ui.battleState = gp.ui.battle_Evolve;

                gp.playSE(gp.cry_SE, gp.se.getFile(3, oldEvolve.toString()));

                typeDialogue("What?\n" + oldEvolve.getName() + " is evolving?");

                startEvolve(oldEvolve, newEvolve, i);

                break;
            }
        }

        endBattle();
    }

    private void startEvolve(Pokemon oldEvolve, Pokemon newEvolve, int index) throws InterruptedException {

        gp.startMusic(1, 0);
        pause(1000);

        for (int i = 0; i < 30; i++) {

            if (i % 2 == 0) {
                gp.ui.evolvePokemon = oldEvolve;
            }
            else {
                gp.ui.evolvePokemon = newEvolve;
            }

            pause(500);

            if (gp.keyH.bPressed) {
                gp.ui.evolvePokemon = oldEvolve;
                gp.stopMusic();
                typeDialogue("Oh?\n" + oldEvolve.getName() + " stopped evolving...", true);
                return;
            }
        }
        gp.stopMusic();
        playSE(3, newEvolve.toString());

        gp.player.pokeParty.set(index, newEvolve);
        gp.ui.evolvePokemon = newEvolve;

        gp.startMusic(1, 2);
        typeDialogue("Congratulations! Your " + oldEvolve.getName() +
                "\nevolved into " + newEvolve.getName() + "!", true);

        gp.player.trackSeenPokemon(newEvolve);
        gp.player.trackOwnPokemon(newEvolve.getIndex());

        checkNewMove(newEvolve);

    }
    /** END EVOLVE METHODS **/

    /**
     * END BATTLE METHODS
     **/
    public void endBattle() {
        gp.stopMusic();
        gp.setupMusic();

        for (Pokemon p : gp.player.pokeParty) {
            p.resetValues();
        }

        if (trainer != null) {
            for (Pokemon p : trainer.pokeParty) {
                p.resetValues();
            }
        }

        gp.ui.isFighterCaptured = false;
        gp.ui.player = 0;
        gp.ui.commandNum = 0;

        resetValues();

        fightStage = fight_Encounter;
        gp.gameState = gp.playState;
    }

    private void resetValues() {
        battleQueue.clear();

        active = false;
        running = false;
        pcBattle = false;
        cpu = true;
        battleEnd = true;

        trainer = null;
        fighter[0] = null;
        fighter[1] = null;
        newFighter[0] = null;
        newFighter[1] = null;
        otherFighters.clear();

        playerMove = null;
        cpuMove = null;
        newMove = null;
        playerFurryCutterCount = 10;
        cpuFurryCutterCount = 10;
        playerDamageTaken = 0;
        cpuDamageTaken = 0;
        playerPoison = 1;
        cpuPoison = 1;
        gravityCounter = 0;
        ballUsed = null;

        weather = Weather.CLEAR;
        weatherDays = -1;

        winner = -1;
        loser = -1;

        escapeAttempts = 0;

        gp.player.canMove = true;
        gp.particleList.clear();
    }
    /** END BATTLE METHODS **/

    /**
     * MISC METHODS
     **/
    public void typeDialogue(String dialogue) throws InterruptedException {

        gp.ui.battleDialogue = "";

        for (char letter : dialogue.toCharArray()) {
            gp.ui.battleDialogue += letter;
            pause(textSpeed);
        }

        pause(800);

        gp.ui.battleDialogue = "";
    }

    public void typeDialogue(String dialogue, boolean canSkip) throws InterruptedException {

        gp.ui.battleDialogue = "";

        for (char letter : dialogue.toCharArray()) {
            gp.ui.battleDialogue += letter;
            pause(textSpeed);
        }

        if (canSkip) {
            gp.ui.canSkip = true;
            waitForKeyPress();
            gp.ui.canSkip = false;
            gp.keyH.aPressed = false;
        }
    }

    private void waitForKeyPress() throws InterruptedException {
        while (!gp.keyH.aPressed) {
            pause(5);
        }
    }

    private void increaseHP(Pokemon pkm, int gainedHP) throws InterruptedException {

        int hpTimer = getHPTimer(gainedHP);

        int newHP = pkm.getHP() + gainedHP;
        if (newHP > pkm.getBHP()) {
            newHP = pkm.getBHP();
        }
        while (pkm.getHP() < newHP) {
            pkm.setHP(pkm.getHP() + 1);
            pause(hpTimer);
        }
    }

    private void decreaseHP(Pokemon pkm, int lostHP) throws InterruptedException {

        int hpTimer = getHPTimer(lostHP);

        int newHP = pkm.getHP() - lostHP;
        if (newHP < 0) {
            newHP = 0;
        }
        while (newHP < pkm.getHP()) {
            pkm.setHP(pkm.getHP() - 1);
            pause(hpTimer);
        }
    }

    private int getHPTimer(int damage) {
        /** FORMULA GENERATED FROM LINEAR REGRESSION CALCULATOR **/


        int hpTimer;

        if (damage < 10) {
            hpTimer = 70;
        }
        else {
            hpTimer = (int) ((-0.2405 * damage) + 71);
            if (hpTimer < 7) {
                hpTimer = 7;
            }
        }

        return hpTimer;
    }

    private void pause(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void playSE(int cat, String name) throws InterruptedException {

        gp.playSE(cat, name);

        double soundDuration = gp.se.getSoundDuration(cat, gp.se.getFile(cat, name));
        soundDuration /= 0.06;

        Thread.sleep((int) soundDuration);
    }
    /** END MISC METHODS **/
}