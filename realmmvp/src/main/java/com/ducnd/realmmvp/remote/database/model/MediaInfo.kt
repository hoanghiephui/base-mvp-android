package com.ducnd.realmmvp.remote.database.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

/**
 * Created by ducnd on 8/11/17.
 */
@RealmClass
open class MediaInfo : RealmObject() {
    companion object {
        val VIDEO = "video"
        val IMAGE = "image"
    }

    @PrimaryKey
    var id: String? = null

    @Required
    var linkImage: String? = null

    var totalLength: Int = 0

    var currentLengthDownload: Int = 0

    @Required
    var folderName: String? = null

    @Required
    var pathLocal: String? = null


    @Required
    var mediaType: String? = null
}