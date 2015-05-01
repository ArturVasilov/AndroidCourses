package ru.guar7387.surfaceviewsample.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.guar7387.surfaceviewsample.gamedata.BitmapsStorage;
import ru.guar7387.surfaceviewsample.gamedata.BitmapsStorageImpementation;
import ru.guar7387.surfaceviewsample.gamedata.Fireball;
import ru.guar7387.surfaceviewsample.gamedata.Hero;
import ru.guar7387.surfaceviewsample.gamedata.ImagesSize;
import ru.guar7387.surfaceviewsample.gamedata.Monster;
import ru.guar7387.surfaceviewsample.utils.Logger;

public class StandardGameModel implements GameModel {

    private static final String TAG = StandardGameModel.class.getSimpleName();

    private final BitmapsStorage bitmapsStorage;

    private final Hero hero;

    private final List<Monster> monsters;

    private final List<Fireball> fireballs;

    public StandardGameModel(int screenWidth, int screenHeight) {
        Logger.log(TAG, "screentWidth - " + screenWidth + "; screenHeight - " + screenHeight);

        ImagesSize.init(screenWidth, screenHeight);

        this.bitmapsStorage = new BitmapsStorageImpementation();

        hero = new Hero();
        monsters = Collections.synchronizedList(new ArrayList<Monster>());
        fireballs = Collections.synchronizedList(new ArrayList<Fireball>());
    }

    @Override
    public void init() {
    }

    @Override
    public Hero getHero() {
        return hero;
    }

    @Override
    public List<Monster> getMonsters() {
        return monsters;
    }

    @Override
    public List<Fireball> getFireballs() {
        return fireballs;
    }

    @Override
    public BitmapsStorage getBitmapsStorage() {
        return bitmapsStorage;
    }
}


