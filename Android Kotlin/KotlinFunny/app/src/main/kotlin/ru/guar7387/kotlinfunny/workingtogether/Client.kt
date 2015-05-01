package ru.guar7387.kotlinfunny.workingtogether

public class Client(val storage: UsersStorage) {

    public fun filter(filter: (User) -> Boolean) : List<User> {
        return storage.getUsers().filter { filter.invoke(it) }
    }

}


