package com.ducnd.realmmvp.ui.base

/**
 * Created by ducnd on 8/9/17.
 */

interface BaseViewUI {
    val layoutMain: Int

    fun findViewByIds()

    fun setEvents()

    fun initComponents()

    fun onBackRoot()

    fun showProgress()

    fun hideProgress()

    fun showMessage(message: String)

    fun showMessage(messageId: Int)

    val isDestroyView: Boolean

    fun onResumeControl()

    fun onPauseControl()

}
