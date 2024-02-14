package com.jewelsbox.employee.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.jewelsbox.employee.retrofit.API.rest
import kotlinx.coroutines.launch
import org.json.JSONObject

class AuthViewModel : ViewModel() {

    private val TAG = "AuthViewModel"

    fun autoLogin(
        deviceId: String,
        dashboard: (JsonObject) -> Unit,
        login: () -> Unit,
        fail: (String) -> Unit
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("firebase_id", "firebase_id")
            body.addProperty("device_id", deviceId)

            try {
                val res = rest.autoLogin(body)
                if (res.get("status").asBoolean) dashboard(res.get("data").asJsonObject)
                else login()

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "autoLogin: ${e.localizedMessage}")
            }

        }


    }

    fun login(
        empId: String,
        password: String,
        deviceId: String,
        success: (JsonObject) -> Unit,
        fail: (String) -> Unit
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("email_id", empId)
            body.addProperty("password", password)
            body.addProperty("firebase_id", "firebase_id")
            body.addProperty("device_id", deviceId)

            try {
                val res = rest.login(body)
                if (res.get("status").asBoolean) success(res.get("data").asJsonObject)
                else fail(res.get("message").asString)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "login: ${e.localizedMessage}")
            }

        }
    }


    fun logout(
        userId: String,
        success: () -> Unit,
        fail: (String) -> Unit
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)

            try {

                val res = rest.logout(body)
                if (res.get("status").asBoolean) success()
                else fail(res.get("message").asString)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "login: ${e.localizedMessage}")
            }

        }
    }


}