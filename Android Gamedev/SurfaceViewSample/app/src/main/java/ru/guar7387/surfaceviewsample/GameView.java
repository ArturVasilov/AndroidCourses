package ru.guar7387.surfaceviewsample;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ru.guar7387.surfaceviewsample.controller.GameController;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread mGameThread;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        newGame();
    }

    private void newGame() {
        mGameThread = new GameThread(getContext(), this);
        mGameThread.setState(GameThread.State.GAME_RUNNING);
        mGameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mGameThread.setState(GameThread.State.STOPPED);
        while (true) {
            try {
                mGameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            GameThread.State state = mGameThread.getGameState();
            if (state == GameThread.State.GAME_RUNNING) {
                GameController controller = mGameThread.getController();
                controller.shoot((int) event.getX(), (int) event.getY());
            }
            if (state == GameThread.State.DEFEATED) {
                newGame();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}


