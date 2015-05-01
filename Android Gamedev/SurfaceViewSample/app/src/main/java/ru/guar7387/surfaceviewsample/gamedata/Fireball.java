package ru.guar7387.surfaceviewsample.gamedata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import ru.guar7387.surfaceviewsample.R;
import ru.guar7387.surfaceviewsample.utils.Logger;

public class Fireball implements GameObject {

    private static final String TAG = Fireball.class.getSimpleName();
    private final Area area;

    private final double angle;

    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    private double difX;
    private double difY;

    public Fireball(int x, int y, int destinationX, int destinationY) {
        left = x;
        top = y;
        right = x + ImagesSize.Fireball.BITMAP_WIDTH;
        bottom = top + ImagesSize.Fireball.BITMAP_HEIGHT;
        this.area = new ObjectArea(left, top, right, bottom);

        difX = 0;
        difY = 0;

        Logger.log(TAG, "Creating fireball, area - " + area);
        Logger.log(TAG, "x - " + x + "; y - " + y + "; destinationX - " + destinationX + "; destinationY - " + destinationY);

        if (x > destinationX) {
            if (destinationY < y) {
                angle = Math.atan(1.0 * (x - destinationX) / (y - destinationY)) + Math.PI / 2;
            }
            else {
                angle = -Math.atan(1.0 * (x - destinationX) / (destinationY - y)) - Math.PI / 2;
            }
        }
        else {
            if (destinationY < y) {
                angle = Math.atan(1.0 * (y - destinationY) / (destinationX - x));
            }
            else {
                angle = -Math.atan(1.0 * (destinationY - y) / (destinationX - x));
            }
        }

        Logger.log(TAG, "Fireball created; angle - " + angle);
    }

    @Override
    public int getBitmapId() {
        return R.mipmap.fireball;
    }

    @Override
    public Area getObjectArea() {
        return area;
    }

    @Override
    public void move(long time) {
        int speed = 500;

        int left = this.left;
        int top = this.top;
        int right = this.right;
        int bottom = this.bottom;

        difX += Math.cos(angle) * speed * (1.0 * time / 1000);
        difY += Math.sin(angle) * speed * (1.0 * time / 1000);

        left += (int) difX;
        right += (int) difX;
        top -= (int) difY;
        bottom -= (int) difY;

        area.changePosition(left, top, right, bottom);
    }

    @Override
    public void render(Bitmap bitmap, Paint paint, Canvas canvas) {
        canvas.drawBitmap(bitmap, area.getLeft(), area.getTop(), paint);
    }

}
