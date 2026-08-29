package hr.algebra.dnd5e.api

import com.google.gson.annotations.SerializedName

data class ApiReferenceList (
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<ApiReference>
)
