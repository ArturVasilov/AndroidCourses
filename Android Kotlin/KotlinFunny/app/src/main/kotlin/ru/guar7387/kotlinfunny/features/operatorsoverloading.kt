package ru.guar7387.kotlinfunny.features


class Complex(val real: Double, val imaginative: Double) {

    public fun plus(complex: Complex) : Complex {
        return Complex(real + complex.real,
                imaginative + complex.imaginative)
    }
}

public fun testOverloading() {
    val a = Complex(1.0, 1.0)
    val b = Complex(2.0, 2.0)
    val c = a + b
}


public fun Complex.module() : Double {
    return Math.sqrt(real * real + imaginative * imaginative)
}


class MyInt(val int: Int) :Comparable<MyInt> {

    override fun compareTo(other: MyInt): Int {
        return int.compareTo(other.int)
    }

    public fun rangeTo(item: MyInt) : Range<MyInt> {
        return RangeImpl(this, item)
    }

    public class RangeImpl(override val start: MyInt, override val end: MyInt) : Range<MyInt> {

        override fun contains(item: MyInt): Boolean {
            return start.int < item.int && item.int < end.int
        }
    }
}

public fun testCustom() {
    val a = MyInt(10)
    val b = MyInt(100)
    val c = a..b
    assert(MyInt(50) in c)
}


