package com.sdkGuru.gurusdkpro

import retrofit2.http.GET
import retrofit2.http.Header

interface ApiServiceClass {

    @GET("V1.7.3/Business/List?Page=1&PageSize=10")
    suspend fun getBusinessList(
        @Header("Authorization") token: String,
        @Header("Content-Type") json: String
    ): BusinessResponse
}