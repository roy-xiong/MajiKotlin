package com.roy.kotlin.test.http

import com.aleyn.mvvm.base.IBaseResponse

data class BaseResult<T>(
    val errorMessage: String,
    val errorCode: Int,
    val data: T,
    val status: Int
) : IBaseResponse<T> {

    override fun code() = errorCode

    override fun msg() = errorMessage

    override fun data() = data

    override fun isSuccess() = status == 200

}