package ru.guar7387.surfaceviewsample.gamedata;

import android.graphics.Point;

import ru.guar7387.surfaceviewsample.utils.Logger;

public class ObjectArea implements Area {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public ObjectArea(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void changePosition(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public int getRight() {
        return right;
    }

    @Override
    public int getBottom() {
        return bottom;
    }

    @Override
    public boolean intersects(Area area) {
        Point point = new Point(area.getLeft(), area.getTop());
        if (isPointInRect(point)) return true;

        point.set(area.getLeft(), area.getBottom());
        if (isPointInRect(point)) return true;

        point.set(area.getRight(), area.getTop());
        if (isPointInRect(point)) return true;

        point.set(area.getRight(), area.getBottom());
        return isPointInRect(point);
    }

    private boolean isPointInRect(Point point) {
        boolean isXOk = getLeft() <= point.x && point.x <= getRight();
        boolean isYOk = getTop() <= point.y && point.y <= getBottom();
        return isXOk && isYOk;
    }

    @Override
    public String toString() {
        return "ObjectArea{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }
}
