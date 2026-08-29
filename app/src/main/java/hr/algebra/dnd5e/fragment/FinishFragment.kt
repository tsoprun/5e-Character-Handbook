package hr.algebra.dnd5e.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import hr.algebra.dnd5e.CharacterCreatePagerActivity
import hr.algebra.dnd5e.DND_PROVIDER_CONTENT_URI
import hr.algebra.dnd5e.R
import hr.algebra.dnd5e.databinding.FragmentFinishBinding
import hr.algebra.dnd5e.framework.toContentValues
import hr.algebra.dnd5e.model.Ability
import hr.algebra.dnd5e.model.Character
import kotlin.math.floor


class FinishFragment : Fragment() {

    private lateinit var binding: FragmentFinishBinding

    // inicijalizACIJSKA
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreate.setOnClickListener{createCharacter()}
    }

    private fun createCharacter() {
        val activity = requireActivity() as CharacterCreatePagerActivity

        val name = binding.etName.text.toString().trim()
        if (name.isEmpty()){
            Toast.makeText(requireContext(), R.string.enter_name, Toast.LENGTH_SHORT).show()
            return
        }

        fun score(ability: Ability) =
            activity.abilityScores.first{it.ability==ability}.total

        fun mod (value: Int) = floor((value-10)/2.0).toInt()
        val character = Character(
            null,
            name,
            activity.selectedRace!!,
            activity.selectedClass!!,
            activity.selectedSubclass ?: "",
            1,
             activity.selectedClassHitDie + mod(score(Ability.CON)),
            activity.selectedClassHitDie  + mod(score(Ability.CON)),
            10 + mod(score(Ability.DEX)),
            activity.selectedRaceSpeed,
            score(Ability.STR),
            score(Ability.DEX),
            score(Ability.CON),
            score(Ability.INT),
            score(Ability.WIS),
            score(Ability.CHA),
            "",
            false
        )

    requireContext().contentResolver.insert(
        DND_PROVIDER_CONTENT_URI,
        character.toContentValues()
    )
    Toast.makeText(requireContext(), R.string.character_created, Toast.LENGTH_SHORT).show()
    activity.finish()
    }

}