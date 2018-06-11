package net.overmy.likehunters.logic;

/*
     Created by Andrey Mikheev on 20.04.2017
     Contact me → http://vk.com/id17317
 */

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.overmy.likehunters.resource.IMG;
import net.overmy.likehunters.resource.ModelAsset;
import net.overmy.likehunters.resource.TextAsset;


public enum Item {
    //TRIGGER01
    ;

    private TextAsset  name       = null;
    private TextAsset  about      = null;
    private IMG        imageID    = null;
    private ModelAsset modelAsset = null;
    private boolean    weapon     = false;


    public static void init () {

    }


    public boolean isWeapon () {
        return weapon;
    }


    private void setModel ( ModelAsset modelAsset ) {
        this.modelAsset = modelAsset;
    }


    private Item itIsWeapon () {
        this.weapon = true;
        return this;
    }


    private Item setIMG ( IMG imageID ) {
        this.imageID = imageID;
        return this;
    }


    public ModelAsset getModelAsset () {
        return modelAsset;
    }


    public String getName () {
        return name.get();
    }


    private Item setName ( TextAsset name ) {
        this.name = name;
        return this;
    }


    public String getAbout () {
        return about.get();
    }


    private Item setAbout ( TextAsset about ) {
        this.about = about;
        return this;
    }


    public Image getImage ( final float width, final float height ) {
        return imageID.getImageActor( width, height );
    }
}
