package com.example.ruchir.calldetector

import retrofit2.Call
import retrofit2.http.*

//https://docs.google.com/forms/u/1/d/e/1FAIpQLSdsLmzBziQx0S1eCn_r8HD503LlggsllIJ7Q2YI2HKNobSvUA/formResponse
interface ApiInterface {

    @POST("1/d/e/1FAIpQLSdsLmzBziQx0S1eCn_r8HD503LlggsllIJ7Q2YI2HKNobSvUA/formResponse")
    @FormUrlEncoded
    fun completeQuestionnaire(@Field("entry.1595229327")  name:String, @Field("entry.692228126") address:String): Call<Void>


}