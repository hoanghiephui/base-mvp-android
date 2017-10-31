package com.ducnd.realmmvp.utils.action

/**
 * Created by ducnd on 8/10/17.
 */
interface ActionExtractAttribute<Obj, Attribute> : BaseAction {
    fun extract(o: Obj): Attribute
}