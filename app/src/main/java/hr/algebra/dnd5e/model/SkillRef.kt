package hr.algebra.dnd5e.model

data class SkillRef(
    var _id: Long?,
    val apiIndex: String,
    val name: String,
    val ability: String,
)
