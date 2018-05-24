package net.overmy.likehunters.logic;

/*
        Created by Andrey Mikheev on 02.03.2018
        Contact me → http://vk.com/id17317
*/

import com.badlogic.ashley.utils.ImmutableArray;
import net.overmy.likehunters.logic.NPCAction.ACTION_ID;
import net.overmy.likehunters.resources.TextAsset;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/** Билдер скриптов */
public final class ScriptBuilder {
    private ScriptBuilder () {
    }


    public static ImmutableArray< NPCAction > SOME_NPC_MOVE_ON_GROUND () {
        Array< NPCAction > queue = new Array< NPCAction >();
        queue.add( wait( 1.0f ) );
        queue.add( move(28.615776f, -46.920258f) );
        queue.add( wait( 2.0f ) );
        queue.add( move(18.464893f, -37.144436f ) );
        queue.add( wait( 1.0f ) );
        queue.add( move(29.282198f, -36.65033f ) );
        queue.add( wait( 3.0f ) );
        return new ImmutableArray< NPCAction >( queue );
    }

    public static ImmutableArray< NPCAction > STAR_ON_LEVEL1 () {
        Array< NPCAction > queue = new Array< NPCAction >();
        queue.add( move(-7.297408f, -42.540188f) );
        queue.add( wait( 1.0f ) );
        queue.add( hunt() );
        queue.add( move(-10.403938f, -40.797947f) );
        queue.add( wait( 2.0f ) );
        queue.add( move(-18.388197f, -45.05218f) );
        queue.add( wait( 1.0f ) );
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
}
