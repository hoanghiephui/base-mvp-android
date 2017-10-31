package com.ducnd.demorealmmvp.remote.interact.interf

import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.realmmvp.remote.interact.source.IBaseApiConnector
import io.reactivex.Observable

/**
 * Created by ducnd on 8/10/17.
 */

interface IApiConnector : IBaseApiConnector {
    fun getListSong(name: String): Observable<MutableList<ItemSong>>
}
