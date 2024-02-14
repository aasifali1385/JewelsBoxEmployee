package com.jewelsbox.employee.home.membership

data class MembershipRes(

    val status: Boolean,
    val message: String,
    val data: List<Membership>
)
