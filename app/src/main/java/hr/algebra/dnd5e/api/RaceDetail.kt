package hr.algebra.dnd5e.api

import com.google.gson.annotations.SerializedName

data class RaceDetail(
    @SerializedName("index") val index: String,
    @SerializedName("name") val name: String,
    @SerializedName("speed") val speed: Int,
    @SerializedName("ability_bonuses") val ability_bonuses: List<AbilityBonus>
)

data class AbilityBonus(
    @SerializedName("ability_score") val ability_score: ApiReference,
    @SerializedName("bonus") val bonus: Int
)
