package com.roy.kotlin.test.vm

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.roy.kotlin.test.http.HttpData

class MainViewModel : BaseViewModel() {

    private val todayLuckyData = MutableLiveData<Any>()
    fun getGitHubApi(): MutableLiveData<Any> {
        launchGo({
            val result = HttpData.getInstance().getGitHubApi()
            todayLuckyData.value = result
        })
        return todayLuckyData
    }
}