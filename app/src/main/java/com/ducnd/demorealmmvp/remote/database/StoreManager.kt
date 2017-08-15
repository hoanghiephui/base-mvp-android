package com.ducnd.demorealmmvp.remote.database

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.ducnd.demorealmmvp.remote.model.MediaInfo
import com.ducnd.realmmvp.remote.database.BaseStoreManager
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

/**
 * Created by ducnd on 8/11/17.
 */

class StoreManager(context: Context) : BaseStoreManager(context, NAME_SCHEMA, VERSION_SCHEMA), IStoreManager {

    companion object {
        private val NAME_SCHEMA: String = "music.realm"
        private val VERSION_SCHEMA: Long = 4
        private val TAG: String = StoreManager::class.java.simpleName
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
                mediaInfo.pathLocal = (Environment.getDataDirectory().path + File.separator + "data" + File.separator
                        + mPackageName + File.separator + localFolderMedia + File.separator + (mediaInfo.lastModifierMedia!!.time.toString() + "IMG.PNG"))
            } else {

                mediaInfo.pathLocal = (Environment.getDataDirectory().path + File.separator + "data" + File.separator
                        + mPackageName + File.separator + localFolderMedia + File.separator + (mediaInfo.lastModifierMedia!!.time.toString() + "IMG.JPG"))
                Log.d(TAG, "saveMediaInfoAtFreeThread path: " + mediaInfo.pathLocal)
            }
            mediaInfo.width = bitmap.width
            mediaInfo.height = bitmap.height
            mediaInfo.linkImage = linkImage
            mediaInfo.folderName = localFolderMedia
            mediaInfo.mediaType = MediaInfo.IMAGE
            mediaInfo.isSaveFinish = false
            saveAtFreeThread(mediaInfo)

            val file: File = File(mediaInfo.pathLocal)
            file.parentFile.mkdir()
            val out: OutputStream = FileOutputStream(file)
            bitmap.compress(format, 100, out)
            out.close()
            mediaInfo.isSaveFinish = true
            saveAtFreeThread(mediaInfo)
        }).subscribeOn(ManagerProcess.schedulerLoadMedia)
                .subscribe({

                }, {

                })
    }
}
