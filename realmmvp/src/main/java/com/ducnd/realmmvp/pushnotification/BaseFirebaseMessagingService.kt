package com.ducnd.realmmvp.pushnotification

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.JsonParseException

import java.io.IOException
import android.os.Build
import android.media.RingtoneManager
import android.content.Intent
import android.graphics.*
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.ducnd.realmmvp.R
import com.ducnd.realmmvp.ui.customview.GlideApp
import android.app.PendingIntent
import android.app.NotificationManager
import android.graphics.drawable.Drawable
import com.ducnd.realmmvp.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers


/**
 * Created by ducnd on 8/16/17.
 */

abstract class BaseFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(BaseFirebaseMessagingService::class.java.simpleName, "onMessageReceived.........111")
        if (remoteMessage != null && remoteMessage.data != null && remoteMessage.data.size > 0) {
            try {
                Log.d(BaseFirebaseMessagingService::class.java.simpleName, "onMessageReceived.........")
                val messagePush = getMessagePushNotification(remoteMessage)
                makeNotification(messagePush)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: JsonParseException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(NullPointerException::class, JsonParseException::class, IOException::class)
    protected abstract fun getMessagePushNotification(remoteMessage: RemoteMessage): MessagePush

    protected fun makeNotification(messagePush: MessagePush) {
        val builderNotifaction = NotificationCompat.Builder(this, messagePush.notificationID.toString())
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builderNotifaction.setSound(uri)
        builderNotifaction.setVibrate(longArrayOf(500L, 200L, 200L, 500L))
        builderNotifaction.priority = NotificationCompat.PRIORITY_HIGH
        if (messagePush.isHideProvide && Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            builderNotifaction.setVisibility(Notification.VISIBILITY_PUBLIC)
        }
        builderNotifaction.setSmallIcon(messagePush.resSmallIcon)
        builderNotifaction.setContentTitle(messagePush.title)
        builderNotifaction.setContentText(messagePush.content)
        val resultIntent = Intent()
        resultIntent.setClass(this, messagePush.clazzOpenNotitication)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        messagePush.data.putInt(Constants.KEY_ID, messagePush.notificationID)
        resultIntent.putExtras(messagePush.data)
        if (messagePush.linkImage != null) {
            Observable.just("dd")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        GlideApp.with(this)
                                .asBitmap()
                                .load(messagePush.linkImage)
                                .override(300)
                                .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                                        val bm = createBitmapNotification(resource)
                                        builderNotifaction.setLargeIcon(bm)
                                        val resultPendingIntent = PendingIntent.getActivity(applicationContext, 0,
                                                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                                        builderNotifaction.setContentIntent(resultPendingIntent)
                                        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                        notificationManager.notify(messagePush.notificationID, builderNotifaction.build())
                                    }

                                    override fun onLoadFailed(errorDrawable: Drawable?) {
                                        builderNotifaction.setLargeIcon(BitmapFactory.decodeResource(resources, messagePush.resLargeIcon))
                                        val resultPendingIntent = PendingIntent.getActivity(applicationContext, 0,
                                                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                                        builderNotifaction.setContentIntent(resultPendingIntent)
                                        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                        notificationManager.notify(messagePush.notificationID, builderNotifaction.build())
                                    }
                                })
                    })

        } else {
            builderNotifaction.setLargeIcon(BitmapFactory.decodeResource(resources, messagePush.resLargeIcon))
            val resultPendingIntent = PendingIntent.getActivity(applicationContext, 0,
                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            builderNotifaction.setContentIntent(resultPendingIntent)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(messagePush.notificationID, builderNotifaction.build())
        }

    }

    private fun createBitmapNotification(source: Bitmap): Bitmap {
        val size = getResources().getDimensionPixelSize(R.dimen.size_avatar_notification)
        val mBitmapShader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val mBitmapPaint = Paint()
        mBitmapPaint.setAntiAlias(true)
        mBitmapPaint.setShader(mBitmapShader)
        val mBitmapHeight = source.height
        val mBitmapWidth = source.width
        val scale: Float
        var dx = 0f
        var dy = 0f
        if (mBitmapWidth * size > size * mBitmapHeight) {
            scale = size / mBitmapHeight.toFloat()
            dx = (size - mBitmapWidth * scale) * 0.5f
        } else {
            scale = size / mBitmapWidth.toFloat()
            dy = (size - mBitmapHeight * scale) * 0.5f
        }
        val matrix = Matrix()
        matrix.setScale(scale, scale)
        matrix.postTranslate(dx + 0.5f, dy + 0.5f)
        mBitmapShader.setLocalMatrix(matrix)
        val bm = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        canvas.drawCircle(size.toFloat() / 2, size.toFloat() / 2, size.toFloat() / 2, mBitmapPaint)
        source.recycle()
        return bm
    }
}
