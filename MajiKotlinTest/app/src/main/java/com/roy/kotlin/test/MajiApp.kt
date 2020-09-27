package com.roy.kotlin.test

import android.app.Application
import com.roy.kotlin.test.greendao.DaoMaster
import com.roy.kotlin.test.greendao.DaoMaster.DevOpenHelper
import com.roy.kotlin.test.greendao.DaoSession


class MajiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initGreenDao()
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private fun initGreenDao() {
        val helper = DevOpenHelper(this, "info.db")
        val db = helper.writableDatabase
        val daoMaster = DaoMaster(db)
        daoSession = daoMaster.newSession()
    }

    private var daoSession: DaoSession? = null
    fun getDaoSession(): DaoSession? {
        return daoSession
    }
}