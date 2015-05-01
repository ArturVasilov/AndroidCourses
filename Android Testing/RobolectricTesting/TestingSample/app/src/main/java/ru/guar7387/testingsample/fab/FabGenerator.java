package ru.guar7387.testingsample.fab;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import ru.guar7387.testingsample.R;

public class FabGenerator {

    private FabGenerator() { }

    public static void createFab(Context context, FloatingActionButton fab, int drawableId, View.OnClickListener onClickListener) {
        Resources res = context.getResources();
        float size = res.getDimension(R.dimen.fab_size);
        Bitmap bitmap = BitmapFactory.decodeResource(res, drawableId);
        //plus 20 it's to fix same strange bug with scaling - image has white borders
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) size + 20, (int) size + 20, false);
        fab.setDrawable(new BitmapDrawable(res, bitmap));
        fab.setBackgroundColor(Color.TRANSPARENT);
        fab.setOnClickListener(onClickListener);
    }
}
