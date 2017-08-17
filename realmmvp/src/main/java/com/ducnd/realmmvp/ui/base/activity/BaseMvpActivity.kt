package com.ducnd.realmmvp.ui.base.activity

import com.ducnd.realmmvp.remote.interact.source.IBasePresenter

/**
 * Created by ducnd on 8/10/17.
 */

abstract class BaseMvpActivity<Presenter : IBasePresenter> : BaseActivity() {
    var mPresenter: Presenter? = null
        protected set

    override fun onDestroyControl() {
        if (mPresenter != null) {
            mPresenter!!.onDestroy()
        }
        super.onDestroyControl()
    }
}
