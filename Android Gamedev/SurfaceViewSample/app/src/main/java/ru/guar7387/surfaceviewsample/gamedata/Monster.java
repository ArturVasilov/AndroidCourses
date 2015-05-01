package ru.guar7387.surfaceviewsample.gamedata;

public interface Monster extends GameObject {

    public int getHitPoints();

    public void shoot(int damage);

    public int getSpeed();

    boolean isDead();

    int getWidth();
}


