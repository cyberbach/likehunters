/*
      Created by Andrey Mikheev
      Contact me → http://vk.com/id17317
 */

package net.overmy.likehunters.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

import net.overmy.likehunters.DEBUG;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

public enum MusicAsset {
    ;

    private final String path;
    private        Music music  = null;
    private static float timer  = 0.01f;
    private static float volume = 0.0f;


    public static void setVolume ( float fvolume ) {
        volume = fvolume;

        for ( int i = 0; i < MusicAsset.values().length; i++ ) {
            if ( MusicAsset.values()[ i ].music != null ) {
                MusicAsset.values()[ i ].music.setVolume( volume );
            }
        }
    }


    MusicAsset ( final String path ) {
        String DEFAULT_DIR = "music/";
        //String DEFAULT_EXT = ".mp3";
        this.path = DEFAULT_DIR + path;// + DEFAULT_EXT;
    }


/*

    public static void updateVolume( float newVolume ) {
        //float musicVolume = (float) Settings.MUSIC.getInteger() / 100.0f;
        currentTrack.setVolume( newVolume );
    }

*/


    public static void stopAll () {
        if ( DEBUG.ON_WINDOWS.get() ) {
            return;
        }

        for ( int i = 0; i < MusicAsset.values().length; i++ ) {
            if ( MusicAsset.values()[ i ].music != null ) {
                MusicAsset.values()[ i ].music.stop();
            }
        }
    }


    public static void build ( final AssetManager manager ) {
        if ( DEBUG.ON_WINDOWS.get() ) {
            return;
        }

        for ( int i = 0; i < MusicAsset.values().length; i++ ) {
            MusicAsset.values()[ i ].music = manager.get( MusicAsset.values()[ i ].path,
                                                          Music.class );
        }
    }


    public static void load ( final AssetManager manager ) {
        if ( DEBUG.ON_WINDOWS.get() ) {
            return;
        }

        for ( int i = 0; i < MusicAsset.values().length; i++ ) {
            manager.load( MusicAsset.values()[ i ].path, Music.class );
        }
    }


    public static void unload ( final AssetManager manager ) {
        if ( DEBUG.ON_WINDOWS.get() ) {
            return;
        }

        stopAll();
        for ( int i = 0; i < MusicAsset.values().length; i++ ) {

            if ( MusicAsset.values()[ i ].music != null ) {
                MusicAsset.values()[ i ].music.dispose();
                MusicAsset.values()[ i ].music = null;

                manager.unload( MusicAsset.values()[ i ].path );
            }
        }
    }


    public void play ( boolean loop ) {
        if ( DEBUG.ON_WINDOWS.get() || volume == 0.0f ) {
            return;
        }

        this.music.setLooping( loop );
        this.music.play();
        this.music.setVolume( volume );
    }

/*
    public void setPan ( float x ) {
        this.music.setPan( x, 1 );
    }
*/


    public void play () {
        play( false );
    }

/*
    public void stop () {
        this.music.stop();
    }
*/

    // HARDCODE!
    //public Music get() { return Gdx.audio.newMusic( Gdx.files.internal( this.path ) ); }


    public Music get () {
        return this.music;
    }


    public void stopLoop () {
        if ( DEBUG.ON_WINDOWS.get() ) {
            return;
        }

        if ( this.music.isPlaying() ) {
            this.music.setLooping( false );
        }
    }

}
