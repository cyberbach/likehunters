package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.Item;


/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class ItemComponent implements Component {

    public final Item item;


    public ItemComponent ( Item item ) {
        this.item = item;
    }
}
