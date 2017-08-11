package com.ducnd.realmmvp.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ducnd.realmmvp.ui.base.BaseViewUI

/**
 * Created by ducnd on 8/10/17.
 */

interface ViewFragment : BaseViewUI {
    fun onCreateViewControl(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
    fun onViewCreatedControl(view: View, savedInstanceState: Bundle?)
    fun onDestroyViewControl()
    fun reload(bundle: Bundle)
}
