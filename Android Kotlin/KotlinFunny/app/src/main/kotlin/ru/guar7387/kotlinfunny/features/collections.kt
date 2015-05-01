package ru.guar7387.kotlinfunny.features

import java.util.ArrayList
import java.util.HashMap

public fun listTest() {
    val immutableList = listOf(1, 2, 3, 4, 5)

    val list = ArrayList<Int>()
    for (value in immutableList) {
        list.add(value)
    }

    assert(list.size() == 5)
    assert(list.sum() == 15)
    assert(list.min() == 1)
    assert(list.max() == 5)

    assert(list[3] == 4)
    list[3] = 5;
}

public fun mapTest() {
    val immutableMap = mapOf(1 to "Vasya", 2 to "Kolya", 3 to "Oleg")

    val map = HashMap<Int, String>()
    for ((key, value) in immutableMap) {
        map.put(key, value)
    }

    assert(map.size() == 3)
    assert(map[1] == "Vasya")
    map[2] = "Olezhka"
}


public fun collectionsWithLambdas() {
    val list = listOf(1, 2, 3, 4, 5)

    list.forEach { print(it) } //list.forEach( { print(it) } )

    val filtered = list.filter { it >= 3 }
    assert(filtered[1] == 4)

    val map = list.toMap { it * 31 }
    assert(map[31] == 1)

    val partitioned = list.partition { it % 2 == 1 }
    assert(partitioned.first[1] == 1)
    assert(partitioned.second[0] == 2)

    val sum = list.reduce { sum, value -> sum + value }
    assert(sum == list.sum())
}


public fun maps() {
    val map = mapOf(1 to "Vasya", 2 to "Kolya", 3 to "Oleg")

    map.forEach { print(it.getKey().toString() + ":" + it.getValue()) }

    val keys = map.keySet()
    val values = map.values()

    val filtered = map.filter { it.getValue().length() > 4 }
    assert(filtered.size() == 2)
}


