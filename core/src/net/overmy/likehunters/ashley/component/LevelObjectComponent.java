package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.objects.ObjectBuilder;
/*
      Created by Andrey (cb) Mikheev on 03.03.2018
      Contact me → http://vk.com/id17317
 */

public class LevelObjectComponent implements Component {

    // FIXME здесь должна быть ссылка на объект, а не на сборщик
    public final ObjectBuilder levelObject;


    public LevelObjectComponent ( ObjectBuilder levelObject ) {
        this.levelObject = levelObject;
    }
}
