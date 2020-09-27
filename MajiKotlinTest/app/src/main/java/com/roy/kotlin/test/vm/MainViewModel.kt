package com.roy.kotlin.test.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.roy.kotlin.test.MajiApp
import com.roy.kotlin.test.entity.InfoEntity
import com.roy.kotlin.test.http.HttpData
import java.util.logging.Logger

class MainViewModel : BaseViewModel() {

    private val httpData = MutableLiveData<InfoEntity>()
    fun getGitHubApi(): MutableLiveData<InfoEntity> {
        launchGo({
            val result = HttpData.getInstance().getGitHubApi()
            Log.e("打印", result.toString())
            val dao = getApplication<MajiApp>().getDaoSession()
            dao?.infoEntityDao?.insert(result)
            httpData.value = result
        })

        return httpData
    }
}