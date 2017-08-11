package com.ducnd.demorealmmvp.remote.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.json.JSONObject

/**
 * Created by ducnd on 8/11/17.
 */
@RealmClass
open class ItemSong : RealmObject() {
    companion object {
        @JvmStatic
        fun convertToJSon(itemSong: ItemSong): String {
            val json: JSONObject = JSONObject()
            json.put("Id", itemSong.id)
            json.put("Avatar", itemSong.avatar)
            json.put("UrlJunDownload", itemSong.urlJunDownload)
            json.put("LyricsUrl", itemSong.lyricsUrl)
            json.put("UrlSource", itemSong.urlSource)
            json.put("SiteId", itemSong.siteId)
            json.put("Artist", itemSong.artist)
            return json.toString()
        }
    }


    @PrimaryKey
    @SerializedName("Id")
    var id: String? = null

    @SerializedName("Title")
    var title: String? = null

    @SerializedName("Avatar")
    var avatar: String? = null

    @SerializedName("UrlJunDownload")
    var urlJunDownload: String? = null

    @SerializedName("LyricsUrl")
    var lyricsUrl: String? = null

    @SerializedName("UrlSource")
    var urlSource: String? = null

    @SerializedName("SiteId")
    var siteId: String? = null

    @SerializedName("Artist")
    var artist: String? = null


}
