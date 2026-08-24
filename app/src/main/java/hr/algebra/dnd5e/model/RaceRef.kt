package hr.algebra.dnd5e.model
import kotlin.math.floor

data class RaceRef(
    var _id: Long?,
    val apiIndex: String,
    val name: String,
    val speed: Int,
    val abilityBonuses: String

)
