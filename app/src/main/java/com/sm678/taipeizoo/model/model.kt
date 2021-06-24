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

package com.sm678.taipeizoo.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class ResultResponsePlant(
    @Json(name = "result") val result: PlantResponse
)

data class PlantResponse(
    @Json(name = "limit") val limit: Int,
    @Json(name = "offset") val offset: Int,
    @Json(name = "count") val count: Int,
    @Json(name = "sort") val sort: String,
    @Json(name = "results") val results: List<Plant>
)

@Parcelize
data class Plant(
    @Json(name = "F_Pic01_URL") val picUrl: String,
    @Json(name = "F_Name_Latin") val nameLatin: String,
    @Json(name = "\uFEFFF_Name_Ch") val name: String,
    @Json(name = "F_AlsoKnown") val alsoKnow: String,
    @Json(name = "F_Family") val family: String,
    @Json(name = "F_Brief") val brief: String,
    @Json(name = "F_Function＆Application") val function: String,
    @Json(name = "F_Feature") val feature: String,
    @Json(name = "F_Update") val update: String,
    @Json(name = "_id") val _id: Int
): DataItem(), Parcelable {
    override val id = _id
}
//val item = Plant(
//    picUrl = "http://www.zoo.gov.tw/iTAP/04_Plant/Sapindaceae/henryi/henryi_1.jpg",
//    name = "臺灣欒樹",
//    brief = "臺灣原產，本園各區普遍栽植。由於其開花至果熟期樹冠顏色甚有變化，饒富觀賞價值，故為極佳庭園、行道樹種。",
//    alsoKnow = "苦苓舅、苦楝舅",
//    nameLatin = "Koelreuteria henryi Dumm",
//    feature = "落葉喬木，幹直立。葉互生，二回奇數或偶數羽狀複葉，小葉10~13枚，紙質，鋸齒緣。蒴果表面具3翅，粉紅色，膨大成氣囊狀。種子球形，黑色。花期9-10月，果期10-12月。",
//    function = "1. 樹姿優美，花色多變化，是優良的園藝樹種。\\n2. 木材黃白色，質脆易開裂，可為板材和家具用材。",
//    update = "2017/9/4",
//    _id = 9,
//    family = "無患子科Sapindaceae"
//)
data class ResultResponse(
    @Json(name = "result") val result: AreaResponse
)

data class AreaResponse(
    @Json(name = "limit") val limit: Int,
    @Json(name = "offset") val offset: Int,
    @Json(name = "count") val count: Int,
    @Json(name = "sort") val sort: String,
    @Json(name = "results") val results: List<Area>
)

@Parcelize
data class Area(
    @Json(name = "E_Pic_URL") val picUrl: String,
    @Json(name = "E_Geo") val geo: String,
    @Json(name = "E_Info") val info: String,
    @Json(name = "E_no") val no: String,
    @Json(name = "E_Category") val category: String,
    @Json(name = "E_Name") val name: String,
    @Json(name = "E_Memo") val memo: String,
    @Json(name = "_id") val _id: Int,
    @Json(name = "E_URL") val url: String
): Parcelable
//var clickedItem: Area = Area(
//    picUrl = "http://www.zoo.gov.tw/iTAP/05_Exhibit/01_FormosanAnimal.jpg",
//    name = "臺灣動物區",
//    info = "臺灣動物區以臺灣原生動物與棲息環境為展示重點，佈置模擬動物原生棲地之生態環境，讓動物表現如野外般自然的生活習性，引導觀賞者更正確地認識本土野生動物。臺灣位處於亞熱帶，雨量充沛、氣候溫暖，擁有各種地形景觀，因而孕育了豐富龐雜的生物資源",
//    url = "",
//    _id = 1,
//    category = "戶外區"
//)
sealed class DataItem {
    abstract val id: Int
}

object TextHeader: DataItem() {
    override val id = Int.MIN_VALUE
}

data class AreaHeader(
    val picUrl: String,
    val info: String,
    val name: String,
    val category: String,
    val url: String) : DataItem() {
    override val id = Int.MIN_VALUE + 1
}

object PlantPlaceholder: DataItem() {
    override val id = Int.MIN_VALUE + 2
}