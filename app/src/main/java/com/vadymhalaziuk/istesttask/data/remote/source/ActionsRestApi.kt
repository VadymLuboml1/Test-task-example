package com.vadymhalaziuk.istesttask.data.remote.source

import com.vadymhalaziuk.istesttask.data.remote.model.ActionDtoItem
import retrofit2.Response
import retrofit2.http.GET

interface ActionsRestApi {

    @GET("androidexam/butto_to_action_config.json")
    suspend fun getActions(): Response<List<ActionDtoItem?>?>
}