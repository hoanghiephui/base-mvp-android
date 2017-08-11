package com.ducnd.realmmvp.utils

/**
 * Created by ducnd on 8/10/17.
 */
interface ActionExtractAttribute<Obj, Attribute> {
    fun extract(o: Obj): Attribute
}