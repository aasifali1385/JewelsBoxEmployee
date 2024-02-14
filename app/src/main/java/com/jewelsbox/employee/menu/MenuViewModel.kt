package com.jewelsbox.employee.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.jewelsbox.employee.retrofit.API.rest
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    private val TAG = "MenuViewModel"

    fun helpSupport(
        userId: String,
        success: (String, String,String) -> Unit,
        fail: (String) -> Unit
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)

            try {
                val res = rest.helpSupport(body)
                if (res.get("status").asBoolean) {
//                    val data = res.get("data").asJsonObject

                    success(
                        res.get("email_id").asString,
                        res.get("whatsapp_no").asString,
                        res.get("mobile_no").asString
                    )

                } else fail(res.get("message").asString)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "helpSupport: ${e.localizedMessage}")
            }

        }
    }


    fun getCMS(
        userId: String,
        type: String,
        success: (String, String) -> Unit,
        fail: (String) -> Unit
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)

            try {
                val res = if (type == "terms") rest.terms(body) else rest.policy(body)
                if (res.get("status").asBoolean) {
                    val data = res.get("data").asJsonObject
                    success(data.get("heading").asString, data.get("description").asString)
                } else fail(res.get("message").asString)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "getCMS: ${e.localizedMessage}")
            }

        }
    }

}