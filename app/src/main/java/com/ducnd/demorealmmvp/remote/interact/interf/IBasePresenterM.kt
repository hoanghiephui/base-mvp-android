package com.ducnd.demorealmmvp.remote.interact.interf

import com.ducnd.demorealmmvp.remote.model.MediaInfo
import com.ducnd.realmmvp.remote.interact.source.IBasePresenter
import io.realm.RealmObject

/**
 * Created by ducnd on 8/15/17.
 */

interface IBasePresenterM : IBasePresenter {
    fun findImageMediaInfoAtMainThread(fieldName: String, valueCondition: String): MediaInfo?
    fun <T : RealmObject> deleteRealmObject(realmObject: T): Boolean
}
