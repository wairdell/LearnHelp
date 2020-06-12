package com.wairdell

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    SimpleObservable.just(1, 2).map {
        println("map ${threadNameStr()}")
        "map$it"
    }.flatMap {
        println("flatMap ${threadNameStr()}")
        SimpleObservable.fromIterable(it.split("p"))
    }.subscribeOn(newExecutorService("thread-1")).observeOn(newExecutorService("thread-2"))
        .subscribe(object : Observer<String> {
            override fun onSubscribe() {
                println("onSubscribe ${threadNameStr()}")
            }

            override fun onNext(t: String) {
                println("onNext ${threadNameStr()}")
            }

            override fun onComplete() {
                println("onComplete ${threadNameStr()}")
            }
        })
    //Thread.sleep(1000)
}

fun threadNameStr(): String {
    return "threadName = [${Thread.currentThread().name}]"
}

fun newExecutorService(name: String): ExecutorService {
    return Executors.newSingleThreadExecutor {
        Thread(it, name)
    }
}
