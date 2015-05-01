package ru.guar7387.surfaceviewsample.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ru.guar7387.surfaceviewsample.audio.GameAudio;
import ru.guar7387.surfaceviewsample.gamedata.Area;
import ru.guar7387.surfaceviewsample.gamedata.BossMonster;
import ru.guar7387.surfaceviewsample.gamedata.Fireball;
import ru.guar7387.surfaceviewsample.gamedata.Hero;
import ru.guar7387.surfaceviewsample.gamedata.ImagesSize;
import ru.guar7387.surfaceviewsample.gamedata.MiddleMonster;
import ru.guar7387.surfaceviewsample.gamedata.Monster;
import ru.guar7387.surfaceviewsample.gamedata.SmallMonster;
import ru.guar7387.surfaceviewsample.model.GameModel;
import ru.guar7387.surfaceviewsample.utils.Logger;

public class GameControllerImplementation implements GameController {

    private static final String TAG = GameControllerImplementation.class.getName();

    private final GameModel gameModel;

    private final GameAudio audio;

    private long monsterLastTimeAdded;

    private static final long MONSTERS_MINIMUM_DELAY = 500;

    private int score;

    private final Random random;

    private final List<GameResult> gameResultListeners;

    private int monstersCountController = 60 * 5_000;

    public GameControllerImplementation(GameModel gameModel, GameAudio audio) {
        score = 0;

        this.gameModel = gameModel;
        this.audio = audio;

        random = new SecureRandom();

        gameResultListeners = new ArrayList<>();
    }

    @Override
    public GameModel getModel() {
        return gameModel;
    }

    @Override
    public void start() {
        gameModel.init();
    }

    @Override
    public void update(long timeDifference) {
        List<Fireball> fireballs = new ArrayList<>(gameModel.getFireballs());
        List<Monster> monsters = new ArrayList<>(gameModel.getMonsters());
        Hero hero = gameModel.getHero();

        Iterator<Monster> monsterIterator = monsters.listIterator();
        Iterator<Fireball> fireballIterator;
        while (monsterIterator.hasNext()) {
            Monster monster = monsterIterator.next();
            fireballIterator = fireballs.listIterator();
            while (fireballIterator.hasNext()) {
                Fireball fireball = fireballIterator.next();
                Area area = fireball.getObjectArea();
                if (area.intersects(monster.getObjectArea())) {
                    Logger.log(TAG, "Monster intersects fireball");
                    audio.playShootSound();
                    gameModel.getFireballs().remove(fireball);
                    fireballIterator.remove();
                    monster.shoot(hero.getDamage());
                    score += hero.getDamage();
                    if (monster.isDead()) {
                        score += 2;
                        Logger.log(TAG, "Monster is dead");
                        gameModel.getMonsters().remove(monster);
                        monsterIterator.remove();
                        break;
                    }
                }
            }
        }

        fireballIterator = fireballs.listIterator();
        while (fireballIterator.hasNext()) {
            Fireball fireball = fireballIterator.next();
            Area area = fireball.getObjectArea();
            if (area.getLeft() < -ImagesSize.Fireball.BITMAP_WIDTH ||
                    area.getTop() < -ImagesSize.Fireball.BITMAP_WIDTH ||
                    area.getRight() > ImagesSize.getScreenWidth() + ImagesSize.Fireball.BITMAP_WIDTH ||
                    area.getBottom() > ImagesSize.getScreenHeight() + ImagesSize.Fireball.BITMAP_HEIGHT) {
                Logger.log(TAG, "Fireball has left game area");
                boolean wasRemoved = gameModel.getFireballs().remove(fireball);
                Logger.log(TAG, "Fireball was removed - " + wasRemoved);
                fireballIterator.remove();
            }
        }

        monsterIterator = monsters.listIterator();
        while (monsterIterator.hasNext()) {
            Monster monster = monsterIterator.next();
            Area area = monster.getObjectArea();
            if (area.getLeft() < -monster.getWidth()) {
                defeated();
                return;
            }
        }

        for (Fireball fireball : fireballs) {
            fireball.move(timeDifference);
        }

        monstersCountController -= timeDifference;
        addMonster();
        for (Monster monster : gameModel.getMonsters()) {
            monster.move(timeDifference);
        }
    }

    @Override
    public void subscribeForGameResult(GameResult gameResult) {
        gameResultListeners.add(gameResult);
    }

    @Override
    public void unsubscribeForGameResult(GameResult gameResult) {
        gameResultListeners.remove(gameResult);
    }

    private void defeated() {
        audio.playDefeatedSound();
        Logger.log(TAG, "Defeated");
        gameModel.getMonsters().clear();
        gameModel.getFireballs().clear();

        for (GameResult gameResult : gameResultListeners) {
            gameResult.defeated(score);
        }
    }

    private void addMonster() {
        long time = System.currentTimeMillis();
        if (time - monsterLastTimeAdded < MONSTERS_MINIMUM_DELAY) {
            return;
        }
        //to add a little randomness for monsters time appearence
        int randomness = Math.max(monstersCountController / 5000, 5);
        boolean shouldAddNewMonster = random.nextInt(randomness) == 5;
        if (shouldAddNewMonster) {
            Monster monster;
            int rand = random.nextInt(15) + 1;
            int x = ImagesSize.getScreenWidth();
            if (rand == 15) {
                monster = createBossMonster(x);
            }
            else if (rand % 3 == 0) {
                monster = createMiddleMonster(x);
            }
            else {
                monster = createSmallMonster(x);
            }
            getModel().getMonsters().add(monster);
            monsterLastTimeAdded = System.currentTimeMillis();
        }
    }

    private Monster createBossMonster(int x) {
        int y = random.nextInt(ImagesSize.getScreenHeight() - ImagesSize.BossMonster.BITMAP_HEIGHT);
        return new BossMonster(x, y);
    }

    private Monster createMiddleMonster(int x) {
        int y = random.nextInt(ImagesSize.getScreenHeight() - ImagesSize.MiddleMonster.BITMAP_HEIGHT);
        return new MiddleMonster(x, y);
    }

    private Monster createSmallMonster(int x) {
        int y = random.nextInt(ImagesSize.getScreenHeight() - ImagesSize.SmallMonster.BITMAP_HEIGHT);
        return new SmallMonster(x, y);
    }

    @Override
    public void shoot(int destinationX, int destinationY) {
        int x = ImagesSize.Hero.BITMAP_WIDTH;
        int y = (ImagesSize.getScreenHeight() - ImagesSize.Fireball.BITMAP_HEIGHT) / 2;
        if (Math.abs(x - destinationX) < 30 || Math.abs(y - destinationY) < 30) {
            return;
        }
        gameModel.getFireballs().add(new Fireball(x, y, destinationX, destinationY));
    }
}


