package net.overmy.likehunters.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.MyCamera;
import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.InteractComponent;
import net.overmy.likehunters.ashley.component.OutOfCameraComponent;
import net.overmy.likehunters.ashley.component.SkipScriptComponent;
import net.overmy.likehunters.ashley.component.TYPE_OF_INTERACT;
import net.overmy.likehunters.logic.Dialog;
import net.overmy.likehunters.logic.InteractDialog;
import net.overmy.likehunters.logic.InteractItem;
import net.overmy.likehunters.logic.Interactable;
import net.overmy.likehunters.logic.Item;


/**
 * Created by Andrey (cb) Mikheev
 * 20.12.2016
 */

public class InteractSystem extends IteratingSystem {

    private final Camera camera;

    private final Vector3 position = new Vector3();
    private       Ray     ray      = null;

    private boolean actOnEntity = false;

    private TYPE_OF_INTERACT type            = TYPE_OF_INTERACT.EMPTY;
    private Item             currentItem     = null;
    private Dialog           currentMyDialog = null;


    @SuppressWarnings( "unchecked" )
    public InteractSystem () {
        super( Family.all( InteractComponent.class )
                     .exclude( OutOfCameraComponent.class ).get() );

        this.camera = MyCamera.get();
    }


    @Override
    public void update ( float delta ) {
        type = TYPE_OF_INTERACT.EMPTY;
        ray = camera.getPickRay( Core.WIDTH_HALF, Core.HEIGHT_HALF );

        super.update( delta );
        actOnEntity = false;
    }


    @Override
    protected void processEntity ( Entity entity, float deltaTime ) {
        MyMapper.MODEL.get( entity ).modelInstance.transform.getTranslation( position );

        final float distance = ray.origin.dst2( position );
        final boolean onMyWay = Intersector.intersectRaySphere( ray, position, 1, null );
        if ( distance < 50.0f && onMyWay ) {
            final InteractComponent interactComponent = MyMapper.INTERACT.get( entity );

            type = interactComponent.type;
            Interactable interactable = interactComponent.interactable;

            if ( actOnEntity ) {
                switch ( type ) {
                    case LOOT:
                        if(interactable instanceof InteractItem)
                        currentItem = ( (InteractItem) interactable ).item;
                        //MyPlayer.addToBag( currentItem );

                        // Устанавливаем в levelObject флаг, чтобы предмет
                        // не создался снова, при перезагрузке уровня
                        if ( MyMapper.LEVEL_OBJECT.has( entity ) ) {
                            MyMapper.LEVEL_OBJECT.get( entity ).levelObject.useEntity();
                        }

                        //SoundAsset.Collect3.play();

                        getEngine().removeEntity( entity );
                        break;

                    case TALK:
                        if(interactable instanceof InteractDialog)
                            currentMyDialog = ( (InteractDialog) interactable ).dialog;
                        //Gdx.app.debug( "говорим с нпс", "" + currentMyDialog.getTitle() );
                        break;

                    case USE:
                        DoorSystem doorSystem = getEngine().getSystem( DoorSystem.class );
                        doorSystem.processKey( currentItem );
                        //Gdx.app.debug( "используем", "" + currentItem.getName() );

                        break;
                }
                actOnEntity = false;
            }

            entity.add( new SkipScriptComponent() );
        } else {
            entity.remove( SkipScriptComponent.class );
        }
    }


    public Dialog getCurrentMyDialog () {
        return currentMyDialog;
    }


    public Item getCurrentItem () {
        return currentItem;
    }


    public TYPE_OF_INTERACT getCurrentType () {
        return type;
    }


    public void act () {
        actOnEntity = true;
    }
}
