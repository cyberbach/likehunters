package net.overmy.likehunters.ashley;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btIndexedMesh;
import com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.StringBuilder;

import net.overmy.likehunters.BulletWorld;
import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.MyRender;
import net.overmy.likehunters.ashley.component.PhysicalBVHComponent;
import net.overmy.likehunters.ashley.component.PhysicalComponent;


/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class MyEntityListener implements EntityListener {

    private StringBuilder log = new StringBuilder();


    public MyEntityListener () {
    }


    @Override
    public void entityAdded ( Entity entity ) {
        if ( MyMapper.GROUP_IN_STAGE.has( entity ) ) {
            MyRender.getStage().addActor( MyMapper.GROUP_IN_STAGE.get( entity ).group );
        }


        if ( DEBUG.ENTITIES.get() ) {
            log.setLength( 0 );
            if ( MyMapper.TYPE.has( entity ) ) {
                log.append( MyMapper.TYPE.get( entity ).type.toString() );
                log.append( " " );
            }
        }

        if ( MyMapper.PHYSICAL.has( entity ) ) {
            BulletWorld.addBody( MyMapper.PHYSICAL.get( entity ).body );
            log.append( " UV=" );
            log.append( +MyMapper.PHYSICAL.get( entity ).body.getUserValue() );
            log.append( " " );
        }

        if ( DEBUG.ENTITIES.get() ) {
            if ( "DecalComponent".equals(
                    entity.getComponents()
                          .get( 0 )
                          .getClass()
                          .getSimpleName() ) && !DEBUG.DECAL_ENTITIES.get() ) {
                return;
            }

            log.append( " [" );
            int size = entity.getComponents().size();
            for ( int i = 0; i < size; i++ ) {
                Component c = entity.getComponents().get( i );
                String name = c.getClass().getSimpleName();
                log.append( name.replace( "Component", "" ) );
                if ( i != size - 1 ) {
                    log.append( ", " );
                }
            }
            log.append( "]" );
            Gdx.app.debug( "Entity Listener ♦ ADD", log.toString() );
        }
    }


    @Override
    public void entityRemoved ( Entity entity ) {


        if ( MyMapper.LEVEL_OBJECT.has( entity ) && MyMapper.NPC.has( entity ) ) {
            if ( MyMapper.NPC.get( entity ).die ) {
                MyMapper.LEVEL_OBJECT.get( entity ).levelObject.used = true;
            }
        }

        if ( MyMapper.DECAL.has( entity ) ) {
            MyMapper.DECAL.get( entity ).animator = null;
            MyMapper.DECAL.get( entity ).decal = null;
        }

        if ( MyMapper.GROUP_IN_STAGE.has( entity ) ) {
            MyMapper.GROUP_IN_STAGE.get( entity ).group.remove();
        }

        if ( DEBUG.ENTITIES.get() ) {
            log.setLength( 0 );
            if ( MyMapper.TYPE.has( entity ) ) {
                log.append( MyMapper.TYPE.get( entity ).type.toString() );
            }
        }

        if ( MyMapper.PHYSICAL.has( entity ) ) {

            PhysicalComponent physicalComponent = MyMapper.PHYSICAL.get( entity );
            if ( DEBUG.ENTITIES.get() ) {
                log.append( " UV=" );
                log.append( physicalComponent.body.getUserValue() );
                log.append( " " );
            }
            BulletWorld.removeBody( physicalComponent.body );

            btMotionState motionState = physicalComponent.body.getMotionState();
            if ( motionState != null ) {
                motionState.dispose();
            }

            btCollisionShape shape = physicalComponent.body.getCollisionShape();
            if ( shape != null ) {
                shape.dispose();
            }

            physicalComponent.constructionInfo.dispose();
            physicalComponent.body.dispose();

            if ( MyMapper.BVH_PHYSICAL.has( entity ) ) {
                PhysicalBVHComponent bvhPhysicalComponent = MyMapper.BVH_PHYSICAL.get( entity );

                btIndexedMesh indexedMesh = bvhPhysicalComponent.indexedMesh;
                indexedMesh.dispose();

                btTriangleIndexVertexArray meshInterface = bvhPhysicalComponent.meshInterface;
                meshInterface.dispose();
            }
        }

        /*if ( MyMapper.MODEL.has( entity ) ) {
            ModelComponent modelComponent = MyMapper.MODEL.get( entity );
            modelComponent.modelInstance.transform.setToRotation( Vector3.Y, 0 );
        }*/

        if ( DEBUG.ENTITIES.get() ) {
            if ( "DecalComponent".equals(
                    entity.getComponents()
                          .get( 0 )
                          .getClass()
                          .getSimpleName() ) && !DEBUG.DECAL_ENTITIES.get() ) {
                return;
            }

            log.append( " [" );
            int size = entity.getComponents().size();
            for ( int i = 0; i < size; i++ ) {
                Component c = entity.getComponents().get( i );
                String name = c.getClass().getSimpleName();
                log.append( name.replace( "Component", "" ) );
                if ( i != size - 1 ) {
                    log.append( ", " );
                }
            }
            log.append( "]" );

            Gdx.app.debug( "Entity Listener ♦ REMOVED", log.toString() );
        }
    }
}
