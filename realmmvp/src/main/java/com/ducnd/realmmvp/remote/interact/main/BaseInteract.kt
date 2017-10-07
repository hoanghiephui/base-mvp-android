package com.ducnd.realmmvp.remote.interact.main

import android.support.annotation.MainThread
import com.ducnd.realmmvp.eventbus.Bus
import com.ducnd.realmmvp.remote.database.IBaseStoreManager
import com.ducnd.realmmvp.remote.interact.source.IBaseApiConnector
import com.ducnd.realmmvp.remote.interact.source.IBaseInteraction
import com.ducnd.realmmvp.utils.action.Action1

/**
 * Created by ducnd on 8/9/17.
 */

abstract class BaseInteract<CommonData, out ApiConnector : IBaseApiConnector, out Store : IBaseStoreManager>
(connector: ApiConnector, store: Store) : IBaseInteraction<CommonData> {

    protected val mConnector: ApiConnector = connector;
    protected val mStore: Store = store;
    protected var mCommonData: CommonData? = null;
    private val bus: Bus

    init {
        bus = Bus()
    }

    override fun getData(): CommonData? {
        return mCommonData
    }

    override fun <T> register(clazz: Class<T>, actionBus: Action1<T>) {
        bus.register(clazz, actionBus)
    }

    @MainThread
    override fun <T> post(clazz: Class<T>, t: T) {
        bus.post(clazz, t)
    }

    override fun <T> unregister(clazz: Class<T>, actionBus: Action1<T>) {
        bus.unregister(clazz, actionBus)
    }
}
