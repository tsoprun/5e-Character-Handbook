package hr.algebra.dnd5e.api

import com.google.gson.annotations.SerializedName

data class SkillDetail(
    @SerializedName("index") val index: String,
    @SerializedName("name") val name: String,
    @SerializedName("ability_score") val ability_score: ApiReference,
)
