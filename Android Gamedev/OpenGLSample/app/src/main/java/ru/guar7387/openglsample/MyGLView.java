package ru.guar7387.openglsample;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLView extends GLSurfaceView {

    private final RendererImplementation mRenderer;

    public MyGLView(Context context) {
        super(context);
        mRenderer = new RendererImplementation();
        init();
    }

    public MyGLView(Context context, RendererImplementation renderer) {
        super(context);
        this.mRenderer = renderer;
        init();
    }

    private void init() {
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}
