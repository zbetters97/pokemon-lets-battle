package pokemon;

import java.util.Arrays;
import java.util.Map;

import entity.Entity;
import moves.Move;
import moves.Moves;
import properties.Ability;
import properties.Type;

public class Marshtomp extends Pokemon {

    public Marshtomp(int level, Entity ball) {
        super(259, "Marshtomp", level, ball, 70, 85, 70, 60, 70, 50, 36, 143, 2, Growth.MEDIUMSLOW, 45);

        id = Pokedex.MARSHTOMP;
        types = Arrays.asList(Type.WATER, Type.GROUND);
        ability = Ability.TORRENT;

        mapMoves();
    }

    protected void mapMoves() {

        moveset.addAll(Arrays.asList(
                /*
                new Move(Moves.WATERGUN),
                new Move(Moves.TACKLE),
                new Move(Moves.GROWL)
                 */
                new Move(Moves.MUDDYWATER),
                new Move(Moves.EARTHQUAKE),
                new Move(Moves.PROTECT),
                new Move(Moves.MUDSHOT)
        ));

        moveLevels = Map.ofEntries(
                Map.entry(6, Moves.MUDSLAP),
                Map.entry(10, Moves.WATERGUN),
                Map.entry(16, Moves.MUDSHOT),
                Map.entry(25, Moves.MUDBOMB),
                Map.entry(31, Moves.TAKEDOWN),
                Map.entry(37, Moves.MUDDYWATER),
                Map.entry(42, Moves.PROTECT),
                Map.entry(46, Moves.EARTHQUAKE),
                Map.entry(53, Moves.ENDEAVOR)
        );
    }

    public Pokemon evolve() {
        Pokemon evolvedForm = new Swampert(level, ball);
        evolvedForm.create(this);

        return evolvedForm;
    }
}