package net.overmy.likehunters.logic.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import net.overmy.likehunters.ashley.EntityBuilder;
import net.overmy.likehunters.ashley.component.RemoveByTimeComponent;
import net.overmy.likehunters.logic.NPCAction;
import net.overmy.likehunters.resources.ModelAsset;

/*
        Created by Andrey Mikheev on 18.05.2018
        Contact me → http://vk.com/id17317
*/
public class NPCObject implements GameObject {
    Vector3                     position;
    ModelAsset                  modelAsset;
    Entity                      entity;
    ImmutableArray< NPCAction > actionArray;

    @Override
    public ModelAsset getModelAsset () {
        //Gdx.app.debug( "try to load npc","here "+modelAsset );
        return modelAsset;
    }


    @Override
    public boolean isUsed () {
        return false;
    }


    @Override
    public void use () {

    }


    public NPCObject ( Vector3 position, ModelAsset modelAsset,ImmutableArray< NPCAction > actionArray ) {
        this.position = position;
        this.modelAsset = modelAsset;
        this.actionArray=actionArray;
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

        modelAsset.build();

        entity = EntityBuilder.createNPC( position,modelAsset ,actionArray);
    }


    @Override
    public void removeEntity () {
        if ( entity == null ) {
            return;
        }

        entity.add( new RemoveByTimeComponent( 0 ) );
        entity = null;
    }
}
