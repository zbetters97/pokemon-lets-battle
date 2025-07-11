package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Arcanine extends Pokemon {

    public Arcanine(int level, Entity ball) {
        super(59, "Arcanine", level, ball, 90, 110, 80, 100, 80, 95, -1, 213, 2, Growth.SLOW, 75);

        id = Pokedex.ARCANINE;
        type = Type.FIRE;
        ability = Ability.FLASHFIRE;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                /*
                new Move(Moves.THUNDERFANG),
                new Move(Moves.BITE),
//       		new Move(Moves.ROAR),
                new Move(Moves.ODORSLEUTH),
                new Move(Moves.FIREFANG)
                 */
                new Move(Moves.FIREBLAST),
                new Move(Moves.TAKEDOWN),
                new Move(Moves.DRAGONRAGE),
                new Move(Moves.EXTREMESPEED)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(39, Moves.EXTREMESPEED)
        );
    }
}