package com.jewelsbox.employee.home.membership

data class Membership(

    val user_plan_id: String,
    val user_plan_code: String,

    val full_name: String,
    val phone_no: String,
    val plan_name: String,
    val investment_amount: String,
    val no_of_months: String,
    val benefit_amount: String,
    val total_amount: String,
    val months_remaining: String,
    val due_amount: String,
    val next_payment_date: String,
    val activated_date: String,
    val created_at: String,
    val status: String

)
