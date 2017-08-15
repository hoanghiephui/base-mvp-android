package com.ducnd.demorealmmvp.remote.database

import android.graphics.Bitmap
import com.ducnd.realmmvp.remote.database.IBaseStoreManager
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/11/17.
 */

interface IStoreManager : IBaseStoreManager{
    fun saveMediaInfoAtFreeThread(bitmap: Bitmap, localFolderMedia: String, linkImage: String): Disposable
}
