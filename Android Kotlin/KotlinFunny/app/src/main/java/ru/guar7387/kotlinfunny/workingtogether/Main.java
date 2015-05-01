package ru.guar7387.kotlinfunny.workingtogether;

import kotlin.Function1;

public class Main {

    public static void main(String[] args) {
        UsersStorage storage = new UsersStorage();
        storage.add(new User("Login1", "123456".toCharArray()));
        storage.add(new User("Login2", "1234567".toCharArray()));
        storage.add(new User("Login3", "12345678".toCharArray()));
        Client client = new Client(storage);
        System.out.println(client.filter(new Function1<User, Boolean>() {
            @Override
            public Boolean invoke(User user) {
                return user.getPassword().length > 6;
            }
        }));
    }

}


