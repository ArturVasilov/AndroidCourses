package ru.guar7387.googleanalytics.data;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoItem implements Parcelable {

    private final String task;

    private final long time;

    private int id;

    public TodoItem(String task, long time) {
        this.task = task;
        this.time = time;
    }

    public String getTask() {
        return task;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "task='" + task + '\'' +
                ", time=" + time +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private TodoItem(Parcel source) {
        task = source.readString();
        time = source.readLong();
        id = source.readInt();
    }

    public static final Creator<TodoItem> CREATOR
            = new Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel source) {
            return new TodoItem(source);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(task);
        dest.writeLong(time);
    }
}
