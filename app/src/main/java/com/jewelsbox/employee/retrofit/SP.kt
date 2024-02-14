package com.jewelsbox.employee.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import com.google.gson.JsonObject
import org.json.JSONObject

class SP(private val cxt: Context) {


    private val sp = cxt.getSharedPreferences("JewelsBoxEmployee", Context.MODE_PRIVATE)

    @SuppressLint("HardwareIds")
    fun getDeviceId() = Settings.Secure.getString(cxt.contentResolver, Settings.Secure.ANDROID_ID)!!


    fun saveUser(json: JsonObject) {
        val edit = sp.edit()
        edit.putString("user_id", json.get("user_id").asString)
        edit.putString("profile_pic", json.get("profile_pic").asString)
        edit.putString("user_code", json.get("user_code").asString)
        edit.putString("full_name", json.get("full_name").asString)
        edit.putString("phone_no", json.get("phone_no").asString)
        edit.putString("email_id", json.get("email_id").asString)
        edit.putString("city_name", json.get("city_name").asString)
        edit.apply()
    }

    fun getUser(out: (Map<String, String?>) -> Unit) {
        out(
            mapOf(
                "profile_pic" to sp.getString("profile_pic", ""),
                "user_code" to sp.getString("user_code", ""),
                "full_name" to sp.getString("full_name", ""),
                "phone_no" to sp.getString("phone_no", ""),
                "email_id" to sp.getString("email_id", ""),
                "city_name" to sp.getString("city_name", ""),
            )
        )
    }

    fun getId() = sp.getString("user_id", "")!!

    fun setId(id: String) {
        val edit = sp.edit()
        edit.putString("user_id", id)
        edit.apply()
    }

    fun setMobile(mobile: String) {
        val edit = sp.edit()
        edit.putString("mobile_no", mobile)
        edit.apply()
    }


}