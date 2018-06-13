package net.overmy.likehunters.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.GroupStrategy;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.overmy.likehunters.Core;
import net.overmy.likehunters.DEBUG;
import net.overmy.likehunters.utils.FloatAnimator;

/*
        Created by Andrey Mikheev on 04.06.2018
        Contact me → http://vk.com/id17317
*/
public final class MyRender {
    private static Stage stage = null;

    private static SpriteBatch spriteBatch = null;
    private static DecalBatch  decalBatch  = null;
    private static ModelBatch  modelBatch  = null;

    private static Environment environment = null;

    private static OrthographicCamera orthographicCamera = null;

    private static FloatAnimator transition            = null;
    private static Sprite        overlappingFullScreen = null; // этот спрайт перекрывает весь экран
    private static Color         transitionColor       = null;


    private MyRender () {
    }


    public static void init () {
        Core.WIDTH = Gdx.graphics.getWidth();
        Core.HEIGHT = Gdx.graphics.getHeight();
        Core.WIDTH_HALF = Core.WIDTH / 2;
        Core.HEIGHT_HALF = Core.HEIGHT / 2;

        transitionColor = new Color( Core.BG_COLOR );

        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho( false, Core.WIDTH, Core.HEIGHT );

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix( orthographicCamera.combined );

        Viewport viewport = new FitViewport( Core.WIDTH, Core.HEIGHT, orthographicCamera );

        stage = new Stage( viewport, spriteBatch );
        stage.setDebugAll( DEBUG.STAGE );


        MyCamera.init();

        final GroupStrategy groupStrategy = new CameraGroupStrategy(
                MyCamera.getPerspectiveCamera() );
        decalBatch = new DecalBatch( groupStrategy );


        overlappingFullScreen = createBGSprite();
        transition = new FloatAnimator( 1, 1, 0 );

        // 3d

        DefaultShader.Config config = new DefaultShader.Config();
        config.numDirectionalLights = 1;
        config.numPointLights = 0;
        config.numBones = 16;

        modelBatch = new ModelBatch( new DefaultShaderProvider( config ) );
        environment = new Environment();

        final Color ambientColor = new Color( 0.4f, 0.4f, 0.4f, 1.0f );
        final Attribute defaultEnvLight;
        defaultEnvLight = new ColorAttribute( ColorAttribute.AmbientLight, ambientColor );
        environment.set( defaultEnvLight );

        final Attribute fog = new ColorAttribute( ColorAttribute.Fog, 0, 0, 0, 1f );
        environment.set( fog );
        environment.add( MyCamera.getLight() );
    }


    public static OrthographicCamera getOrthographicCamera () {
        return orthographicCamera;
    }


    public static Stage getStage () {
        return stage;
    }


    public static SpriteBatch getSpriteBatch () {
        return spriteBatch;
    }


    public static DecalBatch getDecalBatch () {
        return decalBatch;
    }


    public static ModelBatch getModelBatch () {
        return modelBatch;
    }


    public static Environment getEnvironment () {
        return environment;
    }


    public static void TransitionIN ( float time ) {
        transition.setFrom( 0 ).setTo( 1 ).setAnimationTime( time ).resetTime();
    }


    public static void TransitionOUT ( float time ) {
        transition.setFrom( 1 ).setTo( 0 ).setAnimationTime( time ).resetTime();
    }


    public static boolean inTransition () {
        return transition.isNeedToUpdate();
    }


    public static void drawTransitionScreen ( float delta ) {
        if ( transition.isNeedToUpdate() ) {
            spriteBatch.begin();
            // red 1, green 1, blue 1 - обязательно, чтобы вывелся перекрывающий экран
            transitionColor.a = 1 - transition.getCurrent();
            spriteBatch.setColor( transitionColor );
            spriteBatch.draw( overlappingFullScreen, 0, 0, Core.WIDTH, Core.HEIGHT );
            spriteBatch.end();
        }
        // Этот апдейт должен быть здесь, чтобы избежать ситуации
        // когда в момент перехода экранов на секунду появляется предыдущая сцена
        transition.update( delta );
    }


    public static Sprite createBGSprite () {
        Pixmap pixmap = new Pixmap( Core.WIDTH, Core.HEIGHT, Pixmap.Format.RGB888 );
        pixmap.setColor( Core.BG_COLOR );
        pixmap.fill();

        Texture texture = new Texture( pixmap );
        pixmap.dispose();

        return new Sprite( texture );
    }


    public static void dispose () {
        orthographicCamera = null;

        spriteBatch.dispose();
        spriteBatch = null;

        decalBatch.dispose();
        decalBatch = null;

        overlappingFullScreen = null;

        stage.dispose();
        stage = null;

        transition = null;

        modelBatch.dispose();
        modelBatch = null;

        environment = null;
        transitionColor = null;
    }
}
