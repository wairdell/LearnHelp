package com.wairdell.observable

import com.wairdell.ObservableSource
import com.wairdell.Observer

class ObservableFlatMap<T, U>(source: ObservableSource<T>, val mapper: (T) -> ObservableSource<U>) : ObservableWithUpstream<T, U>(source) {

    override fun subscribe(observer: Observer<U>) {
        source.subscribe(object : Observer<T> {
            override fun onSubscribe() {
                observer.onSubscribe()
            }

            override fun onNext(t: T) {
                mapper(t).subscribe(object : Observer<U> {
                    override fun onSubscribe() {
                    }

                    override fun onNext(t: U) {
                        observer.onNext(t)
                    }

                    override fun onComplete() {
                    }

                })
            }

            override fun onComplete() {
                observer.onComplete()
            }

        })
    }
}