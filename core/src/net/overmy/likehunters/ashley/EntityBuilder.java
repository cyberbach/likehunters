package net.overmy.likehunters.ashley;

/*
      Created by Andrey Mikheev on 17.03.2018
      Contact me → http://vk.com/id17317
 */

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;

import net.overmy.likehunters.BulletWorld;
import net.overmy.likehunters.PhysicalBuilder;
import net.overmy.likehunters.ashley.component.AnimationComponent;
import net.overmy.likehunters.ashley.component.BoundingBoxComponent;
import net.overmy.likehunters.ashley.component.GroundedComponent;
import net.overmy.likehunters.ashley.component.LevelIDComponent;
import net.overmy.likehunters.ashley.component.LifeComponent;
import net.overmy.likehunters.ashley.component.ModelComponent;
import net.overmy.likehunters.ashley.component.MyPlayerComponent;
import net.overmy.likehunters.ashley.component.TYPE_OF_ENTITY;
import net.overmy.likehunters.ashley.component.TypeOfEntityComponent;
import net.overmy.likehunters.resources.ModelAsset;

public final class EntityBuilder {


    private static Engine engine = null;


    private EntityBuilder () {
    }


    public static void init ( Engine engineIn ) {
        engine = engineIn;
    }


    public static void dispose () {
        engine = null;
    }


    public static Entity createGround ( ModelAsset modelAsset ) {
        ModelInstance physics = modelAsset.getSimple();
        PhysicalBuilder physicalBuilder = null;
        if ( physics != null ) {
            physicalBuilder = new PhysicalBuilder()
                    .setModelInstance( physics )
                    .defaultMotionState()
                    .zeroMass()
                    .bvhShape()
                    .setCollisionFlag( CollisionFlags.CF_STATIC_OBJECT )
                    .setCallbackFlag( BulletWorld.GROUND_FLAG )
                    .setCallbackFilter( BulletWorld.PLAYER_FLAG );
        }

        Entity entity = new Entity();
        entity.add( new LevelIDComponent( modelAsset.ordinal() ) );
        entity.add( new ModelComponent( modelAsset.get() ) );
        entity.add( new TypeOfEntityComponent( TYPE_OF_ENTITY.GROUND ) );
        if ( physics != null ) {
            entity.add( physicalBuilder.buildPhysicalComponent() );
            entity.add( physicalBuilder.buildBVHPhysicalComponent() );
        }
        entity.add( new BoundingBoxComponent( modelAsset.getBoundingBox() ) );
        engine.addEntity( entity );

        return entity;
    }


    public static void createPlayer ( ModelInstance modelInstance, Vector3 position ) {
        PhysicalBuilder physicalBuilder = new PhysicalBuilder()
                .setModelInstance( modelInstance )
                .defaultMotionState()
                .setMass( 20.0f )
                .setPosition( position )
                .capsuleShape()
                .setCollisionFlag( CollisionFlags.CF_CHARACTER_OBJECT )
                .setCallbackFlag( BulletWorld.PLAYER_FLAG )
                .setCallbackFilter( BulletWorld.PLAYER_FILTER )
                .disableDeactivation()
                .disableRotation();

        Entity entity = new Entity();
        entity.add( physicalBuilder.buildPhysicalComponent() );
        entity.add( new ModelComponent( modelInstance ) );
        entity.add( new AnimationComponent( modelInstance ) );
        entity.add( new GroundedComponent() );
        entity.add( new LifeComponent( 100.0f, 1, 2 ) );
        entity.add( new TypeOfEntityComponent( TYPE_OF_ENTITY.MYPLAYER ) );
        entity.add( new MyPlayerComponent() );

        engine.addEntity( entity );
    }
}
