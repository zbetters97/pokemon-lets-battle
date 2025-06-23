package pokemon;

import application.GamePanel;
import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Nature;
import properties.Status;
import properties.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Pokemon {
    /** STAT, ABILITY, AND MOVE REFERECE: https://www.serebii.net/pokemon/ **/
    /** EXP & EV REFERENCE: https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_effort_value_yield_in_Generation_IV **/
    /** XP GROWTH REFERENCE: https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_experience_type **/
    /**
     * CATCH RATE REFERENCE: https://bulbapedia.bulbagarden.net/wiki/List_of_Pokémon_by_catch_rate
     **/
    public enum Pokedex {
        BULBUSAUR, IVYSAUR, VENUSAUR,
        CHARMANDER, CHARMELEON, CHARIZARD,
        SQUIRTLE, WARTORTLE, BLASTOISE,
        PIKACHU, RAICHU,
        ZUBAT, GOLBAT,
        GROWLITHE, ARCANINE,
        ABRA, KADABRA, ALAKAZAM,
        MACHOP, MACHOKE, MACHAMP,
        GEODUDE, GRAVELER, GOLEM,
        PONYTA, RAPIDASH,
        GASTLY, HAUNTER, GENGAR,
        HITMONLEE, HITMONCHAN,
        HORSEA, SEADRA,
        MAGIKARP, GYARADOS,
        LAPRAS, SNORLAX,
        ARTICUNO, ZAPDOS, MOLTRES,
        DRATINI, DRAGONAIR, DRAGONITE,
        MEWTWO, MEW,
        CHIKORITA, BAYLEEF, MEGANIUM,
        CYNDAQUIL, QUILAVA, TYPHLOSION,
        TOTODILE, CROCONAW, FERALIGATR,
        CROBAT,
        KINGDRA,
        RAIKOU, ENTEI, SUICUNE,
        LUGIA, HOOH, CELEBI,
        TREECKO, GROVYLE, SCEPTILE,
        TORCHIC, COMBUSKEN, BLAZIKEN,
        MUDKIP, MARSHTOMP, SWAMPERT,
        POOCHYENA, MIGHTYENA,
        ZIGZAGOON, LINOONE,
        RALTS, KIRLIA, GARDEVOIR,
        NINCADA, NINJASK, SHEDINJA,
        WHISMUR, LOUDRED, EXPLOUD,
        SPHEAL, SEALEO, WALREIN,
        BAGON, SHELGON, SALAMENCE,
        BELDUM, METANG, METAGROSS,
        KYOGRE, GROUDON, RAYQUAZA,
        JIRACHI, DEOXYS
    }

    public enum Growth {
        MEDIUMFAST, ERATIC, FLUCTUATING, MEDIUMSLOW, FAST, SLOW
    }

    public enum Protection {
        NONE, BOUNCE, DIG, DIVE, FLY, PHANTOMFORCE, SHADOWFORCE, SKYDROP
    }

    protected Pokedex id;
    protected int index;
    protected String name, nickname;
    protected BufferedImage frontSprite, backSprite, menuSprite;
    protected char sex;
    protected Type type = null;
    protected List<Type> types = null;
    protected Nature nature;
    protected Ability ability;
    protected Growth growth;
    protected Status status = null;
    protected Protection protection = Protection.NONE;
    protected Entity ball = null, item = null;

    protected int level, hp, chp, ev, xp, cxp, nxp, xpYield, evolveLevel, catchRate;
    protected int hpIV, attackIV, defenseIV, spAttackIV, spDefenseIV, speedIV;
    protected int baseHP, baseAttack, baseDefense, baseSpAttack, baseSpDefense, baseSpeed;
    protected double attack, defense, spAttack, spDefense, speed, accuracy, evasion;
    protected double cAttack, cDefense, cSpAttack, cSpDefense, cSpeed;
    protected int speedStg, attackStg, defenseStg, spAttackStg, spDefenseStg, accuracyStg, evasionStg;

    protected int statusCounter, statusLimit;
    protected boolean isAlive = true, attacking = false, statChanging = false, hit = false;

    protected List<Move> moveset, activeMoves;
    protected Map<Integer, Moves> moveLevels;

    /**
     * CONSTRUCTORS
     **/
    protected Pokemon(int index, String name, int level, Entity ball,
                      int hp, int attack, int defense, int spAttack, int spDefense, int speed,
                      int evolveLevel, int xpYield, int ev, Growth growth, int catchRate) {

        this.index = index;
        this.name = name;

        frontSprite = setup("/pokedex/front/" + name.toLowerCase(), 48 * 5, 48 * 5);
        backSprite = setup("/pokedex/back/" + name.toLowerCase(), 48 * 5, 48 * 5);
        menuSprite = setup("/pokedex/menu/" + name.toLowerCase(), 48 * 2, 48 * 2);

        sex = Math.random() > 0.5 ? '♂' : '♀';

        this.level = level;
        this.growth = growth;
        this.ball = ball;

        xp = setBXP(level);
        cxp = xp;
        nxp = setNXP();

        // Check if hp is 1 for Shedinja
        this.hp = hp == 1 ? 1 : (int) (Math.floor(((2 * hp + hpIV + Math.floor(0.25 * ev)) * level) / 100) + level + 10);
        chp = this.hp;

        baseHP = hp;
        baseAttack = attack;
        baseDefense = defense;
        baseSpAttack = spAttack;
        baseSpDefense = spDefense;
        baseSpeed = speed;
        this.evolveLevel = evolveLevel;
        this.xpYield = xpYield;
        this.ev = ev;
        this.catchRate = catchRate;

        hpIV = 1 + (int) (Math.random() * ((31 - 1) + 1));
        attackIV = 1 + (int) (Math.random() * ((31 - 1) + 1));
        defenseIV = 1 + (int) (Math.random() * ((31 - 1) + 1));
        spAttackIV = 1 + (int) (Math.random() * ((31 - 1) + 1));
        spDefenseIV = 1 + (int) (Math.random() * ((31 - 1) + 1));
        speedIV = 1 + (int) (Math.random() * ((31 - 1) + 1));

        this.attack = getStat(baseAttack, attackIV);
        this.defense = getStat(baseDefense, defenseIV);
        this.spAttack = getStat(baseSpAttack, spAttackIV);
        this.spDefense = getStat(baseSpDefense, spDefenseIV);
        this.speed = getStat(baseSpeed, speedIV);
        this.accuracy = 1;
        this.evasion = 1;

        // random Nature selection
        int num = (int) (Math.random() * ((Nature.getNatures().size())));
        nature = Nature.getNatures().get(num);
        setNature();

        cAttack = this.attack;
        cDefense = this.defense;
        cSpAttack = this.spAttack;
        cSpDefense = this.spDefense;
        cSpeed = this.speed;

        status = null;
        statusCounter = 0;
        statusLimit = 0;

        resetStats();
        resetStatStages();

        moveset = new ArrayList<>();
        activeMoves = new ArrayList<>();

        isAlive = true;
    }
    /** END CONSTRUCTORS **/

    /**
     * CHILD METHODS
     **/
    protected void mapMoves() {
    }

    public Pokemon evolve() {
        return null;
    }
    /** END CHILD METHODS **/

    /**
     * LEVEL UP METHODS
     **/
    protected int setBXP(int level) {
        /*** XP CALULCATOR REFERENCE https://bulbapedia.bulbagarden.net/wiki/Experience#Experience_at_each_level ***/

        double xp = 0;

        double n = level;
        double n2 = Math.pow(level, 2);
        double n3 = Math.pow(level, 3);

        switch (growth) {

            case MEDIUMFAST:
                xp = n3;
                break;

            case ERATIC:
                if (1 < level && level < 50) {
                    xp = Math.floor((n3 * (100.0 - n)) / 50.0);
                }
                else if (50 <= level && level < 68) {
                    xp = Math.floor((n3 * (150.0 - level)) / 100.0);
                }
                else if (68 <= level && level < 98) {
                    xp = n3 * (Math.floor(1911 - (10 * n)) / 3);
                }
                else if (98 <= level && level <= 100) {
                    xp = Math.floor((n3 * (160.0 - level)) / 100.0);
                }
                break;

            case FLUCTUATING:
                if (1 < level && level < 15) {
                    xp = Math.floor((n3 * (Math.floor((n + 1.0) / 3.0) + 24.0)) / 50.0);
                }
                else if (15 <= level && level < 36) {
                    xp = Math.floor((n3 * (n + 14.0)) / 50.0);
                }
                else if (36 <= level && level <= 100) {
                    xp = Math.floor((n3 * (Math.floor(n / 2.0) + 32.0)) / 50.0);
                }
                break;

            case MEDIUMSLOW:
                xp = ((6.0 / 5.0) * n3) + (-15 * n2) + (100 * n) - 140;
                break;

            case FAST:
                xp = Math.floor((4.0 * n3) / 5.0);
                break;

            case SLOW:
                xp = Math.floor((5.0 * n3) / 4.0);
                break;

            default:
                break;
        }

        if (xp < 0 || level == 1) {
            xp = 0.0;
        }

        return (int) Math.floor(xp);
    }

    protected int setNXP() {
        int nextXP = setBXP(level + 1) - setBXP(level);
        if (nextXP < 0) {
            nextXP = 0;
        }
        return nextXP;
    }

    public boolean checkLevelUp(int gainedXP) {
        return nxp <= xp + gainedXP;
    }

    public void levelUp() {

        level++;

        this.xp = setBXP(level);

        int oldBHP = hp;
        hp = ((((2 * baseHP + hpIV + ev / 4) * level) / 100) + level + 10);

        chp += (hp - oldBHP);
        if (chp > hp) {
            chp = hp;
        }

        attack = getStat(baseAttack, attackIV);
        defense = getStat(baseDefense, defenseIV);
        spAttack = getStat(baseSpAttack, spAttackIV);
        spDefense = getStat(baseSpDefense, spDefenseIV);
        speed = getStat(baseSpeed, speedIV);

        setNature();

        cAttack = attack;
        cDefense = defense;
        cSpAttack = spAttack;
        cSpDefense = spDefense;
        cSpeed = speed;
    }

    public boolean canEvolve() {
        return (evolveLevel != -1) && (level >= evolveLevel);
    }

    protected void create(Pokemon old) {

        nickname = old.nickname;

        sex = old.sex;

        level = old.level;

        xp = setBXP(level);
        cxp = old.cxp;
        nxp = setNXP();

        hpIV = old.hpIV;
        attackIV = old.attackIV;
        defenseIV = old.defenseIV;
        spAttackIV = old.spAttackIV;
        spDefenseIV = old.spDefenseIV;
        speedIV = old.speedIV;

        hp = (int) (Math.floor(((2 * baseHP + hpIV + Math.floor(0.25 * ev)) * level) / 100) + level + 10);
        chp = hp;

        attack = getStat(baseAttack, attackIV);
        defense = getStat(baseDefense, defenseIV);
        spAttack = getStat(baseSpAttack, spAttackIV);
        spDefense = getStat(baseSpDefense, spDefenseIV);
        speed = getStat(baseSpeed, speedIV);
        accuracy = old.accuracy;
        evasion = old.evasion;

        nature = old.nature;
        setNature();

        cAttack = attack;
        cDefense = defense;
        cSpAttack = spAttack;
        cSpDefense = spDefense;
        cSpeed = speed;

        resetStats();
        resetStatStages();

        status = old.status;
        statusCounter = 0;
        statusLimit = 0;

        moveset = old.moveset;
        activeMoves = new ArrayList<>();
        protection = Protection.NONE;

        item = old.item;
        ball = old.ball;
        isAlive = old.isAlive;
    }

    public void create(char sex, int level, int cxp, int ev,
                       int hpIV, int attackIV, int defenseIV, int spAttackIV, int spDefenseIV, int speedIV,
                       Nature nature, Status status, List<Move> moveset, Entity item, Entity ball, boolean isAlive) {

        this.sex = sex;
        this.level = level;

        xp = setBXP(level);

        this.cxp = cxp;
        nxp = setNXP();

        this.ev = ev;

        this.hpIV = hpIV;
        this.attackIV = attackIV;
        this.defenseIV = defenseIV;
        this.spAttackIV = spAttackIV;
        this.spDefenseIV = spDefenseIV;
        this.speedIV = speedIV;

        hp = (int) (Math.floor(((2 * baseHP + hpIV + Math.floor(0.25 * ev)) * level) / 100) + level + 10);
        chp = hp;

        attack = getStat(baseAttack, attackIV);
        defense = getStat(baseDefense, defenseIV);
        spAttack = getStat(baseSpAttack, spAttackIV);
        spDefense = getStat(baseSpDefense, spDefenseIV);
        speed = getStat(baseSpeed, speedIV);

        this.nature = nature;
        setNature();

        cAttack = attack;
        cDefense = defense;
        cSpAttack = spAttack;
        cSpDefense = spDefense;
        cSpeed = speed;

        this.status = status;

        this.moveset = new ArrayList<>(moveset);

        this.item = item;
        this.ball = ball;
        this.isAlive = isAlive;
    }

    public void setIV(int iv) {

        if (iv < 0) {
            iv = 0;
        }
        else if (iv > 31) {
            iv = 31;
        }

        hpIV = iv;
        attackIV = iv;
        defenseIV = iv;
        spAttackIV = iv;
        spDefenseIV = iv;
        speedIV = iv;

        attack = getStat(baseAttack, attackIV);
        defense = getStat(baseDefense, defenseIV);
        spAttack = getStat(baseSpAttack, spAttackIV);
        spDefense = getStat(baseSpDefense, spDefenseIV);
        speed = getStat(baseSpeed, speedIV);
        accuracy = 1;
        evasion = 1;

        cAttack = attack;
        cDefense = defense;
        cSpAttack = spAttack;
        cSpDefense = spDefense;
        cSpeed = speed;
    }
    /** END LEVEL UP METHODS **/

    /**
     * MOVE METHODS
     **/
    public Move getNewMove() {

        Move newMove = null;

        if (moveLevels != null) {
            for (Integer lvl : moveLevels.keySet()) {
                if (level == lvl) {
                    newMove = new Move(moveLevels.get(lvl));
                    break;
                }
            }
        }

        return newMove;
    }

    public void addMove(Moves move) {
        if (moveset.size() < 4) {
            moveset.add(new Move(move));
        }
    }

    public void addMoves(List<Moves> moves) {
        moveset.clear();
        for (Moves move : moves) {
            if (moveset.size() < 4) {
                moveset.add(new Move(move));
            }
        }
    }

    public boolean learnMove(Move move) {

        if (moveset.size() == 4) {
            return false;
        }
        else {
            moveset.add(move);
            return true;
        }
    }

    public boolean forgetMove(Move move) {

        for (int i = 0; i < moveset.size(); i++) {
            if (moveset.get(i).getName().equals(move.getName())) {
                moveset.remove(i);
                return true;
            }
        }

        return false;
    }

    public boolean replaceMove(Move oldMove, Move newMove) {

        for (int i = 0; i < moveset.size(); i++) {
            if (moveset.get(i).getName().equals(oldMove.getName())) {
                moveset.remove(i);
                moveset.add(i, newMove);
                return true;
            }
        }

        return false;
    }

    public void resetMoves() {
        for (Move m : moveset) {
            m.resetPP();
            m.resetMoveTurns();
        }

        Iterator<Move> iterator = activeMoves.iterator();
        while (iterator.hasNext()) {
            Move m = iterator.next();
            m.setTurnCount(m.getTurns());
            iterator.remove();
        }
    }

    public void resetMoveTurns() {
        for (Move m : moveset) {
            m.resetMoveTurns();
        }
    }
    /** END MOVE METHODS **/

    /**
     * NATURE METHODS
     **/
    protected void setNature() {

        // find which values to increase/decrease
        int increase = nature.increase();
        int decrease = nature.decrease();

        // increase by 10%
        switch (increase) {
            case (1):
                attack = Math.rint(attack * 1.10);
                break;
            case (2):
                defense = Math.rint(attack * 1.10);
                break;
            case (3):
                spAttack = Math.rint(spAttack * 1.10);
                break;
            case (4):
                spDefense = Math.rint(spDefense * 1.10);
                break;
            case (5):
                speed = Math.rint(speed * 1.10);
                break;
            default:
                break;
        }
        // decrease by 10%
        switch (decrease) {
            case (1):
                attack = Math.rint(attack * .90);
                break;
            case (2):
                defense = Math.rint(attack * .90);
                break;
            case (3):
                spAttack = Math.rint(spAttack * .90);
                break;
            case (4):
                spDefense = Math.rint(spDefense * .90);
                break;
            case (5):
                speed = Math.rint(speed * .90);
                break;
            default:
                break;
        }
    }
    /** END NATURE METHODS **/

    /**
     * ACTIVE MOVES METHODS
     **/
    public void addActiveMove(Moves move) {
        activeMoves.add(new Move(move));
    }

    public boolean hasActiveMove(Moves move) {
        return activeMoves.stream().anyMatch(m -> m.getMove().equals(move));
    }

    public Move getActiveMove(Moves move) {
        return activeMoves.stream().filter(m -> m.getMove().equals(move)).findAny().orElse(null);
    }

    public void removeActiveMove(Move move) {
        activeMoves.remove(move);
    }

    public void removeActiveMove(Moves move) {
        activeMoves.removeIf(m -> m.getMove() == move);

    }

    public void clearActiveMoves() {
        activeMoves.clear();
    }
    /** END MOVE METHODS **/

    /**
     * GET POKEMON METHODS
     **/
    public static Pokemon get(Pokedex id, int level, Entity ball) {

        Pokemon pokemon = null;

        pokemon = switch (id) {
            case BULBUSAUR -> new Bulbasaur(level, ball);
            case IVYSAUR -> new Ivysaur(level, ball);
            case VENUSAUR -> new Venusaur(level, ball);
            case CHARMANDER -> new Charmander(level, ball);
            case CHARMELEON -> new Charmeleon(level, ball);
            case CHARIZARD -> new Charizard(level, ball);
            case SQUIRTLE -> new Squirtle(level, ball);
            case WARTORTLE -> new Wartortle(level, ball);
            case BLASTOISE -> new Blastoise(level, ball);
            case PIKACHU -> new Pikachu(level, ball);
            case RAICHU -> new Raichu(level, ball);
            case ZUBAT -> new Zubat(level, ball);
            case GOLBAT -> new Golbat(level, ball);
            case GROWLITHE -> new Growlithe(level, ball);
            case ARCANINE -> new Arcanine(level, ball);
            case ABRA -> new Abra(level, ball);
            case KADABRA -> new Kadabra(level, ball);
            case ALAKAZAM -> new Alakazam(level, ball);
            case MACHOP -> new Machop(level, ball);
            case MACHOKE -> new Machoke(level, ball);
            case MACHAMP -> new Machamp(level, ball);
            case GEODUDE -> new Geodude(level, ball);
            case GRAVELER -> new Graveler(level, ball);
            case GOLEM -> new Golem(level, ball);
            case PONYTA -> new Ponyta(level, ball);
            case RAPIDASH -> new Rapidash(level, ball);
            case GASTLY -> new Gastly(level, ball);
            case HAUNTER -> new Haunter(level, ball);
            case GENGAR -> new Gengar(level, ball);
            case HITMONLEE -> new Hitmonlee(level, ball);
            case HITMONCHAN -> new Hitmonchan(level, ball);
            case HORSEA -> new Horsea(level, ball);
            case SEADRA -> new Seadra(level, ball);
            case MAGIKARP -> new Magikarp(level, ball);
            case GYARADOS -> new Gyarados(level, ball);
            case LAPRAS -> new Lapras(level, ball);
            case SNORLAX -> new Snorlax(level, ball);
            case ARTICUNO -> new Articuno(level, ball);
            case ZAPDOS -> new Zapdos(level, ball);
            case MOLTRES -> new Moltres(level, ball);
            case DRATINI -> new Dratini(level, ball);
            case DRAGONAIR -> new Dragonair(level, ball);
            case DRAGONITE -> new Dragonite(level, ball);
            case MEWTWO -> new Mewtwo(level, ball);
            case MEW -> new Mew(level, ball);
            case CHIKORITA -> new Chikorita(level, ball);
            case BAYLEEF -> new Bayleef(level, ball);
            case MEGANIUM -> new Meganium(level, ball);
            case CYNDAQUIL -> new Cyndaquil(level, ball);
            case QUILAVA -> new Quilava(level, ball);
            case TYPHLOSION -> new Typhlosion(level, ball);
            case TOTODILE -> new Totodile(level, ball);
            case CROCONAW -> new Croconaw(level, ball);
            case FERALIGATR -> new Feraligatr(level, ball);
            case CROBAT -> new Crobat(level, ball);
            case KINGDRA -> new Kingdra(level, ball);
            case RAIKOU -> new Raikou(level, ball);
            case ENTEI -> new Entei(level, ball);
            case SUICUNE -> new Suicune(level, ball);
            case LUGIA -> new Lugia(level, ball);
            case HOOH -> new Hooh(level, ball);
            case CELEBI -> new Celebi(level, ball);
            case TREECKO -> new Treecko(level, ball);
            case GROVYLE -> new Grovyle(level, ball);
            case SCEPTILE -> new Sceptile(level, ball);
            case TORCHIC -> new Torchic(level, ball);
            case COMBUSKEN -> new Combusken(level, ball);
            case BLAZIKEN -> new Blaziken(level, ball);
            case MUDKIP -> new Mudkip(level, ball);
            case MARSHTOMP -> new Marshtomp(level, ball);
            case SWAMPERT -> new Swampert(level, ball);
            case POOCHYENA -> new Poochyena(level, ball);
            case MIGHTYENA -> new Mightyena(level, ball);
            case ZIGZAGOON -> new Zigzagoon(level, ball);
            case LINOONE -> new Linoone(level, ball);
            case RALTS -> new Ralts(level, ball);
            case KIRLIA -> new Kirlia(level, ball);
            case GARDEVOIR -> new Gardevoir(level, ball);
            case NINCADA -> new Nincada(level, ball);
            case NINJASK -> new Ninjask(level, ball);
            case SHEDINJA -> new Shedinja(level, ball);
            case WHISMUR -> new Whismur(level, ball);
            case LOUDRED -> new Loudred(level, ball);
            case EXPLOUD -> new Exploud(level, ball);
            case SPHEAL -> new Spheal(level, ball);
            case SEALEO -> new Sealeo(level, ball);
            case WALREIN -> new Walrein(level, ball);
            case BAGON -> new Bagon(level, ball);
            case SHELGON -> new Shelgon(level, ball);
            case SALAMENCE -> new Salamence(level, ball);
            case BELDUM -> new Beldum(level, ball);
            case METANG -> new Metang(level, ball);
            case METAGROSS -> new Metagross(level, ball);
            case KYOGRE -> new Kyogre(level, ball);
            case GROUDON -> new Groudon(level, ball);
            case RAYQUAZA -> new Rayquaza(level, ball);
            case JIRACHI -> new Jirachi(level, ball);
            case DEOXYS -> new Deoxys(level, ball);
            default -> null;
        };

        return pokemon;
    }
    /** END GET POKEMON METHODS **/

    /**
     * GETTERS AND SETTERS
     **/
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getHP() {
        return chp;
    }

    public void setHP(int chp) {
        this.chp = chp;
    }

    public void addHP(int hp) {
        this.chp += hp;
        if (this.chp > this.hp) {
            this.chp = this.hp;
        }
    }

    public int getBXP() {
        return xp;
    }

    public int getXP() {
        return cxp;
    }

    public void setXP(int xp) {
        this.cxp = xp;
    }

    public double getBAttack() {
        return attack;
    }

    public void setBAttack(int attack) {
        this.attack = attack;
    }

    public double getBDefense() {
        return defense;
    }

    public void setBDefense(int defense) {
        this.defense = defense;
    }

    public double getBSpAttack() {
        return spAttack;
    }

    public void setBSpAttack(int spAttack) {
        this.spAttack = spAttack;
    }

    public double getBSpDefense() {
        return spDefense;
    }

    public void setBSpDefense(int spDefense) {
        this.spDefense = spDefense;
    }

    public double getBSpeed() {
        return speed;
    }

    public void setBSpeed(int speed) {
        this.speed = speed;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public double getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public double getAttack() {
        return cAttack;
    }

    public void setAttack(int cAttack) {
        this.cAttack = cAttack;
    }

    public double getDefense() {
        return cDefense;
    }

    public void setDefense(int cDefense) {
        this.cDefense = cDefense;
    }

    public double getSpAttack() {
        return cSpAttack;
    }

    public void setSpAttack(int cSpAttack) {
        this.cSpAttack = cSpAttack;
    }

    public double getSpDefense() {
        return cSpDefense;
    }

    public void setSpDefense(int cSpDefense) {
        this.cSpDefense = cSpDefense;
    }

    public double getSpeed() {
        return cSpeed;
    }

    public void setSpeed(int cSpeed) {
        this.cSpeed = cSpeed;
    }

    public int getAttackStg() {
        return attackStg;
    }

    public void setAttackStg(int attackStg) {
        this.attackStg = attackStg;
    }

    public int getDefenseStg() {
        return defenseStg;
    }

    public void setDefenseStg(int defenseStg) {
        this.defenseStg = defenseStg;
    }

    public int getSpAttackStg() {
        return spAttackStg;
    }

    public void setSpAttackStg(int spAttackStg) {
        this.spAttackStg = spAttackStg;
    }

    public int getSpDefenseStg() {
        return spDefenseStg;
    }

    public void setSpDefenseStg(int spDefenseStg) {
        this.spDefenseStg = spDefenseStg;
    }

    public int getSpeedStg() {
        return speedStg;
    }

    public void setSpeedStg(int speedStg) {
        this.speedStg = speedStg;
    }

    public int getAccuracyStg() {
        return accuracyStg;
    }

    public void setAccuracyStg(int accuracyStg) {
        this.accuracyStg = accuracyStg;
    }

    public int getEvasionStg() {
        return evasionStg;
    }

    public void setEvasionStg(int evasionStg) {
        this.evasionStg = evasionStg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean hasStatus(Status status) {
        return (this.status != null && this.status == status);
    }

    public int getStatusCounter() {
        return statusCounter;
    }

    public void setStatusCounter(int statusCounter) {
        this.statusCounter = statusCounter;
    }

    public int getStatusLimit() {
        return statusLimit;
    }

    public void setStatusLimit(int statusLimit) {
        this.statusLimit = statusLimit;
    }

    public void removeStatus() {
        status = null;
        statusLimit = 0;
        statusCounter = 0;
    }

    public List<Move> getMoveSet() {
        return moveset;
    }

    public void setMoveSet(ArrayList<Move> moveSet) {
        this.moveset = moveSet;
    }

    public List<Move> getActiveMoves() {
        return activeMoves;
    }

    public void setActiveMoves(List<Move> activeMoves) {
        this.activeMoves = activeMoves;
    }

    public Protection getProtection() {
        return protection;
    }

    public void setProtection(Protection protection) {
        this.protection = protection;
    }

    public void clearProtection() {
        protection = Protection.NONE;
    }

    public boolean getAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean getHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean getStatChanging() {
        return statChanging;
    }

    public void setStatChanging(boolean statChanging) {
        this.statChanging = statChanging;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
        chp = 0;
        removeStatus();
        getAbility().setActive(false);
        resetMoves();
        resetStats();
        resetStatStages();
        clearProtection();
    }

    public Entity getBall() {
        return ball;
    }

    public void setBall(Entity ball) {
        this.ball = ball;
    }

    public Entity getItem() {
        return item;
    }

    public void giveItem(Entity item) {
        this.item = item;
    }
    /** END GETTERS AND SETTERS **/

    /**
     * GETTERS
     **/
    public String toString() {
        return name;
    }

    public Pokedex getID() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        if (nickname != null) {
            return nickname;
        }
        else {
            return name.toUpperCase();
        }
    }

    public BufferedImage getFrontSprite() {
        return frontSprite;
    }

    public BufferedImage getBackSprite() {
        return backSprite;
    }

    public BufferedImage getMenuSprite() {
        return menuSprite;
    }

    public char getSex() {
        return sex;
    }

    public Color getSexColor() {
        if (sex == '♂') {
            return Color.BLUE;
        }
        else {
            return Color.RED;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getBHP() {
        return hp;
    }

    public int getNXP() {
        return nxp;
    }

    public Type getType() {
        return type;
    }

    public List<Type> getTypes() {
        return types;
    }

    public boolean checkType(Type type) {

        boolean isType = false;

        if (this.types != null) {
            if (this.types.contains(type)) {
                isType = true;
            }
        }
        else if (this.type == type) {
            isType = true;
        }

        return isType;
    }

    public Nature getNature() {
        return nature;
    }

    public Ability getAbility() {
        return ability;
    }

    public Growth getGrowth() {
        return growth;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getBaseSpAttack() {
        return baseSpAttack;
    }

    public int getBaseSpDefense() {
        return baseSpDefense;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getHPIV() {
        return hpIV;
    }

    public int getAttackIV() {
        return attackIV;
    }

    public int getDefenseIV() {
        return defenseIV;
    }

    public int getSpAttackIV() {
        return spAttackIV;
    }

    public int getSpDefenseIV() {
        return spDefenseIV;
    }

    public int getSpeedIV() {
        return speedIV;
    }

    public int getXPYield() {
        return xpYield;
    }

    public int getEV() {
        return ev;
    }

    public int getEvolveLevel() {
        return evolveLevel;
    }

    public int getCatchRate() {
        return catchRate;
    }
    /** END GETTERS **/

    /**
     * MISC METHODS
     **/
    protected int getStat(double base, int IV) {
        return (int) (Math.floor(0.01 * (2 * base + IV + Math.floor(0.25 * ev)) * level)) + 5;
    }

    public boolean canChangeStat(String stat, int level) {

        boolean canChange = canChange = switch (stat) {
            case "attack" -> level > 0 ? attackStg + level <= 6 : attackStg + level >= -6;
            case "defense" -> level > 0 ? defenseStg + level <= 6 : defenseStg + level >= -6;
            case "spAttack" -> level > 0 ? spAttackStg + level <= 6 : spAttackStg + level >= -6;
            case "spDefense" -> level > 0 ? spDefenseStg + level <= 6 : spDefenseStg + level >= -6;
            case "speed" -> level > 0 ? speedStg + level <= 6 : speedStg + level >= -6;
            case "accuracy" -> level > 0 ? accuracyStg + level <= 6 : accuracyStg + level >= -6;
            case "evasion" -> level > 0 ? evasionStg + level <= 6 : evasionStg + level >= -6;
            default -> true;
        };

        return canChange;
    }

    public String changeStat(String stat, int level) {

        if (!canChangeStat(stat, level)) {
            return "";
        }

        String output = "";
        int difference;
        int change = 0;

        switch (stat) {
            case "attack":
                difference = attackStg + level;
                if (level > 0) {
                    while (attackStg < difference && attackStg < 6) {
                        attackStg++;
                        change++;
                    }
                    cAttack = Math.floor(attack * ((2.0 + attackStg) / 2.0));
                    output = outputChange(stat, change);
                }
                else if (level < 0) {
                    while (attackStg > difference && attackStg > -6) {
                        attackStg--;
                        change--;
                    }
                    cAttack = Math.floor(attack * (2.0 / (2.0 - attackStg)));
                    output = outputChange(stat, change);
                }
                break;
            case "defense":
                difference = defenseStg + level;
                if (level > 0) {
                    while (defenseStg < difference && defenseStg < 6) {
                        defenseStg++;
                        change++;
                    }
                    cDefense = Math.floor(defense * ((2.0 + defenseStg) / 2.0));
                    output = outputChange(stat, change);
                }
                else if (level < 0) {
                    while (defenseStg > difference && defenseStg > -6) {
                        defenseStg--;
                        change--;
                    }
                    cDefense = Math.floor(defense * (2.0 / (2.0 - defenseStg)));
                    output = outputChange(stat, change);
                }
                break;
            case "sp. attack":
                difference = spAttackStg + level;
                if (level > 0) {
                    while (spAttackStg < difference && spAttackStg < 6) {
                        spAttackStg++;
                        change++;
                    }
                    cSpAttack = Math.floor(spAttack * ((2.0 + spAttackStg) / 2.0));
                    output = outputChange(stat, change);
                }
                else if (level < 0) {
                    while (spAttackStg > difference && spAttackStg > -6) {
                        spAttackStg--;
                        change--;
                    }
                    cSpAttack = Math.floor(spAttack * (2.0 / (2.0 - spAttackStg)));
                    output = outputChange(stat, change);
                }
                break;
            case "sp. defense":
                difference = spDefenseStg + level;
                if (level > 0) {
                    while (spDefenseStg < difference && spDefenseStg < 6) {
                        spDefenseStg++;
                        change++;
                    }
                    cSpDefense = Math.floor(spDefense * ((2.0 + spDefenseStg) / 2.0));
                    output = outputChange(stat, change);
                }
                else if (level < 0) {
                    while (spDefenseStg > difference && spDefenseStg > -6) {
                        spDefenseStg--;
                        change--;
                    }
                    cSpDefense = Math.floor(spDefense * (2.0 / (2.0 - spDefenseStg)));
                    output = outputChange(stat, change);
                }
                break;
            case "speed":
                difference = speedStg + level;
                if (level > 0) {
                    while (speedStg < difference && speedStg < 6) {
                        speedStg++;
                        change++;
                    }
                    cSpeed = Math.floor(speed * ((2.0 + speedStg) / 2.0));
                    output = outputChange(stat, change);
                }
                else if (level < 0) {
                    while (speedStg > difference && speedStg > -6) {
                        speedStg--;
                        change--;
                    }
                    cSpeed = Math.floor(speed * (2.0 / (2.0 - speedStg)));
                    output = outputChange(stat, change);
                }
                break;
            case "accuracy":
                difference = accuracyStg + level;
                if (level > 0) {
                    while (accuracyStg < difference && accuracyStg < 6) {
                        accuracyStg++;
                        change++;
                    }
                    accuracy = Math.floor(1 * ((3.0 + accuracyStg) / 3.0));
                    output = outputChange(stat, change);
                }
                else if (level < 0) {
                    while (accuracyStg > difference && accuracyStg > -6) {
                        accuracyStg--;
                        change--;
                    }
                    accuracy = Math.floor(1 * (3.0 / (3.0 - accuracyStg)));
                    output = outputChange(stat, change);
                }
                break;
            case "evasion":
                difference = evasionStg + level;
                if (level > 0) {
                    while (evasionStg < difference && evasionStg < 6) {
                        evasionStg++;
                        change++;
                    }
                    evasion = Math.floor(1 * ((3.0 + evasionStg) / 3.0));
                    output = outputChange(stat, change);
                }
                else if (level < 0) {
                    while (evasionStg > difference && evasionStg > -6) {
                        evasionStg--;
                        change--;
                    }
                    evasion = Math.floor(1 * (3.0 / (3.0 - evasionStg)));
                    output = outputChange(stat, change);
                }
                break;
        }

        return output;
    }

    private String outputChange(String stat, int level) {

        String output = "";

        if (level == 1) {
            output = getName() + "'s " + stat + "\nrose!";
        }
        else if (level == 2) {
            output = getName() + "'s " + stat + "\ngreatly rose!";
        }
        else if (level >= 3) {
            output = getName() + "'s " + stat + "\ndrastically rose!";
        }
        else if (level == -1) {
            output = getName() + "'s " + stat + "\nfell!";
        }
        else if (level == -2) {
            output = getName() + "'s " + stat + "\ngreatly fell!";
        }
        else if (level <= -3) {
            output = getName() + "'s " + stat + "\nseverely fell!";
        }

        return output;
    }

    public void resetStats() {
        cAttack = attack;
        cDefense = defense;
        cSpAttack = spAttack;
        cSpDefense = defense;
        cSpeed = speed;
        accuracy = 1;
        evasion = 1;
    }

    public void resetStatStages() {
        attackStg = 0;
        defenseStg = 0;
        spAttackStg = 0;
        spDefenseStg = 0;
        speedStg = 0;
        accuracyStg = 0;
        evasionStg = 0;
    }

    protected BufferedImage setup(String imagePath, int width, int height) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = GamePanel.utility.scaleImage(image, width, height);
        }
        catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return image;
    }
    /** END MISC METHODS **/
}