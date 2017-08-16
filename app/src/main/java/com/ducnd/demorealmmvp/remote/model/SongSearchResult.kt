package com.ducnd.demorealmmvp.remote.model

import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * Created by ducnd on 8/11/17.
 */
@RealmClass
open class SongSearchResult : RealmObject() {
    companion object {
        @JvmStatic
        fun convertToJSon(itemSongs: SongSearchResult): String {
            val json: JSONObject = JSONObject()
            val array: JSONArray = JSONArray()
            if (itemSongs.itemSongs != null) {
                itemSongs.itemSongs!!.forEach {
                    array.put(ItemSong.convertToJSon(it))
                }
            }
            json.put("itemSongs", array)
            json.put("Id", itemSongs.id)
            json.put("nameSearch", itemSongs.nameSearch)
            val content: String = json.toString()
            return content
        }


        val NAME_SEARCH: String = "nameSearch"
        val DATE_CREATE: String = "dateCreate"
    }

    @SerializedName("Id")
    @PrimaryKey
    var id: String? = null

    @SerializedName("nameSearch")
    var nameSearch: String? = null

    @SerializedName("itemSongs")
    var itemSongs: RealmList<ItemSong>? = null

    @Index
    var dateCreate: Date? = null
}
