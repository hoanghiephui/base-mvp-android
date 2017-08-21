package com.ducnd.realmmvp.eventbus

/**
 * Created by ducnd on 8/18/17.
 */

interface ActionBus {
    fun <T> call(t: T): Unit
}
