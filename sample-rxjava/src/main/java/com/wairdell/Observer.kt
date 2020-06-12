package com.wairdell

interface Observer<T> {

    fun onSubscribe()

    fun onNext(t : T)

    fun onComplete()

}