package net.overmy.likehunters.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.MyGdxGame;
import net.overmy.likehunters.MyGdxGame.SCREEN_TYPE;
import net.overmy.likehunters.MyRender;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public class BaseScreen implements Screen, GestureDetector.GestureListener {
    private final MyGdxGame game;
    protected Stage       stage      = null;
    private   SCREEN_TYPE nextScreen = null;


    BaseScreen ( MyGdxGame game ) {
        this.game = game;
    }


    @Override
    public void show () {
        stage = MyRender.getStage();
        setInputProcessors();

        MyRender.TransitionIN();
    }


    protected void setInputProcessors () {
        InputProcessor keys = new MyKeysProcessor();
        InputProcessor gestures = new GestureDetector( this );
        InputProcessor processor = new InputMultiplexer( stage, keys, gestures );

        Gdx.input.setInputProcessor( processor );
        Gdx.input.setCatchBackKey( true );
        Gdx.input.setCatchMenuKey( true );
    }


    @Override
    public void render ( float delta ) {
        stage.act( delta );
        update( delta );

        draw( delta );
        stage.draw();

        MyRender.drawTransitionScreen( delta );
        if ( nextScreen != null && !MyRender.inTransition() ) {
            switchCurrentScreen();
        }
    }


    private void draw ( float delta ) {
    }


    protected void update ( float delta ) {
    }


    @Override
    public void resize ( int width, int height ) {
    }


    @Override
    public void pause () {

    }


    @Override
    public void resume () {

    }


    @Override
    public void hide () {

    }


    @Override
    public void dispose () {
        nextScreen = null;
        stage = null;
    }


    @Override
    public boolean touchDown ( float x, float y, int pointer, int button ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, touchDown", "x=" + x + " y=" + y );
        }
        return false;
    }


    @Override
    public boolean tap ( float x, float y, int count, int button ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, tap", "x=" + x + " y=" + y );
        }
        return false;
    }


    @Override
    public boolean longPress ( float x, float y ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, longPress", "x=" + x + " y=" + y );
        }
        return false;
    }


    @Override
    public boolean fling ( float velocityX, float velocityY, int button ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, fling",
                           "velocityX=" + velocityX + " velocityY=" + velocityY );
        }
        return false;
    }


    @Override
    public boolean pan ( float x, float y, float deltaX, float deltaY ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, pan",
                           "x=" + x + " y=" + y + " deltaX=" + deltaX + " deltaY=" + deltaY );
        }
        return false;
    }


    @Override
    public boolean panStop ( float x, float y, int pointer, int button ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, panStop", "x=" + x + " y=" + y );
        }
        return false;
    }


    @Override
    public boolean zoom ( float initialDistance, float distance ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, zoom",
                           "initialDistance=" + initialDistance + " distance=" + distance );
        }
        return false;
    }


    @Override
    public boolean pinch ( Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
                           Vector2 pointer2 ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, pinch",
                           "initialPointer1=" + initialPointer1 +
                           " initialPointer2=" + initialPointer2 +
                           "pointer1=" + pointer1 + " pointer2=" + pointer2 );
        }
        return false;
    }


    @Override
    public void pinchStop () {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen", "pinchStop" );
        }
    }


    private boolean touchUp ( float x, float y, int pointer, int button ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, touchUp", "x=" + x + " y=" + y );
        }
        return false;
    }


    private boolean scrolled ( int amount ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, scrolled", "amount=" + amount );
        }
        return false;
    }


    private boolean keyDown ( int keycode ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, keyDown", "keycode=" + keycode );
        }

        if ( keycode == Input.Keys.ESCAPE ) {
            backButton();
        }

        return true;
    }


    private boolean keyUp ( int keycode ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, keyUp", "keycode=" + keycode );
        }

        return false;
    }


    public void backButton () {
        // Default transition
        transitionTo( SCREEN_TYPE.MENU );
    }


    private boolean touchDragged ( float x, float y ) {
        return false;
    }


    void transitionTo ( SCREEN_TYPE screen ) {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen, transitionTo", "screen=" + screen );
        }

        if ( MyRender.inTransition() ) {
            return;
        }

        if ( nextScreen != null ) {
            return;
        }

        Gdx.app.debug( "BaseScreen, transitionTo", "MyRender.TransitionOUT()" );
        MyRender.TransitionOUT();
        nextScreen = screen;
    }


    private void switchCurrentScreen () {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "BaseScreen", "switchCurrentScreen" );
        }

        game.switchTo( nextScreen );
    }


    private class MyKeysProcessor implements InputProcessor {

        @Override
        public boolean keyDown ( int keycode ) {
            return BaseScreen.this.keyDown( keycode );
        }


        @Override
        public boolean keyUp ( int keycode ) {
            return BaseScreen.this.keyUp( keycode );
        }


        @Override
        public boolean keyTyped ( char character ) {
            return false;
        }


        @Override
        public boolean touchDown ( int screenX, int screenY, int pointer, int button ) {
            return BaseScreen.this.touchDown( screenX, screenY, pointer, button );
        }


        @Override
        public boolean touchUp ( int screenX, int screenY, int pointer, int button ) {
            return BaseScreen.this.touchUp( screenX, Core.HEIGHT - screenY, pointer, button );
        }


        @Override
        public boolean touchDragged ( int screenX, int screenY, int pointer ) {
            return BaseScreen.this.touchDragged( screenX, screenY );
        }


        @Override
        public boolean mouseMoved ( int screenX, int screenY ) {
            return false;
        }


        @Override
        public boolean scrolled ( int amount ) {
            return BaseScreen.this.scrolled( amount );
        }
    }
}
