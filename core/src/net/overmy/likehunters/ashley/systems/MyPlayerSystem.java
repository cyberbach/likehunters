package net.overmy.likehunters.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import net.overmy.likehunters.MyCamera;
import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.MyPlayerComponent;

/*
     Created by Andrey Mikheev on 22.04.2017
     Contact me → http://vk.com/id17317
 */

public class MyPlayerSystem extends IteratingSystem {


    private final Vector2 direction      = new Vector2();
    private final Vector3 velocity       = new Vector3();
    private       Matrix4 bodyTransform  = new Matrix4();
    private final Vector3 notFilteredPos = new Vector3();
    private       float   modelAngle     = 0.0f;

    private boolean jump = false;

    private Quaternion rotation = new Quaternion();


    @SuppressWarnings( "unchecked" )
    public MyPlayerSystem () {
        super( Family.all( MyPlayerComponent.class ).get() );
    }


    @Override
    protected void processEntity ( Entity entity, float delta ) {
        btRigidBody playerBody = MyMapper.PHYSICAL.get( entity ).body;

        // Двигаем или останавливаем физическое тело
        velocity.set( direction.x, 0, direction.y );
        velocity.add( 0, playerBody.getLinearVelocity().y, 0 );

        playerBody.getWorldTransform( bodyTransform );
        bodyTransform.getTranslation( notFilteredPos );

        if ( direction.len() != 0 ) {
            bodyTransform.idt();
            bodyTransform.setToTranslation( notFilteredPos );
            bodyTransform.rotate( Vector3.Y, modelAngle );
            playerBody.proceedToTransform( bodyTransform );

            MyMapper.ANIMATION.get( entity ).play( 1, 1.0f );
        }
        playerBody.setLinearVelocity( velocity );

        if ( jump ) {
            jump = false;
            playerBody.applyCentralImpulse( new Vector3( 0, 800, 0 ) );
            MyMapper.ANIMATION.get( entity ).play( 2, 1.0f );
        }

        //MyCamera.setCameraPosition( notFilteredPos );

        MyMapper.ANIMATION.get( entity ).queue( 0,1.0f );
    }


    public void move ( float x, float y ) {
        direction.set( x, y );

        float myspeed = direction.len() * 10;

        direction.nor();
        direction.rotate( -MyCamera.getCameraAngle() );
        direction.scl( myspeed );

        // Сохраняем угол для поворота модели
        modelAngle = 90 - direction.angle();
    }


    public Vector3 getNotFilteredPos () {
        return notFilteredPos;
    }


    public float getAngle () {
        bodyTransform.getRotation( rotation );
        return rotation.getAxisAngle( Vector3.Y );
    }


    public void startJump () {
        jump = true;
    }


    public void startAttack () {
    }


    @Override
    public void removedFromEngine ( Engine engine ) {
        super.removedFromEngine( engine );

        //disableWalkSound();
    }
}
