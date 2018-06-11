package net.overmy.likehunters.logic;

/*
        Created by Andrey Mikheev on 02.03.2018
        Contact me → http://vk.com/id17317
*/

import com.badlogic.ashley.utils.ImmutableArray;

import net.overmy.likehunters.logic.NPCAction.*;
import net.overmy.likehunters.resource.TextAsset;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/**
 * Билдер скриптов
 */
public final class ScriptBuilder {
    private ScriptBuilder () {
    }


    public static ImmutableArray< NPCAction > MONSTER1_MOVE_ON_LEVEL1 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move( 12.729724f, -26.19313f ) );
        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move( -0.5718983f, -15.974129f ) );
        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move( -3.3788373f, -20.052458f ) );
        queue.add( hunt() );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > DRAKON1_MOVE_ON_LEVEL4 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move( -32.800106f, -73.38079f ) );
        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move( -39.30264f, -80.23786f ) );
        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move( -47.024467f, -77.53844f ) );
        queue.add( hunt() );
        return new ImmutableArray< NPCAction >( queue );
    }


    private static NPCAction wait ( float time ) {
        return new NPCAction( ACTION_ID.WAIT, time );
    }


    private static NPCAction hunt () {
        return new NPCAction( ACTION_ID.HUNT, 5.0f );
    }


    private static NPCAction move ( float x, float y ) {
        return new NPCAction( ACTION_ID.MOVE, new Vector2( x, y ), 10.0f );
    }


    private static NPCAction say ( TextAsset text, float time ) {
        return new NPCAction( ACTION_ID.SAY, text, time );
    }


    private static NPCAction animate ( int ID, float time ) {
        return new NPCAction( ACTION_ID.ANIMATE, time, ID );
    }


    public static ImmutableArray< NPCAction > BALL1_MOVE_ON_LEVEL1 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move( -9.281758f, -23.482405f ) );
        queue.add( wait( 1.0f ) );
        queue.add( move( -6.1234503f, -26.158615f ) );
        queue.add( hunt() );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > BALL1_MOVE_ON_LEVEL5 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 2.0f ) );
        queue.add( move( -71.38321f, -91.84781f ) );
        queue.add( wait( 1.5f ) );
        queue.add( move( -67.97911f, -84.08572f ) );
        queue.add( hunt() );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > BALL2_MOVE_ON_LEVEL5 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 1.0f ) );
        queue.add( move( -79.01407f, -63.310196f ) );
        queue.add( wait( 1.5f ) );
        queue.add( move( -75.01784f, -66.4751f ) );
        queue.add( hunt() );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > MONSTER1_MOVE_ON_LEVEL5 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 2.0f ) );
        queue.add( move( -63.426556f, -55.366985f ) );
        queue.add( wait( 1.5f ) );
        queue.add( move( -65.05518f, -60.24537f ) );
        queue.add( wait( 3.5f ) );
        queue.add( move( -57.625965f, -53.564537f ) );
        queue.add( hunt() );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > BALL1_MOVE_ON_LEVEL6 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 1.0f ) );
        queue.add( move( -50.74866f, -31.391026f ) );
        queue.add( wait( 2.5f ) );
        queue.add( move( -45.635914f, -32.151855f ) );
        queue.add( hunt() );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > SPIDER1_MOVE_ON_LEVEL7 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 3.0f ) );
        queue.add( move( -3.7015839f, -8.430518f ) );
        queue.add( wait( 2.5f ) );
        queue.add( hunt() );
        queue.add( move( -8.048356f, -11.526027f ) );
        queue.add( wait( 2.5f ) );
        queue.add( hunt() );
        queue.add( move( -2.4151776f, -16.846405f ) );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > BALL1_MOVE_ON_LEVEL8 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 3.0f ) );
        queue.add( move( 34.691044f, -86.50605f ) );
        queue.add( wait( 2.5f ) );
        queue.add( hunt() );
        queue.add( move( 35.78192f, -81.42391f ) );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > BALL2_MOVE_ON_LEVEL8 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 3.0f ) );
        queue.add( move( 70.22678f, -91.830734f ) );
        queue.add( wait( 2.5f ) );
        queue.add( hunt() );
        queue.add( move( 66.559f, -92.08643f ) );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > MONSTER1_MOVE_ON_LEVEL7 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 3.0f ) );
        queue.add( move( 13.787048f, -32.723427f ) );
        queue.add( wait( 2.5f ) );
        queue.add( move( 24.915009f, -24.653265f ) );
        queue.add( wait( 1.5f ) );
        queue.add( hunt() );
        queue.add( move( 21.029007f, -35.242065f ) );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > BALL2_1_MOVE_ON_LEVEL10 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 3.0f ) );
        queue.add( move( 57.967804f, -29.103464f ) );
        queue.add( wait( 2.5f ) );
        queue.add( move( 63.54963f, -28.371105f ) );
        queue.add( wait( 1.5f ) );
        queue.add( hunt() );
        queue.add( move( 63.085598f, -33.569633f ) );
        return new ImmutableArray< NPCAction >( queue );
    }


    public static ImmutableArray< NPCAction > BALL2_2_MOVE_ON_LEVEL10 () {
        Array< NPCAction > queue = new Array< NPCAction >();

        queue.add( wait( 1.0f ) );
        queue.add( move( 67.602005f, -33.15316f ) );
        queue.add( wait( 2.5f ) );
        queue.add( move( 73.64838f, -39.504917f ) );
        queue.add( wait( 2.5f ) );
        queue.add( hunt() );
        queue.add( move( 64.00668f, -37.141827f ) );
        return new ImmutableArray< NPCAction >( queue );
    }
}
