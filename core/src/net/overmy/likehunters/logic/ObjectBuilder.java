package net.overmy.likehunters.logic;

/*
        Created by Andrey Mikheev on 21.03.2018
        Contact me → http://vk.com/id17317
*/

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

class ObjectBuilder {

    // Этот класс работает по принципу ENUM


    Array< LevelObjectBuilder > LEVEL0 () {
        Array< LevelObjectBuilder > objects = new Array< LevelObjectBuilder >();

        return objects;
    }

    // helpers


    private LevelObjectBuilder weapon ( float x, float y, float z, Item itemInBox ) {
        return new LevelObjectBuilder()
                .setType( OBJECT_TYPE.WEAPON )
                .setItem( itemInBox )
                .setPosition( new Vector3( x, y, z ) );
    }
}
