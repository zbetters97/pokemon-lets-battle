package pokemon;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

public class Hooh extends Pokemon {

    public Hooh(int level, Entity ball) {
        super(250, "Ho-Oh", level, ball, 106, 130, 90, 110, 154, 90, -1, 220, 3, Growth.SLOW, 3);

        id = Pokedex.HOOH;
        types = Arrays.asList(Type.FIRE, Type.FLYING);
        ability = Ability.PRESSURE;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(List.of(
                /*
                new Move(Moves.FIRESPIN),
                new Move(Moves.STOMP),
                new Move(Moves.SWAGGER),
                new Move(Moves.CALMMIND)
                 */
                new Move(Moves.LAVAPLUME),
                new Move(Moves.EXTRASENSORY),
                new Move(Moves.SWAGGER),
                new Move(Moves.CALMMIND)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(8, Moves.EMBER),
                Map.entry(22, Moves.FIRESPIN),
                Map.entry(29, Moves.STOMP),
                Map.entry(36, Moves.FLAMETHROWER),
                Map.entry(43, Moves.SWAGGER),
                Map.entry(50, Moves.FIREFANG),
                Map.entry(57, Moves.LAVAPLUME),
                Map.entry(64, Moves.EXTRASENSORY),
                Map.entry(71, Moves.FIREBLAST),
                Map.entry(78, Moves.CALMMIND)
        );
    }
}