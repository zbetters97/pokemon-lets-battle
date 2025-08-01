package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Beldum extends Pokemon {

    public Beldum(int level, Entity ball) {
        super(374, "Beldum", level, ball, 40, 55, 80, 35, 60, 30, 25, 103, 1, Growth.SLOW, 3);

        id = Pokedex.BELDUM;
        types = Arrays.asList(Type.STEEL, Type.PSYCHIC);
        ability = Ability.CLEARBODY;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                //   new Move(Moves.TAKEDOWN)
                new Move(Moves.SUPERSONIC),
                new Move(Moves.REST),
                new Move(Moves.SLEEPTALK),
                new Move(Moves.HYPNOSIS)
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
        Pokemon evolvedForm = new Metang(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}