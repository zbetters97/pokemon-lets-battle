package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Zubat extends Pokemon {

    public Zubat(int level, Entity ball) {
        super(41, "Zubat", level, ball, 40, 45, 35, 30, 40, 55, 22, 54, 1, Growth.MEDIUMFAST, 255);

        id = Pokedex.ZUBAT;
        types = Arrays.asList(Type.FLYING, Type.POISON);
        ability = Ability.INNERFOCUS;

        mapMoves();
    }

    protected void mapMoves() {
        moveset.addAll(Arrays.asList(
                //  new Move(Moves.LEECHLIFE)
                new Move(Moves.CONFUSERAY),
                new Move(Moves.MEGADRAIN),
                new Move(Moves.TOXIC),
                new Move(Moves.DOUBLEEDGE)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(5, Moves.SUPERSONIC),
                Map.entry(9, Moves.ASTONISH),
                Map.entry(13, Moves.BITE),
                Map.entry(17, Moves.WINGATTACK),
                Map.entry(21, Moves.CONFUSERAY),
                Map.entry(25, Moves.AIRCUTTER),
                Map.entry(29, Moves.MEANLOOK),
                Map.entry(33, Moves.POISONFANG),
                Map.entry(37, Moves.HAZE),
                Map.entry(41, Moves.AIRSLASH)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Golbat(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}