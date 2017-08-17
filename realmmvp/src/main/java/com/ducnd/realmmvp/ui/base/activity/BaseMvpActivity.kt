package com.ducnd.realmmvp.ui.base.activity

import android.graphics.Bitmap
import android.support.annotation.MainThread
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ducnd.realmmvp.remote.interact.source.IBasePresenter
import com.ducnd.realmmvp.ui.customview.GlideApp
import com.ducnd.realmmvp.ui.customview.ImageViewLocal
import java.io.File

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
