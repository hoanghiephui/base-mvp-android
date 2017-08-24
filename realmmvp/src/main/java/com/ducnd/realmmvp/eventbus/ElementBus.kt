package com.ducnd.realmmvp.eventbus

import com.ducnd.realmmvp.utils.action.BaseAction

/**
 * Created by ducnd on 8/18/17.
 */
class ElementBus(id: String) {
    val listAction: MutableList<BaseAction> = mutableListOf()
    val id: String = id
}