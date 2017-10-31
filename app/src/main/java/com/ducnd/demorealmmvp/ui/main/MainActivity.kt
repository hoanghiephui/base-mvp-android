package com.ducnd.demorealmmvp.ui.main

import android.util.Log
import com.ducnd.demorealmmvp.R
import com.ducnd.demorealmmvp.remote.model.ItemSong
import com.ducnd.demorealmmvp.ui.base.activity.BaseMvpActivityM
import com.ducnd.demorealmmvp.ui.main.searchsong.IMain
import com.ducnd.demorealmmvp.ui.utils.OpenFragmentUtils
import com.ducnd.realmmvp.utils.action.Action1


class MainActivity : BaseMvpActivityM<IMain.Presenter>(), IMain.View, Action1<MutableList<ItemSong>> {


    override fun getLayoutMain(): Int = R.layout.activity_main

    override fun findViewByIds() {
    }

    override fun initComponents() {
        OpenFragmentUtils.openFirstSearchSongFragment(supportFragmentManager)
        getAccountInteractor().registerList(ItemSong::class.java, this)
    }

    override fun setEvents() {
    }

    override fun call(data: MutableList<ItemSong>) {
        Log.d("MainActivity", "call..............")
    }

    override fun onDestroyControl() {
        getAccountInteractor().unregisterList(ItemSong::class.java, this)
        super.onDestroyControl()
    }

}
