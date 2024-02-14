package com.jewelsbox.employee.home.membership

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.jewelsbox.employee.retrofit.API
import com.jewelsbox.employee.retrofit.API.rest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MembershipViewModel : ViewModel() {

    private val TAG = "MembershipViewModel"

    private val _membership = MutableLiveData<List<Membership>>()
    val membership get() = _membership


    fun searchMemberPlan(
        userId: String,
        memberId: String,
        fail: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)
            body.addProperty("member_id_phone", memberId)

            try {
                val res = API.rest.searchMemberPlan(body)
                if (res.status) _membership.value = res.data
                else fail(res.message)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "searchMemberPlan: ${e.localizedMessage}")
            }
        }
    }


    fun paymentMode(
        userId: String,
        fail: (String) -> Unit,
        success: (List<String>) -> Unit
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)

            try {
                val res = rest.paymentMode(body)
                if (res.status) success(res.data)
                else fail(res.message)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "searchMemberPlan: ${e.localizedMessage}")
            }
        }
    }


    fun getPayment(
        userId: String,
        planId: String,
        amount: String,
        payMode: String,
        date: String,
        txnId: String,
        image: File,
        fail: (String) -> Unit,
        success: (String) -> Unit
    ) {
        viewModelScope.launch {

            try {
                val res = rest.getPayment(
                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    planId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    amount.toRequestBody("text/plain".toMediaTypeOrNull()),
                    payMode.toRequestBody("text/plain".toMediaTypeOrNull()),
                    date.toRequestBody("text/plain".toMediaTypeOrNull()),
                    txnId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    MultipartBody.Part.createFormData(
                        "image",
                        image.name,
                        image.asRequestBody("image/*".toMediaTypeOrNull())
                    )
                )

                if (res.get("status").asBoolean) success(res.get("message").asString)
                else fail(res.get("message").asString)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "getPayment: ${e.localizedMessage}")
            }
        }
    }

}