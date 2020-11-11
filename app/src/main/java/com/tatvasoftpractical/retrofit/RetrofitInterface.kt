package com.tatvasoftpractical.retrofit

import com.tatvasoftpractical.model.BaseModel
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface RetrofitInterface {

    //    @FormUrlEncoded
//    @POST("get_category_language_data.php")
//    fun langCatApi(@Query("action") action: String): Call<CatLanDataModel>

    @GET("users")
    fun getUserDetails(
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ) : Call<BaseModel>


}