package net.overmy.likehunters.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.resources.Assets;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class LoadIndicator extends Group {

    private boolean loadingComplete = false;


    @Override
    public void act ( float delta ) {
        loadingComplete = Assets.getManager().update();

        if ( !loadingComplete ) {
            setPosition( 0, 0 );
            super.act( delta );
        } else {
            setPosition( Core.WIDTH, 0 );
        }
    }


    @Override
    public void draw ( Batch batch, float parentAlpha ) {
        if ( !loadingComplete ) {
            super.draw( batch, parentAlpha );
        }
    }
}
