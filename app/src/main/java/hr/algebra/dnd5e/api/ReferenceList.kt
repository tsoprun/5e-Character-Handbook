package hr.algebra.dnd5e.api

import com.google.gson.annotations.SerializedName

data class ReferenceList (
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<ApiReference>
)
