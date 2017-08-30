package com.ducnd.demorealmmvp.ui.main

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.ducnd.demorealmmvp.R
import com.ducnd.demorealmmvp.remote.database.StoreManager
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.demorealmmvp.ui.base.activity.BaseMvpActivityM
import com.ducnd.realmmvp.ui.customview.ImageViewLocal
import com.ducnd.realmmvp.utils.action.Action1
import io.reactivex.disposables.Disposable
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivityM<IMain.Presenter>(), IMain.View, TextWatcher, SongAdapter.ISongAdapter {
    private var dispose: Disposable? = null
    private lateinit var mAdapter: SongAdapter
    private var mItemSong: MutableList<ItemSong>? = null

    override fun getLayoutMain(): Int = R.layout.activity_main

    override fun findViewByIds() {
        edt_name.addTextChangedListener(this)
    }

    override fun setEvents() {
    }

    override fun initComponents() {
        mAdapter = SongAdapter(this)
        val manager = LinearLayoutManager(this);
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
        dispose = mPresenter!!.getItemSong(name, object : Action1<MutableList<ItemSong>> {
            override fun call(data: MutableList<ItemSong>) {
                val song = SongSearchResult()
                song.itemSongs = RealmList<ItemSong>()
                song.itemSongs!!.addAll(data.asIterable())
                song.nameSearch = edt_name.editableText.toString().trim()
                song.id = edt_name.editableText.toString().trim()
                mPresenter!!.saveLocalSongResultSearch(song)
                mItemSong = data
                mAdapter.notifyDataSetChanged()
            }
        }, object : Action1<Throwable> {
            override fun call(data: Throwable) {
                errorSearch(data)
            }
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
        loadAndSaveImage(StoreManager.FOLDER_NAME_IMAGE, link, ivImg, R.drawable.zing, 500)
    }


    override fun onDestroyControl() {
        if (dispose != null) {
            dispose!!.dispose()
        }
        super.onDestroyControl()
    }
}
