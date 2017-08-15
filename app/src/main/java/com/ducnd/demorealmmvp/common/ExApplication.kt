package com.ducnd.demorealmmvp.common

import com.ducnd.demorealmmvp.remote.dagger.component.DaggerExApplicationComponent
import com.ducnd.demorealmmvp.remote.dagger.component.ExApplicationComponent
import com.ducnd.demorealmmvp.remote.dagger.module.ExApplicationModule
import com.ducnd.realmmvp.MVPApplication

/**
 * Created by ducnd on 8/10/17.
 */

class ExApplication : MVPApplication<ExApplicationComponent>() {
    override fun initAppComponent() {
        mComponent = DaggerExApplicationComponent.builder().exApplicationModule(ExApplicationModule(this)).build()
        mComponent!!.inject(this)
    }


}
