package com.ducnd.realmmvp.remote.interact.source

import android.graphics.Bitmap
import com.ducnd.realmmvp.utils.action.Action1
import io.reactivex.disposables.Disposable
import io.realm.RealmObject

/**
 * Created by ducnd on 8/9/17.
 */

interface IBaseInteraction<DataCommon> {
    fun getData(): DataCommon?

    fun saveMediaInfoAtFreeThread(bitmap: Bitmap, localFolderMedia: String, linkImage: String): Disposable

    fun <T : RealmObject> findItemAtMainThread(clazz: Class<T>, fieldName: String, valueCondition: String): T?

    fun <T> register(clazz: Class<T>, actionBus: Action1<T>)
    fun <T> post(clazz: Class<T>, t: T)
    fun <T> unregister(clazz: Class<T>, actionBus: Action1<T>)
    fun <T> registerList(clazz: Class<T>, actionBus: Action1<MutableList<T>>)
    fun <T> postList(clazz: Class<T>, list: MutableList<T>)
    fun <T> unregisterList(clazz: Class<T>, actionBus: Action1<MutableList<T>>)
}
