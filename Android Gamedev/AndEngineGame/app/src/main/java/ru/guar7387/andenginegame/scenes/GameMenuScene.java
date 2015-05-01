package ru.guar7387.andenginegame.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;

import ru.guar7387.andenginegame.AbstractGameActivity;
import ru.guar7387.andenginegame.Logger;
import ru.guar7387.andenginegame.managers.ResourcesManager;
import ru.guar7387.andenginegame.managers.SceneManager;

public class GameMenuScene extends AbstractScene implements MenuScene.IOnMenuItemClickListener {

    private static final String TAG = GameMenuScene.class.getSimpleName();

    private MenuScene mMenuScene;

    private final int[] mLevels = {1, 2, 3};

    public GameMenuScene(AbstractGameActivity activity) {
        super(activity);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.MENU_SCENE;
    }

    @Override
    public void populateScene() {
        createBackground();
        createMenuChildScene();
    }

    @Override
    public void destroyScene() {
        Logger.log(TAG, "destroying menu scene");
        mMenuScene.clearMenuItems();
        mMenuScene.closeMenuScene();

        this.detachSelf();
        this.dispose();
    }

    @Override
    public void onBackKeyPressed() {
        getActivity().finish();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        int id = pMenuItem.getID();
        Logger.log(TAG, "Menu item clicked");
        for (int menuId : mLevels) {
            if (id == menuId) {
                Logger.log(TAG, "Selected level " + menuId);
                AbstractGameActivity activity = getActivity();
                if (activity != null) {
                    Logger.log(TAG, "Creating game scene");
                    SceneManager sceneManager = activity.getSceneManager();
                    sceneManager.uploadScene(SceneType.GAME_SCENE);
                    GameScene gameScene = (GameScene) sceneManager.getScene(SceneType.GAME_SCENE);
                    gameScene.setLevel(menuId);
                    gameScene.populateScene();

                    activity.getEngine().setScene(gameScene);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private void createBackground() {
        Logger.log(TAG, "creating menu background");
        AbstractGameActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        ResourcesManager manager = activity.getResourcesManager();
        TextureRegion region = TextureRegionFactory.extractFromTexture(manager.getTexture("gfx/menu_background.jpg"));
        attachChild(new Sprite(activity.getCameraWidth() / 2, activity.getCameraHeight() / 2,
                region, manager.getVertexBufferObjectManager()) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }

    private void createMenuChildScene() {
        Logger.log(TAG, "creating menu items");
        AbstractGameActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        ResourcesManager manager = activity.getResourcesManager();

        mMenuScene = new MenuScene(activity.getEngine().getCamera());
        mMenuScene.setPosition(activity.getCameraWidth() / 2, activity.getCameraHeight() / 2);

        TextureRegion[] levelRegionS = {
                TextureRegionFactory.extractFromTexture(manager.getTexture("gfx/level1.png")),
                TextureRegionFactory.extractFromTexture(manager.getTexture("gfx/level2.png")),
                TextureRegionFactory.extractFromTexture(manager.getTexture("gfx/level3.png")),
        };

        IMenuItem[] menuItems = {
                new SpriteMenuItem(mLevels[0], levelRegionS[0], manager.getVertexBufferObjectManager()),
                new SpriteMenuItem(mLevels[1], levelRegionS[1], manager.getVertexBufferObjectManager()),
                new SpriteMenuItem(mLevels[2], levelRegionS[2], manager.getVertexBufferObjectManager()),
        };

        for (IMenuItem menuItem : menuItems) {
            mMenuScene.addMenuItem(menuItem);
        }

        int width = activity.getCameraWidth() / 2;
        int height = activity.getCameraHeight() / 2;
        menuItems[0].setPosition(-width + 100,  height - 100);
        menuItems[1].setPosition(-width + 300, height - 100);
        menuItems[2].setPosition(-width + 500, height - 100);

        mMenuScene.buildAnimations();
        mMenuScene.setBackgroundEnabled(false);
        mMenuScene.setOnMenuItemClickListener(this);
        setChildScene(mMenuScene);
    }

}
