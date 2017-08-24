package com.ducnd.demorealmmvp.ui.base.activity

import android.graphics.Bitmap
import android.support.annotation.MainThread
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ducnd.demorealmmvp.common.ExApplication
import com.ducnd.demorealmmvp.remote.interact.interf.IAccountInteraction
import com.ducnd.demorealmmvp.remote.interact.interf.IBasePresenterM
import com.ducnd.demorealmmvp.remote.model.MediaInfo
import com.ducnd.realmmvp.ui.base.activity.BaseMvpActivity
import com.ducnd.realmmvp.ui.customview.GlideApp
import com.ducnd.realmmvp.ui.customview.ImageViewLocal
import java.io.File

/**
 * Created by ducnd on 8/16/17.
 */

abstract class BaseMvpActivityM<Presenter : IBasePresenterM> : BaseMvpActivity<Presenter>() {
    @MainThread
    fun loadAndSaveImage(localFolderMedia: String, linkImage: String, ivImg: ImageViewLocal, resHolder: Int, targetSize: Int) {
        ivImg.destroyDisposable()
        val mediaInfo: MediaInfo? = mPresenter!!.findImageMediaInfoAtMainThread(MediaInfo.LINK_IMAGE, linkImage)
        if (mediaInfo != null) {
            if (mediaInfo.isSaveFinish) {
                if (File(mediaInfo.pathLocal).exists()) {
                    GlideApp.with(ivImg)
                            .load(File(mediaInfo.pathLocal))
                            .placeholder(resHolder)
                            .error(resHolder)
                            .centerCrop()
                            .override(targetSize)
                            .into(ivImg)
                    return
                }
            } else {
                if (File(mediaInfo.pathLocal).exists()) {
                    File(mediaInfo.pathLocal).delete()
                }
                mPresenter!!.deleteRealmObject(mediaInfo)
            }
        }

        GlideApp.with(ivImg)
                .asBitmap()
                .load(linkImage)
                .placeholder(resHolder)
                .error(resHolder)
                .centerCrop()
                .override(targetSize)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean =
                            true

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        if (resource != null) {
                            ivImg.setDisposable(mPresenter!!.saveMediaInfoAtFreeThread(resource, localFolderMedia, linkImage))
                        }
                        ivImg.setImageBitmap(resource)
                        return true
                    }
                })
                .into(ivImg)
    }

    protected fun getAccountInteractor(): IAccountInteraction {
        return (applicationContext as ExApplication).getComponent()!!.accountCount
    }
}
