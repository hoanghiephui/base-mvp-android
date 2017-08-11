package com.ducnd.realmmvp

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by ducnd on 8/8/17.
 */

abstract class MVPApplication<AppComponent> : MultiDexApplication() {
    companion object {
        var widthScreen: Int = 0
            protected set
        var heightScreen: Int = 0
            protected set
        var density: Float = 0.toFloat()
            protected set
    }

    protected var mComponent: AppComponent? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    override fun onCreate() {
        super.onCreate()
        inits()
        initAppComponent()
    }

    protected abstract fun initAppComponent()

    private fun inits() {
        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(displayMetrics)
        widthScreen = displayMetrics.widthPixels
        heightScreen = displayMetrics.heightPixels
        density = displayMetrics.density
    }

    val getComponent: AppComponent?
        get() = mComponent

}
