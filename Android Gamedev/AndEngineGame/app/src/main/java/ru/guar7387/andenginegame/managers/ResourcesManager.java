package ru.guar7387.andenginegame.managers;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.guar7387.andenginegame.AbstractGameActivity;

public class ResourcesManager {

    private final WeakReference<AbstractGameActivity> mActivityReference;
    private final Map<String, ITexture> mTextures;
    private final VertexBufferObjectManager mVertexBufferObjectManager;

    public ResourcesManager(AbstractGameActivity activity) {
        this.mActivityReference = new WeakReference<>(activity);
        mTextures = new ConcurrentHashMap<>();
        mVertexBufferObjectManager = new VertexBufferObjectManager();
    }

    public void uploadTexture(final String assetPath) throws IOException {
        if (mTextures.containsKey(assetPath)) {
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
        texture.load();
        mTextures.put(assetPath, texture);
    }

    public ITexture getTexture(String assetPath) {
        return mTextures.get(assetPath);
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return mVertexBufferObjectManager;
    }
}


