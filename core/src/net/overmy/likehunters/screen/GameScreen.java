package net.overmy.likehunters.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.TimeUtils;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.MyGdxGame;
import net.overmy.likehunters.ashley.AshleyWorld;
import net.overmy.likehunters.ashley.system.RenderSystem;
import net.overmy.likehunters.bullet.BulletWorld;
import net.overmy.likehunters.logic.DynamicLevels;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public class GameScreen extends BaseScreen {
    public GameScreen ( MyGdxGame game ) {
        super( game );
    }


    @Override
    public void show () {
        super.show();

        InputProcessor keys = new MyKeysProcessor();
        InputProcessor processor = new InputMultiplexer( stage, keys );
        Gdx.input.setInputProcessor( processor );

        stage.clear();

        MyPlayerGUI.init();

        DynamicLevels.reload();
    }


    float startTime;
    private StringBuilder log = new StringBuilder();


    @Override
    public void update ( float delta ) {
        super.update( delta );

        DynamicLevels.update( delta );
        AshleyWorld.update( delta );
        MyCamera.update( delta );

        if ( DEBUG.FPS ) {
            if ( TimeUtils.nanoTime() - startTime > 1000000000 ) /* 1,000,000,000ns == one second */ {
                log.setLength( 0 );
                log.append( "▓▒░ FPS = " );
                log.append( Gdx.graphics.getFramesPerSecond() );
                log.append( " " );

                RenderSystem rend = AshleyWorld.getEngine().getSystem( RenderSystem.class );
                int models = rend.getVisibleModelsCount();
                int totalModels = rend.getTotalModelsCount();
                log.append( " Models=" );
                log.append( models );
                log.append( "/" );
                log.append( totalModels );
/*

                DecalSystem decalSystem = AshleyWorld.getEngine().getSystem( DecalSystem.class );
                log.append( " Decals=" );
                log.append( decalSystem.getVisibleDecalCount() );
                log.append( "/" );
                log.append( decalSystem.getDecalCount() );
*/

                log.append( " ░▒▓" );

                Gdx.app.log( "", log.toString() );
                startTime = TimeUtils.nanoTime();

                //fpsLabel.setText( log.toString() );
            }
        }
    }


    @Override
    public void draw ( float delta ) {
        if ( DEBUG.PHYSICAL_MESH ) {
            BulletWorld.drawDebug();
        }
    }


    @Override
    public void dispose () {
        super.dispose();

        AshleyWorld.getEngine().removeAllEntities();
    }


    private class MyKeysProcessor implements InputProcessor {
        float xTouch = 0.0f;
        float yTouch = 0.0f;


        @Override
        public boolean keyDown ( int keycode ) {
            return false;
        }


        @Override
        public boolean keyUp ( int keycode ) {
            return false;
        }


        @Override
        public boolean keyTyped ( char character ) {
            return false;
        }


        @Override
        public boolean touchDown ( int screenX, int screenY, int pointer, int button ) {
            xTouch = screenX;
            yTouch = Core.HEIGHT - screenY;
            return false;
        }


        @Override
        public boolean touchUp ( int screenX, int screenY, int pointer, int button ) {
            return false;
        }


        @Override
        public boolean touchDragged ( int screenX, int screenY, int pointer ) {
            float x = xTouch - screenX;
            float y = Core.HEIGHT - screenY - yTouch;
            MyCamera.controlDirectionByDrag( x, y );
            return true;
        }


        @Override
        public boolean mouseMoved ( int screenX, int screenY ) {
            return false;
        }


        @Override
        public boolean scrolled ( int amount ) {
            return false;
        }
    }
}
