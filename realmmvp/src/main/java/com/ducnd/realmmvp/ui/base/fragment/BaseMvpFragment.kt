package com.ducnd.realmmvp.ui.base.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ducnd.realmmvp.remote.interact.source.IBasePresenter
import java.util.*
import com.ducnd.realmmvp.utils.Action1
import com.ducnd.realmmvp.utils.Constants


/**
 * Created by ducnd on 8/10/17.
 */

abstract class BaseMvpFragment<Presenter : IBasePresenter> : BaseFragment() {
    protected var mPresenter: Presenter? = null
    private var mFirstLoad: Long = 0


    override fun onCreateViewControl(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mFirstLoad = Date().getTime()
        return super.onCreateViewControl(inflater, container, savedInstanceState)
    }

    protected fun <T> finishLoad(t: T, action: Action1<T>) {
        if (mIsDestroyView) {
            return
        }
        if (mFirstLoad === -1.toLong()) {
            action.call(t)
        } else {
            val currentTime = Date().time
            if (currentTime - mFirstLoad >= Constants.DURATION_ANIMATION) {
                action.call(t)
            } else {
                Handler().postDelayed({
                    if (mIsDestroyView) {
                        return@postDelayed
                    }
                    action.call(t)
                }, Constants.DURATION_ANIMATION - (currentTime - mFirstLoad))
            }
            mFirstLoad = -1
        }
    }


    override fun onDestroyViewControl() {
        mPresenter!!.onDestroy()
        super.onDestroyViewControl()
    }
}
