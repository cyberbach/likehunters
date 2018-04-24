package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;

/*
     Created by Andrey Mikheev on 24.04.2017
     Contact me → http://vk.com/id17317
 */

public class PhysicalConnectComponent implements Component {

    public btRigidBody body;


    public PhysicalConnectComponent ( btRigidBody body ) {
        this.body = body;
    }
}
