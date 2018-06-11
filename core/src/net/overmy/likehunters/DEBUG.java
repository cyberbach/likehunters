package net.overmy.likehunters;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public enum DEBUG {
    WINDOWS( true ),
    SETTINGS( false ),
    ENABLE_ENGLISH_TEXT( false ),

    // 2d screen info
    BASE_SCREEN( false ),
    STAGE( false ),

    // 3d loading stuff
    SHOW_MODEL_INFO( false ),

    // bullet
    DYNAMIC_LEVELS( true ),
    PHYSICAL_MESH( true ),

    // ashley
    CONTACTS( false ),
    ENTITIES( true ),
    DECAL_ENTITIES( false ),;

    private final boolean value;


    DEBUG ( boolean value ) {
        this.value = value;
    }


    public static boolean anything () {
        for ( DEBUG d : DEBUG.values() ) {
            if ( d.get() ) {
                return true;
            }
        }
        return false;
    }


    public boolean get () {
        return value;
    }
}
