package com.example.woltvenuesapp.Network

import com.example.woltvenuesapp.Model.VenueModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("v1/pages/restaurants?lat=60.170187&lon=24.930599")
    fun getVenues(@Query("lat") locationLatitude: String, @Query("lon") locationLongitude: String) : Call<VenueModel>

}