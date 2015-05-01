package ru.guar7387.surfaceviewsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

import ru.guar7387.surfaceviewsample.audio.GameAudio;
import ru.guar7387.surfaceviewsample.audio.StandardGameAudio;
import ru.guar7387.surfaceviewsample.controller.GameController;
import ru.guar7387.surfaceviewsample.controller.GameControllerImplementation;
import ru.guar7387.surfaceviewsample.controller.GameResult;
import ru.guar7387.surfaceviewsample.gamedata.BitmapsStorage;
import ru.guar7387.surfaceviewsample.gamedata.Fireball;
import ru.guar7387.surfaceviewsample.gamedata.Hero;
import ru.guar7387.surfaceviewsample.gamedata.ImagesSize;
import ru.guar7387.surfaceviewsample.gamedata.Monster;
import ru.guar7387.surfaceviewsample.model.GameModel;
import ru.guar7387.surfaceviewsample.model.StandardGameModel;
import ru.guar7387.surfaceviewsample.utils.Logger;

public class GameThread extends Thread implements GameResult {

    public static enum State {
        GAME_RUNNING,
        STOPPED,
        DEFEATED,
        WON,
        ;
    }

    private static final long MINIMUM_RENDER_DELAY = 18;
    private static final String TAG = GameThread.class.getSimpleName();

    private final Context mContext;

    private final GameView mGameView;

    private final GameModel mGameModel;

    private final GameController mGameController;

    private final Paint mPaint;

    private volatile State state;

    public GameThread(Context context, GameView gameView) {
        this.mContext = context;
        this.mGameView = gameView;

        this.mGameModel = new StandardGameModel(gameView.getWidth(), gameView.getHeight());

        GameAudio audio = new StandardGameAudio(context);
        this.mGameController = new GameControllerImplementation(mGameModel, audio);

        mGameController.subscribeForGameResult(this);

        mPaint = new Paint();
    }

    @Override
    public void run() {
        mGameController.start();
        while (state == State.GAME_RUNNING) {
            long startTime = System.currentTimeMillis();
            render();
            long endTime = System.currentTimeMillis();

            long difference = endTime - startTime;
            if (difference < MINIMUM_RENDER_DELAY) {
                try {
                    sleep(MINIMUM_RENDER_DELAY - difference);
                } catch (InterruptedException ie) {
                    break;
                }
                mGameController.update(MINIMUM_RENDER_DELAY);
            }
            else {
                mGameController.update(difference);
            }
        }
    }

    private void render() {
        SurfaceHolder holder = mGameView.getHolder();
        if (holder == null) {
            return;
        }
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawARGB(255, 0, 0, 0);

        BitmapsStorage bitmapsStorage = mGameModel.getBitmapsStorage();

        Hero hero = mGameModel.getHero();
        Bitmap bitmap = bitmapsStorage.loadBitmap(mContext.getResources(), hero.getBitmapId());
        hero.render(bitmap, mPaint, canvas);
        for (Monster monster : mGameModel.getMonsters()) {
            bitmap = bitmapsStorage.loadBitmap(mContext.getResources(), monster.getBitmapId());
            monster.render(bitmap, mPaint, canvas);
        }
        //to prevent concuttrent modification
        List<Fireball> fireballs = new ArrayList<>(mGameModel.getFireballs());
        for (Fireball fireball : fireballs) {
            bitmap = bitmapsStorage.loadBitmap(mContext.getResources(), fireball.getBitmapId());
            fireball.render(bitmap, mPaint, canvas);
        }
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void defeated(int score) {
        Logger.log(TAG, "Defeated");
        SurfaceHolder holder = mGameView.getHolder();
        if (holder == null) {
            return;
        }
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawARGB(255, 0, 0, 0);

        mPaint.setTextSize(108);
        mPaint.setFakeBoldText(true);
        mPaint.setColor(Color.RED);
        canvas.drawText("Game over! Score - " + score,
                ImagesSize.getScreenWidth() / 6, ImagesSize.getScreenHeight() / 3, mPaint);

        holder.unlockCanvasAndPost(canvas);

        setState(State.DEFEATED);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getGameState() {
        return state;
    }

    public GameController getController() {
        return mGameController;
    }
}
