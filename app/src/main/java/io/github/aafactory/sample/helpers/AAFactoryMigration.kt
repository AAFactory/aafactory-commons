package io.github.aafactory.sample.helpers

import io.realm.DynamicRealm
import io.realm.RealmMigration

class AAFactoryMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {}
}