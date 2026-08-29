package hr.algebra.dnd5e.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.dnd5e.CharacterCreatePagerActivity
import hr.algebra.dnd5e.adapter.RaceAdapter
import hr.algebra.dnd5e.databinding.FragmentRaceBinding
import hr.algebra.dnd5e.framework.fetchRaces


class RaceFragment : Fragment() {

    private lateinit var binding: FragmentRaceBinding
    private lateinit var adapter: RaceAdapter

    // inicijalizACIJSKA
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReferences.layoutManager = LinearLayoutManager(requireContext())

            val races = requireContext().fetchRaces()
            adapter = RaceAdapter(requireContext(), races) { race ->
                val activity = requireActivity() as CharacterCreatePagerActivity
                activity.selectedRace = race.name
                activity.selectedRaceSpeed=race.speed
                activity.applyRatialBonuses(race.abilityBonuses)
                adapter.selectedName = race.name
                adapter.notifyDataSetChanged()
            }
            adapter.selectedName = (requireActivity() as CharacterCreatePagerActivity).selectedRace
            binding.rvReferences.adapter = adapter
        }


    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.selectedName = (requireActivity() as CharacterCreatePagerActivity).selectedRace
            adapter.notifyDataSetChanged()
        }
    }
}
