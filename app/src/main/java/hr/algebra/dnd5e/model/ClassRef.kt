package hr.algebra.dnd5e.model

data class ClassRef(
    var _id: Long?,
    val apiIndex: String,
    val name: String,
    val hitDie: Int,
    val savingThrows: String,
    val skillChoiceCount: Int,
    val skillOptions: String
)
