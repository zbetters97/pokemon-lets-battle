package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Suicune extends Pokemon {

    public Suicune(int level, Entity ball) {
        super(245, "Suicune", level, ball, 100, 75, 115, 90, 115, 85, -1, 215, 3, Growth.SLOW, 3);

        id = Pokedex.SUICUNE;
        type = Type.WATER;
        ability = Ability.PRESSURE;

        mapMoves();
    }

    protected void mapMoves() {
        /*
        moveset.add(
                new Move(Moves.LEER)
        );
         */

        moveset.addAll(Arrays.asList(
                new Move(Moves.BUBBLEBEAM),
                new Move(Moves.GUST),
                new Move(Moves.MIST),
                new Move(Moves.ICEFANG)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(8, Moves.BUBBLEBEAM),
                Map.entry(15, Moves.RAINDANCE),
                Map.entry(22, Moves.GUST),
                Map.entry(29, Moves.AURORABEAM),
                Map.entry(50, Moves.ICEFANG),
                Map.entry(64, Moves.EXTRASENSORY),
                Map.entry(71, Moves.HYDROPUMP),
                Map.entry(78, Moves.CALMMIND)
        );
    }
}