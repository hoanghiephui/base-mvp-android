package com.ducnd.realmmvp.remote.database

import android.content.Context
import android.support.annotation.MainThread
import com.ducnd.realmmvp.utils.action.Action1Obtain
import com.ducnd.realmmvp.utils.action.ActionExtractAttribute
import io.realm.*


/**
 * Created by ducnd on 8/9/17.
 */

abstract class BaseStoreManager(context: Context, nameSchema: String, versionSchema: Long) : IBaseStoreManager {
    protected val mRealm: Realm
    protected val mPackageName: String

    init {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration.Builder()
                .name(nameSchema)
                .schemaVersion(versionSchema)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        mRealm = Realm.getDefaultInstance()
        mPackageName = context.packageName

    }

    /**
     * @return return RealmObject on MainThread
     */
    override fun getRealmDefault(): Realm {
        return mRealm
    }


    /**
     * @param clazz is class RealmObject T to find
     * @return RealmObject T reference into database on MainThread
     */
    @MainThread
    override fun <T : RealmObject> findFistItemAtMainThread(clazz: Class<T>): T? {
        return mRealm.where(clazz).findFirst()
    }

    /**
     * @param clazz is class RealmObject T to find
     * @param actionObtain action to convert RealmOject ont reference into database
     * @return RealmObject T not reference into database on MainThread
     */
    @MainThread
    override fun <T : RealmObject> findFistItemObtainAtMainThread(clazz: Class<T>, actionObtain: Action1Obtain<T>): T? {
        val t: T = mRealm.where(clazz).findFirst() ?: return null
        return actionObtain.call(t)
    }

    /**
     * @param actionObtain is class RealmObject T to find
     * @param actionObtain action convert RealmObject to Object not reference into database
     * @return Object not into database on free thread
     */
    override fun <T : RealmObject> findFistItemObtainAtFreeThread(clazz: Class<T>, actionObtain: Action1Obtain<T>): T? {
        val realm: Realm = Realm.getDefaultInstance();
        val t: T? = realm.where(clazz).findFirst()
        if (t == null) {
            realm.close()
            return null
        }
        val tResult: T = actionObtain.call(t)
        realm.close()
        return tResult
    }

    /**
     * @param clazz class RealmObject T
     * @param nameSort name field to soft
     * @param sort sort sort degree or increase
     * @return RealmResults on MainThread reference into database on MainThread
     */
    @MainThread
    override fun <T : RealmObject> findListItemAtMainThread(clazz: Class<T>, nameSort: String, sort: Sort): RealmResults<T> {
        return mRealm.where(clazz).findAllSorted(nameSort, sort)
    }


    /**
     * @param clazz class RealmObject T
     * @param nameSort name field to soft
     * @param sort sort sort degree or increase
     * @param actionObtain action convert RealmObject to Object not reference into database
     * @return list T not reference into database on MainThread
     */
    @MainThread
    override fun <T : RealmObject> findListItemObtainAtMainThread(clazz: Class<T>, nameSort: String, sort: Sort, actionObtain: Action1Obtain<T>): MutableList<T> {
        val ts: RealmResults<T> = mRealm.where(clazz).findAllSorted(nameSort, sort)
        val listData: MutableList<T> = mutableListOf<T>()
        for (t in ts) {
            listData.add(actionObtain.call(t))
        }
        return listData
    }

    /**
     * @param clazz class RealmObject T
     * @param nameSort name field to soft
     * @param sort sort sort degree or increase
     * @param actionObtain action convert RealmObject to Object not reference into database
     * @return list T not reference into database on free thread
     */
    override fun <T : RealmObject> findFistItemObtainAtFeeThread(clazz: Class<T>, nameSort: String, sort: Sort, actionObtain: Action1Obtain<T>): MutableList<T> {
        val realm: Realm = Realm.getDefaultInstance()
        val ts: RealmResults<T> = realm.where(clazz).findAllSorted(nameSort, sort)
        val listData: MutableList<T> = mutableListOf<T>()
        for (t in ts) {
            listData.add(actionObtain.call(t))
        }
        realm.close()
        return listData
    }


    /**
     * @param clazz class RealmObject T
     * @return RealmObject T reference into database on MainThread
     */
    @MainThread
    override fun <T : RealmObject> saveAtMainThread(t: T): T {
        mRealm.beginTransaction()
        val t2: T = mRealm.copyToRealmOrUpdate(t)
        mRealm.commitTransaction()
        return t2
    }

    /**
     * @param clazz class RealmObject T
     * @return RealmObject T not reference into database on free thread
     */
    override fun <T : RealmObject> saveAtFreeThread(t: T) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(t);
        realm.commitTransaction()
        realm.close()
    }

    /**
     * merge list realmObject into database, not delete any items no match with list on Main Thread
     * @param clazz class RealmObject T
     * @param listRealmObject list all items need merge into database
     */
    @MainThread
    override fun <T : RealmObject> mergeListRealmObjectAtMainThread(listRealmObject: MutableList<T>) {
        mRealm.beginTransaction()
        mRealm.copyToRealmOrUpdate(listRealmObject)
        mRealm.commitTransaction()
    }

    /**
     * merge list realmObject into database, not delete any items no match with list on Free Thread
     * @param listRealmObject list all items need merge into database
     */
    override fun <T : RealmObject> mergeListRealmObjectAtFreeThread(listRealmObject: MutableList<T>) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(listRealmObject)
        realm.commitTransaction()
        realm.close()
    }

    /**
     * merge list RealmObject into database and delete all item in database no match with list on Main Thread
     * @param listRealmObject list all items need merge into database
     * @param fieldID field identify of RealmObject
     * @param actionExtrctID action get attribute id RealmObject
     */
    @MainThread
    override fun <T : RealmObject> mergeListRealmObjectAndDeleteItemsNoMatchAtMainThread(listRealmObject: MutableList<T>, fieldID: String, actionExtractID: ActionExtractAttribute<T, String>) {
        val results = getRealmObjectNotMathInDatabaseAtMainThread(listRealmObject, fieldID, actionExtractID, mRealm)
        if (results != null && !results.isEmpty()) {
            mRealm.beginTransaction()
            results.deleteAllFromRealm()
            mRealm.commitTransaction()
        }
        mRealm.beginTransaction()
        mRealm.copyToRealmOrUpdate(listRealmObject)
        mRealm.commitTransaction()
    }

    private fun <T : RealmObject> getRealmObjectNotMathInDatabaseAtMainThread(listRealmObject: MutableList<T>?, fieldID: String, actionExtractID: ActionExtractAttribute<T, String>, realm: Realm): RealmResults<T>? {
        if (listRealmObject == null && listRealmObject!!.size == 0) {
            return null
        }
        val realmQuery: RealmQuery<T> = realm.where(listRealmObject.get(0).javaClass);
        for (realmObject in listRealmObject) {
            realmQuery.notEqualTo(fieldID, actionExtractID.extract(realmObject))
        }
        return realmQuery.findAll()
    }

    /**
     * merge list RealmObject into database and delete all item in database no match with list on Free Thread
     * @param listRealmObject list all items need merge into database
     * @param fieldID field identify of RealmObject
     * @param actionExtrctID action get attribute id RealmObject
     */
    override fun <T : RealmObject> mergeListRealmObjectAndDeleteItemsNoMatchAtFreeThread(listRealmObject: MutableList<T>, fieldID: String, actionExtratID: ActionExtractAttribute<T, String>) {
        val realm: Realm = Realm.getDefaultInstance()
        val results: RealmResults<T>? = getRealmObjectNotMathInDatabaseAtMainThread(listRealmObject, fieldID, actionExtratID, realm)
        if (results != null && !results.isEmpty()) {
            realm.beginTransaction()
            results.deleteAllFromRealm()
            realm.commitTransaction()
        }
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(listRealmObject)
        realm.commitTransaction()
        realm.close()
    }

    /**
     * get RealmObject reference into database on Main Thread
     * @param clazz class RealmObject
     * @param field to query
     * @param valueCondition condition to query
     */
    override fun <T : RealmObject> findItemAtMainThread(clazz: Class<T>, fieldName: String, valueCondition: String): T? {
        return mRealm.where(clazz).equalTo(fieldName, valueCondition).findFirst()
    }

    /**
     * get RealmObject not reference into database on Main Thread
     * @param clazz class RealmObject
     * @param field to query
     * @param valueCondition condition to query
     * @param action1Obtain obtain from RealObject not reference into database
     * @return RealmObject not reference into database on Main Thread
     */
    override fun <T : RealmObject> findItemAtMainThread(clazz: Class<T>, fieldName: String, valueCondition: String, action1Obtain: Action1Obtain<T>): T? {
        val t: T = mRealm.where(clazz).equalTo(fieldName, valueCondition).findFirst() ?: return null
        if (!t.isLoaded) {
            return t
        } else {
            return action1Obtain.call(t)
        }
    }

    /**
     * get RealmObject not reference into database on Free Thread
     * @param clazz class RealmObject
     * @param field to query
     * @param valueCondition condition to query
     * @param action1Obtain obtain from RealObject not reference into database
     * @return RealmObject not reference into database on Free Thread
     */
    override fun <T : RealmObject> findItemAtFreeThread(clazz: Class<T>, fieldName: String, valueCondition: String, action1Obtain: Action1Obtain<T>): T? {
        val realm: Realm = Realm.getDefaultInstance()
        val t: T = realm.where(clazz).equalTo(fieldName, valueCondition).findFirst() ?: return null
        if (!t.isLoaded) {
            realm.close()
            return t
        } else {
            val t2: T = action1Obtain.call(t);
            realm.close()
            return t2
        }
    }


    /**
     * close realm on main thread
     */
    override fun closeRealm() {
        mRealm.close()
    }
}
