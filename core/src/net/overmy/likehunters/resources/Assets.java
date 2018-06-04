package net.overmy.likehunters.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public class Assets {

    private static AssetManager manager = null;


    private Assets () {
    }


    public static void init () {
        final FileHandleResolver resolver = new InternalFileHandleResolver();
        final AssetLoader fontsGenerator = new FreeTypeFontGeneratorLoader( resolver );
        final AssetLoader fontsLoader = new FreetypeFontLoader( resolver );

        manager = new AssetManager();
        manager.setLoader( FreeTypeFontGenerator.class, fontsGenerator );
        manager.setLoader( BitmapFont.class, ".ttf", fontsLoader );

        FontAsset.setManager( manager );
        IMG.setManager( manager );
    }


    public static void setManagerLogLevel ( int LOG_LEVEL ) {
        manager.getLogger().setLevel( LOG_LEVEL );
    }


    public static void load () {
        FontAsset.load();
        IMG.load();
    }


    public static void build () {
        FontAsset.build();
        IMG.build();
    }


    public static void unload () {
        FontAsset.unload();
        IMG.unload();

        manager.finishLoading();
        manager.dispose();

        /*Settings.save();*/
    }


    public static AssetManager getManager () {
        return manager;
    }
}
