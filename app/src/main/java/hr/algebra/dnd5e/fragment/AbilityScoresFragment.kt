package hr.algebra.dnd5e.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.dnd5e.CharacterCreateActivity
import hr.algebra.dnd5e.adapter.AbilityScoreAdapter
import hr.algebra.dnd5e.model.Ability
import hr.algebra.dnd5e.model.AbilityScore

import hr.algebra.dnd5e.databinding.FragmentAbilityScoresBinding

class AbilityScoresFragment : Fragment(){

    private lateinit var binding: FragmentAbilityScoresBinding

    // inicijalizACIJSKA
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAbilityScoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activiy = requireActivity() as CharacterCreateActivity
        binding.rvAbilities.layoutManager =LinearLayoutManager(requireContext())
        binding.rvAbilities.adapter = AbilityScoreAdapter(requireContext(), activiy.abilityScores)
    }

}