package net.overmy.likehunters.logic.collectables;

import com.badlogic.gdx.Gdx;

import net.overmy.likehunters.logic.cutscenes.CutScene;
import net.overmy.likehunters.logic.cutscenes.SCENE_ID;

/*
        Created by Andrey Mikheev on 18.05.2018
        Contact me → http://vk.com/id17317
*/
public class CollectableProcessor {
    private CollectableProcessor () {
    }


    public static void process ( Collectable collectable ) {
        if ( collectable instanceof TriggerCollectable ) {
            TriggerCollectable triggerCollectable = (TriggerCollectable) collectable;
            switch ( triggerCollectable.trigger ) {
                case MY_TEST_TIRGGER:
                    Gdx.app.debug( "processssed", "MY_TEST_TIRGGER- 4 seconds" );
                    CutScene.start( SCENE_ID.SOME_SCENE, 4 );
                    break;
            }
        }
    }
}
