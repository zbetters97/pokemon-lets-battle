package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Mewtwo extends Pokemon {

    public Mewtwo(int level, Entity ball) {
        super(150, "Mewtwo", level, ball, 106, 110, 90, 154, 90, 130, -1, 220, 3, Growth.SLOW, 3);

        id = Pokedex.MEWTWO;
        type = Type.PSYCHIC;
        ability = Ability.PRESSURE;

        mapMoves();
    }

    protected void mapMoves() {

        /*
        moveset.add(
                new Move(Moves.CONFUSION)
        );
        */

        moveset.addAll(Arrays.asList(
                new Move(Moves.PSYCHOCUT),
                new Move(Moves.AURASPHERE),
                new Move(Moves.MIRACLEEYE),
                new Move(Moves.AMNESIA)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(8, Moves.BARRIER),
                Map.entry(15, Moves.SWIFT),
                Map.entry(22, Moves.FUTURESIGHT),
                Map.entry(29, Moves.PSYCHUP),
                Map.entry(36, Moves.MIRACLEEYE),
                Map.entry(43, Moves.MIST),
                Map.entry(50, Moves.PSYCHOCUT),
                Map.entry(57, Moves.AMNESIA),
                Map.entry(71, Moves.PSYCHIC),
                Map.entry(86, Moves.RECOVER),
                Map.entry(93, Moves.SAFEGUARD),
                Map.entry(100, Moves.AURASPHERE)
        );
    }
}