package net.overmy.likehunters.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.MyGdxGame;
import net.overmy.likehunters.MyGdxGame.SCREEN_TYPE;
import net.overmy.likehunters.MyRender;
import net.overmy.likehunters.resources.Assets;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public class LoadingScreen extends BaseScreen {

    private SCREEN_TYPE screenAfterLoad;

    private Image loadingBar = null;

    private boolean buildFinished = false;


    public LoadingScreen ( MyGdxGame game, SCREEN_TYPE nextScreen ) {
        super( game );

        this.screenAfterLoad = nextScreen;

        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "LoadingScreen", "loading()" );
        }

        switch ( screenAfterLoad ) {
            case MENU:
                loadingMenu();
                break;

            case GAME:
                loadingGame();
                break;
        }
    }


    private static Sprite createLoadingSprite () {
        Pixmap pixmap = new Pixmap( Core.WIDTH, Core.HEIGHT / 14, Pixmap.Format.RGB888 );
        pixmap.setColor( Core.BAR_COLOR );
        pixmap.fill();

        Texture texture = new Texture( pixmap );
        pixmap.dispose();

        return new Sprite( texture );
    }


    private void loadingMenu () {
        Assets.load();
    }


    private void loadingGame () {
        //DynamicLevels.reload();
    }


    private void build () {
        if ( DEBUG.BASE_SCREEN.get() ) {
            Gdx.app.debug( "LoadingScreen", "build()" );
        }
        switch ( screenAfterLoad ) {
            case MENU:
                buildMenu();
                break;

            case GAME:
                buildGame();
                break;
        }
    }


    private void buildMenu () {
        Assets.build();
    }


    private void buildGame () {

    }


    @Override
    public void backButton () {
        Gdx.app.debug( "Back button in loading screen is", "disabled" );
    }


    @Override
    public void show () {
        super.show();

        loadingBar = new Image( createLoadingSprite() );
        stage.addActor( loadingBar );
    }


    @Override
    public void update ( float delta ) {
        super.update( delta );

        if ( Assets.getManager().update() ) {
            if ( !buildFinished ) {
                build();

                transitionTo( screenAfterLoad );
                buildFinished = true;
            }
        }

        float progress = Assets.getManager().getProgress();
        if ( DEBUG.BASE_SCREEN.get() && !buildFinished ) {
            Gdx.app.debug( "LoadingScreen", "progress=" + progress );
        }
        loadingBar.setScaleX( progress );
    }


    @Override
    public void dispose () {
        super.dispose();

        loadingBar.clear();
        loadingBar = null;
    }
}
