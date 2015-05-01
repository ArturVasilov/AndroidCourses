package ru.guar7387.andenginesample;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameResources {

    private final WeakReference<BaseGameActivity> mActivityReference;

    private final Map<String, ITexture> resources;

    public GameResources(BaseGameActivity activity) {
        this.mActivityReference = new WeakReference<>(activity);
        resources = new ConcurrentHashMap<>();
    }

    public void addTextureAndLoad(final String assetPath) throws IOException {
        if (resources.containsKey(assetPath)) {
            return;
        }
        final BaseGameActivity gameActivity = mActivityReference.get();
        if (gameActivity == null) {
            return;
        }
        ITexture texture = new BitmapTexture(gameActivity.getTextureManager(), new IInputStreamOpener() {
            @Override
            public InputStream open() throws IOException {
                return gameActivity.getAssets().open(assetPath);
            }
        });
        resources.put(assetPath, texture);
        texture.load();
    }

    public ITexture getTexture(String assetPath) {
        return resources.get(assetPath);
    }

}

