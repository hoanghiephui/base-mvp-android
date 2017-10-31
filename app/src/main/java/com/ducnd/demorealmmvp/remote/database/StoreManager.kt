package com.ducnd.demorealmmvp.remote.database

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.ducnd.demorealmmvp.remote.model.MediaInfo
import com.ducnd.demorealmmvp.remote.model.SongSearchResult
import com.ducnd.realmmvp.remote.database.BaseStoreManager
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.realm.Realm
import io.realm.Sort
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

@Suppress("DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES")
/**
 * Created by ducnd on 8/11/17.
 */

class StoreManager(context: Context) : BaseStoreManager(context, NAME_SCHEMA, VERSION_SCHEMA), IStoreManager {

    companion object {
        private val NAME_SCHEMA: String = "music.realm"
        private val VERSION_SCHEMA: Long = 1
        private val LIMIT_COUNT_MEDIA: Int = 400
        val FOLDER_NAME_IMAGE: String = "image"
        private val LIMIT_COUNT_SONG_SEARCH_RESULT: Int = 100
    }

    /**
     * save bitmap to file
     * @param bitmap bitmap source
     * @param localFolderMedia folder name
     * @param linkImage link media
     * @return return Disposable loading
     */
    override fun saveMediaInfoAtFreeThread(bitmap: Bitmap, localFolderMedia: String, linkImage: String): Disposable {
        return Observable.create(ObservableOnSubscribe<String> {
            val mediaInfo = MediaInfo()
            mediaInfo.id = localFolderMedia + "@" + linkImage
            mediaInfo.lastModifierMedia = Date()
            val format: Bitmap.CompressFormat
            if (bitmap.config != null) {
                when (bitmap.config) {
                    Bitmap.Config.ARGB_8888, Bitmap.Config.ARGB_4444 -> {
                        format = Bitmap.CompressFormat.JPEG
                    }
                    else -> {
                        format = Bitmap.CompressFormat.JPEG
                    }
                }
            } else {
                format = Bitmap.CompressFormat.JPEG
            }
            if (format == Bitmap.CompressFormat.PNG) {
                mediaInfo.pathLocal = Environment.getDataDirectory().path + File.separator + "data" + File.separator + mPackageName + File.separator + localFolderMedia + File.separator + mediaInfo.lastModifierMedia!!.time.toString() + "IMG.PNG"
            } else {
                mediaInfo.pathLocal = Environment.getDataDirectory().path + File.separator + "data" + File.separator + mPackageName + File.separator + localFolderMedia + File.separator + mediaInfo.lastModifierMedia!!.time.toString() + "IMG.JPG"
            }
            mediaInfo.width = bitmap.width
            mediaInfo.height = bitmap.height
            mediaInfo.linkImage = linkImage
            mediaInfo.folderName = localFolderMedia
            mediaInfo.mediaType = MediaInfo.IMAGE
            mediaInfo.isSaveFinish = false
            saveAtFreeThread(mediaInfo)

            try {
                val file = File(mediaInfo.pathLocal)
                file.parentFile.mkdir()
                val out: OutputStream = FileOutputStream(file)
                bitmap.compress(format, 100, out)
                out.close()
                mediaInfo.isSaveFinish = true
                saveAtFreeThread(mediaInfo)
            } catch (ex: IOException) {

            }

        }).subscribeOn(ManagerProcess.schedulerLoadMedia)
                .subscribe({

                }, {

                })
    }

    /**
     * optimize store= delete, update.....
     */
    override fun optimizeStore() {
        Observable.create(ObservableOnSubscribe<String> {
            val realm: Realm = Realm.getDefaultInstance()
            //media
            val count = realm.where(MediaInfo::class.java).equalTo(MediaInfo.FOLDER, FOLDER_NAME_IMAGE).count()
            if (count > LIMIT_COUNT_MEDIA) {
                val result = realm.where(MediaInfo::class.java).equalTo(MediaInfo.FOLDER, FOLDER_NAME_IMAGE).findAllSorted(MediaInfo.LAST_MOTIFIER_MEDIA, Sort.DESCENDING)
                val date = result.get(LIMIT_COUNT_MEDIA).lastModifierMedia
                val resultDelete = result.where().lessThanOrEqualTo(MediaInfo.LAST_MOTIFIER_MEDIA, date).findAll()
                if (resultDelete.size > 0) {
                    for (mediaInfo in resultDelete) {
                        File(mediaInfo.pathLocal).delete()
                    }
                    realm.beginTransaction()
                    resultDelete.deleteAllFromRealm()
                    realm.commitTransaction()
                }

            }
            val countSongResult = realm.where(SongSearchResult::class.java).count()
            if (countSongResult > LIMIT_COUNT_SONG_SEARCH_RESULT) {
                val result = realm.where(SongSearchResult::class.java).findAllSorted(SongSearchResult.DATE_CREATE, Sort.DESCENDING)
                val date = result.get(LIMIT_COUNT_MEDIA).dateCreate
                val resultDelete = result.where().lessThanOrEqualTo(SongSearchResult.DATE_CREATE, date).findAll()
                if (resultDelete.size > 0) {
                    realm.beginTransaction()
                    resultDelete.deleteAllFromRealm()
                    realm.commitTransaction()
                }
            }
            realm.close()

        }).subscribeOn(ManagerProcess.schedulerInteractServer)
                .subscribe()
    }
}
