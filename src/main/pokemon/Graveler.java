package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Graveler extends Pokemon {

    public Graveler(int level, Entity ball) {
        super(75, "Graveler", level, ball, 55, 95, 115, 45, 45, 35, 40, 134, 2, Growth.MEDIUMSLOW, 120);

        id = Pokedex.GRAVELER;
        types = Arrays.asList(Type.ROCK, Type.GROUND);
        ability = Ability.ROCKHEAD;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                /*
                new Move(Moves.TACKLE),
                new Move(Moves.DEFENSECURL)
                 */
                new Move(Moves.EARTHQUAKE),
                new Move(Moves.SEISMICTOSS),
                new Move(Moves.STRENGTH),
                new Move(Moves.SELFDESTRUCT)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(8, Moves.ROCKPOLISH),
                Map.entry(11, Moves.ROCKTHROW),
                Map.entry(15, Moves.MAGNITUDE),
                Map.entry(18, Moves.SELFDESTRUCT),
                Map.entry(22, Moves.ROLLOUT),
                Map.entry(27, Moves.ROCKBLAST),
                Map.entry(33, Moves.EARTHQUAKE),
                Map.entry(38, Moves.EXPLOSION),
                Map.entry(44, Moves.DOUBLEEDGE),
                Map.entry(49, Moves.STONEEDGE)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Golem(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}