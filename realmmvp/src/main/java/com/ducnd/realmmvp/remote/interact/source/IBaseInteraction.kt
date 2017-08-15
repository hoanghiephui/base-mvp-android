package com.ducnd.realmmvp.remote.interact.source

import android.graphics.Bitmap
import io.reactivex.disposables.Disposable
import io.realm.RealmObject

/**
 * Created by ducnd on 8/9/17.
 */

interface IBaseInteraction<DataCommon> {
    fun getData(): DataCommon?

    fun saveMediaInfoAtFreeThread(bitmap: Bitmap, localFolderMedia: String, linkImage: String): Disposable

    fun <T : RealmObject> findItemAtMainThread(clazz: Class<T>, fieldName: String, valueCondition: String): T?
}
