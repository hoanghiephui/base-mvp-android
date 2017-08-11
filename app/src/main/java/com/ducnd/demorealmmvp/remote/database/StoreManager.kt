package com.ducnd.demorealmmvp.remote.database

import android.content.Context
import com.ducnd.demorealmmvp.remote.model.ItemSong

import com.ducnd.realmmvp.remote.database.BaseStoreManager
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject

/**
 * Created by ducnd on 8/11/17.
 */

class StoreManager(context: Context) : BaseStoreManager(context, NAME_SCHEMA, VERSION_SCHEMA) {

    companion object {
        private val NAME_SCHEMA: String = "music.realm"
        private val VERSION_SCHEMA: Long = 2
    }
}
