package net.overmy.likehunters.ashley.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

import net.overmy.likehunters.resources.GameColor;
import net.overmy.likehunters.utils.GFXHelper;


/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public class LifeComponent implements Component {

    public final float heightOffset;
    public final float width;
    public final Decal decal;
    public       float life;
    private      float fullLife;


    public LifeComponent ( float newLifeValue, float heightOffset, float width ) {
        this.fullLife = newLifeValue;
        this.life = fullLife;

        this.heightOffset = heightOffset;
        this.width = width;

        // FIXME цвет бара
        final TextureRegion regionRed = new TextureRegion(
                GFXHelper.createTexture( 16, 16, GameColor.BG.get() ) );
        final float decalSize = 0.12f;

        decal = Decal.newDecal( decalSize, decalSize, regionRed, false );
    }


    public void decLife ( float decValue ) {
        life -= decValue;
        if ( life < 1.0f ) {
            life = 0.0f;
        }
    }


    public float getLifePercent () {
        if ( life == 0.0f ) {
            return 0.0f;
        } else {
            return life / fullLife;
        }
    }
}
