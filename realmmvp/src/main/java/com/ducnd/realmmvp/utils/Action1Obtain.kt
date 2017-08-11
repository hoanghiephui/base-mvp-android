package com.ducnd.realmmvp.utils

/**
 * Created by ducnd on 8/9/17.
 */

interface Action1Obtain<Data> {
    fun call(data: Data): Data
}
