package net.overmy.likehunters.logic.objects;

import com.badlogic.ashley.core.Entity;

import net.overmy.likehunters.resource.ModelAsset;


/*
        Created by Andrey Mikheev on 20.04.2018
        Contact me → http://vk.com/id17317
*/
public interface GameObject {
    ModelAsset getModelAsset ();

    boolean isUsed ();
    void use ();

    Entity getEntity ();
    void buildEntity ();
    void removeEntity ();
}
