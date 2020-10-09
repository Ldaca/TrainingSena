package com.ldaca.app.prueba1.app

import android.app.Application
//import com.facebook.stetho.Stetho
//import com.uphyca.stetho_realm.RealmInspectorModulesProvider
//import io.realm.Realm
//import io.realm.RealmConfiguration

class InitDBSQL : Application() {
    override fun onCreate() {
        super.onCreate()
        /*initRealm()
        val realm = Realm.getDefaultInstance()
        val realmInspector = RealmInspectorModulesProvider.builder(this)
            .withDeleteIfMigrationNeeded(true)
            .build()
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(realmInspector)
            .build()
        )*/
    }

    private fun initRealm() {
       /* Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)*/
    }
}