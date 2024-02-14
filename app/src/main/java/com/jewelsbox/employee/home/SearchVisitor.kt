package com.jewelsbox.employee.home

import android.os.Parcelable
import java.io.Serializable

data class SearchVisitor(


    val user_id: String,
    val lead_id: String,
    val lead_code: String,

    val profile_pic: String,

    val phone_no: String,
    val full_name: String,

    val dob: String,
    val anniversary_date: String,
    val gender: String,
    val interested_name: String,
    val sub_interested_name: String,

    val email_id: String,

    val address: String,
    val city_id: String,
    val city_name: String,
    val state_id: String,
    val pin_code: String,

    val page_type: String

) : Serializable