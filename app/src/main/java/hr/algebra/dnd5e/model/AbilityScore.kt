package hr.algebra.dnd5e.model

data class AbilityScore(val ability: Ability, var value: Int, var bonus: Int  = 0){
    val total: Int get() = value + bonus
}
