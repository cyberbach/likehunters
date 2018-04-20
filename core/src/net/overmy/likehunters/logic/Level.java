package net.overmy.likehunters.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Andrey (cb) Mikheev
 * 17.03.2017
 */

public class Level {

    // Связь между уровнями. Какой уровень с каким - соседние.
    // Обязательно должен указывать сам на себя!
    final int[] connections;

    // Объекты на уровне, включая NPC и Enemy - это тоже объекты
    ImmutableArray< LevelObjectBuilder > objects = null;


    public Entity entity = null;


    Level ( String connection, Array< LevelObjectBuilder > objects ) {
        this.connections = toInts( connection );
        this.objects = new ImmutableArray< LevelObjectBuilder >( objects );
    }


    Level ( String connection ) {
        this.connections = toInts( connection );
    }


    private static int[] toInts ( String str ) {
        if ( "".equals( str ) ) {
            return null;
        }
        String[] separated = str.replaceAll( " ", "" ).split( "," );
        final int[] result = new int[ separated.length ];
        for ( int p = 0; p < separated.length; p++ ) {
            result[ p ] = Integer.parseInt( separated[ p ] );
        }
        return result;
    }
}
