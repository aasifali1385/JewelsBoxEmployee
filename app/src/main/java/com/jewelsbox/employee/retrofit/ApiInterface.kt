package com.jewelsbox.employee.retrofit

import com.google.gson.JsonObject
import com.jewelsbox.employee.addLead.CityStateRes
import com.jewelsbox.employee.addLead.InterestRes
import com.jewelsbox.employee.home.membership.MembershipRes
import com.jewelsbox.employee.home.SearchVisitorRes
import com.jewelsbox.employee.home.membership.PayModeRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiInterface {


    @POST("autologin")
    suspend fun autoLogin(
        @Body jsonObject: JsonObject
    ): JsonObject


    @POST("login")
    suspend fun login(
        @Body jsonObject: JsonObject
    ): JsonObject


    @POST("loggedout")
    suspend fun logout(
        @Body jsonObject: JsonObject
    ): JsonObject


    @POST("helpsupport")
    suspend fun helpSupport(
        @Body jsonObject: JsonObject
    ): JsonObject

    @POST("terms")
    suspend fun terms(
        @Body jsonObject: JsonObject
    ): JsonObject

    @POST("policy")
    suspend fun policy(
        @Body jsonObject: JsonObject
    ): JsonObject


    @POST("searchlead")
    suspend fun searchLead(
        @Body jsonObject: JsonObject
    ): SearchVisitorRes


    @POST("markvisiting")
    suspend fun markVisiting(
        @Body jsonObject: JsonObject
    ): JsonObject


    @POST("statelist")
    suspend fun stateList(
        @Body jsonObject: JsonObject
    ): CityStateRes


    @POST("citylist")
    suspend fun cityList(
        @Body jsonObject: JsonObject
    ): CityStateRes


    @POST("interestlist")
    suspend fun interestList(
        @Body jsonObject: JsonObject
    ): InterestRes


    @POST("subinterestlist")
    suspend fun subInterestList(
        @Body jsonObject: JsonObject
    ): InterestRes


    @Multipart
    @POST("addlead")
    suspend fun addLead(
        @Part file: MultipartBody.Part,
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): JsonObject

    @Multipart
    @POST("getpayment")
    suspend fun getPayment(
        @Part("user_id") userId: RequestBody,
        @Part("member_plan_id") memberPlanId: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("payment_mode") paymentMode: RequestBody,
        @Part("transaction_date") transactionDate: RequestBody,
        @Part("transaction_id") transactionId: RequestBody,
        @Part file: MultipartBody.Part
    ): JsonObject


    @POST("searchmemberplan")
    suspend fun searchMemberPlan(
        @Body jsonObject: JsonObject
    ): MembershipRes


    @POST("paymentmode")
    suspend fun paymentMode(
        @Body jsonObject: JsonObject
    ): PayModeRes


}