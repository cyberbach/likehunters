package net.overmy.likehunters.logic;

/*
        Created by Andrey Mikheev on 21.03.2018
        Contact me → http://vk.com/id17317
*/

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import net.overmy.likehunters.logic.collectable.TRIGGER;
import net.overmy.likehunters.logic.objects.GameObject;
import net.overmy.likehunters.logic.objects.NPCObject;
import net.overmy.likehunters.logic.objects.TriggerObject;
import net.overmy.likehunters.resource.Asset;
import net.overmy.likehunters.resource.ModelAsset;
import net.overmy.likehunters.resource.SoundAsset;


class LevelBuilder {

    // Этот класс работает по принципу ENUM


    Array< GameObject > LEVEL0 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( new TriggerObject( TRIGGER.TRIGGER_GAMEINTRO,
                                        new Vector3( -1.5443124f, -2.0000188f, -5.023402f ), 5 ) );

        return objects;
    }


    Array< GameObject > LEVEL1 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( createNPC( -9.281758f, -2.0000157f, -23.482405f,
                                ModelAsset.BALL1,
                                ScriptBuilder.BALL1_MOVE_ON_LEVEL1(),
                                SoundAsset.NPC_STEP ) );

        objects.add( createNPC( 9.488587f, 3, -25.605047f, ModelAsset.MONSTER1,
                                ScriptBuilder.MONSTER1_MOVE_ON_LEVEL1(),
                                SoundAsset.NPC_STEP ) );
        return objects;
    }


    public Array< GameObject > LEVEL2 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( new TriggerObject( TRIGGER.TRIGGER1,
                                        new Vector3( -18.518694f, -2.0f, -41.97685f ), 3 ) );

        return objects;
    }


    public Array< GameObject > LEVEL3 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( new TriggerObject( TRIGGER.TRIGGER2,
                                        new Vector3( -1.9388988f, -2.0000007f, -65.750374f ), 4 ) );

        return objects;
    }


    public Array< GameObject > LEVEL4 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( createNPC( -32.800106f, -1.0f, -73.38079f,
                                ModelAsset.DRAKON1,
                                ScriptBuilder.DRAKON1_MOVE_ON_LEVEL4(),
                                SoundAsset.PLAYER_STEP ) );

        objects.add( new TriggerObject( TRIGGER.TRIGGER2,
                                        new Vector3( -28.329607f, -2.0498738f, -70.5543f ), 3 ) );

        return objects;
    }


    public Array< GameObject > LEVEL5 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( createNPC( -71.38321f, -2.248463f, -91.84781f,
                                ModelAsset.BALL2,
                                ScriptBuilder.BALL1_MOVE_ON_LEVEL5(),
                                SoundAsset.NPC_STEP ) );

        objects.add( createNPC( -79.01407f, -4.4164796f, -63.310196f,
                                ModelAsset.BALL2,
                                ScriptBuilder.BALL2_MOVE_ON_LEVEL5(),
                                SoundAsset.NPC_STEP ) );

        objects.add( createNPC( -63.426556f, -7.3967466f, -55.366985f,
                                ModelAsset.MONSTER1,
                                ScriptBuilder.MONSTER1_MOVE_ON_LEVEL5(),
                                SoundAsset.NPC_STEP ) );
        return objects;
    }


    public Array< GameObject > LEVEL6 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( createNPC( -50.74866f, -7.9563437f, -31.391026f,
                                ModelAsset.BALL1,
                                ScriptBuilder.BALL1_MOVE_ON_LEVEL6(),
                                SoundAsset.NPC_STEP ) );

        objects.add( new TriggerObject( TRIGGER.TRIGGER4,
                                        new Vector3( -52.626076f, -7.4869747f, -38.24315f ), 3 ) );

        return objects;
    }


    public Array< GameObject > LEVEL7 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( createNPC( -3.7015839f, -10.959431f, -8.430518f,
                                ModelAsset.SPIDER1,
                                ScriptBuilder.SPIDER1_MOVE_ON_LEVEL7(),
                                SoundAsset.NPC_STEP ) );

        objects.add( createNPC( 13.787048f, -10.960495f, -32.723427f,
                                ModelAsset.MONSTER1,
                                ScriptBuilder.MONSTER1_MOVE_ON_LEVEL7(),
                                SoundAsset.NPC_STEP ) );

        objects.add( new TriggerObject( TRIGGER.TRIGGER5,
                                        new Vector3( 15.383123f, -10.960565f, -11.940803f ), 3 ) );

        return objects;
    }


    public Array< GameObject > LEVEL8 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( createNPC( 34.691044f, 3.7579386f, -86.50605f,
                                ModelAsset.BALL1,
                                ScriptBuilder.BALL1_MOVE_ON_LEVEL8(),
                                SoundAsset.NPC_STEP ) );

        objects.add( createNPC( 70.22678f, -4.7638574f, -91.830734f,
                                ModelAsset.BALL1,
                                ScriptBuilder.BALL2_MOVE_ON_LEVEL8(),
                                SoundAsset.NPC_STEP ) );

        objects.add( new TriggerObject( TRIGGER.TRIGGER6,
                                        new Vector3( 42.60468f, -12.563421f, -40.40446f ), 3 ) );

        return objects;
    }


    public Array< GameObject > LEVEL9 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( new TriggerObject( TRIGGER.TRIGGER3,
                                        new Vector3( 25.12256f, 2.9295511f, -61.09331f ), 3 ) );

        return objects;
    }


    public Array< GameObject > LEVEL10 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( createNPC( 57.967804f, 4.154869f, -29.103464f,
                                ModelAsset.BALL2,
                                ScriptBuilder.BALL2_1_MOVE_ON_LEVEL10(),
                                SoundAsset.NPC_STEP ) );

        objects.add( createNPC( 67.602005f, 4.1550283f, -33.15316f,
                                ModelAsset.BALL2,
                                ScriptBuilder.BALL2_2_MOVE_ON_LEVEL10(),
                                SoundAsset.NPC_STEP ) );

        objects.add( new TriggerObject( TRIGGER.TRIGGER7,
                                        new Vector3( 51.672215f, 4.155028f, -30.979536f ), 3 ) );

        return objects;
    }


    public Array< GameObject > LEVEL11 () {
        Array< GameObject > objects = new Array< GameObject >();

        objects.add( new TriggerObject( TRIGGER.TRIGGER_GAMEOVER,
                                        new Vector3( 99.10154f, 6.182393f, -32.62249f ), 7 ) );

        return objects;
    }


    private NPCObject createNPC ( float x, float y, float z, ModelAsset npcModelAsset,
                                  ImmutableArray< NPCAction > actionArray,
                                  SoundAsset walkSound ) {
        Vector3 position = new Vector3( x, y, z );
        Array< Asset > assetArray = new Array< Asset >();
        assetArray.add( npcModelAsset );
        assetArray.add( walkSound );
        ImmutableArray< Asset > assets = new ImmutableArray< Asset >( assetArray );
        return new NPCObject( position, assets, actionArray );
    }
}
