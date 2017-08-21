package com.ducnd.realmmvp.eventbus

/**
 * Created by ducnd on 8/18/17.
 */
class ElementBus(id: String) {
    val listAction: MutableList<ActionBus> = mutableListOf()
    val id: String = id
}