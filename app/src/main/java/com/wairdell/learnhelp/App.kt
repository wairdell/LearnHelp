package com.wairdell.learnhelp

import android.app.Application
import android.content.Context
import com.wairdell.learnhelp.kodein.kodeinSampleModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override val kodein: Kodein by Kodein.lazy {
        bind<Context>() with singleton { this@App }
        import(androidCoreModule(this@App))
        import(androidXModule(this@App))
        import(kodeinSampleModule)
    }

}