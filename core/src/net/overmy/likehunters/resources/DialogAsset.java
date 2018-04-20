package net.overmy.likehunters.resources;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */


import com.badlogic.gdx.Gdx;

import net.overmy.likehunters.DEBUG;

public enum DialogAsset {
    Empty( "" ),;

    public static boolean russianLocale = true;
    private final String russianText;
    private final String englishText;


    DialogAsset ( String russianText ) {
        this.russianText = russianText;
        this.englishText = "";
    }


    DialogAsset ( String russianText, String englishText ) {
        this.russianText = russianText;
        this.englishText = englishText;
    }


    public static void init () {
        String defaultLocale = java.util.Locale.getDefault().toString();
        String clsName = DialogAsset.class.getSimpleName();
        if ( DEBUG.SETTINGS.get() ) {
            Gdx.app.debug( "" + clsName + " locale", "" + defaultLocale );
        }
        russianLocale = !DEBUG.ENABLE_ENGLISH_TEXT.get() && "ru_RU".equals( defaultLocale );
    }


    public String get () {
        return russianLocale ? russianText : englishText;
    }
}
