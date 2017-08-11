package com.ducnd.demorealmmvp.remote.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.json.JSONArray
import org.json.JSONObject

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
            Log.d("SongSearchResult", "content: " + content)
            return content
        }

        val NAME_SERCH: String = "nameSearch"
    }

    @SerializedName("Id")
    @PrimaryKey
    var id: String? = null

    @SerializedName("nameSearch")
    var nameSearch: String? = null

    @SerializedName("itemSongs")
    var itemSongs: RealmList<ItemSong>? = null
}
