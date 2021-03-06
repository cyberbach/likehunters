package net.overmy.likehunters.logic.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import net.overmy.likehunters.resource.Asset;
import net.overmy.likehunters.resource.ModelAsset;


/*
        Created by Andrey Mikheev on 11.06.2018
        Contact me → http://vk.com/id17317
*/
public interface GameObject {
    ImmutableArray< Asset > getAssets ();

    boolean isUsed ();
    void use ();

    Entity getEntity ();
    void buildEntity ();
    void removeEntity ();
}
