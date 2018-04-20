package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.Interactable;


/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class InteractComponent implements Component {

    public TYPE_OF_INTERACT type;
    public Interactable     interactable;


    public InteractComponent ( TYPE_OF_INTERACT type, Interactable interactable ) {
        this.type = type;
        this.interactable = interactable;
    }
}
