package net.overmy.likehunters.logic.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.overmy.likehunters.AshleyWorld;
import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.ashley.component.ModelComponent;
import net.overmy.likehunters.logic.Dialog;
import net.overmy.likehunters.logic.Item;
import net.overmy.likehunters.logic.NPCAction;
import net.overmy.likehunters.resources.GameColor;
import net.overmy.likehunters.resources.ModelAsset;

/**
 * Created by Andrey (cb) Mikheev
 * 17.03.2017
 */

public class ObjectBuilder {

    public    ModelAsset                  dynamicModelAsset = null;
    protected Vector3                     position          = null;
    private   OBJECT_TYPE                 type              = null;
    public    Item                        item              = null;
    private   Dialog                      myDialog          = null;
    private   ImmutableArray< NPCAction > script            = null; // очередь действий - это скрипт
    protected Entity                      entity            = null;
    public    boolean                     used              = false;
    private   float                       heightOfLadder    = 0.0f;
    private   float                       rotation          = 0.0f;
    private   float                       size              = 1.0f;
    private   GameColor                   tint              = null;

    private Vector2 doorAngles = null;


    public ObjectBuilder setHeightOfLadder ( float heightOfLadder ) {
        this.heightOfLadder = heightOfLadder;
        return this;
    }


    public ObjectBuilder setDynamicModelAsset ( ModelAsset dynamicModelAsset ) {
        this.dynamicModelAsset = dynamicModelAsset;
        return this;
    }


    public ObjectBuilder setPosition ( Vector3 position ) {
        this.position = position;
        return this;
    }


    public ObjectBuilder setPositionR ( float x, float y, float z, float rot ) {
        this.position = new Vector3( x, y, z );
        setRotation( rot );
        return this;
    }


    public ObjectBuilder setType ( OBJECT_TYPE type ) {
        this.type = type;
        return this;
    }


    public ObjectBuilder setItem ( Item item ) {
        this.item = item;/*
        if ( item.getModelAsset() != null ) {
            this.dynamicModelAsset = item.getModelAsset();
        }*/
        return this;
    }


    public ObjectBuilder setMyDialog ( Dialog myDialog ) {
        this.myDialog = myDialog;
        return this;
    }


    public ObjectBuilder setScript ( ImmutableArray< NPCAction > actionsQueue ) {
        this.script = actionsQueue;
        return this;
    }


    public ObjectBuilder setRotation ( float rotation ) {
        this.rotation = rotation;
        return this;
    }


    public ObjectBuilder setSize ( float size ) {
        this.size = size;
        return this;
    }


    public void useEntity () {
        used = true;
        removeEntity();
    }


    void removeEntity () {
        if ( entity != null ) {
            AshleyWorld.getEngine().removeEntity( entity );
        }

        if ( DEBUG.DYNAMIC_LEVELS.get() ) {
            Gdx.app.debug( "removeEntity", "item=" + item + " model=" + dynamicModelAsset );
        }
        entity = null;
    }


    void buildEntity () {
        // Энтити уже создана
        if ( entity != null ) {
            return;
        }

        // Энтити была уже создана и мы её использовали (подобрали или убили)
        if ( used ) {
            return;
        }

        if ( DEBUG.DYNAMIC_LEVELS.get() ) {
            Gdx.app.debug( "Need to build OBJECT", "" + this.type );
        }
/*

        switch ( type ) {
            case DOOR:
                entity = EntityBuilder.createDoor( position, item, doorAngles );
                break;

            case DOOR_SWITCH:
                entity = EntityBuilder.createDoorSwitch( position, item, rotation );
                break;

            case LADDER:
                entity = EntityBuilder.createLadder( position, heightOfLadder );
                break;

            case TRIGGER:
                entity = EntityBuilder.createTrigger( position, item, size );
                break;

            case PICKABLE:
                entity = EntityBuilder.createPickable( position, item, dynamicModelAsset, this );
                break;

            case BOX:
                entity = EntityBuilder.createCrate( position, dynamicModelAsset, item, this );
                break;

            case ROCK:
                entity = EntityBuilder.createRock( position, dynamicModelAsset, this );
                break;

            case COLLECTABLE:
                entity = EntityBuilder.createCollectable( position, item, dynamicModelAsset, this );
                break;

            case HOVER_COLLECTABLE:
                entity = EntityBuilder.createHoverCollectable( position, item,
                                                            dynamicModelAsset, this );
                break;

            case NPC:
                entity = EntityBuilder.createNPC( position, dynamicModelAsset, myDialog,
                                               script );
                break;

            case ENEMY:
                entity = EntityBuilder.createEnemy( position, dynamicModelAsset, script, item, this );
                break;

            case WEAPON:
                entity = EntityBuilder.createWeapon( position, item, this );
                break;

            case INTERACTIVE:
                entity = EntityBuilder.createInteractive( position, dynamicModelAsset,
                                                       myDialog, rotation );
                break;
        }
*/

        if ( tint != null ) {
            ModelInstance instance = entity.getComponent( ModelComponent.class ).modelInstance;
            //EntityBuilder.changeMaterialColor( instance, tint );
        }
    }


    public ObjectBuilder setDoorAngles ( float fromAngle, float toAngle ) {
        doorAngles = new Vector2();
        doorAngles.set( fromAngle, toAngle );
        return this;
    }


    public ObjectBuilder setColorTint ( GameColor colorTint ) {
        this.tint = colorTint;
        return this;
    }

/*

    void buildModel () {
        if ( item != null ) {
            dynamicModelAsset = item.getModelAsset();
        }

        if ( dynamicModelAsset != null ) {
            if ( !isWeapon( dynamicModelAsset ) ) {
                if ( entity == null && !used ) {
                    dynamicModelAsset.build();
                }
            }
        }
    }
*/
}
