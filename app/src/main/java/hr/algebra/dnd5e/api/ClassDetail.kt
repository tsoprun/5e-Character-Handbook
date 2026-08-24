package hr.algebra.dnd5e.api

import com.google.gson.annotations.SerializedName

data class ClassDetail(
    @SerializedName("index") val index: String,
    @SerializedName("name")val name: String,
    @SerializedName("hit_die")val hit_die: Int,
    @SerializedName("saving_throws")val saving_throws: List<ApiReference>,
    @SerializedName("proficiency_choices")val proficiency_choices: List<ProficiencyChoice>,
)

data class ProficiencyChoice(
    @SerializedName ("desc") val desc: String,
    @SerializedName ("choose") val choose: Int,
    @SerializedName ("type") val type: String,
    @SerializedName ("from") val from: OptionSet,
)

data class OptionSet(
    @SerializedName("option_set_type") val option_set_type: String,
    @SerializedName("options") val options: List<Option>
)

data class Option(
    @SerializedName("option_type") val option_type: String,
    @SerializedName("item")val item: ApiReference
)