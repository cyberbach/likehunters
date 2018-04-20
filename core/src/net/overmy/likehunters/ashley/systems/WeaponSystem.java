package net.overmy.likehunters.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.PhysicalComponent;
import net.overmy.likehunters.ashley.component.WeaponComponent;

/*
      Created by Andrey Mikheev on 13.03.2018
      Contact me → http://vk.com/id17317
 */

public class WeaponSystem extends IteratingSystem {

    @SuppressWarnings( "unchecked" )
    public WeaponSystem () {
        super( Family.all( WeaponComponent.class, PhysicalComponent.class ).get() );
    }


    @Override
    protected void processEntity ( Entity entity, float delta ) {
        WeaponComponent myWeaponComponent = MyMapper.MY_WEAPON.get( entity );
        PhysicalComponent physicalComponent = MyMapper.PHYSICAL.get( entity );

        // просто передать myWeaponComponent.hand.globalTransform в
        // physicalComponent.body.setWorldTransform( сюда )

        Vector3 weaponTranslation = new Vector3();
        myWeaponComponent.pointerToHandNode.globalTransform.getTranslation( weaponTranslation );
        Quaternion weaponRotation = new Quaternion();
        myWeaponComponent.pointerToHandNode.globalTransform.getRotation( weaponRotation );

        Matrix4 matrix4 = myWeaponComponent.transform;
        matrix4.translate( weaponTranslation );
        matrix4.rotate( weaponRotation );
        physicalComponent.body.setWorldTransform( matrix4 );
    }
}
