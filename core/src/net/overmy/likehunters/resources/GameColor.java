/**
 * OVERMY.NET - Make your device live! *
 * <p/>
 * Games: http://play.google.com/store/apps/developer?id=OVERMY
 *
 * @author Andrey Mikheev (cb)
 */

package net.overmy.likehunters.resources;

import com.badlogic.gdx.graphics.Color;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public enum GameColor {

    BG( 0x2F343B ),
    FOX( 0xECB2B9 ),
    ;

    private final Color color;



    GameColor( final int newColor ) {
        this.color = hexToColor( newColor );
    }



    GameColor( final Color color2 ) {
        this.color = color2;
    }

/*
    public static Color getByNumber( final int n ) {
        return GameColor.values()[ n ].color;
    }
*/



    private static Color hexToColor( int valueInHex ) {
        final int mask = 0xFF;

        final int int_r = (valueInHex >> 16);
        final int int_g = (valueInHex >> 8) & mask;
        final int int_b = valueInHex & mask;

        final float float_r = (float) int_r / (float) 0xFF;
        final float float_g = (float) int_g / (float) 0xFF;
        final float float_b = (float) int_b / (float) 0xFF;

        return new Color( float_r, float_g, float_b, 1 );
    }



    public Color get() {
        return color;
    }
}
