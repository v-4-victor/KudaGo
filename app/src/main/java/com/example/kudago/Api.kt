package com.example.kudago

import android.os.Parcelable
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.android.parcel.Parcelize

@Module
object ApiModule{
    @Provides
    fun retrofit()  = Retrofit.Builder()
        .baseUrl("https://kudago.com/public-api/v1.4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}

enum class ApiStatus { LOADING, ERROR, DONE }
@Parcelize
data class Image(val image:String):Parcelable
@Parcelize
data class NewsElem(val title:String, val description:String, val images:List<Image>):Parcelable
@Parcelize
data class Results(val results:List<NewsElem>):Parcelable

interface ApiService {
    @GET("news/")
    suspend fun getNews(@Query("fields") user: String = "title,images,description"): Results
}
