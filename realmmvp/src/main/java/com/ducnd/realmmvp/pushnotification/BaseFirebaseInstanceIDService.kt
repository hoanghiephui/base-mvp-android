package com.ducnd.realmmvp.pushnotification

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by ducnd on 8/16/17.
 */

abstract class BaseFirebaseInstanceIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(BaseFirebaseInstanceIDService::class.java.simpleName, "onTokenRefresh token: " + refreshedToken)
        sendRegistrationToServer(refreshedToken)
    }

    protected abstract fun sendRegistrationToServer(token: String?)
}
