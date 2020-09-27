package com.roy.kotlin.test.http

import com.roy.kotlin.test.entity.InfoEntity
import retrofit2.http.*

interface ApiService {

    @GET("/")
    suspend fun getGitHubApi(): InfoEntity

}