package com.ducnd.realmmvp.ui.base.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar

import com.ducnd.realmmvp.ui.base.AnimationScreen
import com.ducnd.realmmvp.ui.base.activity.BaseActivity

/**
 * Created by ducnd on 8/10/17.
 */

abstract class BaseFragment : Fragment(), ViewFragment {
    protected var mIsDestroyView = true
    protected var mAnimationContinueId: Int = 0
    protected var mProgressBar: ProgressBar? = null

    final override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mIsDestroyView = false
        return onCreateViewControl(inflater, container, savedInstanceState)
    }

    override fun onCreateViewControl(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (inflater == null) {
            val creatInflater: LayoutInflater = LayoutInflater.from(context);
            return creatInflater.inflate(layoutMain, container, false)
        } else return inflater.inflate(layoutMain, container, false)
    }

    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view != null) {
            onViewCreatedControl(view, savedInstanceState)
        }
    }

    override fun onViewCreatedControl(view: View, savedInstanceState: Bundle?) {
        findViewByIds()
        initComponents()
        setEvents()
    }

    fun setAnimationContinueId(runAnimationContitue: Int) {
        mAnimationContinueId = runAnimationContitue
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        if (mAnimationContinueId != 0) {
            val animation = AnimationUtils.loadAnimation(context, mAnimationContinueId)
            mAnimationContinueId = 0
            return animation
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun showProgress() {
        if (!mIsDestroyView && mProgressBar != null) {
            mProgressBar!!.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        if (!mIsDestroyView && mProgressBar != null) {
            mProgressBar!!.visibility = View.GONE
        }
    }

    override fun showMessage(messageId: Int) {
        if (!mIsDestroyView) {
            baseActivity.showMessage(messageId)
        }
    }

    override fun showMessage(message: String) {
        if (!mIsDestroyView) {
            baseActivity.showMessage(message)
        }
    }


    final override fun onResume() {
        super.onResume()
        onResumeControl()
    }

    override fun onResumeControl() {

    }

    final override fun onPause() {
        onPauseControl()
        super.onPause()
    }

    override fun onPauseControl() {

    }

    final override fun onDestroyView() {
        mIsDestroyView = true
        onDestroyViewControl()
        super.onDestroyView()
    }

    override fun onDestroyViewControl() {

    }

    val baseActivity: BaseActivity
        get() = activity as BaseActivity

    override fun reload(bundle: Bundle) {

    }

    override fun onBackRoot() {
        baseActivity.onBackParent()
    }

    override val isDestroyView: Boolean
        get() = mIsDestroyView

    companion object {

        fun openFragment(manager: FragmentManager, transaction: FragmentTransaction, clazz: Class<out BaseFragment>, bundle: Bundle,
                         hasAddbackstack: Boolean, hasCommitTransaction: Boolean, animations: AnimationScreen,
                         fragmentContent: Int): Fragment? {
            val tag = clazz.name
            var fragment: Fragment?
            try {
                //if added backstack
                fragment = manager.findFragmentByTag(tag)
                if (hasAddbackstack) {
                    if (fragment == null || !fragment.isAdded) {
                        fragment = clazz.newInstance()
                        fragment!!.arguments = bundle
                        setAnimationFragment(transaction, animations)
                        transaction.add(fragmentContent, fragment, tag)
                    } else {
                        transaction.show(fragment)
                    }
                    transaction.addToBackStack(tag)
                } else {
                    if (fragment != null) {
                        setAnimationFragment(transaction, animations)
                        transaction.show(fragment)
                    } else {
                        fragment = clazz.newInstance()
                        fragment!!.arguments = bundle
                        setAnimationFragment(transaction, animations)
                        transaction.add(fragmentContent, fragment, tag)
                    }
                }
                if (hasCommitTransaction) {
                    transaction.commit()
                }
                return fragment
            } catch (e: java.lang.InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            return null
        }

        fun openFragment(transaction: FragmentTransaction, fragment: BaseFragment, bundle: Bundle,
                         hasAddbackstack: Boolean, hasCommitTransaction: Boolean, animations: AnimationScreen,
                         fragmentContent: Int) {
            val tag = fragment.javaClass.name
            fragment.arguments = bundle
            setAnimationFragment(transaction, animations)
            transaction.add(fragmentContent, fragment, tag)

            if (hasAddbackstack) {
                transaction.addToBackStack(tag)
            }
            if (hasCommitTransaction) {
                transaction.commit()
            }
        }

        fun hideFragment(manager: FragmentManager,
                         transaction: FragmentTransaction, animations: AnimationScreen,
                         hasAddBackstack: Boolean, hasCommit: Boolean, tag: String) {
            val fragment = manager.findFragmentByTag(tag) as BaseFragment
            if (fragment.isVisible) {
                setAnimationFragment(transaction, animations)
                transaction.hide(fragment)
                if (hasAddBackstack) {
                    transaction.addToBackStack(tag)
                }
                if (hasCommit) {
                    transaction.commit()
                }
            }
        }

        fun removeFragment(manager: FragmentManager, transaction: FragmentTransaction, animations: AnimationScreen,
                           hasAddBackStack: Boolean, hasCommit: Boolean, tag: String) {
            val fragment = manager.findFragmentByTag(tag) as BaseFragment
            setAnimationFragment(transaction, animations)
            transaction.remove(fragment)
            if (hasAddBackStack) {
                transaction.addToBackStack(tag)
            }
            if (hasCommit) {
                transaction.commit()
            }
        }

        private fun setAnimationFragment(transaction: FragmentTransaction, animations: AnimationScreen?) {
            if (animations != null) {
                transaction.setCustomAnimations(animations.enterToLeft, animations.exitToLeft, animations.enterToRight, animations.exitToright)
            }
        }


        fun getCurrentFragment(fragmentManager: FragmentManager): BaseFragment? {
            val frags = fragmentManager.fragments
            if (frags != null) {
                for (i in frags.indices.reversed()) {
                    val fr = frags[i]
                    if (fr != null && fr.isVisible && fr.tag != null) {
                        return fr as BaseFragment
                    }
                }
            }
            return null
        }
    }
}
