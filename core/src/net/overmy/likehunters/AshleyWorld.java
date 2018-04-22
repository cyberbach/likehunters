package net.overmy.likehunters;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;

import net.overmy.likehunters.ashley.EntityBuilder;
import net.overmy.likehunters.ashley.MyEntityListener;
import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.systems.AnimationSystem;
import net.overmy.likehunters.ashley.systems.DecalSystem;
import net.overmy.likehunters.ashley.systems.DoorSystem;
import net.overmy.likehunters.ashley.systems.InteractSystem;
import net.overmy.likehunters.ashley.systems.LifeSystem;
import net.overmy.likehunters.ashley.systems.MyPlayerSystem;
import net.overmy.likehunters.ashley.systems.PhysicalSystem;
import net.overmy.likehunters.ashley.systems.RenderSystem;
import net.overmy.likehunters.ashley.systems.RotationPhysicSystem;
import net.overmy.likehunters.ashley.systems.TextDecalSystem;
import net.overmy.likehunters.ashley.systems.WeaponSystem;
import net.overmy.likehunters.ashley.systems.NPCSystem;
import net.overmy.likehunters.ashley.systems.RemoveByTimeSystem;

public final class AshleyWorld {
    public static Engine getEngine () {
        return engine;
    }


    private static Engine engine = null;


    private AshleyWorld () {
    }


    public static void init () {
        engine = new Engine();

        EntityBuilder.init( engine );
        MyMapper.init();

        engine.addSystem( new LifeSystem() );
        engine.addSystem( new MyPlayerSystem() );
        engine.addSystem( new RemoveByTimeSystem() );
        engine.addSystem( new NPCSystem() );
        engine.addSystem( new WeaponSystem() );
        engine.addSystem( new AnimationSystem() );
        engine.addSystem( new RotationPhysicSystem() );
        engine.addSystem( new PhysicalSystem() );
        engine.addSystem( new RenderSystem() );
        engine.addSystem( new TextDecalSystem() );
        engine.addSystem( new DecalSystem() );
        engine.addSystem( new InteractSystem() );
        engine.addSystem( new DoorSystem() );

        engine.addEntityListener( new MyEntityListener() );
    }


    public static void dispose () {
        engine.removeSystem( engine.getSystem( LifeSystem.class ) );
        engine.removeSystem( engine.getSystem( MyPlayerSystem.class ) );
        engine.removeSystem( engine.getSystem( RemoveByTimeSystem.class ) );
        engine.removeSystem( engine.getSystem( NPCSystem.class ) );
        engine.removeSystem( engine.getSystem( AnimationSystem.class ) );
        engine.removeSystem( engine.getSystem( RotationPhysicSystem.class ) );
        engine.removeSystem( engine.getSystem( PhysicalSystem.class ) );
        engine.removeSystem( engine.getSystem( RenderSystem.class ) );
        engine.removeSystem( engine.getSystem( DecalSystem.class ) );
        engine.removeSystem( engine.getSystem( InteractSystem.class ) );
        engine.removeSystem( engine.getSystem( WeaponSystem.class ) );
        engine.removeSystem( engine.getSystem( DoorSystem.class ) );

        engine.removeAllEntities();
        engine = null;

        EntityBuilder.dispose();
        MyMapper.dispose();
    }


    public static void update ( float delta ) {
        engine.update( delta );

        if ( DEBUG.CONTACTS.get() ) {
            Gdx.app.debug( "==================================",
                           "tick ===============================" );
        }
    }
}
