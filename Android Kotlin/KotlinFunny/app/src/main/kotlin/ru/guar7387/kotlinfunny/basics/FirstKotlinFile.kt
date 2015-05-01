package ru.guar7387.kotlinfunny.basics

fun sum(a: Int, b: Int) : Int {
    return a + b
}

fun sum2(a: Int, b: Int) = a + b



val a = 5;
var b = a;

fun any() {
    b = 100
    //a = 150 doesn't compile
}


fun f() {
    val a = RuntimeException()
    assert(a is RuntimeException)
}

fun varags(a: Int, b: Int = 0, c: String = "hehe") {
    if (b == 0) {
        return
    }
    if (c.equals("hehe")) {
        varags(a)
    }
    varags(5, 6)
}

fun test() = varags(3, 4, "aa")

fun testing() {
    val o = FirstKotlinClass(1, 5.0)
    assert(o.sum() < 8.01)
}

