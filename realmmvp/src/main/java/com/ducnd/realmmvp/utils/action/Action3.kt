package com.ducnd.realmmvp.utils.action

/**
 * Created by ducnd on 8/9/17.
 */
interface Action3<Data1, Data2, Data3> {
    fun call(data1: Data1, data2: Data2, data3: Data3);
}