package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;


/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class WeaponComponent implements Component {
    public final Node pointerToHandNode;

    // TODO что это за матрица?
    public final Matrix4 transform;


    public WeaponComponent ( Node hand, Matrix4 transform ) {
        this.pointerToHandNode = hand;
        this.transform = transform;
    }
}
