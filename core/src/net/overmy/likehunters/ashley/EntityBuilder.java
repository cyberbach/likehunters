package net.overmy.likehunters.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.utils.Array;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.ashley.component.AnimationComponent;
import net.overmy.likehunters.ashley.component.BoundingBoxComponent;
import net.overmy.likehunters.ashley.component.GroundedComponent;
import net.overmy.likehunters.ashley.component.LevelIDComponent;
import net.overmy.likehunters.ashley.component.LifeComponent;
import net.overmy.likehunters.ashley.component.ModelComponent;
import net.overmy.likehunters.ashley.component.MyPlayerComponent;
import net.overmy.likehunters.ashley.component.NPCComponent;
import net.overmy.likehunters.ashley.component.CharacterStateComponent;
import net.overmy.likehunters.ashley.component.PhysicalComponent;
import net.overmy.likehunters.ashley.component.RemoveByTimeComponent;
import net.overmy.likehunters.ashley.component.SoundWalkComponent;
import net.overmy.likehunters.ashley.component.TYPE_OF_ENTITY;
import net.overmy.likehunters.ashley.component.TypeOfEntityComponent;
import net.overmy.likehunters.bullet.BulletWorld;
import net.overmy.likehunters.bullet.PhysicalBuilder;
import net.overmy.likehunters.logic.Item;
import net.overmy.likehunters.logic.NPCAction;
import net.overmy.likehunters.logic.CHARACTER_STATE;
import net.overmy.likehunters.resource.ModelAsset;
import net.overmy.likehunters.resource.SoundAsset;

/*
        Created by Andrey Mikheev on 05.06.2018
        Contact me → http://vk.com/id17317
*/
public class EntityBuilder {

    private static void initSound ( SoundWalkComponent walkSoundComponent, SoundAsset asset ) {
        walkSoundComponent.walk = asset.get();
        //walkSoundComponent.id = walkSoundComponent.walk.loop( 0 );
    }


    public static Texture createTexture ( int width, int height, Color color ) {
        Pixmap pixmap = new Pixmap( width, height, Pixmap.Format.RGB888 );
        pixmap.setColor( color );
        pixmap.fill();

        Texture texture = new Texture( pixmap );
        pixmap.dispose();

        return texture;
    }


    public Entity createGround ( ModelAsset levelAsset ) {
        ModelInstance modelInstance = levelAsset.get();
        ModelInstance physicalInstance = levelAsset.getSimple();

        PhysicalBuilder physicalBuilder = new PhysicalBuilder()
                .setModelInstance( physicalInstance )
                .defaultMotionState()
                .zeroMass()
                .bvhShape()
                .setCollisionFlag( CollisionFlags.CF_STATIC_OBJECT )
                .setCallbackFlag( BulletWorld.GROUND_FLAG )
                .setCallbackFilter( BulletWorld.PLAYER_FLAG );

        Entity entity = new Entity();
        entity.add( ModelComponentFromInstance( modelInstance ) );
        entity.add( LevelIDComponentFromAsset( levelAsset ) );
        //entity.add( AnimationComponentFromInstance( modelInstance ) );
        entity.add( TypeComponent( TYPE_OF_ENTITY.GROUND ) );
        entity.add( BoundingBoxFromAsset( levelAsset ) );
        entity.add( physicalBuilder.buildPhysicalComponent() );
        entity.add( physicalBuilder.buildBVHPhysicalComponent() );

        return entity;
    }


    public Entity createPlayer ( ModelAsset myPlayer, Vector3 position ) {
        ModelInstance modelInstance = myPlayer.get();

        PhysicalBuilder physicalBuilder = new PhysicalBuilder()
                .setModelInstance( modelInstance )
                .defaultMotionState()
                .setMass( 20.0f )
                .setPosition( position )
                .capsuleShape( 0.5f, 1.0f )
                .setCollisionFlag( CollisionFlags.CF_CHARACTER_OBJECT )
                .setCallbackFlag( BulletWorld.PLAYER_FLAG )
                .setCallbackFilter( BulletWorld.PLAYER_FILTER )
                .disableDeactivation()
                .disableRotation();

        Entity entity = new Entity();
        entity.add( ModelComponentFromInstance( modelInstance ) );
        entity.add( AnimationComponentFromInstance( modelInstance ) );
        entity.add( TypeComponent( TYPE_OF_ENTITY.MYPLAYER ) );
        entity.add( new MyPlayerComponent() );
        entity.add( physicalBuilder.buildPhysicalComponent() );
        entity.add( new GroundedComponent() );
        entity.add( CharacterState() );
        //entity.add( RemoveByTime(5) );
        return entity;
    }

    // helpers


    public Entity createNPC ( Vector3 position, ModelAsset modelAsset,
                              ImmutableArray< NPCAction > actionArray,
                              SoundAsset walkSoundAsset ) {

        ModelInstance modelInstance = modelAsset.get();

        SoundWalkComponent walkSound = new SoundWalkComponent();
        walkSound.walk = walkSoundAsset.get();

        PhysicalBuilder physicalBuilderNPC;

        // FIXME refactor

        if ( modelAsset.equals( ModelAsset.SPIDER1 ) ) {
            physicalBuilderNPC = new PhysicalBuilder()
                    .setModelInstance( modelInstance )
                    .defaultMotionState()
                    .setPosition( position )
                    .setMass( 60.0f )
                    .capsuleShape( 1.5f, 0 )
                    .setCollisionFlag( CollisionFlags.CF_CHARACTER_OBJECT )
                    .setCallbackFlag( BulletWorld.NPC_FLAG )
                    .setCallbackFilter( BulletWorld.ENEMY_FILTER )
                    .disableRotation()
                    .disableDeactivation();
            //initSound( walkSound, SoundAsset.NPC_STEP );
        } else if ( modelAsset.equals( ModelAsset.SPIDER1 ) ) {
            physicalBuilderNPC = new PhysicalBuilder()
                    .setModelInstance( modelInstance )
                    .defaultMotionState()
                    .setPosition( position )
                    .setMass( 60.0f )
                    .capsuleShape( 1.0f, 1 )
                    .setCollisionFlag( CollisionFlags.CF_CHARACTER_OBJECT )
                    .setCallbackFlag( BulletWorld.NPC_FLAG )
                    .setCallbackFilter( BulletWorld.ENEMY_FILTER )
                    .disableRotation()
                    .disableDeactivation();
            //initSound( walkSound, SoundAsset.NPC_STEP );
        } else if ( modelAsset.equals( ModelAsset.MONSTER1 ) ) {
            physicalBuilderNPC = new PhysicalBuilder()
                    .setModelInstance( modelInstance )
                    .defaultMotionState()
                    .setPosition( position )
                    .setMass( 60.0f )
                    .capsuleShape( 1.5f, 1.5f )
                    .setCollisionFlag( CollisionFlags.CF_CHARACTER_OBJECT )
                    .setCallbackFlag( BulletWorld.NPC_FLAG )
                    .setCallbackFilter( BulletWorld.ENEMY_FILTER )
                    .disableRotation()
                    .disableDeactivation();
            //initSound( walkSound, SoundAsset.NPC_STEP );
        } else if ( modelAsset.equals( ModelAsset.DRAKON1 ) ) {
            physicalBuilderNPC = new PhysicalBuilder()
                    .setModelInstance( modelInstance )
                    .defaultMotionState()
                    .setPosition( position )
                    .setMass( 60.0f )
                    .capsuleShape( 1.5f, 1.0f )
                    .setCollisionFlag( CollisionFlags.CF_CHARACTER_OBJECT )
                    .setCallbackFlag( BulletWorld.NPC_FLAG )
                    .setCallbackFilter( BulletWorld.ENEMY_FILTER )
                    .disableRotation()
                    .disableDeactivation();
            //initSound( walkSound, SoundAsset.PLAYER_DAMAGE );
        } else {
            physicalBuilderNPC = new PhysicalBuilder()
                    .setModelInstance( modelInstance )
                    .defaultMotionState()
                    .setPosition( position )
                    .setMass( 60.0f )
                    .capsuleShape()
                    .setCollisionFlag( CollisionFlags.CF_CHARACTER_OBJECT )
                    .setCallbackFlag( BulletWorld.NPC_FLAG )
                    .setCallbackFilter( BulletWorld.ENEMY_FILTER )
                    .disableRotation()
                    .disableDeactivation();
            //initSound( walkSound, SoundAsset.NPC_STEP );
        }

        PhysicalComponent physicalComponent = physicalBuilderNPC.buildPhysicalComponent();
        physicalComponent.body.setRollingFriction( 0.1f );
        physicalComponent.body.setFriction( 0.1f );
        physicalComponent.body.setSpinningFriction( 0.1f );

        Entity entity = new Entity();
        entity.add( ModelComponentFromInstance( modelInstance ) );
        entity.add( AnimationComponentFromInstance( modelInstance ) );
        entity.add( TypeComponent( TYPE_OF_ENTITY.NPC ) );
        entity.add( life( 100.0f, 3, 2 ) );
        entity.add( NPC( actionArray, 5, null ) );
        entity.add( physicalComponent );
        entity.add( walkSound );
        entity.add( CharacterState() );

        return entity;
    }


    private LifeComponent life ( float newLifeValue, float heightOffset, float width ) {
        LifeComponent lifeComponent = new LifeComponent();

        lifeComponent.fullLife = newLifeValue;
        lifeComponent.life = newLifeValue;

        lifeComponent.heightOffset = heightOffset;
        lifeComponent.width = width;

        // FIXME цвет бара
        final TextureRegion regionRed = new TextureRegion(
                createTexture( 16, 16, Core.LOADING_BAR_COLOR ) );
        final float decalSize = 0.12f;

        lifeComponent.decal = Decal.newDecal( decalSize, decalSize, regionRed, false );

        return lifeComponent;
    }


    private CharacterStateComponent CharacterState () {
        CharacterStateComponent npcStateComponent = new CharacterStateComponent();
        npcStateComponent.state = CHARACTER_STATE.IDLE;
        npcStateComponent.nextState = CHARACTER_STATE.IDLE;
        return npcStateComponent;
    }


    private RemoveByTimeComponent RemoveByTime ( float time ) {
        RemoveByTimeComponent removeByTimeComponent = new RemoveByTimeComponent();
        removeByTimeComponent.time = time;
        return removeByTimeComponent;
    }


    private BoundingBoxComponent BoundingBoxFromAsset ( ModelAsset asset ) {
        BoundingBoxComponent typeOfEntityComponent = new BoundingBoxComponent();
        typeOfEntityComponent.boundingBox = asset.getBoundingBox();
        return typeOfEntityComponent;
    }


    private TypeOfEntityComponent TypeComponent ( TYPE_OF_ENTITY type ) {
        TypeOfEntityComponent typeOfEntityComponent = new TypeOfEntityComponent();
        typeOfEntityComponent.type = type;
        return typeOfEntityComponent;
    }


    private ModelComponent ModelComponentFromInstance ( ModelInstance modelInstance ) {
        ModelComponent modelComponent = new ModelComponent();
        modelComponent.modelInstance = modelInstance;
        return modelComponent;
    }


    private LevelIDComponent LevelIDComponentFromAsset ( ModelAsset asset ) {
        LevelIDComponent levelIDComponent = new LevelIDComponent();
        levelIDComponent.id = asset.ordinal();
        return levelIDComponent;
    }


    private NPCComponent NPC ( ImmutableArray< NPCAction > actionArray, float newDamage,
                               Item dropItem ) {
        NPCComponent npcComponent = new NPCComponent();
        npcComponent.actionArray = actionArray;
        npcComponent.time = actionArray.get( 0 ).durationTime;
        npcComponent.damage = newDamage;
        npcComponent.dropItem = dropItem;
        return npcComponent;
    }


    private AnimationComponent AnimationComponentFromInstance ( ModelInstance instance ) {
        AnimationComponent animationComponent = new AnimationComponent();
        animationComponent.controller = new AnimationController( instance );
        animationComponent.controller.allowSameAnimation = true;

        animationComponent.animations = new ImmutableArray< Animation >( instance.animations );

        Array< String > stringArray = new Array< String >();
        for ( int i = 0; i < animationComponent.animations.size(); i++ ) {
            stringArray.add( animationComponent.animations.get( i ).id );
        }
        animationComponent.names = new ImmutableArray< String >( stringArray );

        String id = stringArray.get( 0 );
        animationComponent.controller.animate( id, 1, 1.0f, null, 0f );
        animationComponent.controller.queue( id, -1, 1.0f, null, 0f );

        return animationComponent;
    }
}
