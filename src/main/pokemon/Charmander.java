package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Charmander extends Pokemon {

    public Charmander(int level, Entity ball) {
        super(4, "Charmander", level, ball, 39, 52, 43, 60, 50, 65, 16, 65, 1, Growth.MEDIUMSLOW, 45);

        id = Pokedex.CHARMANDER;
        type = Type.FIRE;
        ability = Ability.BLAZE;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                /*
                new Move(Moves.SCRATCH),
                new Move(Moves.GROWL)
                 */
                new Move(Moves.FLAMETHROWER),
                new Move(Moves.SLASH),
                new Move(Moves.DIG),
                new Move(Moves.FIRESPIN)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(7, Moves.EMBER),
                Map.entry(10, Moves.SMOKESCREEN),
                Map.entry(16, Moves.DRAGONRAGE),
                Map.entry(19, Moves.SCARYFACE),
                Map.entry(25, Moves.FIREFANG),
                Map.entry(28, Moves.SLASH),
                Map.entry(34, Moves.FLAMETHROWER),
                Map.entry(37, Moves.FIRESPIN)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Charmeleon(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}