package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Horsea extends Pokemon {

    public Horsea(int level, Entity ball) {
        super(116, "Horsea", level, ball, 30, 40, 70, 70, 25, 60, 32, 83, 1, Growth.MEDIUMFAST, 225);

        id = Pokedex.HORSEA;
        type = Type.WATER;
        ability = Ability.SNIPER;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                //  new Move(Moves.BUBBLE)
                new Move(Moves.HYDROPUMP),
                new Move(Moves.TOXIC),
                new Move(Moves.SMOKESCREEN),
                new Move(Moves.ICEBEAM)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(4, Moves.SMOKESCREEN),
                Map.entry(8, Moves.LEER),
                Map.entry(11, Moves.WATERGUN),
                Map.entry(18, Moves.BUBBLEBEAM),
                Map.entry(23, Moves.AGILITY),
                Map.entry(26, Moves.TWISTER),
                Map.entry(30, Moves.BRINE),
                Map.entry(35, Moves.HYDROPUMP),
                Map.entry(38, Moves.DRAGONDANCE),
                Map.entry(42, Moves.DRAGONPULSE)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Seadra(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}