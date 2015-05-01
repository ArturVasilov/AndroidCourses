package ru.guar7387.surfaceviewsample.gamedata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import ru.guar7387.surfaceviewsample.R;

public class SmallMonster implements Monster {

    private final Area area;

    private final int left;
    private final int right;

    private int hitPoints;

    private double difX;

    public SmallMonster(int x, int y) {
        left = x;
        right = x + ImagesSize.MiddleMonster.BITMAP_WIDTH;

        area = new ObjectArea(x, y, x + ImagesSize.MiddleMonster.BITMAP_WIDTH, y + ImagesSize.MiddleMonster.BITMAP_HEIGHT);

        difX = 0;

        hitPoints = 5;
    }

    @Override
    public int getBitmapId() {
        return R.mipmap.small_monster;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public boolean isDead() {
        return hitPoints <= 0;
    }

    @Override
    public int getSpeed() {
        return 75;
    }

    @Override
    public Area getObjectArea() {
        return area;
    }

    @Override
    public int getWidth() {
        return ImagesSize.SmallMonster.BITMAP_WIDTH;
    }

    @Override
    public void shoot(int damage) {
        hitPoints -= damage;
    }

    @Override
    public void move(long time) {
        int left = this.left;
        int top = area.getTop();
        int right = this.right;
        int bottom = area.getBottom();

        difX += (getSpeed() * (1.0 * time / 1000));
        left -= (int) difX;
        right -= (int) difX;
        area.changePosition(left, top, right, bottom);
    }

    @Override
    public void render(Bitmap bitmap, Paint paint, Canvas canvas) {
        canvas.drawBitmap(bitmap, area.getLeft(), area.getTop(), paint);
    }
}


