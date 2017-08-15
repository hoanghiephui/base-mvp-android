package com.ducnd.demorealmmvp.ui.main

import com.ducnd.demorealmmvp.remote.interact.interf.IBasePresenterM
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.realmmvp.ui.base.activity.ViewActivity
import com.ducnd.realmmvp.utils.Action1
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/10/17.
 */

interface IMain {
    interface Presenter : IBasePresenterM {
        fun getItemSong(name: String, onNext: Action1<MutableList<ItemSong>>, onError: Action1<Throwable>): Disposable
        fun getItemSongLocal(name: String): SongSearchResult?
        fun saveLocalSongResultSearch(songSearchResult: SongSearchResult)
    }

    interface View : ViewActivity
}
