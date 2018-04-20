package net.overmy.likehunters.resources;

/*
     Created by Andrey Mikheev on 29.09.2017
     Contact me → http://vk.com/id17317
 */

import com.badlogic.gdx.Gdx;

import net.overmy.likehunters.DEBUG;

public enum TextAsset {
    Title( "Квики\nв поисках Мики", "Quicky\nin search of Mickey" ),;

    public static boolean russianLocale = true;
    private final String russianText;
    private final String englishText;

/*
    TextAsset ( String russianText ) {
        this.russianText = russianText;
        this.englishText = "";
    }
*/


    TextAsset ( String russianText, String englishText ) {
        this.russianText = russianText;
        this.englishText = englishText;
    }


    public static void init () {
        String defaultLocale = java.util.Locale.getDefault().toString();
        String clsName = TextAsset.class.getSimpleName();
        if ( DEBUG.SETTINGS.get() ) {
            Gdx.app.debug( "" + clsName + " locale", "" + defaultLocale );
        }
        russianLocale = !DEBUG.ENABLE_ENGLISH_TEXT.get() && "ru_RU".equals( defaultLocale );
    }


    public String get () {
        return russianLocale ? russianText : englishText;
    }
}
