package com.wairdell.learnhelp.kodein

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

const val KODEIN_SAMPLE_MODULE_TAG = "KODEIN_SAMPLE_MODULE_TAG";

val kodeinSampleModule = Kodein.Module(KODEIN_SAMPLE_MODULE_TAG) {
    bind<ExecutorService>() with singleton {
        Executors.newSingleThreadExecutor()
    }
}