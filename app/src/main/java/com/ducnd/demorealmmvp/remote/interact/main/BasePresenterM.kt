package com.ducnd.demorealmmvp.remote.interact.main

import com.ducnd.demorealmmvp.remote.interact.interf.IAccountInteraction
import com.ducnd.realmmvp.remote.interact.main.BasePresenter
import com.ducnd.realmmvp.ui.base.BaseViewUI

/**
 * Created by ducnd on 8/11/17.
 */

abstract class BasePresenterM<V : BaseViewUI>(view: V, protected var mAccountInteraction: IAccountInteraction) : BasePresenter<V>(view)
