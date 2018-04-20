package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Group;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class Stage2DGroupComponent implements Component {
    public Group group;


    public Stage2DGroupComponent ( Group group ) {
        this.group = group;
    }


    public Stage2DGroupComponent () {
        this.group = new Group();
    }
}
