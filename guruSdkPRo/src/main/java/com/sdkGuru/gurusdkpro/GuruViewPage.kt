package com.sdkGuru.gurusdkpro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class GuruViewPage {

    private val clientId = "8d3d8f403ed50c38"
    private val clientSecret = "p9ytXxatvTld5ONE52VfA"
    private val userToken = "4b679e82256e4d71ade245bfd294ab0b"

    @Composable
    fun LoginScreen(
        onLoginSuccess: (String) -> Unit
    ) {
        val coroutineScope = rememberCoroutineScope()
        var jwt by remember { mutableStateOf<String?>(null) }
        var businessList by remember { mutableStateOf<List<Business>>(emptyList()) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        val jwtResult = GenerateJWT.generateJWT(clientId, clientSecret, userToken)
                        jwt = jwtResult
                        onLoginSuccess(jwtResult)
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Login")
            }

            jwt?.let {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val response = GenerateJWT.fetchBusinessList(jwt!!)
                            if (response != null) {
                                businessList = response.Businesses!!
                            }
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Business List")
                }
            }

            if (businessList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(businessList) { business ->
                        BusinessItem(business)
                    }
                }
            }
        }
    }

    @Composable
    fun BusinessItem(business: Business) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Business Name: ${business.BusinessNm}")
            Text(text = "Payer Ref: ${business.PayerRef}")
            Text(text = "EIN or SSN: ${business.EINorSSN}")
            Text(text = "Email: ${business.Email}")
            Text(text = "Contact Name: ${business.ContactNm}")
            Text(text = "Business Type: ${business.BusinessType}")
            Text(text = "Is Business Terminated: ${business.IsBusinessTerminated}")
        }
    }
}