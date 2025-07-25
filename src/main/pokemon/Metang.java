package pokemon;

import java.util.Arrays;
import java.util.Map;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

public class Metang extends Pokemon {

    public Metang(int level, Entity ball) {
        super(375, "Metang", level, ball, 60, 75, 100, 55, 80, 50, 45, 153, 2, Growth.SLOW, 3);

        id = Pokedex.METANG;
        types = Arrays.asList(Type.STEEL, Type.PSYCHIC);
        ability = Ability.CLEARBODY;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                /*
                new Move(Moves.MAGNETRISE),
                new Move(Moves.TAKEDOWN),
                new Move(Moves.METALCLAW),
                new Move(Moves.CONFUSION)
                 */
                new Move(Moves.BULLETPUNCH),
                new Move(Moves.METEORMASH),
                new Move(Moves.SCARYFACE),
                new Move(Moves.IRONDEFENSE)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(20, Moves.METALCLAW),
                Map.entry(21, Moves.CONFUSION),
                Map.entry(24, Moves.SCARYFACE),
                Map.entry(28, Moves.PURSUIT),
                Map.entry(32, Moves.BULLETPUNCH),
                Map.entry(36, Moves.PSYCHIC),
                Map.entry(40, Moves.IRONDEFENSE),
                Map.entry(44, Moves.AGILITY),
                Map.entry(48, Moves.METEORMASH),
                Map.entry(52, Moves.ZENHEADBUTT),
                Map.entry(56, Moves.HYPERBEAM)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Metagross(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}