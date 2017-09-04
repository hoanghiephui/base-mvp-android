package com.ducnd.demorealmmvp.ui.main

import com.ducnd.demorealmmvp.R
import com.ducnd.demorealmmvp.ui.base.activity.BaseMvpActivityM
import com.ducnd.demorealmmvp.ui.main.searchsong.IMain
import com.ducnd.demorealmmvp.ui.utils.OpenFragmentUtils


class MainActivity : BaseMvpActivityM<IMain.Presenter>(), IMain.View {

    override fun getLayoutMain(): Int = R.layout.activity_main

    override fun findViewByIds() {
    }

    override fun initComponents() {
        OpenFragmentUtils.openFirstSearchSongFragment(supportFragmentManager)
    }

    override fun setEvents() {
    }


}
