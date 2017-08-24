package com.ducnd.realmmvp.eventbus

import com.ducnd.realmmvp.utils.action.Action1

/**
 * Created by ducnd on 8/18/17.
 */

class Bus {
    private val elementBuses: MutableList<ElementBus> = mutableListOf()

    fun <T> register(clazz: Class<T>, actionBus: Action1<T>) {
        val className = clazz.name
        for (elementBus in elementBuses) {
            if (elementBus.id.equals(className)) {
                elementBus.listAction.add(actionBus)
                return
            }
        }
        val elementBus = ElementBus(className)
        elementBus.listAction.add(actionBus)
        elementBuses.add(elementBus)
    }

    fun <T> unregister(clazz: Class<T>, actionBus: Action1<T>) {
        val className = clazz.name
        for (elementBus in elementBuses) {
            if (className.equals(elementBus.id)) {
                for (actionBus in elementBus.listAction) {
                    if (actionBus.equals(actionBus)) {
                        elementBus.listAction.remove(actionBus)
                        break
                    }
                }
                break
            }
        }
    }

    fun <T> post(clazz: Class<T>, t: T) {
        val clazzName = clazz.name
        for (elementBus in elementBuses) {
            if (elementBus.id.equals(clazzName)) {
                for (actionBus in elementBus.listAction) (actionBus as Action1<T>).call(t)
                break
            }
        }
    }
}
