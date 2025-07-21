package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Whismur extends Pokemon {

    public Whismur(int level, Entity ball) {
        super(293, "Whismur", level, ball, 64, 51, 23, 51, 23, 28, 20, 68, 1, Growth.MEDIUMSLOW, 190);

        id = Pokedex.WHISMUR;
        type = Type.NORMAL;
        ability = Ability.SOUNDPROOF;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                //  new Move(Moves.POUND)
                new Move(Moves.HYPERVOICE),
                new Move(Moves.REST),
                new Move(Moves.SLEEPTALK),
                new Move(Moves.SCREECH)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(11, Moves.ASTONISH),
                Map.entry(15, Moves.HOWL),
                Map.entry(21, Moves.SUPERSONIC),
                Map.entry(25, Moves.STOMP),
                Map.entry(31, Moves.SCREECH),
                Map.entry(41, Moves.REST),
                Map.entry(42, Moves.SLEEPTALK),
                Map.entry(45, Moves.HYPERVOICE)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Loudred(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}