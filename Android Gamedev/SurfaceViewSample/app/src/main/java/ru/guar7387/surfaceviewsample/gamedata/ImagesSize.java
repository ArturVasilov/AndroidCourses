package ru.guar7387.surfaceviewsample.gamedata;

public class ImagesSize {

    private static boolean wasInit = false;

    public static void init(int screenWidth, int screenHeight) {
        wasInit = true;
        ImagesSize.screenWidth = screenWidth;
        ImagesSize.screenHeight = screenHeight;
    }

    private static int screenWidth;
    private static int screenHeight;

    public static int getScreenWidth() {
        if (wasInit) {
            return screenWidth;
        }
        throw new IllegalStateException("You should instantiate screen params first");
    }

    public static int getScreenHeight() {
        if (wasInit) {
            return screenHeight;
        }
        throw new IllegalStateException("You should instantiate screen params first");
    }

    public static interface Hero {
        int BITMAP_WIDTH = 200;
        int BITMAP_HEIGHT = 266;
    }

    public static interface Fireball {
        int BITMAP_WIDTH = 45;
        int BITMAP_HEIGHT = 39;
    }

    public static interface SmallMonster {
        int BITMAP_WIDTH = 76;
        int BITMAP_HEIGHT = 70;
    }

    public static interface MiddleMonster {
        int BITMAP_WIDTH = 140;
        int BITMAP_HEIGHT = 136;
    }

    public static interface BossMonster {
        int BITMAP_WIDTH = 400;
        int BITMAP_HEIGHT = 297;
    }
}


