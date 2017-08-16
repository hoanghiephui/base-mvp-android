package com.ducnd.realmmvp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by ducnd on 8/8/17.
 */

abstract class MVPApplication<AppComponent> : MultiDexApplication(), Application.ActivityLifecycleCallbacks {

    companion object {
        var widthScreen: Int = 0
            protected set
        var heightScreen: Int = 0
            protected set
        var density: Float = 0.toFloat()
            protected set
    }

    protected var mComponent: AppComponent? = null
    protected lateinit var mActivitiesStarted: MutableList<Activity>

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    override fun onCreate() {
        super.onCreate()
        init()
        initAppComponent()
    }

    protected abstract fun initAppComponent()

    private fun init() {
        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(displayMetrics)
        widthScreen = displayMetrics.widthPixels
        heightScreen = displayMetrics.heightPixels
        density = displayMetrics.density

        mActivitiesStarted = mutableListOf()
        registerActivityLifecycleCallbacks(this)
    }

    fun getComponent(): AppComponent? {
        return mComponent
    }

    override fun onActivityPaused(act: Activity?) {

    }

    override fun onActivityResumed(act: Activity?) {

    }

    override fun onActivityStarted(act: Activity?) {
        if (act != null) {
            mActivitiesStarted.add(act)
        }
    }

    override fun onActivityDestroyed(act: Activity?) {

    }

    override fun onActivitySaveInstanceState(act: Activity?, bundle: Bundle?) {
    }

    override fun onActivityStopped(act: Activity?) {
        if (act != null) {
            mActivitiesStarted.remove(act)
            if (mActivitiesStarted.size == 0) {
                actionStopAllActivity()
            }
        }
    }

    override fun onActivityCreated(act: Activity?, bundle: Bundle?) {

    }

    abstract fun actionStopAllActivity()

}
