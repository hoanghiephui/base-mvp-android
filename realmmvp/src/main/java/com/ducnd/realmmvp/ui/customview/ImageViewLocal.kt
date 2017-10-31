package com.ducnd.realmmvp.ui.customview

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/15/17.
 */

class ImageViewLocal : AppCompatImageView {
    private var mDisposable: Disposable? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun destroyDisposable() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
            mDisposable = null
        }
    }

    fun setDisposable(disposable: Disposable) {
        mDisposable = disposable
    }

    override fun onDetachedFromWindow() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
        super.onDetachedFromWindow()
    }
}
