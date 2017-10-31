package com.ducnd.realmmvp.remote.interact.main

import com.ducnd.realmmvp.remote.interact.source.IBasePresenter
import com.ducnd.realmmvp.ui.base.BaseViewUI
import com.ducnd.realmmvp.utils.Constants
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/9/17.
 */

abstract class BasePresenter<V : BaseViewUI>(val view: V) : IBasePresenter {
    private val mDiableAll: CompositeDisposable
    private var mIsDestroy: Boolean? = null
    private val mView: V = view

    init {
        mDiableAll = CompositeDisposable()
        mIsDestroy = false;
    }

    protected fun <T> subscribeHasDispose(observable: Observable<T>, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
        mDiableAll.add(observable.subscribe(
                {
                    if (mIsDestroy!!) {
                        return@subscribe
                    }
                    onNext(it)
                },
                {
                    run {
                        if (Constants.DEBUG) {
                            it.printStackTrace();
                        }
                        if (mIsDestroy!!) {
                            return@run;
                        }
                        onError(it)
                    }
                }));

    }

    protected fun <T> subscribeNotDispose(observable: Observable<T>, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
        observable.subscribe(
                {
                    if (mIsDestroy!!) {
                        return@subscribe
                    }
                    onNext(it)
                },
                {
                    run {
                        if (Constants.DEBUG) {
                            it.printStackTrace();
                        }
                        if (mIsDestroy!!) {
                            return@run;
                        }
                        onError(it)
                    }
                })
    }

    protected fun <T> subscribeResuleDispose(observable: Observable<T>, onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable {
        return observable.subscribe(
                {
                    if (mIsDestroy!!) {
                        return@subscribe
                    }
                    onNext(it)
                },
                {
                    run {
                        if (Constants.DEBUG) {
                            it.printStackTrace();
                        }
                        if (mIsDestroy!!) {
                            return@run;
                        }
                        onError(it)
                    }
                })
    }

    override fun onDestroy() {
        mIsDestroy = true;
        mDiableAll.dispose();
    }
}
