package com.longjam.sumanta.abridge

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


open class AppExecutors(private val diskIO: Executor, private val mainThread: Executor) {

    constructor() : this(Executors.newSingleThreadExecutor(), MainThreadExecutor())

    fun diskIO(): Executor {
        return diskIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}