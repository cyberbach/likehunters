package net.overmy.likehunters.logic.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import net.overmy.likehunters.ashley.component.RemoveByTimeComponent;
import net.overmy.likehunters.logic.collectable.TRIGGER;
import net.overmy.likehunters.logic.collectable.TriggerCollectable;
import net.overmy.likehunters.resource.ModelAsset;


/*
        Created by Andrey Mikheev on 18.05.2018
        Contact me → http://vk.com/id17317
*/
public class TriggerObject implements GameObject {

    private boolean used = false;

    private Entity             entity      = null;
    private Vector3            position    = null;
    private TriggerCollectable collectable = null;
    private float              size        = 1.0f;


    public TriggerObject ( TRIGGER trigger, Vector3 position, float size ) {
        this.position = position;
        this.collectable = new TriggerCollectable();
        this.collectable.trigger = trigger;
        this.size = size;
    }


    @Override
    public ModelAsset getModelAsset () {
        return null;
    }


    @Override
    public boolean isUsed () {
        return used;
    }


    @Override
    public void use () {
        if ( used ) {
            return;
        }

        used = true;
        Gdx.app.debug( "trigger " + collectable.trigger, "used" );
    }


    @Override
    public Entity getEntity () {
        return entity;
    }


    @Override
    public void buildEntity () {
        if ( entity != null ) {
            return;
        }

        /*if ( !used ) {
            entity = EntityBuilder.createTrigger( this, collectable, position, size );
        }*/
    }


    @Override
    public void removeEntity () {
        if ( entity == null ) {
            return;
        }

        entity.add( new RemoveByTimeComponent() );
        entity = null;
    }
}
