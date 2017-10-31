package com.ducnd.demorealmmvp.remote.interact.interf

import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.demorealmmvp.remote.model.User
import com.ducnd.realmmvp.remote.interact.source.IBaseInteraction
import io.reactivex.Observable
import io.realm.RealmObject

/**
 * Created by ducnd on 8/10/17.
 */

interface IAccountInteraction : IBaseInteraction<User> {
    fun getListSong(name: String): Observable<MutableList<ItemSong>>
    fun saveSongSearchResult(songSearchResult: SongSearchResult)
    fun getSongSearchResultAtFreedThread(nameSong: String): SongSearchResult?
    fun <T : RealmObject> deleteRealmObject(realmObject: T):Boolean
    fun optimizeStore()
}
