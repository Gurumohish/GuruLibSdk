package com.sdkGuru.gurusdkpro

data class BusinessRequest(
    val Page: Int? = null,
    val PageSize: Int? = null,
    val FromDate: String? = null,
    val ToDate: String? = null
)

data class BusinessResponse(
    val StatusCode: Int? = null,
    val StatusName: String? = null,
    val StatusMessage: String? = null,
    val Businesses: List<Business>? = null,
    val Page: Int? = null,
    val TotalRecords: Int? = null,
    val TotalPages: Int? = null,
    val PageSize: Int? = null,
    val Errors: Any? = null
)

data class Business(
    val BusinessId: String? = null,
    val PayerRef: String? = null,
    val BusinessNm: String? = null,
    val EINorSSN: String? = null,
    val Email: String? = null,
    val ContactNm: String? = null,
    val BusinessType: String? = null,
    val IsBusinessTerminated: Boolean? = null
)

data class Error(
    val Id: String,
    val Name: String,
    val Message: String
)