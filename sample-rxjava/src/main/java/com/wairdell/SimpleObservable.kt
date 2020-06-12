package com.wairdell

import com.wairdell.observable.*
import java.util.concurrent.ExecutorService

open abstract class SimpleObservable<T> : ObservableSource<T> {

    companion object {
        fun <T> create(observableOnSubscribe: ObservableOnSubscribe<T>): SimpleObservable<T> {
            return ObservableCreate(observableOnSubscribe)
        }

        fun <T> fromArray(vararg items: T): SimpleObservable<T> {
            return create(object : ObservableOnSubscribe<T> {
                override fun subscribe(observer: Observer<T>) {
                    observer.onSubscribe()
                    for (item in items) {
                        observer.onNext(item)
                    }
                    observer.onComplete()
                }

            })
        }

        fun <T> fromIterable(source: Iterable<T>): SimpleObservable<T> {
            return create(object : ObservableOnSubscribe<T> {
                override fun subscribe(observer: Observer<T>) {
                    observer.onSubscribe()
                    var iterator = source.iterator()
                    while (iterator.hasNext()) {
                        observer.onNext(iterator.next())
                    }
                    observer.onComplete()
                }
            })
        }

        fun <T> just(item: T): SimpleObservable<T> {
            return fromArray(item)
        }

        fun <T> just(item1: T, item2: T): SimpleObservable<T> {
            return fromArray(item1, item2)
        }
    }

    fun <U> map(mapper: (T) -> U): SimpleObservable<U> {
        return ObservableMap<T, U>(this, mapper)
    }

    fun <U> flatMap(mapper: (T) -> ObservableSource<U>): SimpleObservable<U> {
        return ObservableFlatMap<T, U>(this, mapper)
    }

    fun subscribeOn(executorService: ExecutorService): SimpleObservable<T> {
        return ObservableSubscribeOn<T>(this, executorService)
    }

    fun observeOn(executorService: ExecutorService): SimpleObservable<T> {
        return ObservableObserveOn<T>(this, executorService)
    }


}