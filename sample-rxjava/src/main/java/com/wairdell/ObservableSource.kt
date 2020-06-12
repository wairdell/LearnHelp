package com.wairdell

interface ObservableSource<T> {

    fun subscribe(observer : Observer<T>)

}