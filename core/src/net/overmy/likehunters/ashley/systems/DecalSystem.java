package net.overmy.likehunters.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import net.overmy.likehunters.MyCamera;
import net.overmy.likehunters.MyRender;
import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.DecalComponent;
import net.overmy.likehunters.ashley.component.PositionComponent;
import net.overmy.likehunters.utils.Vector3Animator;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class DecalSystem extends IteratingSystem {

    private Vector3 offsetByAnimator  = new Vector3();
    private int     decalCount        = 0;
    private int     visibleDecalCount = 0;


    @SuppressWarnings( "unchecked" )
    public DecalSystem () {
        super( Family.all( DecalComponent.class, PositionComponent.class ).get() );
    }


    @Override
    protected void processEntity ( Entity entity, float delta ) {
        final DecalComponent decalComponent = MyMapper.DECAL.get( entity );

        final Vector3Animator animator = decalComponent.animator;

        animator.update( delta );

        final Color decalColor = decalComponent.decal.getColor();
        decalColor.a = animator.getAlphaPercentage();
        decalComponent.decal.setColor( decalColor );

        final Vector3 position = MyMapper.POSITION.get( entity ).position;

        offsetByAnimator.set(
                animator.getCurrent().x,
                animator.getCurrent().y,
                animator.getCurrent().z
                            );
        offsetByAnimator.add( position );

        if ( MyCamera.isVisible( offsetByAnimator ) ) {
            decalComponent.decal.setPosition( offsetByAnimator );

            // facing overrides all rotations
            decalComponent.decal.lookAt( MyCamera.get().position, MyCamera.get().up );
            MyRender.getDecalBatch().add( decalComponent.decal );
            visibleDecalCount++;
        }

        decalCount++;
    }


    @Override
    public void update ( float delta ) {
        decalCount = 0;
        visibleDecalCount = 0;
        super.update( delta );
    }


    public int getVisibleDecalCount () {
        return visibleDecalCount;
    }


    public int getDecalCount () {
        return decalCount;
    }
}
