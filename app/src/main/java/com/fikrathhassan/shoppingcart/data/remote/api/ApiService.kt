package com.fikrathhassan.shoppingcart.data.remote.api

import com.fikrathhassan.shoppingcart.data.remote.dto.ItemDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getItems(): ApiResponse<List<ItemDto>>

}