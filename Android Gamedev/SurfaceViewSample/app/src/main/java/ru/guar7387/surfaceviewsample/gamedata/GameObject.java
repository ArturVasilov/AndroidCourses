package ru.guar7387.surfaceviewsample.gamedata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface GameObject {

    public void render(Bitmap bitmap, Paint paint, Canvas canvas);

    public int getBitmapId();

    public Area getObjectArea();

    public void move(long time);
}


