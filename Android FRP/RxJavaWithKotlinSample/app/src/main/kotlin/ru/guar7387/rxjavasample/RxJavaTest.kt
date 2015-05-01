package ru.guar73873.rxjavasample

import android.util.Log
import rx.Observable

public fun rxJavaTest() {
    Observable.just<String>("Hello", "World", "From Artur", "And NPP team", "With love")
            .take(4)
            .map<Int>({ s -> s.length() })
            .filter({ i -> i > 5 })
            .repeat(2)
            .flatMap<Int>({ i -> Observable.just(i + 5, i + 10, i + 15) })
            .subscribe( { i -> Log.i("rxJavaKotlinText", "" + i)})
}

