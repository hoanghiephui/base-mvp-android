package com.ducnd.demorealmmvp.ui.main.searchsong

import com.ducnd.demorealmmvp.remote.interact.interf.IAccountInteraction
import com.ducnd.demorealmmvp.remote.interact.main.BasePresenterM
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.demorealmmvp.ui.main.searchsong.IMain
import com.ducnd.realmmvp.utils.action.Action1
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/10/17.
 */

class MainPresenter(view: IMain.View, accountInteraction: IAccountInteraction) : BasePresenterM<IMain.View>(view, accountInteraction), IMain.Presenter {

    override fun getItemSong(name: String, onNext: (MutableList<ItemSong>) -> Unit, onError: (Throwable) -> Unit): Disposable {
        return subscribeResuleDispose(mAccountInteraction.getListSong(name), onNext, onError)
    }

    override fun getItemSongLocal(name: String): SongSearchResult? {
        return mAccountInteraction.getSongSearchResultAtFreedThread(name)
    }

    override fun saveLocalSongResultSearch(songSearchResult: SongSearchResult) {
        mAccountInteraction.saveSongSearchResult(songSearchResult)
    }
}
