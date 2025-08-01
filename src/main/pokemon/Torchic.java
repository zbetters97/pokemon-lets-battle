package pokemon;

import java.util.Arrays;
import java.util.Map;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

public class Torchic extends Pokemon {

    public Torchic(int level, Entity ball) {
        super(255, "Torchic", level, ball, 45, 60, 40, 70, 50, 45, 16, 65, 1, Growth.MEDIUMSLOW, 45);

        id = Pokedex.TORCHIC;
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
                new Move(Moves.PECK),
                new Move(Moves.SANDATTACK),
                new Move(Moves.BULKUP)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(10, Moves.EMBER),
                Map.entry(16, Moves.PECK),
                Map.entry(19, Moves.SANDATTACK),
                Map.entry(25, Moves.FIRESPIN),
                Map.entry(28, Moves.QUICKATTACK),
                Map.entry(34, Moves.SLASH),
                Map.entry(43, Moves.FLAMETHROWER)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Combusken(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}