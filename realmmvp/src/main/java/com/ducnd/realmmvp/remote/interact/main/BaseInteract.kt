package com.ducnd.realmmvp.remote.interact.main

import android.graphics.Bitmap
import com.ducnd.realmmvp.remote.database.IBaseStoreManager
import com.ducnd.realmmvp.remote.interact.source.IBaseApiConnector
import com.ducnd.realmmvp.remote.interact.source.IBaseInteraction
import io.reactivex.disposables.Disposable

/**
 * Created by ducnd on 8/9/17.
 */

abstract class BaseInteract<CommonData, out ApiConnector : IBaseApiConnector, out Store : IBaseStoreManager>
(connector: ApiConnector, store: Store) : IBaseInteraction<CommonData> {

    protected val mConnector: ApiConnector = connector;
    protected val mStore: Store = store;
    protected var mCommonData: CommonData? = null;

    override fun getData(): CommonData? {
        return mCommonData
    }
}
