package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class TypeOfEntityComponent implements Component {

    public final TYPE_OF_ENTITY type;

    public TypeOfEntityComponent ( TYPE_OF_ENTITY type ) {
        this.type = type;
    }
}
