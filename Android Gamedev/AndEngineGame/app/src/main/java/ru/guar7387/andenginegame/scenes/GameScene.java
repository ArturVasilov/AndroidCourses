package ru.guar7387.andenginegame.scenes;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.guar7387.andenginegame.AbstractGameActivity;
import ru.guar7387.andenginegame.managers.ResourcesManager;

public class GameScene extends AbstractScene {

    private Sprite mPlayerSprite;
    private final List<Sprite> mBombsSprite;

    public GameScene(AbstractGameActivity activity) {
        super(activity);
        mBombsSprite = new ArrayList<>();
    }

    public void setLevel(int level) {
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME_SCENE;
    }

    @Override
    public void populateScene() {
        AbstractGameActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        ResourcesManager manager = activity.getResourcesManager();

        TextureRegion backgroundRegion = TextureRegionFactory.extractFromTexture(manager.getTexture("gfx/grass.jpg"));
        ParallaxBackground background = new AutoParallaxBackground(0, 0, 0, 5);
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0, new Sprite(
                0, activity.getCameraHeight() / 2,
                backgroundRegion, manager.getVertexBufferObjectManager())));
        setBackground(background);
        setBackgroundEnabled(true);

       /* BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(
                activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        TiledTextureRegion mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                mBitmapTextureAtlas, activity.getApplicationContext(), "gfx/hero.png", 0, 0, 4, 2);
        AnimatedSprite player = new AnimatedSprite(100, 100, mPlayerTextureRegion, manager.getVertexBufferObjectManager());
        player.animate(new long[]{100, 100, 100}, 5, 7, true);*/

        TextureRegion playerRegion = TextureRegionFactory.extractFromTexture(manager.getTexture("gfx/player.png"));
        mPlayerSprite = new Sprite(activity.getCameraWidth() / 2, activity.getCameraHeight() / 2,
                playerRegion, manager.getVertexBufferObjectManager());
        mPlayerSprite.setScale(3);
        attachChild(mPlayerSprite);

        Random random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            TextureRegion bombRegion = TextureRegionFactory.extractFromTexture(manager.getTexture("gfx/bomb.png"));
            int width = random.nextInt(activity.getCameraWidth());
            int height = random.nextInt(activity.getCameraHeight());
            //noinspection ObjectAllocationInLoop
            Sprite sprite = new Sprite(width, height, bombRegion, manager.getVertexBufferObjectManager());
            mBombsSprite.add(sprite);
            attachChild(sprite);
        }
    }

    @Override
    public void destroyScene() {
        mPlayerSprite.detachSelf();
        mPlayerSprite.dispose();

        for (Sprite bomb : mBombsSprite) {
            bomb.detachSelf();
            bomb.dispose();
        }
        mBombsSprite.clear();
    }

    @Override
    public void onBackKeyPressed() {
        destroyScene();
        getActivity().loadMenu();
    }
}

