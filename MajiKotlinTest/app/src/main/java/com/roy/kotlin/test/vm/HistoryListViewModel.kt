package com.roy.kotlin.test.vm

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.roy.kotlin.test.MajiApp
import com.roy.kotlin.test.entity.InfoEntity

class HistoryListViewModel : BaseViewModel() {

    private val httpData = MutableLiveData<ArrayList<InfoEntity>>()
    fun getGitHubApiFromDB(page: Int, pageSize: Int): MutableLiveData<ArrayList<InfoEntity>> {
        launchGo({
            val dao = getApplication<MajiApp>().getDaoSession()
            val list =
                dao?.infoEntityDao?.queryBuilder()?.limit(pageSize * page)
                    ?.offset((page - 1) * pageSize)?.list()
            httpData.value = list as ArrayList<InfoEntity>?
        })
        return httpData
    }
}