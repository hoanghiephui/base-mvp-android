package com.ducnd.demorealmmvp.remote.interact.main

import android.graphics.Bitmap
import com.ducnd.demorealmmvp.remote.interact.interf.IAccountInteraction
import com.ducnd.demorealmmvp.remote.interact.interf.IBasePresenterM
import com.ducnd.demorealmmvp.remote.model.MediaInfo
import com.ducnd.realmmvp.remote.interact.main.BasePresenter
import com.ducnd.realmmvp.ui.base.BaseViewUI
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/11/17.
 */

abstract class BasePresenterM<V : BaseViewUI>(view: V, protected var mAccountInteraction: IAccountInteraction) : BasePresenter<V>(view), IBasePresenterM {
    override fun saveMediaInfoAtFreeThread(bitmap: Bitmap, localFolderMedia: String, linkImage: String): Disposable {
        return mAccountInteraction.saveMediaInfoAtFreeThread(bitmap, localFolderMedia, linkImage)
    }

    override fun findImageMediaInfoAtMainThread(fieldName: String, valueCondition: String): MediaInfo? {
        return mAccountInteraction.findItemAtMainThread(MediaInfo::class.java, fieldName, valueCondition)
    }
}
