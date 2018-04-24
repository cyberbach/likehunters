package net.overmy.likehunters.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.PhysicalComponent;
import net.overmy.likehunters.ashley.component.PhysicalConnectComponent;

/*
     Created by Andrey Mikheev on 24.04.2017
     Contact me → http://vk.com/id17317
 */

public class PhysicalConnectSystem extends IteratingSystem {


    @SuppressWarnings( "unchecked" )
    public PhysicalConnectSystem () {
        super( Family.all( PhysicalComponent.class, PhysicalConnectComponent.class ).get() );
    }


    private Vector3 position  = new Vector3();
    private Matrix4 transform = new Matrix4();
    private Quaternion rotation = new Quaternion(  );


    @Override
    protected void processEntity ( Entity entity, float delta ) {

        final btRigidBody thisBody = MyMapper.PHYSICAL.get( entity ).body;
        final btRigidBody connectToBody = MyMapper.CONNECT_PHYSICAL.get( entity ).body;

        transform = thisBody.getWorldTransform();
        transform.getRotation( rotation );

        connectToBody.getWorldTransform().getTranslation( position );
        position.add( 0,1.1f,0 );
        transform.setToTranslation( position );
        transform.rotate( rotation );
        thisBody.proceedToTransform( transform );
    }
}
