package ru.guar7387.rxjavasample;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaExamples {

    private static final String TAG = "RxJavaExamples";

    public void helloWorld() {
        Observable<String> helloWorldObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello, ");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };

        helloWorldObservable.subscribe(stringSubscriber);
    }

    public void simplerHelloWorld() {
        Observable.just("Hello", "World", "From Artur", "And NPP team", "With love")
                .take(4)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                })
                .repeat(2)
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        return Observable.just(integer + 5, integer + 10, integer + 15);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(TAG, String.valueOf(integer));
                    }
                });
    }

}

