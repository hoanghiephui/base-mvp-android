package com.ducnd.realmmvp.utils.action

/**
 * Created by ducnd on 8/9/17.
 */
interface Action1<Data> : Action1<Any> {
    fun call(data: Data);
}