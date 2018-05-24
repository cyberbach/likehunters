package net.overmy.likehunters.logic.cutscenes;

/*
        Created by Andrey Mikheev on 19.05.2018
        Contact me → http://vk.com/id17317
*/

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/** Билдер скриптов камеры */
public final class CameraScriptBuilder {
    private CameraScriptBuilder () {
    }

    public static ImmutableArray< Camera1Action > TEST_SCRIPT1 () {
        Array< Camera1Action > queue = new Array< Camera1Action >();
        queue.add( wait( 1.0f ) );
        queue.add( move( -12.349428f, 1.0011797f, -35.222794f ,112, 2 ) );
        queue.add( wait( 1.0f ) );
        queue.add( breakTo( 8.245668f, 0.9999995f, -32.90352f,168 ) );
        queue.add( wait( 1.0f ) );
        queue.add( floatTarget( 116,3 ) );
        queue.add( wait( 1.0f ) );
        queue.add( breakTo( 30.127562f, 3.293261f, -34.92738f,118 ) );
        queue.add( floatTarget( 91,3 ) );
        queue.add( move( 17.638874f, 1.0397451f, -50.853695f,137,3 ) );
        queue.add( wait( 2.0f ) );
        queue.add( breakTo( 17.463295f, 1.2503337f, -60.045914f,19 ) );
        queue.add( wait( 1.0f ) );
        queue.add( breakTo( 8.779057f, 0.99999577f, -58.863937f,40 ) );
        queue.add( wait( 1.0f ) );
        queue.add( move( -1.2107695f, 1.0f, -41.451225f, 90,3 ) );
        queue.add( wait( 1.0f ) );

        return new ImmutableArray< Camera1Action >( queue );
    }

    private static Camera1Action wait ( float time ) {
        return new Camera1Action( CAM_ACTION_ID.WAIT, time );
    }


    private static Camera1Action breakTo ( float x, float y, float z, float angle ) {
        return new Camera1Action( CAM_ACTION_ID.BREAK, new Vector3( x, y, z ), angle, 0.01f );
    }

    private static Camera1Action move ( float x, float y, float z, float angle, float duration ) {
        return new Camera1Action( CAM_ACTION_ID.MOVE, new Vector3( x, y, z ), angle, duration );
    }

    private static Camera1Action floatTarget ( float angle, float duration ) {
        return new Camera1Action( CAM_ACTION_ID.FLOAT_TARGET, angle, duration );
    }

}
