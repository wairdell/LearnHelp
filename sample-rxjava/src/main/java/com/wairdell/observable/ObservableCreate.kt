package com.wairdell.observable

import com.wairdell.ObservableOnSubscribe
import com.wairdell.Observer
import com.wairdell.SimpleObservable

class ObservableCreate<T>(private val observableOnSubscribe: ObservableOnSubscribe<T>) : SimpleObservable<T>() {

    override fun subscribe(observer: Observer<T>) {
        observableOnSubscribe.subscribe(observer)
    }

}