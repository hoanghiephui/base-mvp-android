package com.ducnd.demorealmmvp.remote.interact.main

import com.ducnd.demorealmmvp.remote.database.StoreManager
import com.ducnd.demorealmmvp.remote.interact.interf.IAccountInteraction
import com.ducnd.demorealmmvp.remote.interact.interf.IApiConnector
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.demorealmmvp.remote.model.User
import com.ducnd.realmmvp.remote.interact.main.BaseInteract
import com.ducnd.realmmvp.utils.Action1Obtain

import io.reactivex.Observable
import io.realm.RealmList

/**
 * Created by ducnd on 8/10/17.
 */

class AccountInteraction(connector: IApiConnector, store: StoreManager) : BaseInteract<User, IApiConnector, StoreManager>(connector, store), IAccountInteraction {

    override fun getListSong(name: String): Observable<MutableList<ItemSong>> {
        return mConnector.getListSong(name)
    }

    override fun saveSongSearchResult(songSearchResult: SongSearchResult) {
        mStore.saveAtFreeThread(songSearchResult)
    }

    override fun getSongSearchResultAtFreedThread(nameSong: String): SongSearchResult? {
        return mStore.findItemAtFreeThread(SongSearchResult::class.java, SongSearchResult.NAME_SEARCH, nameSong, object : Action1Obtain<SongSearchResult> {
            override fun call(data: SongSearchResult): SongSearchResult {
                val song: SongSearchResult = SongSearchResult()
                song.id = data.id
                song.nameSearch =data.nameSearch
                song.itemSongs = RealmList<ItemSong>()
                if ( data.itemSongs != null ) {
                    data.itemSongs!!.forEach {
                        val item:ItemSong = ItemSong()
                        item.artist = it.artist
                        item.id = it.id
                        item.title = it.title
                        item.siteId = it.siteId
                        item.urlJunDownload = it.urlJunDownload
                        item.avatar = it.avatar
                        song.itemSongs!!.add(item)
                    }
                }

                return song
            }
        })
    }
}
