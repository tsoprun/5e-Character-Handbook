package hr.algebra.dnd5e.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.dnd5e.CharacterCreateActivity
import hr.algebra.dnd5e.adapter.SkillAdapter
import hr.algebra.dnd5e.databinding.FragmentSkillsBinding
import hr.algebra.dnd5e.framework.fetchSkills


class SkillsFragment : Fragment(){

    private lateinit var binding: FragmentSkillsBinding

    // inicijalizACIJSKA
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSkillsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activiy = requireActivity() as CharacterCreateActivity
        binding.rvSkills.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSkills.adapter = SkillAdapter(
            requireContext(),
            requireContext().fetchSkills(),
            activiy.abilityScores,
            activiy.selectedSkills
        )
    }
}