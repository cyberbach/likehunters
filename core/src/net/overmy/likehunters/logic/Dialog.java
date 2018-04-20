package net.overmy.likehunters.logic;

import com.badlogic.gdx.utils.Array;

import net.overmy.likehunters.resources.DialogAsset;


/**
 * Created by Andrey (cb) Mikheev
 * 14.06.2017
 */

public enum Dialog {

    // shop variants

    BUY_Broom_15coins,;

    private Array< Dialog > connection = new Array< Dialog >();

    private DialogAsset action = null;
    private DialogAsset title  = null;
    private DialogAsset body   = null;


    public static void init () {

    }


    public void setText ( DialogAsset titleOfTextBlock,
                          DialogAsset bodyOfTextBlock,
                          DialogAsset actionText ) {
        this.title = titleOfTextBlock;
        this.body = bodyOfTextBlock;
        this.action = actionText;
    }


    public void connect ( Dialog... nextDialog ) {
        this.connection.clear();
        this.connection.addAll( nextDialog );
    }

/*
    public Dialog addConnect ( Dialog... nextMyDialog ) {
        this.connection.addAll( nextMyDialog );
        return this;
    }
*/


    public Dialog actionToGoHere ( DialogAsset action ) {
        this.action = action;
        return this;
    }


    public Dialog actionToGoHereEE ( DialogAsset action ) {
        this.setTitle( DialogAsset.Empty );
        this.setBody( DialogAsset.Empty );
        this.action = action;
        return this;
    }


    public Dialog setTitle ( DialogAsset title ) {
        this.title = title;
        return this;
    }


    public Dialog setBody ( DialogAsset body ) {
        this.body = body;
        return this;
    }


    public Array< Dialog > getConnections () {
        return connection;
    }


    public String getTitle () {
        return title.get();
    }


    public boolean haveNotBody () {
        return "".equals( body.get() );
    }


    public String getBody () {
        return body.get();
    }


    public String getAction () {
        return action.get();
    }
}
