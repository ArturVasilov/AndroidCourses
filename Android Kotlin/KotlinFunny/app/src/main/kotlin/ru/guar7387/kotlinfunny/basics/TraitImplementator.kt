package ru.guar7387.kotlinfunny.basics

public class TraitImplementator : FirstKotlinTrait {

    override fun print() {
        throw RuntimeException("you'll print nothing AHAHA")
    }

}

public fun testTrait() {
    val ti = TraitImplementator()
    ti.print()
    ti.defaultPrint()
}

