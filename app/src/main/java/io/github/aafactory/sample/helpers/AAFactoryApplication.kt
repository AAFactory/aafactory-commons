package io.github.aafactory.sample.helpers

import android.content.Context
import androidx.multidex.MultiDexApplication
import io.realm.Realm

/**
 * Created by CHO HANJOONG on 2017-03-16.
 */

class AAFactoryApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        context = this
    }

    companion object {
        var context: Context? = null
    }
}
