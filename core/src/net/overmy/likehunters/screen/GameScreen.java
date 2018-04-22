package net.overmy.likehunters.screen;

/*
      Created by Andrey Mikheev on 10.10.2017
      Contact me → http://vk.com/id17317
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

import net.overmy.likehunters.AshleyWorld;
import net.overmy.likehunters.BulletWorld;
import net.overmy.likehunters.Core;
import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.MyCamera;
import net.overmy.likehunters.MyGdxGame;
import net.overmy.likehunters.ashley.EntityBuilder;
import net.overmy.likehunters.ashley.systems.MyPlayerSystem;
import net.overmy.likehunters.logic.GameHelper;
import net.overmy.likehunters.MyRender;
import net.overmy.likehunters.ashley.systems.DecalSystem;
import net.overmy.likehunters.ashley.systems.InteractSystem;
import net.overmy.likehunters.ashley.systems.RenderSystem;
import net.overmy.likehunters.ashley.systems.TextDecalSystem;
import net.overmy.likehunters.logic.DynamicLevels;
import net.overmy.likehunters.resources.FontAsset;
import net.overmy.likehunters.resources.IMG;
import net.overmy.likehunters.resources.ModelAsset;
import net.overmy.likehunters.utils.UIHelper;

import java.util.ArrayList;

public class GameScreen extends Base2DScreen {

    private Image showIngameMenuImage = null;
    private Image aimImage            = null;
    private Image attackButton        = null;
    private Image jumpButton          = null;

    private Touchpad touchpad = null;

    private Group interactGroup = null;
    private Group touchPadGroup = null;
    private Group gameGroup     = null;

    private GUI_TYPE guiType;

    private TextDecalSystem textDecalSystem = null;
    private InteractSystem  interactSystem  = null;
    private MyPlayerSystem  playerSystem    = null;

    private static ArrayList< Vector3 > pushedPositions = new ArrayList< Vector3 >();

    private StringBuilder log = new StringBuilder();

    private long startTime;
    private boolean readyToPick  = false;
    private boolean showGameOver = false;


    public GameScreen ( MyGdxGame game ) {
        super( game );
    }


    @Override
    public void show () {
        super.show();

        textDecalSystem = AshleyWorld.getEngine().getSystem( TextDecalSystem.class );
        textDecalSystem.init();

        playerSystem = AshleyWorld.getEngine().getSystem( MyPlayerSystem.class );

        //AshleyWorld.getEngine().getSystem( NPCSystem.class ).setWalkSound();

        interactSystem = AshleyWorld.getEngine().getSystem( InteractSystem.class );

        MyCamera.setCameraPosition( new Vector3( 0, 300, 0 ) );

        touchPadGroup = new Group();
        MyRender.getStage().addActor( UIHelper.initLoadIndicator() );
        MyRender.getStage().addActor( gameGroup = new Group() );

        if ( DEBUG.GAME_MASTER_MODE.get() ) {
            /*String helpString = "ENTER - push position\n1- show bonus pos\n" +
                                "2-show box pos\n3-show NPC move pos\n" +
                                "\nBackSpace-clear positions\n\n" +
                                "9 speed up\n" +
                                "0 speed normal";*/
            String helpString = "GM";
            Label gameMasterTitle = UIHelper.Label( helpString, FontAsset.MENU_TITLE );
            gameMasterTitle.setPosition( 16, Core.HEIGHT_HALF );

            MyRender.getStage().addActor( gameMasterTitle );
        }

        if ( DEBUG.GAME_MASTER_MODE.get() ) {
            /*for ( int i = 0; i < 20; i++ ) {
                MyPlayer.addToBag( Item.COIN );
                MyPlayer.addToBag( Item.YELLOW_STAR );
                MyPlayer.addToBag( Item.BLUE_STAR );
                MyPlayer.addToBag( Item.GREEN_STAR );
            }

            MyPlayer.addToBag( Item.KEY1 );
            MyPlayer.addToBag( Item.KEY2 );
            MyPlayer.addToBag( Item.KEY3 );
            MyPlayer.addToBag( Item.KEY4 );
            MyPlayer.addToBag( Item.KEY5 );
            MyPlayer.addToBag( Item.KEY6 );
            MyPlayer.addToBag( Item.GUN_WEAPON_UPGRADED );*/
        }

        GameHelper helper = new GameHelper();
        EntityBuilder.createPlayer( ModelAsset.TEST_NPC.get(),
                                    helper.startPositions[ DynamicLevels.getCurrent() ] );

        showGameGUI ();
    }


    @Override
    public boolean touchDragged ( float x, float y ) {
        if ( guiType == GUI_TYPE.INGAME_MENU ) {
            return false;
        }

        MyCamera.addCameraAngle( x * Core.SensitivitySpeedByX );
        MyCamera.addVerticalDirection( -y * Core.SensitivitySpeedByY );
        return true;
    }


    @Override
    public void draw ( float delta ) {

        AshleyWorld.update( delta );

        if ( DEBUG.PHYSICAL_MESH.get() ) {
            BulletWorld.drawDebug();
        }

        MyRender.getDecalBatch().flush();
    }


    @Override
    public void update ( float delta ) {
        super.update( delta );

        //MusicAsset.playRandom( delta );

        if ( DEBUG.GAME_MASTER_MODE.get() ) {
            if ( Gdx.input.isKeyJustPressed( Input.Keys.ENTER ) ) {
                Vector3 thisPosition = playerSystem.getNotFilteredPos();
                pushedPositions.add( thisPosition );


                String pos = "new Vector3( " + thisPosition.x + "f, " +
                             thisPosition.y + "f, " + thisPosition.z + "f )";
                Gdx.app.debug( "Pushed angle = " +
                               playerSystem.getAngle(), "\n" + pos );
                Gdx.app.debug( "THIS LOCATION", "" + DynamicLevels.getCurrent() );
            }

            if ( Gdx.input.isKeyJustPressed( Input.Keys.BACKSPACE ) ) {
                pushedPositions.clear();
                Gdx.app.debug( "♦ Positions", "cleared ♦" );
            }

            if ( Gdx.input.isKeyPressed( Input.Keys.W ) ) {
                playerSystem.move( 0, -1 );
            }

            if ( Gdx.input.isKeyPressed( Input.Keys.S ) ) {
                playerSystem.move( 0, 1 );
            }

            if ( Gdx.input.isKeyPressed( Input.Keys.A ) ) {
                playerSystem.move( -1, 0 );
            }

            if ( Gdx.input.isKeyPressed( Input.Keys.D ) ) {
                playerSystem.move( 1, 0 );
            }

            if ( Gdx.input.isKeyJustPressed( Input.Keys.SPACE ) ) {
                playerSystem.startJump();
            }

            // GameMaster Mode
            // add hover coin
            if ( Gdx.input.isKeyJustPressed( Input.Keys.NUM_1 ) ) {
                StringBuilder stringBuilder = new StringBuilder();

                for ( Vector3 pushed : pushedPositions ) {
                    stringBuilder.append( "objects.add( hoverCoin( " );
                    stringBuilder.append( pushed.x );
                    stringBuilder.append( "f, " );
                    stringBuilder.append( pushed.y );
                    stringBuilder.append( "f, " );
                    stringBuilder.append( pushed.z );
                    stringBuilder.append( "f) );\n" );
                }

                Gdx.app.debug( "Pushed positions", "\n" + stringBuilder.toString() );
            }
            // add box
            if ( Gdx.input.isKeyJustPressed( Input.Keys.NUM_2 ) ) {
                StringBuilder stringBuilder = new StringBuilder();

                for ( Vector3 pushed : pushedPositions ) {
                    stringBuilder.append( "objects.add( box( " );
                    stringBuilder.append( pushed.x );
                    stringBuilder.append( "f, " );
                    stringBuilder.append( pushed.y );
                    stringBuilder.append( "f, " );
                    stringBuilder.append( pushed.z );
                    stringBuilder.append( "f) );\n" );
                }

                Gdx.app.debug( "Pushed positions", "\n" + stringBuilder.toString() );
            }
            // add move point
            if ( Gdx.input.isKeyJustPressed( Input.Keys.NUM_3 ) ) {
                StringBuilder stringBuilder = new StringBuilder();

                for ( Vector3 pushed : pushedPositions ) {
                    //queue.add( new NPCAction( ACTION_ID.MOVE, new Vector2( 15.5f, -3.166f ), 10.0f ) );
                    stringBuilder.append(
                            "queue.add( move(" );
                    stringBuilder.append( pushed.x );
                    stringBuilder.append( "f, " );
                    //stringBuilder.append( pushed.y );
                    //stringBuilder.append( "f, " );
                    stringBuilder.append( pushed.z );
                    stringBuilder.append( "f) );\n" );
                }

                Gdx.app.debug( "Pushed positions", "\n" + stringBuilder.toString() );
            }
        }

        DynamicLevels.update( delta );
        MyCamera.update( delta );

        ///textDecalSystem.setLastPlayerPosition( MyPlayer.getPosition() );

        if ( guiType == GUI_TYPE.GAME_GUI ) {
            playerSystem.move( touchpad.getKnobPercentX(), -touchpad.getKnobPercentY() );
        } else {
            //if ( !DEBUG.GAME_MASTER_MODE.get() ) {
                playerSystem.move( 0, 0 );
            //}
        }

        if ( DEBUG.FPS.get() ) {
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

                DecalSystem decalSystem = AshleyWorld.getEngine().getSystem( DecalSystem.class );
                log.append( " Decals=" );
                log.append( decalSystem.getVisibleDecalCount() );
                log.append( "/" );
                log.append( decalSystem.getDecalCount() );

                log.append( " ░▒▓" );

                Gdx.app.log( "", log.toString() );
                startTime = TimeUtils.nanoTime();
                if ( DEBUG.SCREEN_FPS.get() ) {
                    //fpsLabel.setText( log.toString() );
                }
            }
        }
    }


    @Override
    public void backButton () {
        //SoundAsset.BackSound.play();
        if ( guiType == GUI_TYPE.INGAME_MENU ) {
            //showGameGUI();
        } else {
            touchPadGroup.clearActions();
            jumpButton.clearActions();
            UIHelper.scaleOut( touchPadGroup );
            UIHelper.scaleOut( jumpButton );
            transitionTo( MyGdxGame.SCREEN_TYPE.MENU );
        }
    }


    @Override
    public void dispose () {
        super.dispose();

        textDecalSystem = null;
        //AshleyWorld.getEngine().getSystem( NPCSystem.class ).disableWalkSound();

        //MyPlayer.stopSound();
        //MusicAsset.stopEnvironment();

        jumpButton = null;
        touchpad = null;
        touchPadGroup = null;
        gameGroup = null;

        interactSystem = null;
    }

    private void showGameGUI () {
        guiType = GUI_TYPE.GAME_GUI;

        gameGroup.clear();
        touchPadGroup.clear();

        if ( DEBUG.SCREEN_FPS.get() ) {
            /*if ( fpsLabel == null ) {
                fpsLabel = UIHelper.Label( "", FontAsset.IVENTORY_ITEM );
                fpsLabel.setPosition( 0, Core.HEIGHT * 0.9f );
            }
            gameGroup.addActor( fpsLabel );*/
        }

        if ( touchpad == null ) {
            touchpad = UIHelper.createTouchPad();
            touchpad.setPosition( Core.HEIGHT * 0.05f, Core.HEIGHT * 0.05f );
            touchPadGroup.addActor( touchpad );
            touchPadGroup.setOrigin( Core.HEIGHT * 0.05f + Core.HEIGHT * 0.3f / 2,
                                     Core.HEIGHT * 0.05f + Core.HEIGHT * 0.3f / 2 );
        } else {
            touchpad.setPosition( Core.HEIGHT * 0.05f, Core.HEIGHT * 0.05f );
            touchPadGroup.addActor( touchpad );
            touchPadGroup.setOrigin( Core.HEIGHT * 0.05f + Core.HEIGHT * 0.3f / 2,
                                     Core.HEIGHT * 0.05f + Core.HEIGHT * 0.3f / 2 );
        }
        UIHelper.scaleIn( touchPadGroup );
        gameGroup.addActor( touchPadGroup );

        if ( jumpButton == null ) {
            jumpButton = new Image( IMG.JUMP_BUTTON.createSprite() );
            jumpButton.setSize( Core.HEIGHT * 0.24f, Core.HEIGHT * 0.24f );
            jumpButton.setPosition( Core.WIDTH - jumpButton.getWidth() * 1.3f,
                                    jumpButton.getHeight() * 0.9f );
            jumpButton.setOrigin( jumpButton.getWidth() / 2,
                                  jumpButton.getHeight() / 2 );

            jumpButton.addListener( new ClickListener() {
                @Override
                public void clicked ( InputEvent event, float x, float y ) {
                    if ( showGameOver ) {
                        return;
                    }
                    playerSystem.startJump();
                }
            } );
        }
        UIHelper.scaleIn( jumpButton );
        gameGroup.addActor( jumpButton );

        if ( attackButton == null ) {
            attackButton = new Image( IMG.HIT_BUTTON.createSprite() );
            attackButton.setSize( Core.HEIGHT * 0.24f, Core.HEIGHT * 0.24f );
            attackButton.setPosition( Core.WIDTH - attackButton.getWidth() * 2.5f,
                                      attackButton.getHeight() * 0.4f );
            attackButton.setOrigin( attackButton.getWidth() / 2,
                                    attackButton.getHeight() / 2 );

            attackButton.addListener( new ClickListener() {
                @Override
                public void clicked ( InputEvent event, float x, float y ) {
                    if ( showGameOver ) {
                        return;
                    }
                    playerSystem.startAttack();
                }
            } );
        }
        UIHelper.scaleIn( attackButton );
        gameGroup.addActor( attackButton );

        if ( aimImage == null ) {
            float aimSize = Core.HEIGHT * 0.1f;
            aimImage = IMG.AIM.getImageActor( aimSize, aimSize );
            aimImage.setPosition( Core.WIDTH_HALF - aimSize / 2, Core.HEIGHT_HALF - aimSize / 2 );
        }
        gameGroup.addActor( aimImage );

        if ( interactGroup != null ) {
            interactGroup.clear();
        } else {
            interactGroup = new Group();
        }
        interactGroup.setPosition( Core.WIDTH_HALF, Core.HEIGHT * 0.3f );

        gameGroup.addActor( interactGroup );

        float inGameIconSize = Core.HEIGHT * 0.16f;

        if ( showIngameMenuImage == null ) {
            showIngameMenuImage = IMG.INGAME.getImageActor( inGameIconSize,
                                                            inGameIconSize );
            showIngameMenuImage.setPosition( Core.WIDTH - inGameIconSize,
                                             Core.HEIGHT - inGameIconSize );
            showIngameMenuImage.addListener( new ClickListener() {
                public void clicked ( InputEvent event, float x, float y ) {
                    if ( showGameOver ) {
                        return;
                    }
                    //SoundAsset.Click.play();
                    UIHelper.clickAnimation( showIngameMenuImage );
                    //showInGameMenu();
                }
            } );
        }
        gameGroup.addActor( showIngameMenuImage );
    }


    private enum GUI_TYPE {
        GAME_GUI,
        INGAME_MENU
    }
}
