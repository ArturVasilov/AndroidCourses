package ru.guar7387.kotlinfunny.features

public fun fillTheDifference() {
    val bool = true
    assert(bool)

    val func : (a: Int) -> Boolean = {
        it % 2 == 0
    }
    assert(func.invoke(6))
}

public fun factorial(value: Int, func: (value : Int) -> Int): Int{
    return func.invoke(value)
}

public fun testFactorial() {
    assert(120 == factorial(5, ::fact))
}

public fun fact(value: Int) : Int {
    var result = 1
    for (i in 2..value) {
        result *= i
    }
    return result
}


