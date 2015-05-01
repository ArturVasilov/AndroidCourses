package ru.guar7387.andenginegame;

import android.os.Bundle;
import android.view.KeyEvent;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.ui.activity.BaseGameActivity;

import ru.guar7387.andenginegame.managers.ResourcesManager;
import ru.guar7387.andenginegame.managers.SceneManager;

public abstract class AbstractGameActivity extends BaseGameActivity {

    private static final String TAG = AbstractGameActivity.class.getSimpleName();

    private static final int CAMERA_WIDTH = 1280;
    private static final int CAMERA_HEIGHT = 720;

    private ResourcesManager mResourcesManager;
    private SceneManager mSceneManager;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        Logger.log(TAG, "OnCreate");
        super.onCreate(pSavedInstanceState);
        mResourcesManager = new ResourcesManager(this);
        mSceneManager = new SceneManager(this);
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        Logger.log(TAG, "onCreateEngineOptions");
        Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
                new FillResolutionPolicy(), camera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        return engineOptions;
    }

    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
        Logger.log(TAG, "onCreateEngine");
        return new LimitedFPSEngine(pEngineOptions, 60);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mSceneManager.onBackPressed();
            return true;
        }
        return false;
    }

    public ResourcesManager getResourcesManager() {
        return mResourcesManager;
    }

    public SceneManager getSceneManager() {
        return mSceneManager;
    }

    public int getCameraWidth() {
        return CAMERA_WIDTH;
    }

    public int getCameraHeight() {
        return CAMERA_HEIGHT;
    }

    public abstract void loadMenu();
}


