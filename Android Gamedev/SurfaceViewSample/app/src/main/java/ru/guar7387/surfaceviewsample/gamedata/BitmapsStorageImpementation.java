package ru.guar7387.surfaceviewsample.gamedata;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BitmapsStorageImpementation implements BitmapsStorage {

    private final Map<Integer, Bitmap> bitmapMap;

    public BitmapsStorageImpementation() {
        this.bitmapMap = new ConcurrentHashMap<>();
    }

    @Override
    public Bitmap loadBitmap(Resources resources, int id) {
        Bitmap bitmap = bitmapMap.get(id);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = BitmapFactory.decodeResource(resources, id);
        bitmapMap.put(id, bitmap);
        return bitmap;
    }
}

