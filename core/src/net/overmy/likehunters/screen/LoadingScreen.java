package net.overmy.likehunters.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.MyGdxGame;
import net.overmy.likehunters.MyGdxGame.SCREEN_TYPE;
import net.overmy.likehunters.MyPlayer;
import net.overmy.likehunters.MyRender;
import net.overmy.likehunters.logic.DynamicLevels;
import net.overmy.likehunters.resources.Assets;
import net.overmy.likehunters.utils.GFXHelper;

/*
     Created by Andrey Mikheev on 29.09.2017
     Contact me → http://vk.com/id17317
 */

public class LoadingScreen extends Base2DScreen {

    private Image                 loadingBar      = null;
    private boolean               buildFinished   = false;
    private SCREEN_TYPE screenAfterLoad = SCREEN_TYPE.MENU;


    public LoadingScreen ( final MyGdxGame game, SCREEN_TYPE screenAfterLoad ) {
        super( game );

        this.screenAfterLoad = screenAfterLoad;

        switch ( screenAfterLoad ) {
            case MENU:
                Assets.load();
                break;

            case GAME:
                DynamicLevels.reload();
                break;
        }
    }


    @Override
    public void show () {
        super.show();

        int w = Core.WIDTH;
        int h = Core.HEIGHT / 14;

        loadingBar = new Image( GFXHelper.createSpriteRGB888( w, h ) );
        MyRender.getStage().addActor( loadingBar );
    }


    @Override
    public void update ( float delta ) {
        super.update( delta );

        if ( Assets.getManager().update() ) {
            if ( !buildFinished ) {
                Assets.build();

                // FIXME

                //Assets.getManager().finishLoading();

                transitionTo( screenAfterLoad );
                buildFinished = true;
            }
        }

        float progress = Assets.getManager().getProgress();
        loadingBar.setScaleX( progress );
    }


    @Override
    public void dispose () {
        super.dispose();

        loadingBar.clear();
        loadingBar = null;
    }
}
