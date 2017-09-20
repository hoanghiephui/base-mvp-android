package com.ducnd.demorealmmvp.remote.interact.main

import android.content.Context
import com.ducnd.demorealmmvp.remote.database.ManagerProcess
import com.ducnd.demorealmmvp.remote.interact.interf.IApiConnector
import com.ducnd.demorealmmvp.remote.interact.source.ResApi
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.realmmvp.remote.interact.main.BaseApiConnector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Interceptor

/**
 * Created by ducnd on 8/10/17.
 */

class ApiConnector(endpoint: String, mClazz: Class<ResApi>, context: Context) : BaseApiConnector<ResApi>(endpoint, mClazz, context), IApiConnector {
    override fun getInterceptor(): Interceptor? {
        return null
    }

    override fun getListSong(name: String): Observable<MutableList<ItemSong>> {
        return mApi.getListSong(name, "mp3.zing.vn", "sdfs")
                .subscribeOn(ManagerProcess.schedulerInteractServer)
                .observeOn(AndroidSchedulers.mainThread())

    }
}
