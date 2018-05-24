package net.overmy.likehunters.logic;

/*
        Created by Andrey Mikheev on 21.03.2018
        Contact me → http://vk.com/id17317
*/

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import net.overmy.likehunters.logic.collectables.Trigger;
import net.overmy.likehunters.logic.collectables.TriggerCollectable;
import net.overmy.likehunters.logic.objects.GameObject;
import net.overmy.likehunters.logic.objects.NPCObject;
import net.overmy.likehunters.logic.objects.TriggerObject;
import net.overmy.likehunters.resources.ModelAsset;

class LevelBuilder {

    // Этот класс работает по принципу ENUM


    Array< GameObject > LEVEL0 () {
        Array< GameObject > objects = new Array< GameObject >();


        return objects;
    }


    Array< GameObject > LEVEL2 () {
        Array< GameObject > objects = new Array< GameObject >();
//new Vector3( 29.17271f, 3.2932336f, -37.428074f )
        objects.add( new TriggerObject( Trigger.MY_TEST_TIRGGER,
                                        new Vector3( 11.330224f, 1.4019979f, -42.39821f ), 2 ) );

        objects.add( new NPCObject(new Vector3( 29.282198f, 3.2932568f, -36.65033f ), ModelAsset.BOY,
                                   ScriptBuilder.SOME_NPC_MOVE_ON_GROUND()) );
        return objects;
    }
}
