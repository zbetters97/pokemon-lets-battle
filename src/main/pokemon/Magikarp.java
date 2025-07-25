package pokemon;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

import java.util.Arrays;
import java.util.Map;

public class Magikarp extends Pokemon {

    public Magikarp(int level, Entity ball) {
        super(129, "Magikarp", level, ball, 20, 10, 55, 15, 20, 80, 20, 20, 1, Growth.SLOW, 255);

        id = Pokedex.MAGIKARP;
        type = Type.WATER;
        ability = Ability.SWIFTSWIM;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                new Move(Moves.SPLASH),
                new Move(Moves.TACKLE)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(15, Moves.TACKLE),
                Map.entry(30, Moves.FLAIL)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Gyarados(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}