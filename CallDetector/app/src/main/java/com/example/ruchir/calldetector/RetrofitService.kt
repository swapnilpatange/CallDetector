package com.example.ruchir.calldetector

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit

class RetrofitService {

    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            Log.e("retrofit", "create")
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://docs.google.com/forms/u/")
                    .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

    fun completeQuestionnaire(name: String, address: String) {
        val retrofitCall = create().completeQuestionnaire(name, address)
        retrofitCall.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable?) {
                Log.e("on Failure :", "retrofit error")
            }

            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                Log.e("on succes", "")

            }
        })
    }
}