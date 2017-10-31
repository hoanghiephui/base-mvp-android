package com.ducnd.demorealmmvp.remote.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.*

/**
 * Created by ducnd on 8/11/17.
 */
@RealmClass
open class MediaInfo : RealmObject() {
    companion object {
        val VIDEO = "video"
        val IMAGE = "image"
        val ID = "id"
        val LINK_IMAGE = "linkImage"
        val FOLDER = "folderName"
        val PATH_LOCAL = "pathLocal"
        val LAST_MOTIFIER_MEDIA = "lastModifierMedia"
    }

    @PrimaryKey
    var id: String? = null

    @Required
    var linkImage: String? = null

    @Required
    var folderName: String? = null

    @Required
    var pathLocal: String? = null


    @Required
    var mediaType: String? = null

    var width: Int = 0

    var height: Int = 0

    var isSaveFinish: Boolean = false

    @Index
    var lastModifierMedia: Date? = null
}