package hr.algebra.dnd5e.fragment

import android.content.ContentUris
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.algebra.dnd5e.CHARACTER_POS
import hr.algebra.dnd5e.databinding.CharacterPagerBinding
import hr.algebra.dnd5e.framework.fetchCharacters
import hr.algebra.dnd5e.model.Character
import hr.algebra.dnd5e.DND_PROVIDER_CONTENT_URI



class CharacterSheetFragment : Fragment() {

    private var _binding: CharacterPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var character: Character

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CharacterPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(CHARACTER_POS) ?: 0
        character=requireContext().fetchCharacters()[position]
        bind()
        binding.ivFavorite.setOnClickListener { toggleFavorite() }
        }


private fun bind() = with(binding){
    tvName.text = character.name
    tvSummary.text =
        "${character.race} ${character.characterClass} (${character.subclass}) • Lvl ${character.level}"
    tvAc.text = character.armorClass.toString()
    tvHp.text = "${character.currentHp}/${character.maxHp}"
    tvSpeed.text = character.speed.toString()
    tvInitiative.text = character.initiative.signed()
    tvProficiency.text = character.proficiencyBonus.signed()

    tvStr.text = ability(character.strength, character.strMod)
    tvDex.text = ability(character.dexterity, character.dexMod)
    tvCon.text = ability(character.constitution, character.conMod)
    tvInt.text = ability(character.intelligence, character.intMod)
    tvWis.text = ability(character.wisdom, character.wisMod)
    tvCha.text = ability(character.charisma, character.chaMod)

    tvStrSave.text = character.strSave.signed()
    tvDexSave.text = character.dexSave.signed()
    tvConSave.text = character.conSave.signed()
    tvIntSave.text = character.intSave.signed()
    tvWisSave.text = character.wisSave.signed()
    tvChaSave.text = character.chaSave.signed()

    tvNotes.text = character.notes

    ivFavorite.setImageResource(
        if (character.favorite) android.R.drawable.btn_star_big_on
        else android.R.drawable.btn_star_big_off
    )
}
    private fun toggleFavorite() {
        character.favorite = !character.favorite
        requireContext().contentResolver.update(
           ContentUris.withAppendedId(DND_PROVIDER_CONTENT_URI, character._id!!),
            ContentValues().apply {
                put(Character::favorite.name, character.favorite)
            },
            null,
            null
        )
        bind()
}

private fun ability(score: Int, mod: Int) = "$score (${mod.signed()})"
private fun Int.signed() = if (this >= 0) "+$this" else "$this"


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
