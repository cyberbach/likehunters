package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

/*
      Created by Andrey Mikheev on 22.04.2018
      Contact me → http://vk.com/id17317
 */

public class AnimationComponent implements Component {

    public AnimationController         controller;
    public ImmutableArray< Animation > animations;
    public ImmutableArray< String > names = null;


    public AnimationComponent ( ModelInstance modelInstance ) {
        controller = new AnimationController( modelInstance );
        controller.allowSameAnimation = true;

        animations = new ImmutableArray< Animation >( modelInstance.animations );
    }


    public void queue ( int id, float newSpeed ) {
        if ( controller.queued != null ) {
            return;
        }

        float duration = animations.get( id ).duration * newSpeed;
        controller.queue( names.get( id ), -1, duration, null, 0f );
    }


    public void play ( int id, float newSpeed ) {
        if ( names.get( id ).equals( controller.current.animation.id ) ) {
            return;
        }

        float duration = animations.get( id ).duration * newSpeed;
        controller.animate( names.get( id ), 1, duration, null, 0f );
    }
}
