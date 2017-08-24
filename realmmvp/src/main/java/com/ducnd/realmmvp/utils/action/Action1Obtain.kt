package com.ducnd.realmmvp.utils.action

/**
 * Created by ducnd on 8/9/17.
 */

interface Action1Obtain<Data> : BaseAction {
    fun call(data: Data): Data
}
