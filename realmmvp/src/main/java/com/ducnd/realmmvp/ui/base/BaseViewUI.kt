package com.ducnd.realmmvp.ui.base

/**
 * Created by ducnd on 8/9/17.
 */

interface BaseViewUI {
    fun getLayoutMain(): Int

    fun findViewByIds()

    fun initComponents()

    fun setEvents()

    fun onBackRoot()

    fun showProgress()

    fun hideProgress()

    fun showMessage(message: String)

    fun showMessage(messageId: Int)

    val isDestroyView: Boolean

    fun onResumeControl()

    fun onPauseControl()

    fun hideKeyBoard(): Boolean

}
