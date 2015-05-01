package ru.guar7387.surfaceviewsample.audio;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import ru.guar7387.surfaceviewsample.R;

public class StandardGameAudio implements GameAudio {

    private static final int STREAMS_COUNT = 2;

    private final int shootSoundId;
    private final int defeatedSoundId;

    private final SoundPool soundPool;

    public StandardGameAudio(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            soundPool = createSoundPoolWithBuilder();
        }
        else {
            soundPool = createSoundPool();
        }
        shootSoundId = soundPool.load(context, R.raw.fireball, 9);
        defeatedSoundId = soundPool.load(context, R.raw.defeat, 10);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool createSoundPoolWithBuilder() {
        return new SoundPool.Builder().setMaxStreams(STREAMS_COUNT).build();
    }

    private SoundPool createSoundPool() {
        //noinspection deprecation
        return new SoundPool(STREAMS_COUNT, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void playShootSound() {
        soundPool.stop(shootSoundId);
        soundPool.stop(defeatedSoundId);

        soundPool.play(shootSoundId, 1, 1, 9, 0, 1);
    }

    @Override
    public void playDefeatedSound() {
        soundPool.stop(shootSoundId);
        soundPool.stop(defeatedSoundId);

        soundPool.play(defeatedSoundId, 1, 1, 10, 0, 1);
    }
}

