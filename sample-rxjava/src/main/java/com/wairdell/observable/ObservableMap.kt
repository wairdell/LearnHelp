package com.wairdell.observable

import com.wairdell.ObservableSource
import com.wairdell.Observer

class ObservableMap<T, U>(source: ObservableSource<T>, val mapper: (T) -> U) : ObservableWithUpstream<T, U>(source) {

    override fun subscribe(observer: Observer<U>) {
        source.subscribe(object : Observer<T> {
            override fun onSubscribe() {
                observer.onSubscribe()
            }

            override fun onNext(t: T) {
                observer.onNext(mapper(t))
            }

            override fun onComplete() {
                observer.onComplete()
            }
        })
    }


}