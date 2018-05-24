package net.overmy.likehunters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.dynamics.btFixedConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import net.overmy.likehunters.logic.GameHelper;
import net.overmy.likehunters.utils.Vector3Animator;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class MyCamera {

    private static Vector3 cameraDirection = new Vector3( 0, 0, -1 );
    public static Vector3           filteredPosition    = new Vector3();

    private static Vector3           cameraPosition      = new Vector3();
    private static Vector3Animator   camMotion           = new Vector3Animator();
    private static DirectionalLight  light               = null;
    private static float             cameraAngle         = 0.0f;
    private static PerspectiveCamera camera              = null;
    public static float             filteredCameraAngle = 0.0f;


    private static btRigidBody cameraPhysicalBody      = null;
    private static btRigidBody cameraPhysicalGhostBody = null;

    private static btFixedConstraint constraint = null;

    private static Quaternion lastRotation = new Quaternion();
    private static Vector3    lastPosition = new Vector3();

    private static boolean block = false;


    private MyCamera () {
    }


    public static void init () {
        float cullingDistance = 400.0f;// Задняя плоскость отсечения (дальность тумана)
        float defaultFOV = 58.0f; // Угол обзора (67 - стандартный)

        Vector3 upVector = new Vector3( 0, 10000, 0 );

        camera = new PerspectiveCamera( defaultFOV, Core.WIDTH, Core.HEIGHT );
        camera.near = 0.1f;
        camera.far = cullingDistance;
        camera.up.set( upVector );

        Color lightColor = new Color( 0.6f, 0.6f, 0.6f, 1.0f );
        light = new DirectionalLight();
        light.set( lightColor, 0, 0, 0 );
        unblock ();
    }


    public static void initBody () {
        GameHelper helper = new GameHelper();
        cameraPhysicalBody = helper.createCameraBody();
    }


    public static void setConnectionBody ( btRigidBody body ) {
        if ( constraint != null ) {
            return;
        }

        initBody();

        cameraPhysicalGhostBody = body;

        constraint = new btFixedConstraint(
                body, cameraPhysicalBody,
                cameraPhysicalBody.getWorldTransform(),
                new Matrix4()
        );
        BulletWorld.addConstraint( constraint );
    }


    public static void removeConnectionBody () {
        if ( constraint != null ) {
            constraint.dispose();
        }
        constraint = null;
    }


    public static float getCameraAngle () {
        return cameraAngle;
    }


    public static PerspectiveCamera get () {
        return camera;
    }


    public static boolean isVisible ( Matrix4 matrix4 ) {
        matrix4.getTranslation( cameraPosition );
        return camera.frustum.sphereInFrustum( cameraPosition, 1.5f );
    }


    private static void updateMotion ( float delta ) {
        camMotion.update( delta );
        if ( !camMotion.isNeedToUpdate() ) {
            float x = MathUtils.random( -0.2f, 0.2f );
            float y = MathUtils.random( 0.0f, 0.5f );
            float time = MathUtils.random( 8.0f, 12.0f );
            camMotion.fromCurrent().setTo( x, y, 0 );
            camMotion.setAnimationTime( time ).resetTime();
        }
    }


    public static void update ( float delta ) {
        updateMotion( delta );

        if ( !block ) {
            if ( Gdx.input.isKeyJustPressed( Input.Keys.LEFT ) ) {
                Matrix4 ghostTransform = cameraPhysicalGhostBody.getWorldTransform();
                ghostTransform.rotate( Vector3.Y, 15 );
                cameraPhysicalGhostBody.setWorldTransform( ghostTransform );
            }

            if ( Gdx.input.isKeyJustPressed( Input.Keys.RIGHT ) ) {
                Matrix4 ghostTransform = cameraPhysicalGhostBody.getWorldTransform();
                ghostTransform.rotate( Vector3.Y, -15 );
                cameraPhysicalGhostBody.setWorldTransform( ghostTransform );
            }

            Vector3 positionOfPhysicalBody = new Vector3();
            cameraPhysicalBody.getWorldTransform().getTranslation( positionOfPhysicalBody );

            filteredCameraAngle = LowPassFilter( filteredCameraAngle, cameraAngle );
            filteredPosition.x = LowPassFilter( filteredPosition.x, positionOfPhysicalBody.x );
            filteredPosition.y = LowPassFilter( filteredPosition.y, positionOfPhysicalBody.y );
            filteredPosition.z = LowPassFilter( filteredPosition.z, positionOfPhysicalBody.z );
        }

        camera.position.set( filteredPosition );
        camera.direction.set( cameraDirection );
        camera.direction.rotate( Vector3.Y, filteredCameraAngle );
        camera.update();

        light.direction.set( 0, -1, -2 );
        light.direction.rotate( Vector3.Y, filteredCameraAngle );
    }


    private static final float ALPHA = 0.2f;


    private static float LowPassFilter ( float a, float b ) {
        return a + ALPHA * ( b - a );
    }


    static DirectionalLight getLight () {
        return light;
    }


    public static void addCameraAngle ( float nextCameraAngle ) {
        cameraAngle += nextCameraAngle;

        Matrix4 ghostTransform = cameraPhysicalGhostBody.getWorldTransform();
        // get
        ghostTransform.getRotation( lastRotation );
        ghostTransform.getTranslation( lastPosition );
        // set
        ghostTransform.setToRotation( Vector3.Y, cameraAngle );
        ghostTransform.translate( lastPosition );
        // save transform
        cameraPhysicalGhostBody.setWorldTransform( ghostTransform );
    }


    public static void addVerticalDirection ( float y ) {
        float nextY = cameraDirection.y + y;
        if ( nextY > 0.25f || nextY < -0.4f ) {
            return;
        }
        cameraDirection.add( 0, y, 0 );
    }


    public static boolean isVisible ( BoundingBox boundingBox ) {
        return camera.frustum.boundsInFrustum( boundingBox );
    }


    public static boolean isVisible ( Vector3 testPosition ) {
        return camera.frustum.pointInFrustum( testPosition );
    }


    public void dispose () {
        camera = null;
    }


    public static void block () {
        block = true;
    }


    public static void unblock () {
        block = false;
    }
}
