package com.ducnd.demorealmmvp.remote.database

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by ducnd on 8/15/17.
 */
internal object ManagerProcess {
    val schedulerLoadMedia: Scheduler = Schedulers.from(Executors.newFixedThreadPool(10))
}