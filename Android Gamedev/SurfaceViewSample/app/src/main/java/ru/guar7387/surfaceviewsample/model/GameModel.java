package ru.guar7387.surfaceviewsample.model;

import java.util.List;

import ru.guar7387.surfaceviewsample.gamedata.BitmapsStorage;
import ru.guar7387.surfaceviewsample.gamedata.Fireball;
import ru.guar7387.surfaceviewsample.gamedata.Hero;
import ru.guar7387.surfaceviewsample.gamedata.Monster;

public interface GameModel {

    public Hero getHero();

    public List<Monster> getMonsters();

    public List<Fireball> getFireballs();

    public BitmapsStorage getBitmapsStorage();

    public void init();
}


