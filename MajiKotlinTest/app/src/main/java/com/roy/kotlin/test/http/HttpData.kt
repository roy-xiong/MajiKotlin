package com.roy.kotlin.test.http


/**
 * 网络访问基础类
 */
class HttpData {
    private val mService by lazy { RetrofitClient.getInstance().create(ApiService::class.java) }

    companion object {
        @Volatile
        private var netWork: HttpData? = null
        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: HttpData().also { netWork = it }
        }
    }

    suspend fun getGitHubApi() = mService.getGitHubApi()
}