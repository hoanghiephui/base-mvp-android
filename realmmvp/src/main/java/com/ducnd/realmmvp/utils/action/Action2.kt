package com.ducnd.realmmvp.utils.action

/**
 * Created by ducnd on 8/9/17.
 */
interface Action2<Data1, Data2> : BaseAction {
    fun call(data1: Data1, data2: Data2);
}