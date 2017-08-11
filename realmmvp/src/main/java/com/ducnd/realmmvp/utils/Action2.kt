package com.ducnd.realmmvp.utils

/**
 * Created by ducnd on 8/9/17.
 */
interface Action2<Data1, Data2> {
    fun call(data1: Data1, data2: Data2);
}