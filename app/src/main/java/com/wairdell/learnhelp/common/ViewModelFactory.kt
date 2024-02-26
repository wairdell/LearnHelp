package com.wairdell.learnhelp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

open class ViewModelFactory : ViewModelProvider.Factory {

    companion object multiItem : ViewModelFactory() {

    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.newInstance()
    }
}