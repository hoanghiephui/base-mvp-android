package com.ducnd.demorealmmvp.ui.main.searchsong

import com.ducnd.demorealmmvp.remote.interact.interf.IBasePresenterM
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.realmmvp.ui.base.BaseViewUI
import com.ducnd.realmmvp.ui.base.activity.ViewActivity
import com.ducnd.realmmvp.utils.action.Action1
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/10/17.
 */

interface IMain {
    interface Presenter : IBasePresenterM {
        fun getItemSong(name: String, onNext: (MutableList<ItemSong>) -> Unit, onError: (Throwable) -> Unit): Disposable
        fun getItemSongLocal(name: String): SongSearchResult?
        fun saveLocalSongResultSearch(songSearchResult: SongSearchResult)
    }

    interface View : BaseViewUI
}
