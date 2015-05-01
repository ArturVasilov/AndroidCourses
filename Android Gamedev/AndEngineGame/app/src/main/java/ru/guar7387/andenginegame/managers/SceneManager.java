package ru.guar7387.andenginegame.managers;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.guar7387.andenginegame.AbstractGameActivity;
import ru.guar7387.andenginegame.Logger;
import ru.guar7387.andenginegame.scenes.AbstractScene;
import ru.guar7387.andenginegame.scenes.GameScene;
import ru.guar7387.andenginegame.scenes.GameMenuScene;
import ru.guar7387.andenginegame.scenes.SceneType;
import ru.guar7387.andenginegame.scenes.SplashScene;

public class SceneManager {

    private static final String TAG = SceneManager.class.getSimpleName();

    private final WeakReference<AbstractGameActivity> mActivityReference;
    private final Map<SceneType, AbstractScene> mScenes;

    private AbstractScene mCurrentScene;

    public SceneManager(AbstractGameActivity activity) {
        this.mActivityReference = new WeakReference<>(activity);
        this.mScenes = new ConcurrentHashMap<>();
    }

    public void uploadScene(SceneType type) {
        Logger.log(TAG, "uploading scene, type - " + type);
        mCurrentScene = mScenes.get(type);
        AbstractGameActivity activity = mActivityReference.get();
        if (activity == null) {
            return;
        }
        if (mCurrentScene == null) {
            switch (type) {
                case SPLASH_SCENE:
                    mCurrentScene = new SplashScene(activity);
                    break;

                case MENU_SCENE:
                    mCurrentScene = new GameMenuScene(activity);
                    break;

                case GAME_SCENE:
                    mCurrentScene = new GameScene(activity);
                    break;
            }
        }
        mScenes.put(type, mCurrentScene);
    }

    public AbstractScene getScene(SceneType type) {
        return mScenes.get(type);
    }

    public void onBackPressed() {
        if (mCurrentScene != null) {
            mCurrentScene.onBackKeyPressed();
        }
    }
}
