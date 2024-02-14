package com.jewelsbox.employee.retrofit


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {


    val rest: ApiInterface = Retrofit.Builder().baseUrl("https://crm.jewelsbox.co/api/v1/")
        .addConverterFactory(GsonConverterFactory.create()).build().create(ApiInterface::class.java)


}