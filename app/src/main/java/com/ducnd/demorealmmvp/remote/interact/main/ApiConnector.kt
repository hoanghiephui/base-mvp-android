package com.ducnd.demorealmmvp.remote.interact.main

import com.ducnd.demorealmmvp.remote.interact.interf.IApiConnector
import com.ducnd.demorealmmvp.remote.interact.source.ResApi
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.realmmvp.remote.interact.main.BaseApiConnector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ducnd on 8/10/17.
 */

class ApiConnector(endpoint: String, mClazz: Class<ResApi>) : BaseApiConnector<ResApi>(endpoint, mClazz), IApiConnector {

    override fun getListSong(name: String): Observable<MutableList<ItemSong>> {
        return mApi.getListSong(name, "mp3.zing.vn", "sdfs")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

    }
}
