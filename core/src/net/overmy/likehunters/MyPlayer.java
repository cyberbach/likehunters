package net.overmy.likehunters;

/*
      Created by Andrey Mikheev on 10.10.2017
      Contact me → http://vk.com/id17317
 */

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import net.overmy.likehunters.ashley.EntityBuilder;
import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.TYPE_OF_ENTITY;
import net.overmy.likehunters.ashley.component.TypeOfEntityComponent;
import net.overmy.likehunters.ashley.component.WeaponComponent;
import net.overmy.likehunters.logic.DynamicLevels;
import net.overmy.likehunters.logic.ItemInBagg;
import net.overmy.likehunters.resources.ModelAsset;

public final class MyPlayer {
    private static final Vector2 v2Position     = new Vector2();
    private static final Vector2 direction      = new Vector2();
    private static final Vector3 velocity       = new Vector3();
    private static       Matrix4 bodyTransform  = new Matrix4();
    private static final Vector3 notFilteredPos = new Vector3();
    private static       float   modelAngle     = 0.0f;

    private static float dustTimer = 0.1f;

    public static boolean live = true;

    private static ModelAsset    modelAsset    = null;
    private static Entity        playerEntity  = null;
    public static  ModelInstance modelInstance = null; // for menu screen

    public static float extraSpeed2 = 0.0f;

    private static       boolean jump             = false;
    public static        boolean canJump          = true;
    private static       float   jumpDelayCounter = 0.0f;
    public static        float   extraJump        = 0.0f;
    private final static float   JUMP_DELAY       = 1.1f;

    private static       boolean attack             = false;
    public static        boolean canAttack          = true;
    private static       float   attackDelayCounter = 0.0f;
    private final static float   ATTACK_DELAY       = 0.4f;


    private static float moveY = 0.0f;

    private static float speed;

    public static boolean onLadder = false;

    private static btRigidBody playerBody = null;


    private static ModelInstance weaponInstance = null;

    public static boolean isAttacking = false;

    private static float environmentTimeFX = 0.2f;


    private MyPlayer () {
    }



    public static void init () {

        modelAsset = ModelAsset.TEST_NPC;

        if ( playerEntity != null ) {
            return;
        }

        modelInstance = modelAsset.get();

        Gdx.app.debug( "modelInstance",""+modelInstance );

        playerEntity = EntityBuilder.createPlayer( modelInstance );
        playerBody = MyMapper.PHYSICAL.get( playerEntity ).body;
        //setBody.setSpinningFriction( 0.1f );
        //setBody.setRollingFriction( 0.1f );

        onLadder = false;

        /*walk = SoundAsset.Step3;
        walk.playLoop();
        walk.setThisVolume( 0.0f );*/

        /////// create empty-hand weapon

        PhysicalBuilder physicalBuilder = new PhysicalBuilder()
                .defaultMotionState()
                .zeroMass()
                .boxShape( 0.4f )
                .setCollisionFlag( CollisionFlags.CF_NO_CONTACT_RESPONSE )
                .setCallbackFlag( BulletWorld.MYWEAPON_FLAG )
                .setCallbackFilter( BulletWorld.PLAYER_FILTER )
                .disableDeactivation();


        // set position by loaded dynamic level n

        Matrix4 loadedPosition = new Matrix4();
        loadedPosition.setToTranslation( startPositions[ DynamicLevels.getCurrent() ] );
        playerBody.setWorldTransform( loadedPosition );
    }


    private final static Vector3[] startPositions = new Vector3[] {
            new Vector3( -8.883699f, 3.3939111f, 5.062443f ),
            new Vector3( -46.313328f, 3.857123f, -54.17792f ),
            new Vector3( 0.59057057f, 2.7008367f, -149.63187f ),
            new Vector3( -85.78828f, 0.69114524f, -168.87808f ),
            new Vector3( -136.69661f, 2.7439363f, -362.21973f ),
            new Vector3( -245.94257f, 1.0721123f, -401.8604f ),
            new Vector3( -45.45584f, 3.2740111f, -442.94907f ),
            new Vector3( -122.323654f, 1.9100869f, -439.41284f ),
            new Vector3( -191.47537f, 6.3835006f, -473.49835f ), // 08
            new Vector3( -101.14407f, 3.693308f, -473.77426f ),
            new Vector3( -20.104654f, 8.933582f, -457.48343f ),//10
            new Vector3( -116.753716f, 2.1432183f, -535.78394f ),
            new Vector3( -59.840767f, 4.429752f, -620.98224f ),//12 с камнями
            new Vector3( -26.71163f, 2.1870565f, -656.59534f ),// с мостом
            new Vector3( -3.0696986f, -12.057977f, -620.8561f ),//труба
            new Vector3( -222.9113f, 13.589132f, -601.74225f ),//15 - продолжение 8-го
            new Vector3( 4.819017f, 5.0487814f, -753.78217f ),//зимняя
            new Vector3( -7.4517694f, 6.3198724f, -832.11035f ),//зимняя 17
            new Vector3( -43.897774f, 1.6834409f, -820.6343f ),//18 кривая к боссу
            new Vector3( -82.29632f, 2.3234348f, -834.7413f ),// 19 last boss
            new Vector3( 42.987503f, 6.59883f, -845.1438f ),
            new Vector3( 151.79369f, 9.256329f, -862.6666f ),
            new Vector3( 124.73992f, 3.3195992f, -766.70984f ),
            new Vector3( 134.58124f, 5.1869044f, -712.3334f ),//23
            new Vector3( 64.61289f, 5.4833136f, -592.8458f ),
            new Vector3( 49.58613f, 3.6817787f, -483.6303f ),//25 замок
            new Vector3( 55.648212f, 3.6817815f, -424.52176f ),//26 придворки замка
            new Vector3( 104.72133f, 3.909871f, -401.69968f ),
            new Vector3( 114.18283f, 3.717038f, -359.78253f ),
            new Vector3( 146.52269f, 4.539406f, -292.49527f ), // 29
            new Vector3( 119.88079f, 5.6133566f, -232.79167f ),// last secret
            new Vector3( -145.61096f, 2.1391315f, -859.0328f )// 31 - last one
    };


    public static void updateControls ( float deltaTime ) {

        if ( !live ) {
            return;
        }

        if ( !canJump ) {
            jumpDelayCounter -= deltaTime;
            if ( jumpDelayCounter < 0 ) {
                canJump = true;
            }
        }

        if ( !canAttack ) {
            attackDelayCounter -= deltaTime;
            if ( attackDelayCounter < 0 ) {
                canAttack = true;
            }
        }

        //updateAnimation( deltaTime );

        speed=3;

        boolean playerOnGround = MyMapper.GROUNDED.get( playerEntity ).grounded;

        // Двигаем или останавливаем физическое тело
        velocity.set( direction.x, 0, direction.y );
        velocity.scl( speed );
        if ( !onLadder ) {
            velocity.add( 0, playerBody.getLinearVelocity().y, 0 );
        } else {
            velocity.add( 0, -moveY * 5, 0 );
        }

        playerBody.setLinearVelocity( velocity );

        playerBody.getWorldTransform( bodyTransform );
        bodyTransform.getTranslation( notFilteredPos );
        bodyTransform.idt();
        bodyTransform.setToTranslation( notFilteredPos );
        bodyTransform.rotate( Vector3.Y, modelAngle );
        playerBody.proceedToTransform( bodyTransform );

        MyCamera.setCameraPosition( notFilteredPos );

        v2Position.set( notFilteredPos.x, notFilteredPos.z );

        environmentTimeFX -= deltaTime;
        if ( environmentTimeFX < 0 ) {
            environmentTimeFX = 0.4f;
            //EntityBuilder.createEnvironmentFX( notFilteredPos, 8, GameColor.BLUE );
        }
    }


    private static float   extraSpeed;
    private static boolean speedUpFX;
    private static boolean jumpUpFX;


    private static void updateFX ( float deltaTime ) {
        boolean timerWorked = speedUpTime >= 0;
        speedUpTime -= deltaTime;
        extraSpeed = 0.0f;
        speedUpFX = speedUpTime >= 0;
        if ( speedUpFX ) {
            int textOfTimer = (int) speedUpTime + 1;
            speedUpTimerLabel.setText( "" + textOfTimer );
            GlyphLayout layout = speedUpTimerLabel.getGlyphLayout();
            int iconSize = (int) ( Core.HEIGHT * 0.1f );
            speedUpTimerLabel.setPosition( -layout.width / 2, -layout.height / 2 - iconSize / 2 );
            extraSpeed = 6.0f;
        } else {
            if ( timerWorked ) {
                //SoundAsset.ENDBOTTLE.play();
            }
        }

        timerWorked = immortalTime >= 0;
        immortalTime -= deltaTime;
        boolean immortalFX = immortalTime >= 0;
        if ( immortalFX ) {
            int textOfTimer = (int) immortalTime + 1;
            immortalTimerLabel.setText( "" + textOfTimer );
            GlyphLayout layout = immortalTimerLabel.getGlyphLayout();
            int iconSize = (int) ( Core.HEIGHT * 0.1f );
            immortalTimerLabel.setPosition( -layout.width / 2, -layout.height / 2 - iconSize / 2 );
            immortal = true;
        } else {
            immortal = false;
            if ( timerWorked ) {
                // SoundAsset.ENDBOTTLE.play();
            }
        }

        timerWorked = jumpUpTime >= 0;
        jumpUpTime -= deltaTime;
        jumpUpFX = jumpUpTime >= 0;
        if ( jumpUpFX ) {
            int textOfTimer = (int) jumpUpTime + 1;
            jumpUpTimerLabel.setText( "" + textOfTimer );
            GlyphLayout layout = jumpUpTimerLabel.getGlyphLayout();
            int iconSize = (int) ( Core.HEIGHT * 0.1f );
            jumpUpTimerLabel.setPosition( -layout.width / 2, -layout.height / 2 - iconSize / 2 );
            extraJump = 150.0f;
        } else {
            extraJump = 0.0f;
            if ( timerWorked ) {
                // SoundAsset.ENDBOTTLE.play();
            }
        }
    }


    public static boolean immortal = false;


    private static void dust ( float deltaTime, boolean speedUpFX, boolean jumpUpFX ) {
        dustTimer -= deltaTime;
        if ( dustTimer < 0 ) {
            if ( jumpUpFX ) {
                dustTimer = 0.1f;
                //EntityBuilder.createDustFX( notFilteredPos, 0.72f, GameColor.PURPLE );
            }
            if ( speedUpFX ) {
                dustTimer = 0.08f;
                //EntityBuilder.createDustFX( notFilteredPos, 0.72f, GameColor.GREEN );
            }

            if ( !jumpUpFX && !speedUpFX ) {
                dustTimer = 0.14f;
                notFilteredPos.sub( 0, 0.5f, 0 );
                //EntityBuilder.createDustFX( notFilteredPos, 0.72f, GameColor.WHITEGL );
            }
        }
    }


    public static void startJump () {
        jump = true;
        jumpDelayCounter = JUMP_DELAY;
        canJump = false;
    }


    public static void startAttack () {
        attack = true;
        attackDelayCounter = ATTACK_DELAY;
        canAttack = false;
    }


    public static void move ( float x, float y ) {
        direction.set( x, y );
        moveY = y;
    }


    public static void dispose () {
        modelInstance = null;

        modelAsset = null;
        playerEntity = null;

        playerBody = null;
        weaponInstance = null;
    }


    public static void clearAll () {

        if ( weaponInstance != null ) {
            weaponInstance.nodes.get( 0 ).detach();
        }
        weaponInstance = null;

        playerEntity = null;

        playerBody = null;

        v2Position.set( 0, 0 );

        live = true;
    }


    public static Vector2 getPosition () {
        return v2Position;
    }


    public static Vector3 getNotFilteredPos () {
        return notFilteredPos;
    }


    public static btRigidBody getBody () {
        return playerBody;
    }


    private static float immortalTime       = -0.1f;
    private static Label immortalTimerLabel = null;

    private static float speedUpTime       = -0.1f;
    private static Label speedUpTimerLabel = null;

    private static float jumpUpTime       = -0.1f;
    private static Label jumpUpTimerLabel = null;


    public static Entity getEntity () {
        return playerEntity;
    }
}
