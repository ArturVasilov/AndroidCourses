package ru.guar7387.kotlinfunny.features

import ru.guar7387.kotlinfunny.workingtogether.UsersStorage

public fun double(value : Int) : Int?{
    return value * 2
}

public fun nullChecking() {
    val b = double(5)?.dec()
}

public fun recursiveNullChecking() {
    val us: UsersStorage? = UsersStorage()
    us?.getUsers()?.size()?.compareTo(5)
}

public fun npe() {
    val b = double(10)!!.dec()
}

public fun ternary() {
    val b = double(5)?.dec() ?: 10
}

