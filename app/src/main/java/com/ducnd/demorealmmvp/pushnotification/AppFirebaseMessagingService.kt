package com.ducnd.demorealmmvp.pushnotification

import android.os.Bundle
import com.ducnd.demorealmmvp.ui.main.MainActivity
import com.ducnd.demorealmmvp.R
import com.ducnd.realmmvp.pushnotification.BaseFirebaseMessagingService
import com.ducnd.realmmvp.pushnotification.MessagePush
import com.ducnd.realmmvp.utils.Constants
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by ducnd on 8/16/17.
 */

class AppFirebaseMessagingService : BaseFirebaseMessagingService() {
    companion object {
        var count: Int = 0
    }

    override fun getMessagePushNotification(remoteMessage: RemoteMessage): MessagePush {
        var message: String? = remoteMessage.data.get("message")
        if (message == null) {
            message = "hello notification"
        }
        count++
        val push = MessagePush(count, MainActivity::class.java, "Notification", message, Bundle(), "https://pbs.twimg.com/profile_images/378800000862544758/kgl8noIY_400x400.jpeg",
                R.drawable.zing, R.drawable.ic_adb_black_24dp, true)
        return push
    }
}
