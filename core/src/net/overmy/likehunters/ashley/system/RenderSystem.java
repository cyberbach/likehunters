package net.overmy.likehunters.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.ashley.MyMapper;
import net.overmy.likehunters.ashley.component.ModelComponent;
import net.overmy.likehunters.ashley.component.OutOfCameraComponent;
import net.overmy.likehunters.screen.MyCamera;
import net.overmy.likehunters.screen.MyRender;

/*
        Created by Andrey Mikheev on 05.06.2018
        Contact me → http://vk.com/id17317
*/
public class RenderSystem extends IteratingSystem {

    private final float red   = Core.BG_COLOR.r;
    private final float green = Core.BG_COLOR.g;
    private final float blue  = Core.BG_COLOR.b;

    private final ModelBatch        modelBatch;
    private final Environment       environment;
    private final PerspectiveCamera perspectiveCamera;

    private int visibleModelsCount = 0;
    private int totalModelsCount   = 0;

    private Vector3 tempPosition = new Vector3();

    FrameBuffer frameBuffer = null;

    Mesh screenMesh;


    @SuppressWarnings( "unchecked" )
    public RenderSystem () {
        super( Family.all( ModelComponent.class ).get() );

        environment = MyRender.getEnvironment();
        modelBatch = MyRender.getModelBatch();
        perspectiveCamera = MyCamera.getPerspectiveCamera();

        // render to texture
        frameBuffer = new FrameBuffer( Pixmap.Format.RGBA8888, Core.WIDTH, Core.HEIGHT, true );

        screenMesh = MyRender.createFullScreenQuad();
    }


    public int getVisibleModelsCount () {
        return visibleModelsCount;
    }


    public int getTotalModelsCount () {
        return totalModelsCount;
    }


    @Override
    public void update ( float delta ) {
        visibleModelsCount = 0;
        totalModelsCount = 0;

        frameBuffer.begin();
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
/*
        Gdx.gl.glCullFace( GL20.GL_BACK );
        Gdx.gl.glEnable( GL20.GL_DEPTH_TEST );
        Gdx.gl.glDepthFunc( GL20.GL_LEQUAL );
        Gdx.gl.glDepthMask( true );*/

        modelBatch.begin( perspectiveCamera );
        super.update( delta );
        modelBatch.end();

        frameBuffer.end();

        Gdx.gl.glClearColor( red, green, blue, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        frameBuffer.getColorBufferTexture().bind();
        MyRender.getToonShaderProgram().begin();
        screenMesh.render(
                MyRender.getToonShaderProgram(), GL20.GL_TRIANGLES );
        MyRender.getToonShaderProgram().end();
    }


    @Override
    protected void processEntity ( Entity entity, float deltaTime ) {
        totalModelsCount++;
        // Это код для уровней, только у них есть границы
        if ( MyMapper.BOUNDS.has( entity ) ) {
            BoundingBox boundingBox = MyMapper.BOUNDS.get( entity ).boundingBox;
            if ( perspectiveCamera.frustum.boundsInFrustum( boundingBox ) ) {
                if ( MyMapper.OUT_OF_CAMERA.has( entity ) ) {
                    entity.remove( OutOfCameraComponent.class );
                }
                ModelInstance modelInstance = MyMapper.MODEL.get( entity ).modelInstance;
                modelBatch.render( modelInstance, environment );
                visibleModelsCount++;
            } else {
                entity.add( new OutOfCameraComponent() );
            }
        } else {
            // Код для всех остальных моделей (не уровней)
            ModelInstance modelInstance = MyMapper.MODEL.get( entity ).modelInstance;
            modelInstance.transform.getTranslation( tempPosition );
            if ( perspectiveCamera.frustum.sphereInFrustum( tempPosition, 1.5f ) ) {
                if ( MyMapper.OUT_OF_CAMERA.has( entity ) ) {
                    entity.remove( OutOfCameraComponent.class );
                }
                modelBatch.render( modelInstance, environment );
                visibleModelsCount++;
            } else {
                entity.add( new OutOfCameraComponent() );
            }
        }
    }
}
