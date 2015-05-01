package ru.guar7387.andenginegame.scenes;

import org.andengine.entity.scene.Scene;

import java.lang.ref.WeakReference;

import ru.guar7387.andenginegame.AbstractGameActivity;

public abstract class AbstractScene extends Scene {

    private final WeakReference<AbstractGameActivity> mActivityReference;

    public AbstractScene(AbstractGameActivity activity) {
        this.mActivityReference = new WeakReference<>(activity);
    }

    public AbstractGameActivity getActivity() {
        return mActivityReference.get();
    }

    public abstract SceneType getSceneType();

    public abstract void populateScene();

    public abstract void destroyScene();

    public abstract void onBackKeyPressed();
}



