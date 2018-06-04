package net.overmy.likehunters.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.overmy.likehunters.MyGdxGame;
import net.overmy.likehunters.MyGdxGame.SCREEN_TYPE;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public class MenuScreen extends BaseScreen {
    public MenuScreen ( MyGdxGame game ) {
        super( game );
    }


    @Override
    public void show () {
        super.show();

        stage.clear();
    }


    @Override
    public void backButton () {
        transitionTo( SCREEN_TYPE.EXIT );
    }
}
