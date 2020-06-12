package com.wairdell

interface ObservableOnSubscribe<T> {

    fun subscribe(observer: Observer<T>)

}