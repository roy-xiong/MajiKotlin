package com.roy.kotlin.test.http

import retrofit2.http.*

interface ApiService {

    @GET("/")
    suspend fun getGitHubApi(): Any

}