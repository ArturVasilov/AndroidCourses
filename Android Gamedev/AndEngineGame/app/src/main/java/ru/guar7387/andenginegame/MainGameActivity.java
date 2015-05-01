package ru.guar7387.andenginegame;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

import java.io.IOException;

import ru.guar7387.andenginegame.managers.ResourcesManager;
import ru.guar7387.andenginegame.managers.SceneManager;
import ru.guar7387.andenginegame.scenes.AbstractScene;
import ru.guar7387.andenginegame.scenes.SceneType;

public class MainGameActivity extends AbstractGameActivity {

    private static final String TAG = MainGameActivity.class.getSimpleName();

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        Logger.log(TAG, "onCreateResources");
        ResourcesManager resourcesManager = getResourcesManager();

        String[] images = {"grass.jpg", "menu_background.jpg",
                "level1.png", "level2.png", "level3.png",
                "hero.png", "player.png", "bomb.png"};
        for (String path : images) {
            try {
                resourcesManager.uploadTexture("gfx/" + path);
            } catch (IOException ignored) {
                Logger.log(TAG, "ioexception during loading texture");
            }
        }

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        Logger.log(TAG, "onCreateScene");
        SceneManager sceneManager = getSceneManager();
        sceneManager.uploadScene(SceneType.SPLASH_SCENE);
        AbstractScene scene = sceneManager.getScene(SceneType.SPLASH_SCENE);
        scene.populateScene();
        mEngine.setScene(scene);
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        Logger.log(TAG, "onPopulateScene");
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
            public void onTimePassed(TimerHandler pTimerHandler) {
                Logger.log(TAG, "handler time passed");
                mEngine.unregisterUpdateHandler(pTimerHandler);
                loadMenu();
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public void loadMenu() {
        SceneManager sceneManager = getSceneManager();
        sceneManager.uploadScene(SceneType.MENU_SCENE);
        AbstractScene scene = sceneManager.getScene(SceneType.MENU_SCENE);
        Logger.log(TAG, "before drawing menu scene");
        scene.populateScene();
        Logger.log(TAG, "menu scene was drawn");
        mEngine.setScene(scene);
    }
}


