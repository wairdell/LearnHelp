package com.wairdell.observable

import com.wairdell.ObservableSource
import com.wairdell.Observer
import java.util.concurrent.ExecutorService

class ObservableSubscribeOn<T>(source: ObservableSource<T>, val executorService: ExecutorService) : ObservableWithUpstream<T, T>(source) {

    override fun subscribe(observer: Observer<T>) {
        executorService.execute(SubscribeOnObserver<T>(source, observer))
    }

    class SubscribeOnObserver<T>(private val source: ObservableSource<T>, private val observer: Observer<T>) :
        Runnable {
        override fun run() {
            source.subscribe(observer)
        }
    }

}