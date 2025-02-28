package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Map;

public class Abra extends Pokemon {

    public Abra(int level, Entity ball) {
        super(63, "Abra", level, ball, 25, 20, 15, 105, 55, 90, 16, 75, 1, Growth.MEDIUMSLOW, 200);

        id = Pokedex.ABRA;
        type = Type.PSYCHIC;
        ability = Ability.INNERFOCUS;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.add(
                new Move(Moves.TELEPORT)
        );

        moveLevels = Map.ofEntries(

        );
    }

    public Pokemon evolve() {

        Pokemon evolvedForm = new Kadabra(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}