package ru.guar7387.openglsample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import ru.guar7387.openglsample.shapes.Square;
import ru.guar7387.openglsample.shapes.Triangle;

public class RendererImplementation implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare = new Square();
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Draw triangle
        mTriangle.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}