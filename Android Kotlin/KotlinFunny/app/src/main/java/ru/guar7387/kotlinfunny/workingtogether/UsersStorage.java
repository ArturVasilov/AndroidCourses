package ru.guar7387.kotlinfunny.workingtogether;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersStorage {

    private final List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

}



