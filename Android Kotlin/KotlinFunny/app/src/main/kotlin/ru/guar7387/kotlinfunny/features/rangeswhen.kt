package ru.guar7387.kotlinfunny.features

public fun range1() {
    for (i in 1..100) {
        print(i)
    }

    for (i in 1.0..6.7 step 0.1) {
        print(i)
    }

    for (i in 100 downTo 1) {
        print(i)
    }
}

public fun range2() {
    val a = 1.0 .. 2.5
    assert(1.8 in a)

    assert("ab" in "aa".."bb")
}

public fun funny() {
    100.times() {
        print("A")
    }
}


public fun when1() {
    val a = 5
    when (a) {
        1 -> print("a is 1")
        in 2..4 -> print(10)
        else -> print("failed")
    }
}

public fun when2(): Int = when ("5") {
    "a" -> 0
    "5" -> 1
    else -> 10
}



