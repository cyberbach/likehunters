package net.overmy.likehunters.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.AnimationComponent;
import net.overmy.likehunters.ashley.component.MyPlayerComponent;
import net.overmy.likehunters.screen.MyCamera;
import net.overmy.likehunters.screen.MyPlayerGUI;

/*
        Created by Andrey Mikheev on 05.06.2018
        Contact me → http://vk.com/id17317
*/
public class MyPlayerSystem extends IteratingSystem {

    private float capsuleAngle = 0.0f;

    private Vector3 position      = new Vector3();
    private Vector2 direction     = new Vector2();
    private Vector3 velocity      = new Vector3();
    private Matrix4 bodyTransform = new Matrix4();

    private PLAYER_STATE state     = PLAYER_STATE.IDLE;
    private PLAYER_STATE nextState = PLAYER_STATE.IDLE;


    @SuppressWarnings( "unchecked" )
    public MyPlayerSystem () {
        super( Family.all( MyPlayerComponent.class ).get() );
    }


    @Override
    protected void processEntity ( Entity entity, float delta ) {
        if ( !MyPlayerGUI.isEnabled() ) {
            return;
        }

        btRigidBody body = MyMapper.PHYSICAL.get( entity ).body;
        moveAndRotatePhysicalBody( body );

        AnimationComponent animationComponent = MyMapper.ANIMATION.get( entity );
        changeAnimationFromState( animationComponent );
    }


    private void moveAndRotatePhysicalBody ( btRigidBody body ) {
        float touchPadX = MyPlayerGUI.touchpad.getKnobPercentX();
        float touchPadY = -MyPlayerGUI.touchpad.getKnobPercentY();
        direction.set( touchPadX, touchPadY );

        float speedOfMovement;
        if ( direction.len() == 0 ) {
            nextState = PLAYER_STATE.IDLE;
            speedOfMovement = 0;
        } else {
            nextState = PLAYER_STATE.RUN;
            speedOfMovement = direction.len() * 10.0f;

            direction.nor();
            direction.rotate( -MyCamera.getCameraAngle() );

            // Сохраняем угол для поворота модели
            capsuleAngle = 90 - direction.angle();
        }

        // Двигаем или останавливаем физическое тело
        velocity.set( direction.x, 0, direction.y );
        velocity.scl( speedOfMovement );
        velocity.add( 0, body.getLinearVelocity().y, 0 );

        // передвижение персонажа
        body.setLinearVelocity( velocity );

        // поворот модели
        body.getWorldTransform( bodyTransform );
        bodyTransform.getTranslation( position );
        bodyTransform.idt();
        bodyTransform.setToTranslation( position );
        bodyTransform.rotate( Vector3.Y, capsuleAngle );
        body.proceedToTransform( bodyTransform );

        Core.playerPosition.set( position );
        Core.playerAngle = capsuleAngle;
    }


    private void changeAnimationFromState ( AnimationComponent component ) {
        final int IDLE = 0;
        final int RUN = 1;
        final int JUMP = 2;
        final float animationSpeed = 2.0f;

        if ( !nextState.equals( state ) ) {
            switch ( nextState ) {
                case IDLE:
                    playAnimation( component, IDLE, animationSpeed );
                    queueAnimation( component, IDLE, animationSpeed );
                    break;
                case RUN:
                    playAnimation( component, RUN, animationSpeed );
                    queueAnimation( component, RUN, animationSpeed );
                    break;
                case JUMP:
                    playAnimation( component, JUMP, animationSpeed );
                    queueAnimation( component, JUMP, animationSpeed );
                    break;
            }

            state = nextState;
        } else {
            switch ( nextState ) {
                case IDLE:
                    queueAnimation( component, IDLE, animationSpeed );
                    break;
                case RUN:
                    queueAnimation( component, RUN, animationSpeed );
                    break;
                case JUMP:
                    queueAnimation( component, JUMP, animationSpeed );
                    break;
            }
        }
    }


    private void playAnimation ( AnimationComponent animationComponent, int n, float newSpeed ) {
        float duration = animationComponent.animations.get( n ).duration * newSpeed;
        animationComponent.controller.animate(
                animationComponent.animations.get( n ).id, 1, duration, null, 0f );
    }


    private void queueAnimation ( AnimationComponent animationComponent, int n, float newSpeed ) {
        float duration = animationComponent.animations.get( n ).duration * newSpeed;
        animationComponent.controller.queue(
                animationComponent.animations.get( n ).id, 1, duration, null, 0f );
    }


    enum PLAYER_STATE {
        IDLE,
        RUN,
        JUMP
    }
}
