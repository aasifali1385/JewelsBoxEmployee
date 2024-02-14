package com.jewelsbox.employee.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.jewelsbox.employee.addLead.CityState
import com.jewelsbox.employee.addLead.Interest
import com.jewelsbox.employee.home.membership.Membership
import com.jewelsbox.employee.retrofit.API.rest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"


    fun searchLead(
        userId: String,
        mobileNo: String,
        fail: (String) -> Unit,
        success: (SearchVisitor) -> Unit,
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)
            body.addProperty("phone_no", mobileNo)

            try {
                val res = rest.searchLead(body)
                if (res.status) success(res.data)
                else fail(res.message)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "searchLead: ${e.localizedMessage}")
            }
        }
    }


    fun markVisiting(
        userId: String,
        leadId: String,
        fail: (String) -> Unit,
        success: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)
            body.addProperty("lead_id", leadId)

            try {
                val res = rest.markVisiting(body)
                if (res.get("status").asBoolean) success(res.get("message").asString)
                else fail(res.get("message").asString)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "markVisiting: ${e.localizedMessage}")
            }

        }
    }


    fun stateList(
        userId: String,
        fail: (String) -> Unit,
        success: (List<CityState>) -> Unit,
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)

            try {
                val res = rest.stateList(body)
                if (res.status) success(res.data)
                else fail(res.message)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "stateList: ${e.localizedMessage}")
            }
        }
    }

    fun cityList(
        userId: String,
        stateId: String,
        fail: (String) -> Unit,
        success: (List<CityState>) -> Unit,
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)
            body.addProperty("state_id", stateId)

            try {
                val res = rest.cityList(body)
                if (res.status) success(res.data)
                else fail(res.message)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "cityList: ${e.localizedMessage}")
            }
        }
    }


    fun interestList(
        userId: String,
        fail: (String) -> Unit,
        success: (List<Interest>) -> Unit,
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)

            try {
                val res = rest.interestList(body)
                if (res.status) success(res.data)
                else fail(res.message)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "interestList: ${e.localizedMessage}")
            }
        }
    }


    fun subInterestList(
        userId: String,
        interestedId: String,
        fail: (String) -> Unit,
        success: (List<Interest>) -> Unit,
    ) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("user_id", userId)
            body.addProperty("interested_id", interestedId)

            try {
                val res = rest.subInterestList(body)
                if (res.status) success(res.data)
                else fail(res.message)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "subInterestList: ${e.localizedMessage}")
            }
        }
    }


    fun addLead(
        profileImage: File,
        detailsMap: Map<String, RequestBody>,
        fail: (String) -> Unit,
        success: (String) -> Unit,
    ) {
        viewModelScope.launch {

            val imageBody = profileImage.asRequestBody("image/*".toMediaTypeOrNull())
            val multipartImage = MultipartBody.Part.createFormData("image", profileImage.name, imageBody);

            try {
                val res = rest.addLead(
                    multipartImage,
                    detailsMap)
                if (res.get("status").asBoolean) success(res.get("message").asString)
                else fail(res.get("message").asString)

            } catch (e: Exception) {
                fail("Please check your internet connection!")
                Log.e(TAG, "addLead: ${e.localizedMessage}")
            }

        }


    }

}