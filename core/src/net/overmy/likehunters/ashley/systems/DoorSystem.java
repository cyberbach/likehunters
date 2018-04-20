package net.overmy.likehunters.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.DoorComponent;
import net.overmy.likehunters.ashley.component.PhysicalComponent;
import net.overmy.likehunters.logic.Item;
/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class DoorSystem extends IteratingSystem {

    private Item    testItem    = null;
    private Vector3 oldPosition = new Vector3();


    @SuppressWarnings( "unchecked" )
    public DoorSystem () {
        super( Family.all( DoorComponent.class, PhysicalComponent.class ).get() );
    }


    @Override
    protected void processEntity ( Entity entity, float delta ) {
        DoorComponent doorComponentponent = MyMapper.DOOR.get( entity );

        PhysicalComponent physicalComponent = MyMapper.PHYSICAL.get( entity );
        Matrix4 transform = physicalComponent.body.getWorldTransform();
        transform.getTranslation( oldPosition );

        transform.idt();
        transform.translate( oldPosition );
        transform.rotate( Vector3.Y, doorComponentponent.currentAngle );

        physicalComponent.body.setWorldTransform( transform );

        if ( testItem == null ) {
            return;
        }

        boolean keyIsSame = testItem.equals( doorComponentponent.item );
        if ( doorComponentponent.currentAngle != doorComponentponent.endAngle && keyIsSame ) {

            if ( doorComponentponent.currentAngle < doorComponentponent.endAngle ) {
                doorComponentponent.currentAngle += delta * 10;
                if ( doorComponentponent.currentAngle > doorComponentponent.endAngle ) {
                    doorComponentponent.currentAngle = doorComponentponent.endAngle;
                }
            }

            if ( doorComponentponent.currentAngle > doorComponentponent.endAngle ) {
                doorComponentponent.currentAngle -= delta * 10;
                if ( doorComponentponent.currentAngle < doorComponentponent.endAngle ) {
                    doorComponentponent.currentAngle = doorComponentponent.endAngle;
                }
            }
        }
    }


    public void processKey ( Item item ) {
        testItem = item;
    }
}
