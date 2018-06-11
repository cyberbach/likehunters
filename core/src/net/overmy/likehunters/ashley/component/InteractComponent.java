package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.Interactable;
import net.overmy.likehunters.logic.TYPE_OF_INTERACT;




/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class InteractComponent implements Component {

    public TYPE_OF_INTERACT type;
    public Interactable     interactable;
}
