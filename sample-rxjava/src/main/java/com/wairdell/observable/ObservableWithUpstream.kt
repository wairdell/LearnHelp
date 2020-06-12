package com.wairdell.observable

import com.wairdell.ObservableSource
import com.wairdell.SimpleObservable

abstract class ObservableWithUpstream<T, U>(val source: ObservableSource<T>) : SimpleObservable<U>() {

}