package ru.guar7387.surfaceviewsample.gamedata;

public interface Area {

    public int getLeft();

    public int getTop();

    public int getRight();

    public int getBottom();

    public void changePosition(int left, int top, int right, int bottom);

    public boolean intersects(Area area);
}



