package com.ducnd.demorealmmvp.ui.main

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.ducnd.demorealmmvp.R
import com.ducnd.demorealmmvp.remote.database.StoreManager
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.demorealmmvp.ui.base.activity.BaseMvpActivityM
import com.ducnd.realmmvp.ui.customview.ImageViewLocal
import com.ducnd.realmmvp.utils.action.Action1
import io.reactivex.disposables.Disposable
import io.realm.RealmList



class MainActivity : BaseMvpActivityM<IMain.Presenter>(), IMain.View, TextWatcher, SongAdapter.ISongAdapter {
    private var dispose: Disposable? = null
    private var edtName: EditText? = null
    private var rcSong: RecyclerView? = null
    private lateinit var mAdapter: SongAdapter
    private var mItemSong: MutableList<ItemSong>? = null

    override val layoutMain: Int
        get() = R.layout.activity_main

    override fun findViewByIds() {
        edtName = findViewById<View>(R.id.edt_name) as EditText
        rcSong = findViewById<View>(R.id.rc_song) as RecyclerView

        edtName!!.addTextChangedListener(this)
    }

    override fun setEvents() {
    }

    override fun initComponents() {
        mAdapter = SongAdapter(this)
        val manager: LinearLayoutManager = LinearLayoutManager(this);
        rcSong!!.layoutManager = manager
        rcSong!!.adapter = mAdapter
        mPresenter = MainPresenter(this, getAccountInteractor())
    }

    override fun afterTextChanged(p0: Editable?) {
        if (dispose != null) {
            dispose!!.dispose()
        }
        val name: String = edtName!!.text.toString().trim()
        if (name == "") {
            mItemSong = null
            mAdapter.notifyDataSetChanged()
            return
        }
        dispose = mPresenter!!.getItemSong(name, object : Action1<MutableList<ItemSong>> {
            override fun call(data: MutableList<ItemSong>) {
                val song: SongSearchResult = SongSearchResult()
                song.itemSongs = RealmList<ItemSong>()
                song.itemSongs!!.addAll(data.asIterable())
                song.nameSearch = edtName!!.editableText.toString().trim()
                song.id = edtName!!.editableText.toString().trim()
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
            val song: SongSearchResult? = mPresenter!!.getItemSongLocal(edtName!!.text.toString().trim())
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
