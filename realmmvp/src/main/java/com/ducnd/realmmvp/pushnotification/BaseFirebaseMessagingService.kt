package com.ducnd.realmmvp.pushnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.bumptech.glide.request.target.SimpleTarget
import com.ducnd.realmmvp.R
import com.ducnd.realmmvp.ui.customview.GlideApp
import com.ducnd.realmmvp.utils.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.JsonParseException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.IOException


/**
 * Created by ducnd on 8/16/17.
 */

abstract class BaseFirebaseMessagingService : FirebaseMessagingService() {
    private var listIDChanel: MutableList<String>? = null
    override fun onCreate() {
        super.onCreate()
        listIDChanel = getActionIDChanelList()
        if (listIDChanel != null && listIDChanel!!.size > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (s in listIDChanel!!) {
                val chan1 = NotificationChannel(s, s, NotificationManager.IMPORTANCE_DEFAULT)
                chan1.lightColor = Color.GREEN
                chan1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(chan1)
            }
        }
    }

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

    protected abstract fun getActionIDChanelList(): MutableList<String>

    @Throws(NullPointerException::class, JsonParseException::class, IOException::class)
    protected abstract fun getMessagePushNotification(remoteMessage: RemoteMessage): MessagePush

    protected fun makeNotification(messagePush: MessagePush) {
        val builderNotifaction = NotificationCompat.Builder(this, messagePush.notificationChannel)
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
        builderNotifaction.setStyle(NotificationCompat.BigTextStyle()
                .bigText(messagePush.content))
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
                                    override fun onResourceReady(resource: Bitmap?, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                                        if (resource == null) {
                                            showNotificationNomal(builderNotifaction, messagePush, resultIntent)
                                        } else {
                                            val bm = createBitmapNotification(resource)
                                            builderNotifaction.setLargeIcon(bm)
                                            val resultPendingIntent = PendingIntent.getActivity(applicationContext, 0,
                                                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                                            builderNotifaction.setContentIntent(resultPendingIntent)
                                            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                            notificationManager.notify(messagePush.notificationID, builderNotifaction.build())
                                        }
                                    }

                                    override fun onLoadFailed(errorDrawable: Drawable?) {
                                        showNotificationNomal(builderNotifaction, messagePush, resultIntent)
                                    }
                                })
                    })

        } else {
            showNotificationNomal(builderNotifaction, messagePush, resultIntent)
        }

    }

    private fun showNotificationNomal(builderNotifaction: NotificationCompat.Builder, messagePush: MessagePush, resultIntent: Intent) {
        builderNotifaction.setLargeIcon(BitmapFactory.decodeResource(resources, messagePush.resLargeIcon))
        val resultPendingIntent = PendingIntent.getActivity(applicationContext, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builderNotifaction.setContentIntent(resultPendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(messagePush.notificationID, builderNotifaction.build())
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
