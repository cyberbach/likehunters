package net.overmy.likehunters.logic.cutscenes;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import net.overmy.likehunters.AshleyWorld;
import net.overmy.likehunters.MyCamera;
import net.overmy.likehunters.ashley.systems.MyPlayerSystem;
import net.overmy.likehunters.screen.GameScreen;
import net.overmy.likehunters.utils.FloatAnimator;
import net.overmy.likehunters.utils.Vector3Animator;

/*
        Created by Andrey Mikheev on 18.05.2018
        Contact me → http://vk.com/id17317
*/
public final class CutScene {
    private CutScene () {
    }


    private static float   timer           = 0.0f;
    private static boolean controlReturted = false;
    private static float   currentTime     = 0.0f;
    private static int     currentActionID = 0;
    private static Camera1Action currentAction;
    private static ImmutableArray< Camera1Action > cameraScript = null;


    public static void start ( SCENE_ID id, float durationTime ) {
        MyCamera.block();
        AshleyWorld.getEngine().getSystem( MyPlayerSystem.class ).block();

        gameScreen.hideGameGUI();

        switch ( id ) {
            case SOME_SCENE:
                cameraScript = CameraScriptBuilder.TEST_SCRIPT1();
                break;
        }

        timer = calcFullScriptTime();
    }


    private static float calcFullScriptTime () {
        float fullTime = 0.0f;
        for ( Camera1Action action : cameraScript ) {
            fullTime += action.duration;
        }

        currentTime = cameraScript.first().duration;
        currentAction = cameraScript.first();

        return fullTime;
    }


    static Vector3Animator moveAnimator=new Vector3Animator();
    static FloatAnimator rotateAnimator=new FloatAnimator(  );


    public static void update ( float delta ) {
        if ( timer > 0 ) {
            timer -= delta;
            controlReturted = false;

            // script work


            // skip to next 1 script
            currentTime -= delta;
            if ( currentTime <= 0 ) {
                currentActionID++;
                if ( currentActionID > cameraScript.size() ) {
                    currentActionID = cameraScript.size();
                    timer = 0;
                }
                currentAction = cameraScript.get( currentActionID );
                currentTime = currentAction.duration;

                Gdx.app.debug( ""+currentAction.id+" MyCamera.filteredPosition",""+MyCamera.filteredPosition+" time "+currentTime );

                if(currentAction.id.equals( CAM_ACTION_ID.MOVE ) ||
                   currentAction.id.equals( CAM_ACTION_ID.FLOAT_TARGET )) {
                    float originalCameraAngle = MyCamera.filteredCameraAngle;
                    //float fixedCameraAngle = originalCameraAngle<0 ? originalCameraAngle+180: originalCameraAngle;
                    float fixedCameraAngle = originalCameraAngle < 180 ? originalCameraAngle+360 : originalCameraAngle;
                    rotateAnimator.setFrom( fixedCameraAngle );

                    float originalScriptAngle = currentAction.cameraAngle;
                    //float fixedScriptAngle = originalScriptAngle-90;
                    float fixedScriptAngle = originalScriptAngle+180;
                    rotateAnimator.setTo( fixedScriptAngle );
                    rotateAnimator.setAnimationTime( currentTime ).resetTime();

                    Gdx.app.debug( "original cam "+originalCameraAngle + " fixed cam "+fixedCameraAngle,
                                   "original script "+originalScriptAngle+" fixed script "+fixedScriptAngle );
                }

                if ( currentAction.targetPosition != null ) {
                    moveAnimator.setFrom( MyCamera.filteredPosition );
                    moveAnimator.setTo( currentAction.targetPosition );
                    moveAnimator.setAnimationTime( currentTime ).resetTime();
                }
            }

            switch ( currentAction.id ) {
                case WAIT:
                    break;

                case BREAK:
                    MyCamera.filteredPosition.set( currentAction.targetPosition );
                    MyCamera.filteredCameraAngle = currentAction.cameraAngle-180;
                    currentTime = 0;
                    break;

                case FLOAT_TARGET:
                    rotateAnimator.update( delta );
                    MyCamera.filteredCameraAngle = rotateAnimator.getCurrent();
                    break;

                case MOVE:
                    rotateAnimator.update( delta );
                    moveAnimator.update( delta );
                    MyCamera.filteredCameraAngle = rotateAnimator.getCurrent();
                    MyCamera.filteredPosition.set( moveAnimator.getCurrent() );
                    break;
            }

        } else {
            if ( !controlReturted ) {
                MyCamera.unblock();
                AshleyWorld.getEngine().getSystem( MyPlayerSystem.class ).unblock();
                controlReturted = true;
                gameScreen.showGameGUI();
                cameraScript = null;
            }
        }
    }


    private static GameScreen gameScreen = null;


    public static void setGameScene ( GameScreen gameScreen2 ) {
        gameScreen = gameScreen2;
    }
}
