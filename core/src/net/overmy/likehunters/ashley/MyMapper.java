package net.overmy.likehunters.ashley;

import com.badlogic.ashley.core.ComponentMapper;

import net.overmy.likehunters.ashley.component.AnimationComponent;
import net.overmy.likehunters.ashley.component.BoundingBoxComponent;
import net.overmy.likehunters.ashley.component.GroundedComponent;
import net.overmy.likehunters.ashley.component.LevelIDComponent;
import net.overmy.likehunters.ashley.component.ModelComponent;
import net.overmy.likehunters.ashley.component.MyPlayerComponent;
import net.overmy.likehunters.ashley.component.OutOfCameraComponent;
import net.overmy.likehunters.ashley.component.PhysicalBVHComponent;
import net.overmy.likehunters.ashley.component.PhysicalComponent;
import net.overmy.likehunters.ashley.component.RemoveByTimeComponent;
import net.overmy.likehunters.ashley.component.TypeOfEntityComponent;

/*
        Created by Andrey Mikheev on 05.06.2018
        Contact me → http://vk.com/id17317
*/
public final class MyMapper {
    public static ComponentMapper< RemoveByTimeComponent > REMOVE_BY_TIME = null;
    public static ComponentMapper< BoundingBoxComponent >  BOUNDS         = null;
    public static ComponentMapper< ModelComponent >        MODEL          = null;
    public static ComponentMapper< OutOfCameraComponent >  OUT_OF_CAMERA  = null;
    public static ComponentMapper< PhysicalComponent >     PHYSICAL       = null;
    public static ComponentMapper< PhysicalBVHComponent >  BVH_PHYSICAL   = null;
    public static ComponentMapper< TypeOfEntityComponent > TYPE           = null;
    public static ComponentMapper< AnimationComponent >    ANIMATION      = null;
    public static ComponentMapper< MyPlayerComponent >     MY_PLAYER      = null;
    public static ComponentMapper< GroundedComponent >     GROUNDED       = null;
    public static ComponentMapper< LevelIDComponent >      LEVEL_ID       = null;


    private MyMapper () {
    }


    public static void init () {
        REMOVE_BY_TIME = ComponentMapper.getFor( RemoveByTimeComponent.class );
        BOUNDS = ComponentMapper.getFor( BoundingBoxComponent.class );
        MODEL = ComponentMapper.getFor( ModelComponent.class );
        OUT_OF_CAMERA = ComponentMapper.getFor( OutOfCameraComponent.class );
        PHYSICAL = ComponentMapper.getFor( PhysicalComponent.class );
        BVH_PHYSICAL = ComponentMapper.getFor( PhysicalBVHComponent.class );
        TYPE = ComponentMapper.getFor( TypeOfEntityComponent.class );
        ANIMATION = ComponentMapper.getFor( AnimationComponent.class );
        MY_PLAYER = ComponentMapper.getFor( MyPlayerComponent.class );
        GROUNDED = ComponentMapper.getFor( GroundedComponent.class );
        LEVEL_ID = ComponentMapper.getFor( LevelIDComponent.class );
    }


    public static void dispose () {
        REMOVE_BY_TIME = null;
        BOUNDS = null;
        MODEL = null;
        OUT_OF_CAMERA = null;
        PHYSICAL = null;
        BVH_PHYSICAL = null;
        TYPE = null;
        ANIMATION = null;
        MY_PLAYER = null;
        GROUNDED = null;
        LEVEL_ID = null;
    }
}