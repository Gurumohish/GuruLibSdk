package com.sdkGuru.gurusdkpro

import android.util.Log
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

object GenerateJWT {

    private fun generateJWS(clientId: String, clientSecret: String, userToken: String): String {
        return try {
            val algorithm = Algorithm.HMAC256(clientSecret)
            val payload = mapOf(
                "iss" to clientId,
                "sub" to clientId,
                "aud" to userToken,
                "iat" to System.currentTimeMillis() / 1000
            )
            JWT.create()
                .withPayload(payload)
                .sign(algorithm)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    suspend fun generateJWT(clientId: String, clientSecret: String, userToken: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val jwsToken = generateJWS(clientId, clientSecret, userToken)
                val url = URL("https://oauth.spanstage.com/v2/tbsauth")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authentication", "$jwsToken")
                connection.setRequestProperty("Content-Type", "application/json")

                val responseCode = connection.responseCode
                val responseBody = if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.readBytes().toString(StandardCharsets.UTF_8)
                } else {
                    connection.errorStream.readBytes().toString(StandardCharsets.UTF_8)
                }

                // Parse the JSON response
                val jsonResponse = JSONObject(responseBody)
                val accessToken = jsonResponse.optString("AccessToken", "")

                if (accessToken.isNotEmpty()) {
                    "$accessToken"
                } else {
                    "Error in JWT: Invalid response format"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                "Exception occurred: ${e.message}"
            }
        }
    }

    suspend fun fetchBusinessList(
        jwtToken: String
    ): BusinessResponse? {
        return withContext(Dispatchers.IO) {
            try {

                Log.e("token", "$jwtToken")
                Log.e("json", "application/json")

                val response = RetrofitClient.api.getBusinessList(
                    token = "$jwtToken",
                    json = "application/json"
                )
                response
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}