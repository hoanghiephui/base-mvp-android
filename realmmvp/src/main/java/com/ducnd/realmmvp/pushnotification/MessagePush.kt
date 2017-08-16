package com.ducnd.realmmvp.pushnotification

import android.app.Activity
import android.os.Bundle

/**
 * Created by ducnd on 8/16/17.
 */
class MessagePush {
    val notificationID: Int
    val title: String
    val content: String
    val data: Bundle
    val linkImage: String?
    val resSmallIcon: Int
    val resLargeIcon: Int
    val isHideProvide: Boolean
    val clazzOpenNotitication: Class<out Activity>

    constructor(notificationID: Int, clazzOpenNotitication: Class<out Activity>, title: String, content: String, data: Bundle, linkImage: String, resLargeIcon: Int, resSmallIcon: Int, isHideProvide: Boolean) {
        this.notificationID = notificationID
        this.title = title
        this.content = content
        this.linkImage = linkImage
        this.data = data
        this.resSmallIcon = resSmallIcon
        this.resLargeIcon = resLargeIcon
        this.isHideProvide = isHideProvide
        this.clazzOpenNotitication = clazzOpenNotitication
    }

    constructor(notificationID: Int, clazzOpenNotitication: Class<out Activity>, title: String, content: String, data: Bundle, resLargeIcon: Int, resSmallIcon: Int, isHideProvide: Boolean) {
        this.notificationID = notificationID
        this.title = title
        this.content = content
        this.resLargeIcon = resLargeIcon
        this.data = data
        this.resSmallIcon = resSmallIcon
        linkImage = null
        this.isHideProvide = isHideProvide
        this.clazzOpenNotitication = clazzOpenNotitication
    }


}