package ru.guar7387.kotlinfunny.features

public fun String.hasSpaces() : Boolean {
    return contains(" ")
}

public fun basicExtension() {
    val a = "AAA he"
    assert(a.hasSpaces())
}


public class SomeClass(val a: Int, val b: Int)

public fun SomeClass.sum() : Int {
    return a + b
}

public fun test() {
    val sc = SomeClass(5, 4)
    assert(sc.sum() == 9)
}



