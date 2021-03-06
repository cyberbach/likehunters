package net.overmy.likehunters.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;

import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.ashley.system.AnimationSystem;
import net.overmy.likehunters.ashley.system.MyPlayerSystem;
import net.overmy.likehunters.ashley.system.NPCSystem;
import net.overmy.likehunters.ashley.system.PhysicalSystem;
import net.overmy.likehunters.ashley.system.RemoveByTimeSystem;
import net.overmy.likehunters.ashley.system.RenderSystem;

/*
        Created by Andrey Mikheev on 05.06.2018
        Contact me → http://vk.com/id17317
*/
public final class AshleyWorld {
    private static Engine engine = null;


    private AshleyWorld () {
    }


    public static Engine getEngine () {
        return engine;
    }


    public static void init () {
        engine = new Engine();

        //EntityBuilder.init( engine );
        MyMapper.init();

        engine.addSystem( new MyPlayerSystem() );
        engine.addSystem( new NPCSystem() );
        engine.addSystem( new RemoveByTimeSystem() );
        engine.addSystem( new AnimationSystem() );
        engine.addSystem( new PhysicalSystem() );
        engine.addSystem( new RenderSystem() );

        engine.addEntityListener( new MyEntityListener() );
    }


    public static void dispose () {
        engine.removeSystem( engine.getSystem( MyPlayerSystem.class ) );
        engine.removeSystem( engine.getSystem( NPCSystem.class ) );
        engine.removeSystem( engine.getSystem( RemoveByTimeSystem.class ) );
        engine.removeSystem( engine.getSystem( AnimationSystem.class ) );
        engine.removeSystem( engine.getSystem( PhysicalSystem.class ) );
        engine.removeSystem( engine.getSystem( RenderSystem.class ) );

        //engine.removeAllEntities();
        engine = null;

        //EntityBuilder.dispose();
        MyMapper.dispose();
    }


    public static void update ( float delta ) {
        engine.update( delta );

        if ( DEBUG.CONTACTS ) {
            Gdx.app.debug( "AshleyWorld ================================== update",
                           "tick ===============================" );
        }
    }
}
