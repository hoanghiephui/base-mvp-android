package com.ducnd.realmmvp.remote.interact.source

import android.graphics.Bitmap
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/9/17.
 */

interface IBasePresenter {
    fun saveMediaInfoAtFreeThread(bitmap: Bitmap, localFolderMedia: String, linkImage: String): Disposable
    fun onDestroy()
}
