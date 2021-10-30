package io.github.aafactory.sample.helpers

import io.github.aafactory.sample.models.Showcase
import io.realm.Realm
import io.realm.RealmConfiguration

object AAFactoryDbHelper {
    private val config: RealmConfiguration by lazy {
        RealmConfiguration.Builder()
                .name("diary.realm")
                .schemaVersion(1)
                .migration(AAFactoryMigration())
                .modules(Realm.getDefaultModule()!!)
                .build()
    }

    private var mRealmInstance: Realm? = null

    private fun getInstance(): Realm {
        if (mRealmInstance == null || mRealmInstance?.isClosed == true) {
            mRealmInstance = Realm.getInstance(config)
        }
        return mRealmInstance!!
    }

    /***************************************************************************************************
     *   Manage Showcase model
     *   Create: Insert
     *   Read: Find
     *   Update: Update
     *   Delete: Delete
     *
     ***************************************************************************************************/
    fun insertShowcase(showcase: Showcase) {
        getInstance().executeTransaction { realm ->
            var sequence = 1
            if (realm.where(Showcase::class.java).count() > 0L) {
                val number = realm.where(Showcase::class.java).max("sequence")
                number?.let {
                    sequence = it.toInt().plus(1)
                }
            }
            showcase.sequence = sequence
            realm.insert(showcase)
        }
    }

    fun upsertShowcase(showcase: Showcase) {
        val storedShowcase = findShowcase(showcase.owner, showcase.name)
        when (storedShowcase == null) {
            true -> insertShowcase(showcase)
            false -> {
                showcase.sequence = storedShowcase.sequence
                getInstance().insertOrUpdate(showcase)
            }
        }
    }

    fun countShowcase() = getInstance().where(Showcase::class.java).count()

    fun findShowcase(owner: String, name: String): Showcase? = getInstance().where(Showcase::class.java).beginGroup().equalTo("owner", owner).and().equalTo("name", name).endGroup().findFirst()
}