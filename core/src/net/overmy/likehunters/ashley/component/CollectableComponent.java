package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.collectables.Collectable;

/*
        Created by Andrey Mikheev on 18.05.2018
        Contact me → http://vk.com/id17317
*/
public class CollectableComponent implements Component {
    public Collectable collectable;


    public CollectableComponent ( Collectable collectable ) {
        this.collectable = collectable;
    }
}
