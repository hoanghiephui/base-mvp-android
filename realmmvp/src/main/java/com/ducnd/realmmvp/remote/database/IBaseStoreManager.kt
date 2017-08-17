package com.ducnd.realmmvp.remote.database

import com.ducnd.realmmvp.utils.action.Action1Obtain
import com.ducnd.realmmvp.utils.action.ActionExtractAttribute
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.Sort

/**
 * Created by ducnd on 8/9/17.
 */

interface IBaseStoreManager {
    fun getRealmDefault(): Realm
    fun <T : RealmObject> findFistItemAtMainThread(clazz: Class<T>): T?
    fun <T : RealmObject> findFistItemObtainAtMainThread(clazz: Class<T>, actionObtain: Action1Obtain<T>): T?
    fun <T : RealmObject> findFistItemObtainAtFreeThread(clazz: Class<T>, actionObtain: Action1Obtain<T>): T?

    fun <T : RealmObject> findListItemAtMainThread(clazz: Class<T>, nameSort: String, sort: Sort): RealmResults<T>
    fun <T : RealmObject> findListItemObtainAtMainThread(clazz: Class<T>, nameSort: String, sort: Sort, actionObtain: Action1Obtain<T>): MutableList<T>
    fun <T : RealmObject> findFistItemObtainAtFeeThread(clazz: Class<T>, nameSort: String, sort: Sort, actionObtain: Action1Obtain<T>): MutableList<T>

    fun <T : RealmObject> saveAtMainThread(t: T): T
    fun <T : RealmObject> saveAtFreeThread(t: T)

    fun <T : RealmObject> mergeListRealmObjectAtMainThread(listRealmObject: MutableList<T>)
    fun <T : RealmObject> mergeListRealmObjectAtFreeThread(listRealmObject: MutableList<T>)

    fun <T : RealmObject> mergeListRealmObjectAndDeleteItemsNoMatchAtMainThread(listRealmObject: MutableList<T>, fieldID: String, actionExtratID: ActionExtractAttribute<T, String>)
    fun <T : RealmObject> mergeListRealmObjectAndDeleteItemsNoMatchAtFreeThread(listRealmObject: MutableList<T>, fieldID: String, actionExtratID: ActionExtractAttribute<T, String>)

    fun <T : RealmObject> findItemAtMainThread(clazz: Class<T>, fieldName: String, valueCondition: String): T?
    fun <T : RealmObject> findItemAtMainThread(clazz: Class<T>, fieldName: String, valueCondition: String, action1Obtain: Action1Obtain<T>): T?
    fun <T : RealmObject> findItemAtFreeThread(clazz: Class<T>, fieldName: String, valueCondition: String, action1Obtain: Action1Obtain<T>): T?

    fun <T : RealmObject> deleteRealmObjectAtMainThread(realmObject: T): Boolean

    fun optimizeStore()

    fun closeRealm()
}
