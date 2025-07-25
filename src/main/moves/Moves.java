package moves;

import application.GamePanel.Weather;
import moves.Move.MoveType;
import pokemon.Pokemon.Protection;
import properties.Status;
import properties.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/*** MOVES ENUM ***/
public enum Moves {
    /** ATTACK DESCRIPTION REFERENCE (GEN IV): https://www.serebii.net/attackdex-dp/ **/
    /**
     * PRIORITY SPEED REFERENCE (GEN IV): https://bulbapedia.bulbagarden.net/wiki/Priority#Generation_VI
     **/


    ABSORB("Absorb", MoveType.SPECIAL, Type.GRASS, 20, 20, 100, 0,
            "A nutrient-draining attack.\nThe user's HP is restored\nby half the damage taken\nby the target."),
    AEROBLAST("Aeroblast", MoveType.SPECIAL, Type.FLYING, 5, 100, 95, 0, 1,
            "A vortex of air is shot at\nthe foe to inflict damage.\nIt has a high critical-hit\nratio."),
    AGILITY("Agility", MoveType.ATTRIBUTE, Type.PSYCHIC, true, 30, -1, -1, 0, 1, List.of("speed"),
            "The user relaxes and\nlightens its body to move\nfaster. It sharply boosts\nthe Speed stat."),
    AIRCUTTER("Air Cutter", MoveType.SPECIAL, Type.FLYING, 25, 55, 95, 0, 1,
            "The user launches razorlike\nwind to slash the foe. It\nhas a high critical-hit\nratio."),
    AIRSLASH("Air Slash", MoveType.SPECIAL, Type.FLYING, 0.30, 20, 75, 95, 0,
            "The user attacks with a\nblade of air that slices\neven the sky. It may also\nmake the target flinch."),
    AMNESIA("Amnesia", MoveType.ATTRIBUTE, Type.PSYCHIC, true, 20, -1, -1, 0, 2, List.of("sp. defense"),
            "The user temporarily\nempties its mind to forget\nits concerns. It sharply\nraises the user's Sp. Def."),
    ANCIENTPOWER("Ancient Power", MoveType.SPECIAL, Type.ROCK, true, 0.10, 5, 60, 100, 0, 1,
            Arrays.asList("attack", "defense", "sp. attack", "sp. defense", "speed"),
            "The user attacks with a pre-\nhistoric power. It may also\nraise all the user's stats\nat once. "),
    AURASPHERE("Aura Sphere", MoveType.SPECIAL, Type.FIGHTING, 20, 90, -1, 0,
            "The user looses a blast of\naura power from deep within\nits body. This move is\ncertain to hit."),
    AQUATAIL("Aqua Tail", MoveType.PHYSICAL, Type.WATER, 10, 135, 90, 0,
            "The user attacks by swing-\ning its tail as if it were a\nvicious wave in a raging\nstorm."),
    AROMATHERAPY("Aromatherapy", MoveType.OTHER, Type.GRASS, 5, -1, -1, 0,
            "The user releases a soothing\nscent that heals all status\nproblems affecting the\nuser's party."),
    ASSURANCE("Assurance", MoveType.PHYSICAL, Type.DARK, 10, 50, 100, 0,
            "If the foe has already\ntaken some damage in the\nsame turn, this attack's\npower is doubled."),
    ASTONISH("Astonish", MoveType.PHYSICAL, Type.GHOST, 0.30, 15, 30, 100, 0,
            "The user attacks the foe\nwhile shouting in a startling\nfashion. It may also make\nthe target flinch."),
    AURORABEAM("Aurora Beam", MoveType.SPECIAL, Type.ICE, 20, 65, 100, 0,
            "The foe is hit with a rainbow\n-colored beam."),
    BARRIER("Barrier", MoveType.ATTRIBUTE, Type.PSYCHIC, true, 30, -1, -1, 0, 2, List.of("defense"),
            "The user throws up a sturdy\nwall that sharply raises its\nDefense stat."),
    BITE("Bite", MoveType.PHYSICAL, Type.DARK, 0.30, 25, 60, 100, 0,
            "The foe is bitten with\nviciously sharp fangs. It\nmay make the target\nflinch."),
    BLASTBURN("Blast Burn", MoveType.SPECIAL, Type.FIRE, 5, 150, 90, 0, 1, true, Protection.NONE,
            "is\nresting...",
            "The target is razed by a\nfiery explosion. The user\ncan't move on the next\nturn."),
    BLAZEKICK("Blaze Kick", MoveType.PHYSICAL, Type.FIRE, Status.BURN, 0.10, 10, 85, 90, 0, 1,
            "The user launches a kick\nwith a high critical-hit\nratio. It may also leave\nthe target with a burn."),
    BLIZZARD("Blizzard", MoveType.SPECIAL, Type.ICE, Status.FREEZE, 0.10, 5, 120, 70, 0,
            "A howling blizzard is\nsummoned to strike the foe.\nIt may also freeze the\ntarget solid."),
    BODYSLAM("Body Slam", MoveType.PHYSICAL, Type.NORMAL, Status.PARALYZE, 0.10, 15, 85, 100, 0,
            "The user drops onto the foe\nwith its full body weight.\nIt may leave the foe\nparalyzed."),
    BOUNCE("Bounce", MoveType.PHYSICAL, Type.FLYING, Status.PARALYZE, 0.30, 5, 85, 85, 0, 1, false, Protection.BOUNCE,
            "bounced\ninto the air!",
            "The user bounces up high,\nthen drops on the foe on\nthe second turn. It may\nalso paralyze the foe."),
    BRAVEBIRD("Brave Bird", MoveType.PHYSICAL, Type.FLYING, 15, 120, 100, 0, 0.33,
            "The user tucks in its wings\nand charges from a low\naltitude. The user also\ntakes serious damage."),
    BRICKBREAK("Brick Break", MoveType.PHYSICAL, Type.FIGHTING, 15, 75, 100, 0,
            "The user attacks with tough\nfists, etc. It can also\nbreak any barrier such as\nLight Screen and Reflect."),
    BRINE("Brine", MoveType.SPECIAL, Type.WATER, 10, 65, 100, 0,
            "If the foe's HP is down to\nhalf, this attack will hit\nwith double the power."),
    BUBBLE("Bubble", MoveType.SPECIAL, Type.WATER, 30, 20, 100, 0,
            "A spray of countless\nbubbles is jetted at the\nfoe."),
    BUBBLEBEAM("Bubblebeam", MoveType.SPECIAL, Type.WATER, false, 0.10, 20, 65, 100, 0, -1, List.of("speed"),
            "A spray of bubbles is force-\nfully ejected at the foe.\nIt may also lower the\ntarget's Speed stat."),
    BULKUP("Bulk Up", MoveType.ATTRIBUTE, Type.FIGHTING, true, 20, -1, -1, 0, 1, Arrays.asList("attack", "defense"),
            "The user tenses its muscles\nto bulk up its body, boost-\ning both its Attack and\nDefense stats."),
    BULLETPUNCH("Bullet Punch", MoveType.PHYSICAL, Type.STEEL, 30, 40, 100, 1,
            "The user strikes with a\ntough punch as fast as a\nbullet. This move always\ngoes first."),
    CALMMIND("Calm Mind", MoveType.ATTRIBUTE, Type.PSYCHIC, true, 20, -1, -1, 0, 1, Arrays.asList("sp. attack", "sp. defense"),
            "The user quietly focuses\nits mind and calms its spirit\nto raise its Sp. Atk and\nSp. Def stats."),
    CAPTIVATE("Captivate", MoveType.ATTRIBUTE, Type.NORMAL, false, 20, -1, 100, 0, -2, List.of("sp. attack"),
            "If it is the opposite gender\nof the user, the foe is\ncharmed into sharply\nlowering its Sp. Atk stat."),
    CHARGE("Charge", MoveType.OTHER, Type.ELECTRIC, 20, -1, -1, 0, 2,
            "",
            "The user boosts the power\nof the Electric move it uses\nnext. It also raises the\nuser's Sp. Def stat."),
    CHARM("Charm", MoveType.ATTRIBUTE, Type.NORMAL, false, 20, -1, 100, 0, -2, List.of("attack"),
            "The user charmingly stares\nat the foe, making it less\nwary. The target's Attack\nis sharply lowered."),
    CLOSECOMBAT("Close Combat", MoveType.PHYSICAL, Type.FIGHTING, true, 1.0, 5, 120, 100, 0, -1, Arrays.asList("sp. attack", "sp. defense"),
            "The user fights the foe up\nclose without guarding\nitself. It also cuts the\nuser's Defense and Sp. Def."),
    COMETPUNCH("Comet Punch", MoveType.PHYSICAL, Type.NORMAL, 15, 18, 85, 0,
            "The foe is hit with a flurry\nof punches that strike two\nto five times in a row."),
    CONFUSERAY("Confuse Ray", MoveType.STATUS, Type.GHOST, Status.CONFUSE, 10, -1, 100, 0,
            "The foe is exposed to a\nsinister ray that triggers\nconfusion."),
    CONFUSION("Confusion", MoveType.SPECIAL, Type.PSYCHIC, 25, 75, 100, 0,
            "The foe is hit by a weak\ntelekinetic force. It may\nalso leave the foe confused."),
    COSMICPOWER("Cosmic Power", MoveType.ATTRIBUTE, Type.PSYCHIC, true, 20, -1, -1, 0, 1, Arrays.asList("defense", "sp. defense"),
            "The user absorbs a mystical\npower from space to raise\nits Defense and Sp. Def\nstats."),
    COUNTER("Counter", MoveType.PHYSICAL, Type.FIGHTING, 20, -1, 100, -5,
            "A retaliation move that\ncounters any physical\nattack, inflicting double\nthe damage taken."),
    CROSSCHOP("Cross Chop", MoveType.PHYSICAL, Type.FIGHTING, 5, 150, 80, 0, 1,
            "The user delivers a double\nchop with its forearms\ncrossed. It has a high\ncritical-hit ratio."),
    CRUNCH("Crunch", MoveType.PHYSICAL, Type.DARK, 15, 80, 100, 0,
            "The user crunches up the\nfoe with sharp fangs."),
    CUT("Cut", MoveType.PHYSICAL, Type.NORMAL, 30, 50, 95, 0,
            "The foe is cut with a scythe\nor a claw. It can also be\nused to cut down thin\ntrees."),
    DARKPULSE("Dark Pulse", MoveType.SPECIAL, Type.DARK, 0.20, 15, 80, 100, 0,
            "The user releases a horrible\naura imbued with dark\nthoughts. It may also make\nthe target flinch."),
    DEFENSECURL("Defense Curl", MoveType.ATTRIBUTE, Type.NORMAL, true, 40, -1, -1, 0, 1, List.of("defense"),
            "The user curls up to conceal\nweak spots and raise its\nDefense stat."),
    DETECT("Detect", MoveType.OTHER, Type.FIGHTING, 5, -1, -1, 3,
            "It enables the user to\nevade all attacks."),
    DIG("Dig", MoveType.PHYSICAL, Type.GROUND, 10, 80, 100, 0, 1, false, Protection.DIG,
            "dug\ninto the ground!",
            "The user burrows, then\nattacks on the second turn.\nIt can also be used to exit\ndungeons."),
    DISCHARGE("Discharge", MoveType.SPECIAL, Type.ELECTRIC, Status.PARALYZE, 0.30, 15, 80, 100, 0,
            "A flare of electricity is\nloosed to strike the foe.\nIt may also cause\nparalysis."),
    DOOMDESIRE("Doom Desire", MoveType.OTHER, Type.STEEL, 5, 120, 85, 0, 3,
            "",
            "Two turns after this move\nis used, the user blasts the\nfoe with a concentrated\nbundle of light."),
    DOUBLEEDGE("Double Edge", MoveType.PHYSICAL, Type.STEEL, 10, 80, 100, 0, 0.25,
            "A reckless, life-risking\ntackle. It also damages the\nuser by a fairly large\namount."),
    DOUBLEKICK("Double Kick", MoveType.PHYSICAL, Type.FIGHTING, 35, 60, 100, 0,
            "The foe is quickly kicked\ntwice in succession using\nboth feet."),
    DOUBLETEAM("Double Team", MoveType.ATTRIBUTE, Type.NORMAL, true, 15, -1, -1, 0, 1, List.of("evasion"),
            "By moving rapidly, the user\nmakes illusory copies of\nitself to raise its\nevasiveness."),
    DRAGONBREATH("Dragon Breath", MoveType.SPECIAL, Type.DRAGON, Status.PARALYZE, 0.10, 20, 60, 100, 0,
            "The user exhales a mighty\ngust that inflicts damage.\nIt may also paralyze the\ntarget."),
    DRAGONCLAW("Dragon Claw", MoveType.PHYSICAL, Type.DRAGON, 15, 80, 100, 0,
            "The user slashes the foe\nwith huge, sharp claws."),
    DRAGONDANCE("Dragon Dance", MoveType.ATTRIBUTE, Type.DRAGON, true, 20, -1, -1, 0, 1, Arrays.asList("attack", "speed"),
            "The user vigorously\nperforms a mystic, powerful\ndance that raises its\nAttack and Speed stats."),
    DRAGONPULSE("Dragon Pulse", MoveType.SPECIAL, Type.DRAGON, 10, 90, 100, 0,
            "The foe is attacked with a\nshock wave generated by\nthe user's gaping mouth."),
    DRAGONRAGE("Dragon Rage", MoveType.SPECIAL, Type.DRAGON, 10, 1, 100, 0,
            "The foe is stricken by a\nshock wave. This attack\nalways inflicts 40 HP\ndamage."),
    DRAGONRUSH("Dragon Rush", MoveType.PHYSICAL, Type.DRAGON, 10, 100, 75, 0, 1,
            "The user tackles the foe\nwhile exhibiting over-\nwhelming menace. It may also\nmake the target flinch."),
    DREAMEATER("Dream Eater", MoveType.SPECIAL, Type.PSYCHIC, 15, 100, 100, 0,
            "An attack that works only\non a sleeping foe. It\nabsorbs half the damage\ndealt to heal the user's HP."),
    DRILLPECK("Drill Peck", MoveType.PHYSICAL, Type.FLYING, 20, 80, 100, 0,
            "A corkscrewing attack with\nthe sharp beak acting as a\ndrill."),
    DYNAMICPUNCH("Dynamic Punch", MoveType.PHYSICAL, Type.FIGHTING, Status.CONFUSE, 1.0, 5, 150, 50, 0,
            "The foe is punched with the\nuser's full, concentrated\npower. It confuses the foe\nif it hits."),
    EARTHQUAKE("Earthquake", MoveType.PHYSICAL, Type.GROUND, 10, 150, 100, 0,
            "The user sets off an\nearthquake that hits all\nthe Pokémon in the battle."),
    EARTHPOWER("Earth Power", MoveType.SPECIAL, Type.GROUND, false, 0.10, 10, 90, 100, 0, -1, List.of("sp. defense"),
            "The user makes the ground\nunder the foe erupt with\npower. It may also lower\nthe target's Sp. Def."),
    EMBER("Ember", MoveType.SPECIAL, Type.FIRE, Status.BURN, 0.10, 25, 60, 100, 0,
            "The foe is attacked with\nsmall flames. The target\nmay also be left with a\nburn."),
    ENDEAVOR("Endeavor", MoveType.PHYSICAL, Type.NORMAL, 5, 1, 100, 0,
            "An attack move that cuts\ndown the foe's HP to equal\nthe user's HP."),
    ENDURE("Endure", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 3,
            "The user endures any attack\nwith at least 1 HP."),
    ENERGYBALL("Energy Ball", MoveType.SPECIAL, Type.GRASS, false, 0.10, 10, 90, 100, 0, -1, List.of("sp. defense"),
            "The user draws power from\nnature and fires it at the\ntarget. It may also lower\nthe target's Sp. Def."),
    ERUPTION("Eruption", MoveType.SPECIAL, Type.FIRE, 5, 150, 100, 0,
            "The user attacks in an expl-\nosive fury. The lower the\nuser's HP, the less power-\nful this attack becomes."),
    EXPLOSION("Explosion", MoveType.PHYSICAL, Type.NORMAL, 5, 250, 100, 0,
            "The user blows up to inflict\ndamage on the foe. The user\nfaints upon using this move."),
    EXTRASENSORY("Extrasensory", MoveType.SPECIAL, Type.PSYCHIC, 30, 80, 100, 0,
            "The user attacks with an\nodd, unseeable power."),
    EXTREMESPEED("Extreme Speed", MoveType.PHYSICAL, Type.NORMAL, 5, 80, 100, 1,
            "The user charges the foe\nat blinding speed. This\nattack always goes before\nany other move."),
    FALSESWIPE("False Swipe", MoveType.PHYSICAL, Type.NORMAL, 40, 40, 100, 0,
            "A restrained attack that\nprevents the foe from fain-\nting. The target is left\nwith at least 1 HP."),
    FEINT("Feint", MoveType.PHYSICAL, Type.NORMAL, 10, 50, 100, 0,
            "An attack that hits a foe\nusing Protect or Detect.\nIt also lifts the effects\nof those moves."),
    FIREBLAST("Fire Blast", MoveType.SPECIAL, Type.FIRE, Status.BURN, 0.10, 5, 120, 85, 0,
            "The foe is attacked with an\nintense blast of fire. It\nmay also leave the target\nwith a burn."),
    FIREFANG("Fire Fang", MoveType.PHYSICAL, Type.FIRE, Status.BURN, 0.10, 0.10, 15, 95, 95, 0,
            "The user bites with flame\n-cloaked fangs. It may also\nleave the target with a\nburn"),
    FIREPUNCH("Fire Punch", MoveType.PHYSICAL, Type.FIRE, Status.BURN, 0.10, 15, 75, 100, 0,
            "The foe is punched with a\nfiery fist. It may leave the\ntarget with a burn."),
    FIRESPIN("Fire Spin", MoveType.SPECIAL, Type.FIRE, 15, 15, 70, 0,
            "The foe becomes trapped\nwithin a fierce vortex of\nfire that rages for two to\nfive turns."),
    FISSURE("Fissure", MoveType.PHYSICAL, Type.GROUND, 5, 999, 30, 0,
            "The user opens up a fissure\nin the ground and drops the\nfoe in. The target instnant-\nly faints if it hits."),
    FLAIL("Flail", MoveType.PHYSICAL, Type.NORMAL, 15, -1, 100, 0,
            "The user flails about aiml-\nessly to attack. It becomes\nmore powerful the less HP\nthe user has."),
    FLAMETHROWER("Flamethrower", MoveType.SPECIAL, Type.FIRE, Status.BURN, 0.10, 15, 135, 100, 0,
            "The foe is scorched with an\nintense blast of fire.\nThe target may also be\nleft with a burn."),
    FLAMEWHEEL("Flame Wheel", MoveType.PHYSICAL, Type.FIRE, Status.BURN, 0.10, 25, 60, 100, 0,
            "The user cloaks itself in\nfire and charges at the foe.\nIt may also leave the\ntarget with a burn."),
    FLAREBLITZ("Flare Blitz", MoveType.PHYSICAL, Type.FIRE, Status.BURN, 0.10, 15, 120, 100, 0,
            "The foe is scorched with an\nintense blast of fire.\nThe target may also be\nleft with a burn."),
    FLASHCANNON("Flash Cannon", MoveType.SPECIAL, Type.STEEL, 10, 80, 100, 0,
            "The user gathers all its\nlight energy and releases it at\nonce."),
    FLING("Fling", MoveType.OTHER, Type.DARK, 10, -1, 100, 0,
            "The user flings its held\nitem at the foe to attack.\nIts power and effects\ndepend on the item."),
    FLY("Fly", MoveType.PHYSICAL, Type.FLYING, 15, 90, 95, 0, 1, false, Protection.FLY,
            "took\nflight!",
            "The user soars, then\nstrikes on the second turn.\nIt can also be used for\nflying to any familiar town."),
    FOCUSBLAST("Focus Blast", MoveType.SPECIAL, Type.FIGHTING, false, 0.10, 5, 120, 70, 0, -1, List.of("sp. defense"),
            "The user heightens its\nmental focus and unleashes\nits power. It may also\nlower the target's Sp. Def."),
    FOCUSENERGY("Focus Energy", MoveType.OTHER, Type.NORMAL, 30, -1, -1, 0,
            "The user takes a deep\nbreath and focuses to raise\nthe critical-hit ratio\nof its attacks."),
    FORESIGHT("Foresight", MoveType.OTHER, Type.NORMAL, 40, -1, -1, 0,
            "Enables the user to hit a\nGhost type with any type of\nmove. It also enables the\nuser to hit an evasive foe."),
    FRENZYPLANT("Frenzy Plant", MoveType.SPECIAL, Type.GRASS, 5, 150, 90, 0, 1, true, Protection.NONE,
            "is\nresting...",
            "The foe is slammed with an\nenormous tree. The user\ncan't move on the next\nturn."),
    FURYATTACK("Fury Attack", MoveType.PHYSICAL, Type.NORMAL, 20, 15, 85, 0,
            "The foe is jabbed repeatedly\nwith a horn or beak two to\nfive times in a row."),
    FURYCUTTER("Fury Cutter", MoveType.PHYSICAL, Type.BUG, 20, 10, 95, 0,
            "The foe is slashed with\nscythes or claws. Its power\nincreases if it hits in\nsuccession."),
    FURYSWIPES("Fury Swipes", MoveType.PHYSICAL, Type.NORMAL, 15, 18, 80, 0,
            "The foe is raked with sharp\nclaws or scythes for two to\nfive times in quick\nsuccession."),
    FUTURESIGHT("Future Sight", MoveType.OTHER, Type.PSYCHIC, 15, 80, 90, 0, 2,
            "",
            "Two turns after this move\nis used, the foe is attacked\nwith a hunk of psychic\nenergy."),
    GIGADRAIN("Giga Drain", MoveType.SPECIAL, Type.GRASS, 10, 60, 100, 0,
            "A nutrient-draining attack.\nThe user's HP is restored\nby half the damage taken by\nthe target."),
    GIGAIMPACT("Giga Impact", MoveType.PHYSICAL, Type.NORMAL, 5, 150, 90, 0, 1, true, Protection.NONE,
            "is\nresting...",
            "The user charges at the foe\nusing every bit of its power.\nThe user must rest on the\nnext turn."),
    GRAVITY("Gravity", MoveType.OTHER, Type.PSYCHIC, 5, -1, -1, 0,
            "Gravity is intensified for\nfive turns, making moves\ninvolving flying unusable\nand negating Levitation."),
    GROWL("Growl", MoveType.ATTRIBUTE, Type.NORMAL, false, 40, -1, 100, 0, -1, List.of("attack"),
            "The user growls, making the\nfoe less wary. The target's\nAttack stat is lowered."),
    GROWTH("Growth", MoveType.ATTRIBUTE, Type.NORMAL, true, 20, -1, -1, 0, 1, Arrays.asList("attack", "sp. attack"),
            "The user's body grows all at\nonce, raising the Attack\nand Sp. Atk stats."),
    GRUDGE("Grudge", MoveType.OTHER, Type.GHOST, 5, -1, -1, 0,
            "If the user faints, the\nuser's grudge fully depletes\nthe PP of the foe's move\nthat knocked it out."),
    GUST("Gust", MoveType.SPECIAL, Type.FLYING, 35, 40, 100, 0,
            "A gust of wind is whipped up\nby wings and launched at the\nfoe to inflict damage."),
    HAIL("Hail", MoveType.WEATHER, Type.ICE, Weather.HAIL, 10, -1, -1, 0, 5,
            "The user summons a hail-\nstorm lasting five turns.\nIt damages all Pokémon\nexcept the Ice type."),
    HAMMERARM("Hammer Arm", MoveType.PHYSICAL, Type.FIGHTING, true, 1.0, 10, 100, 90, 0, -1, List.of("speed"),
            "The user swings and hits\nwith its strong and heavy\nfist. It lowers the user's\nSpeed, however."),
    HARDEN("Harden", MoveType.ATTRIBUTE, Type.NORMAL, true, 30, -1, -1, 0, 1, List.of("defense"),
            "The user stiffens all the\nmuscles in its body to raise\nits Defense stat."),
    HAZE("Haze", MoveType.OTHER, Type.ICE, 30, -1, -1, 0,
            "The user creates a haze\nthat eliminates every stat\nchange among both Pokémon\nin battle."),
    HEADBUTT("Headbutt", MoveType.PHYSICAL, Type.NORMAL, 0.30, 15, 70, 100, 0,
            "The user attacks with its\nhead. It may make the foe\nflinch."),
    HEALBELL("Heal Bell", MoveType.OTHER, Type.NORMAL, 5, -1, -1, 0,
            "The user makes a soothing\nbell chime to heal the\nstatus problems of all the\nparty Pokémon."),
    HEALBLOCK("Heal Block", MoveType.OTHER, Type.PSYCHIC, 15, -1, 100, 0, 4,
            "wore off!",
            "The user prevents the foe\nfrom using any HP-recovery\nmoves for five turns."),
    HEATWAVE("Heat Wave", MoveType.SPECIAL, Type.FIRE, Status.BURN, 0.10, 10, 100, 90, 0,
            "The user exhales a heated\nbreath on the foe to\nattack. It may also leave\nthe target with a burn."),
    HEAVYSLAM("Heavy Slam", MoveType.PHYSICAL, Type.NORMAL, 20, 80, 75, 0,
            "The user slams into the\ntarget with its heavy body."),
    HEX("Hex", MoveType.SPECIAL, Type.GHOST, 10, 95, 100, 0,
            "This relentless attack does\nmassive damage to a target\naffected by status\nconditions."),
    HIGHJUMPKICK("High Jump Kick", MoveType.PHYSICAL, Type.FIGHTING, 20, 100, 90, 0,
            "The foe is attacked with a\nknee kick from a jump. If it\nmisses, the user is hurt\ninstead."),
    HOWL("Howl", MoveType.ATTRIBUTE, Type.NORMAL, true, 40, -1, -1, 0, 1, List.of("attack"),
            "The user howls loudly to\nraise its spirit, boosting\nits Attack stat."),
    HYDROCANNON("Hydro Cannon", MoveType.SPECIAL, Type.WATER, 5, 150, 90, 0, 1, true, Protection.NONE,
            "is\nresting...",
            "The foe is hit with a watery\nblast. The user must rest\non the next turn, however."),
    HYDROPUMP("Hydro Pump", MoveType.SPECIAL, Type.WATER, 5, 165, 80, 0,
            "The foe is blasted by a huge\nvolume of water launched\nunder great pressure."),
    HYPERBEAM("Hyper Beam", MoveType.SPECIAL, Type.NORMAL, 5, 150, 90, 0, 1, true, Protection.NONE,
            "is\nrecharging...",
            "The foe is attacked with a\npowerful beam. The user\nmust rest on the next turn\nto regain its energy."),
    HYPERVOICE("Hyper Voice", MoveType.SPECIAL, Type.NORMAL, 10, 90, 100, 0,
            "The user lets loose a\nhorribly echoing shout with\nthe power to inflict damage."),
    HYPNOSIS("Hypnosis", MoveType.STATUS, Type.PSYCHIC, Status.SLEEP, 20, -1, 60, 0,
            "The user employs hypnotic\nsuggestion to make the\ntarget fall into a deep\nsleep."),
    ICEBEAM("Ice Beam", MoveType.SPECIAL, Type.ICE, Status.FREEZE, 0.10, 10, 95, 100, 0,
            "The foe is struck with an\nicy-cold beam of energy.\nIt may also freeze the\ntarget solid."),
    ICEFANG("Ice Fang", MoveType.PHYSICAL, Type.ICE, Status.FREEZE, 0.10, 15, 65, 95, 0,
            "The user bites with cold-\ninfused fangs. It may also\nmake the foe freeze."),
    ICEPUNCH("Ice Punch", MoveType.PHYSICAL, Type.ICE, Status.FREEZE, 0.10, 15, 75, 100, 0,
            "The foe is punched with an\nicy fist. It may leave the\ntarget frozen."),
    ICESHARD("Ice Shard", MoveType.PHYSICAL, Type.ICE, 30, 40, 100, 1,
            "The user flash freezes\nchunks of ice and hurls them\nat the target. This move\nalways goes first."),
    IMPRISON("Imprison", MoveType.OTHER, Type.PSYCHIC, 10, -1, -1, 0,
            "If the foe knows any\nmove also known by the\nuser, the foe is prevented\nfrom using it."),
    IRONDEFENSE("Iron Defense", MoveType.ATTRIBUTE, Type.STEEL, true, 15, -1, -1, 0, 2, List.of("defense"),
            "The user hardens its body's\nsurface like iron, sharply\nraising its Defense stat."),
    IRONTAIL("Iron Tail", MoveType.PHYSICAL, Type.STEEL, false, 0.30, 15, 100, 75, 0, -1, List.of("defense"),
            "The foe is slammed with a\nsteel-hard tail. It may also\nlower the target's Defense\nstat."),
    JUMPKICK("Jump Kick", MoveType.PHYSICAL, Type.FIGHTING, 25, 85, 95, 0,
            "The user jumps up high, then\nstrikes with a kick. If the\nkick misses, the user hurts\nitself."),
    KARATECHOP("Karate Chop", MoveType.PHYSICAL, Type.NORMAL, 25, 50, 100, 0, 1,
            "The foe is attacked with a\nsharp chop. It has a high\ncritical-hit ratio."),
    KINESIS("Kinesis", MoveType.ATTRIBUTE, Type.PSYCHIC, false, 15, -1, 80, 0, -1, List.of("accuracy"),
            "The user distracts the foe\nby bending a spoon. It may\nlower the target's\naccuracy."),
    KNOCKOFF("Knock Off", MoveType.PHYSICAL, Type.DARK, 20, 65, 100, 0,
            "The user slaps down the\ntarget's held item,\npreventing that item from\nbeing used in the battle."),
    LASTRESORT("Last Resort", MoveType.PHYSICAL, Type.NORMAL, 5, 130, 100, 0,
            "This move can be used only\nafter the user has used all\nthe other moves it knows in\nthe battle."),
    LAVAPLUME("Lava Plume", MoveType.SPECIAL, Type.FIRE, Status.BURN, 0.30, 15, 80, 100, 0,
            "An inferno of scarlet flames\nwashes over the foe. It may\nalso inflict burns."),
    LEAFBLADE("Leaf Blade", MoveType.PHYSICAL, Type.GRASS, 15, 90, 100, 0, 1,
            "The user handles a sharp\nleaf like a sword and\nattacks by cutting its\ntarget."),
    LEAFSTORM("Leaf Storm", MoveType.SPECIAL, Type.GRASS, 5, 140, 90, 0,
            "A storm of sharp leaves is\nwhipped up."),
    LEECHLIFE("Leech Life", MoveType.PHYSICAL, Type.BUG, 15, 20, 100, 0,
            "A blood-draining attack.\nThe user's HP is restored\nby half the damage taken\nby the target."),
    LEECHSEED("Leech Seed", MoveType.OTHER, Type.GRASS, 10, -1, 90, 0,
            "A seed is planted on the foe.\nIt steals some HP from the\nfoe to heal the user on\nevery turn."),
    LEER("Leer", MoveType.ATTRIBUTE, Type.NORMAL, false, 30, -1, 100, 0, -1, List.of("defense"),
            "The foe is given an intimida-\nting leer with sharp eyes.\nThe target's Defense stat\nis reduced."),
    LICK("Lick", MoveType.PHYSICAL, Type.GHOST, Status.PARALYZE, 0.10, 30, 45, 100, 0,
            "The foe is licked with a long\ntongue, causing damage.\nIt may also paralyze the\ntarget."),
    LIGHTSCREEN("Light Screen", MoveType.OTHER, Type.PSYCHIC, 30, -1, -1, 0, 4,
            "wore off!",
            "A wondrous wall of light is\nput up to suppress damage\nfrom special attacks for\nfive turns."),
    LOWKICK("Low Kick", MoveType.PHYSICAL, Type.FIGHTING, 20, 40, 100, 0,
            "A powerful low kick that\nmakes the foe fall over.\nIt inflicts greater damage\non heavier foes."),
    LOWSWEEP("Low Sweep", MoveType.PHYSICAL, Type.FIGHTING, 20, 95, 100, 0,
            "The user makes a swift\nattack on the target's\nlegs"),
    LUCKYCHANT("Lucky Chant", MoveType.OTHER, Type.NORMAL, 30, -1, -1, 0, 4,
            "wore off!",
            "The user chants an\nincantation toward the sky,\npreventing the foe from\nlanding critical hits."),
    MACHPUNCH("Mach Punch", MoveType.PHYSICAL, Type.FIGHTING, 30, 40, 100, 1,
            "The user throws a punch at\nblinding speed. It is\ncertain to strike first."),
    MAGICALLEAF("Magical Leaf", MoveType.SPECIAL, Type.GRASS, 20, 60, -1, 0,
            "The user scatters curious\nleaves that chase the foe.\nThis attack will not miss."),
    MAGNETRISE("Magnet Rise", MoveType.OTHER, Type.ELECTRIC, 10, -1, -1, 0, 4,
            "wore off!",
            "The user levitates using\nelectrically generated\nmagnetism for five turns."),
    MAGNITUDE("Magnitude", MoveType.PHYSICAL, Type.GROUND, 30, -1, 100, 0,
            "The user looses a ground-\nshaking quake affecting\neveryone in battle. Its\npower varies."),
    MEANLOOK("Mean Look", MoveType.OTHER, Type.NORMAL, 5, -1, -1, 0,
            "The user affixes the foe\nwith a dark, arresting look.\nThe target becomes unable\nto flee."),
    MEDITATE("Meditate", MoveType.ATTRIBUTE, Type.PSYCHIC, true, 40, -1, -1, 0, 1, List.of("attack"),
            "The user meditates to\nawaken the power deep\nwithin its body and raise\nits Attack stat."),
    MEGADRAIN("Mega Drain", MoveType.SPECIAL, Type.GRASS, 15, 40, 100, 0,
            "A nutrient-draining attack.\nThe user's HP is restored by\nhalf the damage taken by\nthe target."),
    MEGAHORN("Megahorn", MoveType.PHYSICAL, Type.BUG, 10, 120, 85, 0,
            "Using its tough and\nimpressive horn, the user\nrams into the foe with no\nletup."),
    MEGAKICK("Mega Kick", MoveType.PHYSICAL, Type.NORMAL, 5, 120, 75, 0,
            "The foe is attacked by a\nkick launched with muscle-\npacked power."),
    MEGAPUNCH("Mega Punch", MoveType.PHYSICAL, Type.NORMAL, 20, 80, 85, 0,
            "The foe is slugged by a\npunch thrown with muscle-\npacked power."),
    METALCLAW("Metal Claw", MoveType.PHYSICAL, Type.STEEL, true, 35, 50, 95, 0, 1, List.of("attack"),
            "The foe is raked with steel\nclaws. It may also raise the\nuser's Attack stat."),
    METEORMASH("Meteor Mash", MoveType.PHYSICAL, Type.STEEL, true, 0.20, 10, 100, 85, 0, 1, List.of("attack"),
            "The foe is hit with a hard\npunch fired like a meteor.\nIt may also raise the user's\nAttack."),
    METRONOME("Metronome", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 0,
            "The user waggles a finger\nand stimulates the brain\ninto randomly using nearly\nany move."),
    MINDREADER("Mind Reader", MoveType.OTHER, Type.NORMAL, 5, -1, -1, 0, 2,
            "",
            "The user senses the foe's\nmovements with its mind to\nensure its next attack does\nnot miss."),
    MIST("Mist", MoveType.OTHER, Type.ICE, 30, -1, -1, 0, 4,
            "wore off!",
            "The user cloaks its body\nwith a white mist that pre-\nvents any of its stats from\nbeing cut for five turns."),
    MIRACLEEYE("Miracle Eye", MoveType.OTHER, Type.PSYCHIC, 40, -1, -1, 0,
            "Enables the user to hit a\nDark type with any type of\nmove. It also enables the\nuser to hit an evasive foe."),
    MUDBOMB("Mud Bomb", MoveType.SPECIAL, Type.GROUND, false, 0.30, 10, 65, 85, 0, -1, List.of("accuracy"),
            "The user launches a hard-\npacked mud ball to attack.\nIt may also lower the\ntarget's Accuracy."),
    MUDSHOT("Mud Shot", MoveType.SPECIAL, Type.GROUND, false, 1.0, 15, 55, 95, 0, -1, List.of("speed"),
            "The user attacks by hurling\na blob of mud at the foe.\nIt also reduces the\ntarget's Speed."),
    MUDSLAP("Mud Slap", MoveType.SPECIAL, Type.GROUND, false, 1.0, 10, 20, 100, 0, -1, List.of("accuracy"),
            "The user hurls mud in the\nfoe's face to inflict damage\nand lower its Accuracy."),
    MUDDYWATER("Muddy Water", MoveType.SPECIAL, Type.WATER, false, 0.30, 10, 95, 85, 0, -1, List.of("accuracy"),
            "The user attacks by\nshooting out muddy water.\nIt may also lower the foe's\nAccuracy."),
    NASTYPLOT("Nasty Plot", MoveType.ATTRIBUTE, Type.DARK, true, 20, -1, -1, 0, 2, List.of("sp. attack"),
            "The user stimulates its\nbrain by thinking bad\nthoughts. It sharply raises\nthe user's Sp. Atk."),
    NIGHTSHADE("Night Shade", MoveType.SPECIAL, Type.GHOST, 15, 1, 100, 0,
            "The user makes the foe see\na mirage. It inflicts damage\nmatching the user's level."),
    ODORSLEUTH("Odor Sleuth", MoveType.OTHER, Type.NORMAL, 40, -1, -1, 0,
            "Enables the user to hit a\nGhost type with any type of\nmove. It also enables the\nuser to hit an evasive foe."),
    OUTRAGE("Outrage", MoveType.PHYSICAL, Type.DRAGON, 15, 120, 100, 0, 2, false, Protection.NONE,
            "",
            "The user rampages and\nattacks for two to three\nturns. However, it then\nbecomes confused."),
    PAYBACK("Payback", MoveType.PHYSICAL, Type.DARK, 10, 50, 100, 0,
            "If the user can use this\nattack after the foe\nattacks, its power is\ndoubled."),
    PECK("Peck", MoveType.PHYSICAL, Type.FLYING, 35, 35, 100, 0,
            "The foe is jabbed with a\nsharply pointed beak or\nhorn."),
    PERISHSONG("Perish Song", MoveType.OTHER, Type.NORMAL, 5, -1, -1, 0, 3,
            "",
            "Any Pokémon that hears this\nsong faints in three turns,\nunless it switches out of\nbattle."),
    PETALBLIZZARD("Petal Blizzard", MoveType.PHYSICAL, Type.GRASS, 15, 135, 100, 0,
            "The user stirs up a violent\npetal blizzard and attacks\neverything around it."),
    PETALDANCE("Petal Dance", MoveType.SPECIAL, Type.GRASS, 20, 90, 100, 0, 2, false, Protection.NONE,
            "",
            "The user attacks by scatt-\nering petals for two to\nthree turns. The user then\nbecomes confused."),
    PLAYNICE("Play Nice", MoveType.ATTRIBUTE, Type.NORMAL, false, 20, -1, -1, 0, -1, List.of("attack"),
            "The user and the target\nbecome friends. This lowers\nthe target's Attack stat."),
    PLUCK("Pluck", MoveType.PHYSICAL, Type.FLYING, 20, 60, 100, 0,
            "The user pecks the foe. If\nthe foe is holding a Berry,\nthe user plucks it and gains\nits effect."),
    POISONFANG("Poison Fang", MoveType.PHYSICAL, Type.POISON, Status.POISON, 0.30, 15, 50, 100, 0,
            "The user bites the foe with\ntoxic fangs. It may also\nleave the foe badly\npoisoned."),
    POISONJAB("Poison Jab", MoveType.PHYSICAL, Type.POISON, Status.POISON, 0.30, 20, 80, 100, 0,
            "The foe is stabbed with a\ntentacle or arm steeped in\npoison. It may also poison\nthe foe."),
    POISONPOWDER("Poison Powder", MoveType.STATUS, Type.POISON, Status.POISON, 45, -1, 75, 0,
            "A cloud of poisonous dust\nis scattered on the foe.\nIt may poison the target."),
    POUND("Pound", MoveType.PHYSICAL, Type.NORMAL, 35, 40, 100, 0,
            "The foe is physically\npounded with a long tail or\na foreleg, etc."),
    POWDERSNOW("Powder Snow", MoveType.SPECIAL, Type.ICE, Status.FREEZE, 0.10, 25, 40, 100, 0,
            "The user attacks with a\nchilling gust of powdery\nsnow. It may also freeze\nthe target."),
    PROTECT("Protect", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 0, 3,
            "It enables the user to\nevade all attacks."),
    PSYBEAM("Psybeam", MoveType.SPECIAL, Type.PSYCHIC, Status.CONFUSE, 1.0, 20, 95, 100, 0,
            "The foe is attacked with a\npeculiar ray. It may also\nleave the target confused."),
    PSYCHIC("Psychic", MoveType.SPECIAL, Type.PSYCHIC, 10, 135, 100, 0,
            "The foe is hit by a strong\ntelekinetic force."),
    PSYCHOBOOST("Psycho Boost", MoveType.SPECIAL, Type.PSYCHIC, false, 1.0, 5, 140, 90, 0, -2, List.of("sp. attack"),
            "The user attacks the foe at\nfull power. The attack's\nrecoil sharply reduces the\nuser's Sp. Atk stat."),
    PSYCHOCUT("Psycho Cut", MoveType.PHYSICAL, Type.PSYCHIC, 20, 105, 100, 0, 1,
            "The user tears at the foe\nwith blades formed by\npsychic power. Critical hits\nland more easily."),
    PSYCHOSHIFT("Psycho Shift", MoveType.OTHER, Type.PSYCHIC, 10, -1, 90, 0,
            "Using its psychic power of\nsuggestion, the user trans-\nfers its status problems to\nthe target. "),
    PSYCHUP("Psych UP", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 0,
            "The user hypnotizes itself\ninto copying any stat\nchange made by the foe."),
    PUNISHMENT("Punishment", MoveType.PHYSICAL, Type.DARK, 5, -1, 100, 0,
            "This attack's power incr-\neases the more the foe has\npowered up with stat\nchanges."),
    PURSUIT("Pursuit", MoveType.PHYSICAL, Type.DARK, 20, 40, 100, 0,
            "An attack move that inflicts\ndouble damage if used on a\nfoe that is switching out of\nbattle."),
    QUICKATTACK("Quick Attack", MoveType.PHYSICAL, Type.NORMAL, 30, 40, 100, 1,
            "The user lunges at the foe\nat a speed that makes it\nalmost invisible. It is sure\nto strike first."),
    RAGE("Rage", MoveType.PHYSICAL, Type.NORMAL, true, 20, 20, 100, 0, 1, List.of("attack"),
            "While this move is in use,\nit gains attack power each\ntime the user is hit in\nbattle."),
    RAINDANCE("Rain Dance", MoveType.WEATHER, Type.WATER, Weather.RAIN, 5, -1, -1, 0, 5,
            "The user summons a heavy\nrain that falls for five\nturns, powering up Water-\ntype moves."),
    RAPIDSPIN("Rapid Spin", MoveType.PHYSICAL, Type.NORMAL, 40, 20, 100, 0,
            "A spin attack that can also\neliminate such moves as\nBind, Wrap, Leech Seed, and\nSpikes."),
    RAZORLEAF("Razor Leaf", MoveType.PHYSICAL, Type.GRASS, 25, 80, 95, 0, 1,
            "Sharp-edged leaves are\nlaunched to slash at the\nfoe. It has a high critical-\nhit ratio."),
    RECOVER("Recover", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 0,
            "A self-healing move. The\nuser restores its own HP by\nup to half of its max HP."),
    REFLECT("Reflect", MoveType.OTHER, Type.NORMAL, 20, -1, -1, 0, 4,
            "wore off!",
            "A wondrous wall of light is\nput up to suppress damage\nfrom physical attacks for\nfive turns."),
    REFRESH("Refresh", MoveType.OTHER, Type.NORMAL, 20, -1, -1, 0,
            "The user rests to cure\nitself of a poisoning, burn,\nor paralysis."),
    REST("Rest", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 0,
            "The user goes to sleep for\ntwo turns. It fully\nrestores the user's HP and\nheals any status problem."),
    REVENGE("Revenge", MoveType.PHYSICAL, Type.FIGHTING, 10, 60, 100, -4,
            "An attack move that inflicts\ndouble the damage if the\nuser has been hurt by the\nfoe in the same turn."),
    REVERSAL("Reversal", MoveType.PHYSICAL, Type.FIGHTING, 15, -1, 100, 0,
            "An all-out attack that\nbecomes more powerful the\nless HP the user has."),
    ROCKBLAST("Rock Blast", MoveType.PHYSICAL, Type.ROCK, 10, 25, 80, 0, 2, false, Protection.NONE,
            "",
            "The user hurls hard rocks\nat the foe. Two to five\nrocks are launched in\nquick succession."),
    ROCKPOLISH("Rock Polish", MoveType.ATTRIBUTE, Type.ROCK, true, 20, -1, -1, 0, 2, List.of("speed"),
            "The user polishes its body\nto reduce drag. It can\nsharply raise the Speed\nstat."),
    ROCKSMASH("Rock Smash", MoveType.PHYSICAL, Type.FIGHTING, false, 0.50, 15, 40, 100, 0, 1, List.of("defense"),
            "The user hits with a punch\nthat may lower the target's\nDefense. It can also smash\ncracked boulders."),
    ROCKSLIDE("Rock Slide", MoveType.PHYSICAL, Type.ROCK, 0.30, 10, 75, 90, 0,
            "Large boulders are hurled\nat opposing Pokémon to inflict\ndamage. This may also\nmake the opposing Pokémon flinch."),
    ROCKTHROW("Rock Throw", MoveType.PHYSICAL, Type.ROCK, 15, 75, 90, 0,
            "The user picks up and\nthrows a small rock at the\nfoe to attack."),
    ROLLINGKICK("Rolling Kick", MoveType.PHYSICAL, Type.FIGHTING, 15, 60, 85, 0, 1,
            "The user lashes out with a\nquick, spinning kick. It may\nalso make the target flinch."),
    ROLLOUT("Rollout", MoveType.PHYSICAL, Type.ROCK, 20, 45, 90, 0, 2, false, Protection.NONE,
            "",
            "The user continually rolls\ninto the foe two to five\nturns."),
    ROOST("Roost", MoveType.OTHER, Type.FLYING, 10, -1, -1, 0,
            "The user lands and rests\nits body. It restores the\nuser's HP by up to half of\nits max HP."),
    SACREDFIRE("Sacred Fire", MoveType.PHYSICAL, Type.FIRE, Status.BURN, 0.50, 5, 100, 95, 0,
            "The foe is razed with a\nmystical fire of great\nintensity. It may also leave\nthe target with a burn."),
    SANDATTACK("Sand Attack", MoveType.ATTRIBUTE, Type.GROUND, false, 15, 100, -1, 0, -1, List.of("accuracy"),
            "Sand is hurled in the foe's\nface, reducing its accuracy."),
    SAFEGUARD("Safeguard", MoveType.OTHER, Type.NORMAL, 25, -1, -1, 0, 4,
            "wore off!",
            "The user creates a protect-\nive field that prevents\nstatus problems for five\nturns."),
    SANDSTORM("Sandstorm", MoveType.WEATHER, Type.ROCK, Weather.SANDSTORM, 10, -1, -1, 0, 5,
            "A five-turn sand-storm is\nsummoned to hurt all\ncombatant types except\nRock, Ground, and Steel."),
    SCARYFACE("Scary Face", MoveType.ATTRIBUTE, Type.NORMAL, false, 10, -1, 90, 0, -2, List.of("speed"),
            "The user frightens the foe\nwith a scary face to\nsharply reduce its Speed\nstat."),
    SCRATCH("Scratch", MoveType.PHYSICAL, Type.NORMAL, 35, 40, 100, 0,
            "Hard, pointed, and sharp\nclaws rake the foe to\ninflict damage."),
    SCREECH("Screech", MoveType.ATTRIBUTE, Type.NORMAL, false, 40, -1, 85, 0, -2, List.of("defense"),
            "An earsplitting screech is\nemitted to sharply reduce\nthe foe's Defense stat."),
    SEEDBOMB("Seed Bomb", MoveType.PHYSICAL, Type.GRASS, 15, 80, 100, 0,
            "The user slams a barrage of\nhard-shelled seeds down on\nthe target from above."),
    SEISMICTOSS("Seismic Toss", MoveType.PHYSICAL, Type.FIGHTING, 20, -1, 100, 0,
            "The foe is thrown using the\npower of gravity. It\ninflicts damage equal to\nthe user's level."),
    SELFDESTRUCT("Selfdestruct", MoveType.PHYSICAL, Type.NORMAL, 5, 200, 100, 0,
            "The user blows up to inflict\ndamage on the foe. The user\nfaints upon using this move."),
    SHADOWBALL("Shadow Ball", MoveType.SPECIAL, Type.GHOST, 15, 120, 100, 0,
            "The user hurls a shadowy\nblob at the foe."),
    SHADOWPUNCH("Shadow Punch", MoveType.PHYSICAL, Type.GHOST, 20, 90, -1, 0,
            "The user throws a punch at\nthe foe from the shadows.\nThe punch lands without\nfail."),
    SHADOWSNEAK("Shadow Sneak", MoveType.PHYSICAL, Type.GHOST, 30, 40, 100, 1,
            "The user extends its shadow\nand attacks the foe from\nbehind. This move always\ngoes first."),
    SHEERCOLD("Sheer Cold", MoveType.SPECIAL, Type.ICE, 5, 999, 30, 0,
            "The foe is attacked with a\nblast of absolute-zero\ncold. The foe instantly\nfaints if it hits."),
    SKULLBASH("Skull Bash", MoveType.PHYSICAL, Type.NORMAL, 15, 100, 100, 0, 1, false, Protection.NONE,
            "is\npreparing its attack...",
            "The user tucks in its head\nto raise its Defense in the\nfirst turn, then rams the\nfoe on the next turn."),
    SKYATTACK("Sky Attack", MoveType.PHYSICAL, Type.FLYING, 0.30, 5, 140, 90, 0, 1, false, Protection.NONE,
            "became\ncloaked in a harsh light!",
            "A second-turn attack move\nwith a high critical-hit\nratio. It may also make the\ntarget flinch."),
    SKYUPPERCUT("Sky Uppercut", MoveType.PHYSICAL, Type.FIGHTING, 15, 120, 100, 0,
            "The user attacks the foe\nwith an uppercut thrown\nskyward with force."),
    SLAM("Slam", MoveType.PHYSICAL, Type.NORMAL, 20, 80, 75, 0,
            "The foe is slammed with a\nlong tail, vines, etc., to\ninflict damage."),
    SLASH("Slash", MoveType.PHYSICAL, Type.NORMAL, 20, 70, 100, 0, 1,
            "The foe is attacked with a\nslash of claws, etc. It\nhas a high critical-\nhit ratio."),
    SLEEPPOWDER("Sleep Powder", MoveType.STATUS, Type.GRASS, Status.SLEEP, 15, -1, 75, 0,
            "The user scatters a big\ncloud of sleep-inducing dust\naround the target."),
    SLEEPTALK("Sleep Talk", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 0,
            "While it is asleep, the user\nrandomly uses one of the\nmoves it knows."),
    SLUDGEBOMB("Sludge Bomb", MoveType.SPECIAL, Type.POISON, Status.POISON, 0.30, 10, 90, 100, 0,
            "Unsanitary sludge is hurled\nat the target. It may also\npoison the target."),
    SMOKESCREEN("Smokescreen", MoveType.ATTRIBUTE, Type.NORMAL, false, 20, -1, 100, 0, -1, List.of("accuracy"),
            "The user releases an obscu-\nring cloud of smoke or ink.\nIt reduces the target's\naccuracy."),
    SNATCH("Snatch", MoveType.OTHER, Type.DARK, 10, -1, -1, 4,
            "The user steals the effects\nof any healing or stat-\nchanging move the foe\nattempts to use."),
    SNORE("Snore", MoveType.SPECIAL, Type.NORMAL, 0.30, 15, 40, 100, 0,
            "An attack that can be used\nonly if the user is asleep.\nThe harsh noise may also\nmake the foe flinch."),
    SOLARBEAM("Solar Beam", MoveType.SPECIAL, Type.GRASS, 10, 180, 100, 0, 1, false, Protection.NONE,
            "is\ncharging a light beam...",
            "A two-turn attack. The\nuser gathers light, then\nblasts a bundled beam on\nthe second turn."),
    SPARK("Spark", MoveType.PHYSICAL, Type.ELECTRIC, Status.PARALYZE, 0.30, 20, 65, 100, 0,
            "The user throws an electr-\nically charged tackle at the\nfoe. It may also leave the\ntarget paralyzed."),
    SPITE("Spite", MoveType.OTHER, Type.GHOST, 10, -1, 100, 0,
            "The user looses its grudge\non the move last used by\nthe foe by cutting 4 PP\nfrom it."),
    SPLASH("Splash", MoveType.OTHER, Type.NORMAL, 40, -1, -1, 0,
            "The user just flops and\nsplashes around to no\neffect at all..."),
    STOMP("Stomp", MoveType.PHYSICAL, Type.NORMAL, 0.30, 20, 65, 100, 0,
            "The foe is stomped with a big\nfoot. It may also make the\ntarget flinch."),
    STONEEDGE("Stone Edge", MoveType.PHYSICAL, Type.ROCK, 5, 100, 90, 0, 1,
            "The user stabs the foe with\na sharpened stone. It has a\nhigh critical-hit ratio."),
    STRENGTH("Strength", MoveType.PHYSICAL, Type.NORMAL, 15, 80, 100, 0,
            "The foe is slugged with a\npunch thrown at maximum\npower. It can also be used\nto move boulders."),
    STRUGGLE("Struggle", MoveType.PHYSICAL, Type.NORMAL, 10, 50, 100, 0, 0.25,
            "struggle"),
    STUNSPORE("Stun Spore", MoveType.STATUS, Type.GRASS, Status.PARALYZE, 30, -1, 75, 0,
            "The user scatters a\ncloud of numbing powder\nthat paralyzes the target. "),
    SUBMISSION("Submission", MoveType.PHYSICAL, Type.FIGHTING, 25, 80, 80, 0, 0.25,
            "The user grabs the foe and\nrecklessly dives for the\nground. It also hurts the\nuser slightly."),
    SUCKERPUNCH("Sucker Punch", MoveType.PHYSICAL, Type.DARK, 5, 80, 100, 1,
            "This move enables the user\nto attack first. It fails if\nthe foe is not readying an\nattack, however."),
    SUNNYDAY("Sunny Day", MoveType.WEATHER, Type.FIRE, Weather.SUNLIGHT, 5, -1, -1, 0, 5,
            "The user intensifies the\nsun for five turns,\npowering up Fire-type\nmoves."),
    SUPERPOWER("Superpower", MoveType.PHYSICAL, Type.FIGHTING, true, 1.0, 5, 120, 100, 0, -1, Arrays.asList("attack", "defense"),
            "The user attacks the foe\nwith great power. However,\nit also lowers the user's\nAttack and Defense."),
    SUPERSONIC("Supersonic", MoveType.STATUS, Type.NORMAL, Status.CONFUSE, 20, -1, 55, 0,
            "The user generates odd\nsound waves from its body.\nIt may confuse the target."),
    SURF("Surf", MoveType.SPECIAL, Type.WATER, 15, 95, 100, 0,
            "It swamps the entire\nbattlefield with a giant\nwave. It can also be used\nfor crossing water."),
    SWAGGER("Swagger", MoveType.ATTRIBUTE, Type.NORMAL, false, 15, -1, 90, 0, 2, List.of("attack"),
            "The user enrages the foe\ninto confusion. However, it\nalso sharply raises the\nfoe's Attack stat."),
    SWEETSCENT("Sweet Scent", MoveType.ATTRIBUTE, Type.NORMAL, false, 20, -1, 100, 0, -1, List.of("evasion"),
            "A sweet scent that lowers\nthe foe's evasiveness."),
    SWIFT("Swift", MoveType.SPECIAL, Type.NORMAL, 20, 60, -1, 0,
            "Star-shaped rays are shot\nat the foe. This attack\nnever misses."),
    SWORDSDANCE("Swords Dance", MoveType.ATTRIBUTE, Type.NORMAL, true, 30, -1, -1, 0, 2, List.of("attack"),
            "A frenetic dance to uplift\nthe fighting spirit. It\nsharply raises the user's\nAttack stat."),
    SYNTHESIS("Synthesis", MoveType.OTHER, Type.GRASS, 5, -1, -1, 0,
            "The user restores its own\nHP. The amount of HP\nregained varies with the\nweather."),
    TACKLE("Tackle", MoveType.PHYSICAL, Type.NORMAL, 35, 40, 95, 0,
            "A physical attack in which\nthe user charges and slams\ninto the foe with its whole\nbody."),
    TAILWHIP("Tail Whip", MoveType.ATTRIBUTE, Type.NORMAL, false, 30, -1, 100, 0, -1, List.of("defense"),
            "The user wags its tail\ncutely, making the foe less\nwary. The target's Defense\nstat is lowered."),
    TAKEDOWN("Take Down", MoveType.PHYSICAL, Type.NORMAL, 20, 90, 85, 0, 0.25,
            "A reckless, full-body\ncharge attack for slamming\ninto the foe. It also\ndamages the user a little."),
    TELEPORT("Teleport", MoveType.OTHER, Type.PSYCHIC, 20, -1, -1, 0,
            "Use it to flee from any wild\nPokémon."),
    THRASH("Thrash", MoveType.PHYSICAL, Type.NORMAL, 20, 90, 100, 0, 2, false, Protection.NONE,
            "",
            "The user rampages and\nattacks for two to three\nturns. It then becomes\nconfused, however."),
    THUNDER("Thunder", MoveType.SPECIAL, Type.ELECTRIC, Status.PARALYZE, 0.10, 10, 120, 70, 0,
            "A wicked thunderbolt is\ndropped on the foe to\ninflict damage. It may also\nleave the target paralyzed."),
    THUNDERBOLT("Thunder Bolt", MoveType.SPECIAL, Type.ELECTRIC, Status.PARALYZE, 0.10, 15, 135, 100, 0,
            "A strong electric blast is\nloosed at the foe. It may\nalso leave the foe\nparalyzed."),
    THUNDERFANG("Thunder Fang", MoveType.PHYSICAL, Type.ELECTRIC, Status.PARALYZE, 0.10, 15, 65, 95, 0,
            "The user bites with electri-\nfied fangs. It may also make\nthe foe paralyzed."),
    THUNDERPUNCH("Thunder Punch", MoveType.PHYSICAL, Type.ELECTRIC, Status.PARALYZE, 0.10, 15, 110, 100, 0,
            "The foe is punched with an\nelectrified fist. It may\nleave the target with\nparalysis."),
    THUNDERSHOCK("Thunder Shock", MoveType.SPECIAL, Type.ELECTRIC, Status.PARALYZE, 0.10, 40, 60, 100, 0,
            "A jolt of electric is hurled\nat the foe to inflict\ndamage. It may also leave\nthe foe paralyzed."),
    THUNDERWAVE("Thunder Wave", MoveType.STATUS, Type.ELECTRIC, Status.PARALYZE, 20, -1, 90, 0,
            "A weak eletric charge is\nlaunched at the foe.\nIt causes paralysis if it\nhits."),
    TOXIC("Toxic", MoveType.STATUS, Type.POISON, Status.BADPOISON, 10, -1, 85, 0,
            "A move that leaves the\ntarget badly poisoned.\nIts poison damage worsens\nevery turn."),
    TWISTER("Twister", MoveType.SPECIAL, Type.DRAGON, 20, 40, 100, 0,
            "The user whips up a vicious\ntornado to tear at the foe."),
    VACUUMWAVE("Vacuum Wave", MoveType.SPECIAL, Type.FIGHTING, 30, 40, 100, 1,
            "The user whirls its fists to\nsend a wave of pure vacuum\nat the foe. This move always\ngoes first."),
    VINEWHIP("Vine Whip", MoveType.PHYSICAL, Type.GRASS, 25, 65, 100, 0,
            "The foe is struck with\nslender, whiplike vines to\ninflict damage."),
    VITALTHROW("Vital Throw", MoveType.PHYSICAL, Type.FIGHTING, 10, 105, -1, -1,
            "The user allows the foe to\nattack first. In return,\nthis throw move is guaran-\nteed not to miss."),
    VOLTTACKLE("Volt Tackle", MoveType.PHYSICAL, Type.ELECTRIC, 15, 120, 100, 0, 0.33,
            "The user electrifies itself,\nthen charges at the foe. It\ncauses considerable damage\nto the user as well."),
    WAKEUPSLAP("Wake-up Slap", MoveType.PHYSICAL, Type.FIGHTING, 10, 60, 100, 0,
            "This attack inflicts high\ndamage on a sleeping foe.\nIt also wakes the foe up,\nhowever."),
    WATERGUN("Water Gun", MoveType.SPECIAL, Type.WATER, 25, 60, 100, 0,
            "The foe is blasted with a\nforceful shot of water."),
    WATERPULSE("Water Pulse", MoveType.SPECIAL, Type.WATER, 20, 90, 100, 0,
            "The user attacks the foe\nwith a pulsing blast of\nwater. It may also confuse\nthe foe."),
    WATERSPOUT("Water Spout", MoveType.SPECIAL, Type.WATER, 5, 150, 100, 0,
            "The user spouts water to\ndamage the foe. The lower\nthe user's HP, the less\npowerful it becomes."),
    WINGATTACK("Wing Attack", MoveType.PHYSICAL, Type.FLYING, 35, 60, 100, 0,
            "The foe is struck with\nlarge, imposing wings spread\nwide to inflict damage."),
    WISH("Wish", MoveType.OTHER, Type.NORMAL, 10, -1, -1, 0, 2,
            "",
            "A self-healing move. The\nuser restores its own HP by\nup to half of its maximum\nHP in the next turn."),
    WITHDRAW("Withdraw", MoveType.ATTRIBUTE, Type.WATER, true, 40, -1, -1, 0, 1, List.of("defense"),
            "The user withdraws its body\ninto its hard shell, raising\nits Defense stat."),
    WRAP("Wrap", MoveType.OTHER, Type.NORMAL, 20, 15, 85, 0, 4,
            " wore off!",
            "A long body or vines are\nused to wrap and squeeze\nthe foe for two to five\nturns."),
    XSCISSOR("X-scissor", MoveType.PHYSICAL, Type.BUG, 15, 80, 100, 0,
            "The user slashes at the foe\nby crossing its scythes or\nclaws as if they were a pair\nof scissors."),
    YAWN("Yawn", MoveType.STATUS, Type.NORMAL, Status.SLEEP, 10, -1, 100, 0,
            "The user lets loose a huge\nyawn that lulls the foe into\nfalling asleep."),
    ZENHEADBUTT("Zen Headbutt", MoveType.PHYSICAL, Type.PSYCHIC, 0.30, 15, 80, 90, 0,
            "The user focuses its will-\npower to its head and rams\nthe foe. It may also make\nthe target flinch.");
    /**
     * END INITIALIZE ENUMS
     **/

    public static final List<Moves> allMoves;

    static {
        allMoves = new ArrayList<>(EnumSet.allOf(Moves.class));
    }

    /**
     * INITIALIZE VALUES
     **/
    private final String name;
    private final String delay;
    private final String info;
    private final MoveType mType;
    private final Type type;
    private final Status effect;
    private final Weather weather;
    private final int pp;
    private final int power;
    private final int accuracy;
    private final int level;
    private final int crit;
    private final int turns;
    private final int priority;
    private final boolean toSelf;
    private final boolean recharge;
    private final double probability;
    private final double damageToSelf;
    private final double flinch;
    private final List<String> stats;
    private final Protection protection;
    /** END INITIALIZE VALUES **/

    /**
     * CONSTRUCTORS
     **/
    Moves(String name, MoveType mType, Type type,
          Status effect, double probability,
          List<String> stats, boolean toSelf,
          int pp, int power, int accuracy, int priority,
          int level, int crit, int turns,
          double damageToSelf, double flinch,
          boolean recharge, Protection protection,
          Weather weather, String delay, String info) {

        this.name = name;
        this.mType = mType;
        this.type = type;

        this.effect = effect;
        this.probability = probability;

        this.stats = stats;
        this.toSelf = toSelf;

        this.pp = pp;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;

        this.level = level;
        this.crit = crit;
        this.turns = turns;

        this.recharge = recharge;
        this.protection = protection;

        this.damageToSelf = damageToSelf;
        this.flinch = flinch;

        this.weather = weather;
        this.delay = delay;
        this.info = info;
    }

    Moves(String name, MoveType mType, Type type, int pp, int power, int accuracy, int priority, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, 0,
                0.0, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, int pp, int power, int accuracy, int priority, int crit, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, crit, 0,
                0.0, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, int pp, int power, int accuracy, int priority, double damageToSelf, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, 0,
                damageToSelf, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, int pp, int power, int accuracy, int priority, int turns, String delay, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, turns,
                0.0, 0.0,
                false, null,
                null, delay, info);
    }

    Moves(String name, MoveType mType, Type type, double flinch, int pp, int power, int accuracy, int priority, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, 0,
                0.0, flinch,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, boolean toSelf, int pp, int power, int accuracy, int priority, int level, List<String> stats, String info) {
        this(name, mType, type,
                null, 0.0,
                stats, toSelf,
                pp, power, accuracy, priority,
                level, 0, 0,
                0.0, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, boolean toSelf, double probability, int pp, int power, int accuracy, int priority, int level, List<String> stats, String info) {
        this(name, mType, type,
                null, probability,
                stats, toSelf,
                pp, power, accuracy, priority,
                level, 0, 0,
                0.0, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, Weather weather, int pp, int power, int accuracy, int priority, int turns, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, turns,
                0.0, 0.0,
                false, null,
                weather, "", info);
    }

    Moves(String name, MoveType mType, Type type, Status effect, int pp, int power, int accuracy, int priority, String info) {
        this(name, mType, type,
                effect, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, 0,
                0.0, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, Status effect, double probability, int pp, int power, int accuracy, int priority, String info) {
        this(name, mType, type,
                effect, probability,
                null, false,
                pp, power, accuracy, priority,
                0, 0, 0,
                0.0, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, Status effect, double probability, int pp, int power, int accuracy, int priority, int crit, String info) {
        this(name, mType, type,
                effect, probability,
                null, false,
                pp, power, accuracy, priority,
                0, crit, 0,
                0.0, 0.0,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, Status effect, double probability, double flinch, int pp, int power, int accuracy, int priority, String info) {
        this(name, mType, type,
                effect, probability,
                null, false,
                pp, power, accuracy, priority,
                0, 0, 0,
                0.0, flinch,
                false, null,
                null, "", info);
    }

    Moves(String name, MoveType mType, Type type, int pp, int power, int accuracy, int priority, int turns, boolean recharge, Protection protection, String delay, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, turns,
                0.0, 0.0,
                recharge, protection,
                null, delay, info);
    }

    Moves(String name, MoveType mType, Type type, double flinch, int pp, int power, int accuracy, int priority, int turns, boolean recharge, Protection protection, String delay, String info) {
        this(name, mType, type,
                null, 0.0,
                null, false,
                pp, power, accuracy, priority,
                0, 0, turns,
                0.0, flinch,
                recharge, protection,
                null, delay, info);
    }

    Moves(String name, MoveType mType, Type type, Status effect, double probability, int pp, int power, int accuracy, int priority, int turns, boolean recharge, Protection protection, String delay, String info) {
        this(name, mType, type,
                effect, probability,
                null, false,
                pp, power, accuracy, priority,
                0, 0, turns,
                0.0, 0.0,
                recharge, protection,
                null, delay, info);
    }
    /** END CONSTRUCTORS **/

    /**
     * GETTERS
     **/
    public String getName() {
        return name;
    }

    public MoveType getMType() {
        return mType;
    }

    public Type getType() {
        return type;
    }

    public int getpp() {
        return pp;
    }

    public Status getEffect() {
        return effect;
    }

    public double getProbability() {
        return probability;
    }

    public double getFlinch() {
        return flinch;
    }

    public double getSelfInflict() {
        return damageToSelf;
    }

    public boolean isToSelf() {
        return toSelf;
    }

    public int getAccuracy() {
        if (accuracy == -1) {
            return 100;
        }
        else {
            return accuracy;
        }
    }

    public int getPower() {
        return power;
    }

    public int getTurns() {
        return turns;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getRecharge() {
        return recharge;
    }

    public Weather getWeather() {
        return weather;
    }

    public String getDelay(String name) {
        return name + " " + delay;
    }

    public Protection getProtection() {
        return protection;
    }

    public String getInfo() {
        return info;
    }

    public int getCrit() {
        return crit;
    }

    public int getLevel() {
        return level;
    }

    public List<String> getStats() {
        return stats;
    }
    /** END GETTERS **/
}
/*** END MOVES ENUM ***/