package ru.guar7387.andenginesample;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

public class GameActivity extends SimpleBaseGameActivity {

    private static final String TAG = GameActivity.class.getSimpleName();

    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    private GameResources mResources;

    @Override
    protected void onCreateResources() throws IOException {
        Logger.log(TAG, "onCreateResources");
        mResources = new GameResources(this);
        try {
            mResources.addTextureAndLoad("background.jpg");
            mResources.addTextureAndLoad("fish.png");
        } catch (IOException ignored) {
            Logger.log(TAG, "ioexception during loading backround assets");
        }
    }

    @Override
    protected Scene onCreateScene() {
        Logger.log(TAG, "onCreateScene");
        Scene scene = new Scene();
        ITexture background = mResources.getTexture("background.jpg");
        ITextureRegion region = TextureRegionFactory.extractFromTexture(background);
        Logger.log(TAG, "region: x - " + region.getTextureX() + "; y - " + region.getTextureY() +
                "; width - " + region.getWidth() + "; height - " + region.getHeight());
        Sprite backgroundSprite = new Sprite(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, region, getVertexBufferObjectManager());
        scene.attachChild(backgroundSprite);

        ITexture fish = mResources.getTexture("fish.png");
        ITextureRegion fishRegion = TextureRegionFactory.extractFromTexture(fish);
        Sprite fishSprite = new Sprite(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, fishRegion, getVertexBufferObjectManager());
        scene.attachChild(fishSprite);

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        Logger.log(TAG, "onCreateEngineOptions");

        Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        return engineOptions;
    }

}




