package com.ducnd.realmmvp.ui.base.activity

import android.os.Bundle
import android.view.View

import com.ducnd.realmmvp.ui.base.BaseViewUI
import com.ducnd.realmmvp.ui.base.fragment.BaseFragment

/**
 * Created by ducnd on 8/10/17.
 */

interface ViewActivity : BaseViewUI {
    fun onCreateControl(savedInstanceState: Bundle?)
    fun onDestroyControl()
    fun findFragmentByTag(tag: String): BaseFragment
    fun setViewRoot(viewRoot: View)
    fun onBackParent()
    fun onStartControl()
    fun onStopControl()

}
