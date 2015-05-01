package ru.guar7387.andenginegame.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;

import java.io.IOException;

import ru.guar7387.andenginegame.AbstractGameActivity;
import ru.guar7387.andenginegame.Logger;
import ru.guar7387.andenginegame.managers.ResourcesManager;

public class SplashScene extends AbstractScene {

    private static final String TAG = SplashScene.class.getSimpleName();

    private Sprite mSplash;

    public SplashScene(AbstractGameActivity activity) {
        super(activity);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SPLASH_SCENE;
    }

    @Override
    public void populateScene() {
        AbstractGameActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        ResourcesManager resourcesManager = activity.getResourcesManager();
        try {
            resourcesManager.uploadTexture("gfx/splash.jpg");
        } catch (IOException e) {
            return;
        }
        TextureRegion splashRegion = TextureRegionFactory.extractFromTexture(
                resourcesManager.getTexture("gfx/splash.jpg"));
        mSplash = new Sprite(0, 0, splashRegion, resourcesManager.getVertexBufferObjectManager()) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        mSplash.setPosition(activity.getCameraWidth() / 2, activity.getCameraHeight() / 2);
        attachChild(mSplash);
    }

    @Override
    public void onBackKeyPressed() {
        getActivity().finish();
    }

    @Override
    public void destroyScene() {
        Logger.log(TAG, "destroying splash scene");
        mSplash.detachSelf();
        mSplash.dispose();
        this.detachSelf();
        this.dispose();
    }
}
