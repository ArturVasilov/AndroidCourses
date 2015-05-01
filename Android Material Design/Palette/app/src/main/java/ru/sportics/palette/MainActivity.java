package ru.sportics.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    private final View[] mViews = new View[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] ids = {R.id.colorView1, R.id.colorView2, R.id.colorView3,
                R.id.colorView4, R.id.colorView5, R.id.colorView6,};
        for (int i = 0; i < ids.length; i++) {
            mViews[i] = findViewById(ids[i]);
        }

        extractColors();

        setPaletteAsync();
    }

    private void extractColors() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Palette palette = Palette.generate(bitmap);
        setColors(palette);
    }

    private void setPaletteAsync() {
        //Generating bitmap isn't not async, but OK, it's not so important.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                setColors(palette);
            }
        });
    }

    private void setColors(Palette palette) {
        int[] colors = { palette.getVibrantColor(Color.BLACK),
                palette.getLightVibrantColor(Color.BLACK),
                palette.getDarkVibrantColor(Color.BLACK),
                palette.getMutedColor(Color.BLACK),
                palette.getLightMutedColor(Color.BLACK),
                palette.getDarkMutedColor(Color.BLACK), };
        for (int i = 0; i < colors.length; i++) {
            mViews[i].setBackgroundColor(colors[i]);
        }
    }

}
