package net.overmy.likehunters.logic.cutscenes;

import com.badlogic.gdx.math.Vector3;

/*
        Created by Andrey Mikheev on 19.05.2018
        Contact me → http://vk.com/id17317
*/
public class Camera1Action {

    Vector3       targetPosition;
    float         cameraAngle;
    float         duration;
    CAM_ACTION_ID id;


    public Camera1Action ( CAM_ACTION_ID id, float duration ) {
        this.duration = duration;
        this.id = id;
    }


    public Camera1Action ( CAM_ACTION_ID id,float cameraAngle, float duration ) {
        this.cameraAngle = cameraAngle;
        this.duration = duration;
        this.id = id;
    }


    public Camera1Action ( CAM_ACTION_ID id, Vector3 position, float cameraAngle, float duration ) {
        this.targetPosition = position;
        this.cameraAngle = cameraAngle;
        this.duration = duration;
        this.id = id;
    }
}
