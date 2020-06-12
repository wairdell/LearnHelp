package com.wairdell.observable

import com.wairdell.ObservableSource
import com.wairdell.Observer
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue

class ObservableObserveOn<T>(source: ObservableSource<T>, private val executorService: ExecutorService) :
    ObservableWithUpstream<T, T>(source) {

    override fun subscribe(observer: Observer<T>) {
        source.subscribe(ObserveOnObserver(observer, executorService))
    }

    class ObserveOnObserver<T>(val observer: Observer<T>, private val executorService: ExecutorService) : Observer<T>,
        Runnable {

        private val queue: BlockingQueue<Node<T>> = LinkedBlockingQueue<Node<T>>()

        override fun run() {
            observer.onSubscribe()
            var next: Node<T> = queue.take()
            while (next.value != null) {
                observer.onNext(next.value!!)
                next = queue.take()
            }
            observer.onComplete()
        }

        override fun onSubscribe() {
            executorService.execute(this)
        }

        override fun onNext(t: T) {
            queue.put(Node(t))
        }

        override fun onComplete() {
            queue.put(Node(null))
        }

    }

    data class Node<T>(val value: T?)


}