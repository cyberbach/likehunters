package net.overmy.likehunters.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public enum FontAsset {
    MENU_TITLE( "bb.otf", 32, 1 ),;

    private static AssetManager manager = null;
    private final String path;
    private final int    size;
    private final float  borderSize;
    private final Color  color;
    private final Color  borderColor;
    private BitmapFont font = null;


    FontAsset ( String path, int size ) {
        this( path, size, 0, Color.WHITE, Color.LIGHT_GRAY );
    }


    FontAsset ( String path, int size, float borderSize ) {
        this( path, size, borderSize, Color.WHITE, Color.LIGHT_GRAY );
    }


    FontAsset ( String path, int size, float borderSize, Color color ) {
        this( path, size, borderSize, color, Color.LIGHT_GRAY );
    }


    FontAsset ( String path, int size, float borderSize, Color color, Color borderColor ) {
        String DEFAULT_PATH = "fonts/";

        this.path = DEFAULT_PATH + path;
        this.size = size;
        this.borderSize = borderSize;
        this.color = color;
        this.borderColor = borderColor;
    }


    public static void setManager ( final AssetManager assetManager ) {
        manager = assetManager;
    }


    public static void build () {
        final float scale = Gdx.graphics.getHeight() / 480.0f;

        for ( int i = 0; i < FontAsset.values().length; i++ ) {
            FreeTypeFontParameter myFontParameters = new FreeTypeFontParameter();
            myFontParameters.borderWidth = FontAsset.values()[ i ].borderSize;
            myFontParameters.borderColor = FontAsset.values()[ i ].borderColor;
            myFontParameters.characters = createChars();

            FreeTypeFontGenerator myFontGenerator = manager.get( FontAsset.values()[ i ].path,
                                                                 FreeTypeFontGenerator.class );
            myFontParameters.size = (int) ( FontAsset.values()[ i ].size * scale );
            myFontParameters.color = FontAsset.values()[ i ].color;
            FontAsset.values()[ i ].font = myFontGenerator.generateFont( myFontParameters );
        }
    }


    public static void load () {
        for ( int i = 0; i < FontAsset.values().length; i++ ) {
            String loadPath = FontAsset.values()[ i ].path;
            if ( !manager.isLoaded( loadPath ) ) {
                manager.load( loadPath, FreeTypeFontGenerator.class );
            }
        }
    }


    public static void unload () {
        for ( int i = 0; i < FontAsset.values().length; i++ ) {
            if ( FontAsset.values()[ i ].font != null ) {
                FontAsset.values()[ i ].font.dispose();
                FontAsset.values()[ i ].font = null;

                String unloadPath = FontAsset.values()[ i ].path;
                if ( manager.isLoaded( unloadPath ) ) {
                    manager.unload( unloadPath );
                }
            }
        }

        manager = null;
    }


    private static String createChars () {
        final StringBuilder fontChars = new StringBuilder();

        for ( int i = 0x20; i < 0x7B; i++ ) {
            fontChars.append( (char) i );
        }

        for ( int i = 0x401; i < 0x452; i++ ) {
            fontChars.append( (char) i );
        }

        return fontChars.toString();
    }


    public BitmapFont get () {
        return font;
    }
}
