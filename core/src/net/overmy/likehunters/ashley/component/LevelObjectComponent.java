package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.LevelObjectBuilder;
/*
      Created by Andrey (cb) Mikheev on 03.03.2018
      Contact me → http://vk.com/id17317
 */

public class LevelObjectComponent implements Component {

    // FIXME здесь должна быть ссылка на объект, а не на сборщик
    public final LevelObjectBuilder levelObject;


    public LevelObjectComponent ( LevelObjectBuilder levelObject ) {
        this.levelObject = levelObject;
    }
}
