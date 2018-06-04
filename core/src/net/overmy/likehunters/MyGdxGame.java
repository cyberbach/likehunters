package net.overmy.likehunters;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public class MyGdxGame extends ApplicationAdapter {

    private boolean disableRender = false;
    private Screen  screen        = null;


    public MyGdxGame () {
    }


    @Override
    public void create () {
    }


    @Override
    public void resize ( int width, int height ) {
        screen.resize( width, height );
    }


    @Override
    public void render () {
        if ( disableRender ) {
            return;
        }

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        screen.render( Gdx.graphics.getDeltaTime() );
    }


    @Override
    public void pause () {
        disableRender = true;
        screen.pause();
    }


    @Override
    public void resume () {
        disableRender = false;
        screen.resume();
    }


    @Override
    public void dispose () {
    }


    public void switchTo ( final SCREEN_TYPE screenType ) {
        if ( screen != null ) {
            disableRender = true;
            screen.hide();
            screen.dispose();
        }
/*

        if ( DEBUG.anything() ) {
            Gdx.app.debug( "? Screen switch to", screenType.toString() );
        }

        switch ( screenType ) {
            case LOADING_MENU:
                screen = new LoadingScreen( this, SCREEN_TYPE.MENU );
                break;

            case LOADING_GAME:
                screen = new LoadingScreen( this, SCREEN_TYPE.GAME );
                break;

            case MENU:
                screen = new MenuScreen( this );
                break;

            case GAME:
                screen = new GameScreen( this );
                break;

            case EXIT:
                Gdx.app.exit();
                return;
        }
*/

        disableRender = false;
        screen.show();
    }


    public enum SCREEN_TYPE {
        LOADING_MENU, LOADING_GAME, MENU, GAME, EXIT
    }
}
