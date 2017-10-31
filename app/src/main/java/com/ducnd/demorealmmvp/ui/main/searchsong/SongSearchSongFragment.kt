package com.ducnd.demorealmmvp.ui.main.searchsong

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.ducnd.demorealmmvp.remote.database.StoreManager
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.demorealmmvp.ui.base.fragment.BaseMvpFragmentM
import com.ducnd.demorealmmvp.R
import com.ducnd.realmmvp.ui.customview.ImageViewLocal
import io.reactivex.disposables.Disposable
import io.realm.RealmList
import kotlinx.android.synthetic.main.fragment_song_search_song.*

/**
 * Created by ducnd on 9/4/17.
 */

class SongSearchSongFragment : BaseMvpFragmentM<IMain.Presenter>(), IMain.View, TextWatcher, SongAdapter.ISongAdapter {
    private var dispose: Disposable? = null
    private lateinit var mAdapter: SongAdapter
    private var mItemSong: MutableList<ItemSong>? = null

    override fun getLayoutMain(): Int = R.layout.fragment_song_search_song


    override fun findViewByIds() {

    }

    override fun setEvents() {
        edt_name.addTextChangedListener(this)
    }

    override fun initComponents() {
        mAdapter = SongAdapter(this)
        val manager = LinearLayoutManager(getBaseActivity());
        rc_song.layoutManager = manager
        rc_song.adapter = mAdapter
        mPresenter = MainPresenter(this, getAccountInteractor())
    }

    override fun afterTextChanged(p0: Editable?) {
        if (dispose != null) {
            dispose!!.dispose()
        }
        val name: String = edt_name.text.toString().trim()
        if (name == "") {
            mItemSong = null
            mAdapter.notifyDataSetChanged()
            return
        }
        dispose = mPresenter!!.getItemSong(name, {
            val song = SongSearchResult()
            song.itemSongs = RealmList<ItemSong>()
            song.itemSongs!!.addAll(it.asIterable())
            song.nameSearch = edt_name.editableText.toString().trim()
            song.id = edt_name.editableText.toString().trim()
            mPresenter!!.saveLocalSongResultSearch(song)
            mItemSong = it
            mAdapter.notifyDataSetChanged()
        }, {
            errorSearch(it)
        })

    }

    private fun errorSearch(error: Throwable) {
        if (error.message != null) {
            val song: SongSearchResult? = mPresenter!!.getItemSongLocal(edt_name.text.toString().trim())
            if (song == null) {
                mItemSong = null
            } else {
                mItemSong = song.itemSongs
            }
            mAdapter.notifyDataSetChanged()
        }

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun getCount(): Int {
        if (mItemSong == null) {
            return 0
        }
        return mItemSong!!.size
    }

    override fun getData(position: Int): ItemSong {
        return mItemSong!!.get(position)
    }

    override fun loadImage(link: String, ivImg: ImageViewLocal) {
        loadAndSaveImage(StoreManager.FOLDER_NAME_IMAGE, link, ivImg, com.ducnd.demorealmmvp.R.drawable.zing, 500)
    }

    override fun onDestroyViewControl() {
        if (dispose != null) {
            dispose!!.dispose()
        }
        super.onDestroyViewControl()
    }
}
