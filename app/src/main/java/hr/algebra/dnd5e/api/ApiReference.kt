package hr.algebra.dnd5e.api
import com.google.gson.annotations.SerializedName

data class ApiReference(
    @SerializedName("index") val index: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
