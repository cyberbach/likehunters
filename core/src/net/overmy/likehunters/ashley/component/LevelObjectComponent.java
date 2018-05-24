package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;

import net.overmy.likehunters.logic.objects.GameObject;
import net.overmy.likehunters.logic.objects.ObjectBuilder;
/*
      Created by Andrey (cb) Mikheev on 03.03.2018
      Contact me → http://vk.com/id17317
 */

public class LevelObjectComponent implements Component {

    public final GameObject gameObject;


    public LevelObjectComponent ( GameObject gameObject ) {
        this.gameObject = gameObject;
    }
}
