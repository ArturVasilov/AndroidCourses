package ru.guar7387.kotlinfunny.basics

public class InheritedKotlinClass(val x: Int) : FirstKotlinClass(x, 5.0) {

    override fun sum() : Double {
        return super.sum() + 1
    }

}







