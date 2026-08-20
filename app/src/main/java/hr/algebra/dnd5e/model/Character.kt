package hr.algebra.dnd5e.model
import kotlin.math.floor

enum class Ability {STR, DEX, CON, INT, WIS, CHA}

data class Character(
    var _id: Long?,
    val name: String,
    val race: String,
    val characterClass: String,
    val subclass: String,
    val level: Int,
    val maxHp: Int,
    var currentHp: Int,
    val armorClass: Int, 
    val speed: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val notes: String,
    var favorite: Boolean
){
    val proficiencyBonus: Int get() = 2 + (level - 1) / 4

    val strMod: Int get() = modifier(strength)
    val dexMod: Int get() = modifier(dexterity)
    val conMod: Int get() = modifier(constitution)
    val intMod: Int get() = modifier(intelligence)
    val wisMod: Int get() = modifier(wisdom)
    val chaMod: Int get() = modifier(charisma)
    
    val initiative: Int get() = dexMod

    val strSave: Int get() = save(Ability.STR, strMod)
    val dexSave: Int get() = save(Ability.DEX, dexMod)
    val conSave: Int get() = save(Ability.CON, conMod)
    val intSave: Int get() = save(Ability.INT, intMod)
    val wisSave: Int get() = save(Ability.WIS, wisMod)
    val chaSave: Int get() = save(Ability.CHA, chaMod)

    private fun modifier(score: Int) = floor((score - 10) / 2.0).toInt()

    private fun save(ability: Ability, mod: Int) =
        mod + if (ability in proficientSaves(characterClass)) proficiencyBonus else 0


    private fun proficientSaves(characterClass: String): Set<Ability> =
        when (characterClass) {
            "barbarian" ->setOf(Ability.STR, Ability.CON)
            "bard" -> setOf(Ability.CHA, Ability.DEX)
            "cleric" -> setOf(Ability.WIS, Ability.STR)
            "druid" -> setOf(Ability.WIS, Ability.INT)
            "fighter" -> setOf(Ability.STR, Ability.CON)
            "monk" -> setOf(Ability.WIS, Ability.DEX)
            "paladin" -> setOf(Ability.WIS, Ability.CHA)
            "ranger" -> setOf(Ability.WIS, Ability.STR)
            "rogue" -> setOf(Ability.DEX, Ability.INT)
            "sorcerer" -> setOf(Ability.CHA, Ability.CON)
            "warlock" -> setOf(Ability.CHA, Ability.WIS)
            "wizard" -> setOf(Ability.INT, Ability.WIS)
            else -> emptySet()
        }

}
