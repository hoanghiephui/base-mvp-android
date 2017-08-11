package com.ducnd.realmmvp.ui.base.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

import com.ducnd.realmmvp.ui.base.fragment.BaseFragment

/**
 * Created by ducnd on 8/10/17.
 */

abstract class BaseActivity : AppCompatActivity(), ViewActivity {
    protected var mIsClearMemoryActivity: Boolean = false
    protected var mProgress: ProgressBar? = null
    protected var mIsDestroyView = true
    private var mViewRoot: View? = null

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIsDestroyView = false
        beforLoadUI()
        onCreateControl(savedInstanceState)
    }

    override fun onCreateControl(savedInstanceState: Bundle?) {
        if (!mIsClearMemoryActivity) {
            setContentView(layoutMain)
            findViewByIds()
            initComponents()
            setEvents()
        }
    }

    protected fun beforLoadUI() {
        mIsClearMemoryActivity = false
    }

    override fun findFragmentByTag(tag: String): BaseFragment {
        return supportFragmentManager.findFragmentByTag(tag) as BaseFragment
    }

    override fun setViewRoot(viewRoot: View) {
        mViewRoot = viewRoot
    }

    override fun showProgress() {
        if (!mIsDestroyView && mProgress != null) {
            mProgress!!.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        if (!mIsDestroyView && mProgress != null) {
            mProgress!!.visibility = View.GONE
        }
    }

    override fun showMessage(message: String) {
        if (!mIsDestroyView) {
            if (mViewRoot == null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                return
            }
            val snackbar = Snackbar.make(mViewRoot!!, message, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    override fun showMessage(messageId: Int) {
        if (!mIsDestroyView) {
            if (mViewRoot == null) {
                Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
                return
            }
            val snackbar = Snackbar.make(mViewRoot!!, messageId, Snackbar.LENGTH_LONG)
            snackbar.show()
        }

    }

    final override fun onBackParent() {
        super.onBackPressed()
    }

    override fun onBackRoot() {
        onBackParent()
    }

    override fun onBackPressed() {
        val baseFragment = BaseFragment.getCurrentFragment(supportFragmentManager)
        if (null == baseFragment) {
            onBackParent()
        } else {
            baseFragment.onBackRoot()
        }
    }

    final override fun onResume() {
        super.onResume()
        if (!mIsClearMemoryActivity) {
            onResumeControl()
        }
    }

    override fun onResumeControl() {

    }

    final override fun onPause() {
        if (!mIsClearMemoryActivity) {
            onPauseControl()
        }
        super.onPause()
    }

    override fun onPauseControl() {

    }

    final override fun onDestroy() {
        mIsDestroyView = true
        onDestroyControl()
        super.onDestroy()
    }

    override fun onDestroyControl() {
    }

    override val isDestroyView: Boolean
        get() = mIsDestroyView
}