package com.ducnd.demorealmmvp.ui.utils

import android.support.v4.app.FragmentManager
import com.ducnd.demorealmmvp.ui.main.searchsong.SongSearchSongFragment
import com.ducnd.demorealmmvp.R
import com.ducnd.realmmvp.ui.base.fragment.BaseFragment

/**
 * Created by ducnd on 9/4/17.
 */
class OpenFragmentUtils{
    companion object {
        @JvmStatic
        fun openFirstSearchSongFragment(manager:FragmentManager){
            val transaction = manager.beginTransaction()
            BaseFragment.openFragment(manager, transaction, SongSearchSongFragment::class.java, null, false, true, null, R.id.content)
        }
    }
}