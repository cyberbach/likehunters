package net.overmy.likehunters.logic.objects;

import com.badlogic.ashley.core.Entity;

import net.overmy.likehunters.ashley.EntityBuilder;
import net.overmy.likehunters.resources.ModelAsset;

/*
        Created by Andrey Mikheev on 20.04.2018
        Contact me → http://vk.com/id17317
*/
public class LadderObject implements GameObject {

    private Entity entity = null;


    @Override
    public ModelAsset getModelAsset () {
        return null;
    }


    @Override
    public boolean isUsed () {
        return false;
    }


    @Override
    public void use () {

    }


    @Override
    public Entity getEntity () {
        return entity;
    }


    @Override
    public void buildEntity () {
        //entity = EntityBuilder.createLadder(  );
    }


    @Override
    public void removeEntity () {

    }
}
