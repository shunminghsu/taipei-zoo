/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.sm678.taipeizoo.network

import com.sm678.taipeizoo.model.ResultResponse
import com.sm678.taipeizoo.model.ResultResponsePlant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://data.taipei/api/v1/dataset/"


private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface TaipeiZooApiService {

    //zoo area
    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    suspend fun getZooArea(
        @Query("scope") scope: String = "resourceAquire",
        @Query("limit") limit: Int = 0,
        @Query("offset") offset: Int = 0
    ): ResultResponse

    //plant
    @GET("f18de02f-b6c9-47c0-8cda-50efad621c14")
    suspend fun getPlants(
        @Query("scope") scope: String = "resourceAquire",
        @Query("limit") limit: Int = 30,
        @Query("offset") offset: Int = 0,
        @Query("q") where: String
    ): ResultResponsePlant
}

object TaipeiZooApi {
    val retrofitService : TaipeiZooApiService by lazy { retrofit.create(TaipeiZooApiService::class.java) }
}
