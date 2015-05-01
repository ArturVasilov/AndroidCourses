package ru.guar7387.surfaceviewsample.gamedata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import ru.guar7387.surfaceviewsample.R;
import ru.guar7387.surfaceviewsample.utils.Logger;

public class Hero implements GameObject {

    private static final String TAG = Hero.class.getSimpleName();

    private final Area area;

    public Hero() {
        int y = (ImagesSize.getScreenHeight() - ImagesSize.Hero.BITMAP_HEIGHT) / 2;
        int x = 5;
        area = new ObjectArea(x, y, x + ImagesSize.Hero.BITMAP_WIDTH, y + ImagesSize.Hero.BITMAP_HEIGHT);

        Logger.log(TAG, area.toString());
    }

    @Override
    public Area getObjectArea() {
        return area;
    }

    @Override
    public void move(long time) {
        throw new UnsupportedOperationException("Hero can't move");
    }

    @Override
    public int getBitmapId() {
        return R.mipmap.hero;
    }

    @Override
    public void render(Bitmap bitmap, Paint paint, Canvas canvas) {
        canvas.drawBitmap(bitmap, area.getLeft(), area.getTop(), paint);
    }

    public int getDamage() {
        return 3;
    }
}


