package com.ducnd.demorealmmvp.remote.interact.main

import com.ducnd.demorealmmvp.remote.database.ManagerProcess
import com.ducnd.demorealmmvp.remote.interact.interf.IApiConnector
import com.ducnd.demorealmmvp.remote.interact.source.ResApi
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.realmmvp.remote.interact.main.BaseApiConnector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by ducnd on 8/10/17.
 */

class ApiConnector(endpoint: String, mClazz: Class<ResApi>) : BaseApiConnector<ResApi>(endpoint, mClazz), IApiConnector {

    override fun getListSong(name: String): Observable<MutableList<ItemSong>> {
        return mApi.getListSong(name, "mp3.zing.vn", "sdfs")
                .subscribeOn(ManagerProcess.schedulerInteractServer)
                .observeOn(AndroidSchedulers.mainThread())

    }
}
