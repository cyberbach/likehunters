package net.overmy.likehunters;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public enum DEBUG {
    BASE_SCREEN( true ),
    WINDOWS( true ),
    STAGE( true );

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
