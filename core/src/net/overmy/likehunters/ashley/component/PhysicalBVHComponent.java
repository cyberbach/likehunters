package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btIndexedMesh;
import com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class PhysicalBVHComponent implements Component {

    public btIndexedMesh              indexedMesh   = null;
    public btTriangleIndexVertexArray meshInterface = null;



    public PhysicalBVHComponent ( btIndexedMesh indexedMesh,
                                  btTriangleIndexVertexArray meshInterface ) {
        this.indexedMesh = indexedMesh;
        this.meshInterface = meshInterface;
    }
}
