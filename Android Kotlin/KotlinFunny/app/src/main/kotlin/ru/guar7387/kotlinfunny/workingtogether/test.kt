package ru.guar7387.kotlinfunny.workingtogether

public fun test() {
    val storage = UsersStorage()
    storage.add(User("Login1", "123456".toCharArray()))
    storage.add(User("Login2", "1234567".toCharArray()))
    storage.add(User("Login3", "12345678".toCharArray()))
    val client = Client(storage)
    print(client.filter { it.password.size() > 6 })
}


