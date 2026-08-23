package hr.algebra.dnd5e.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.dnd5e.CharacterCreateActivity
import hr.algebra.dnd5e.adapter.ReferenceAdapter
import hr.algebra.dnd5e.api.ApiReference
import hr.algebra.dnd5e.api.Dnd5eFetcher
import hr.algebra.dnd5e.databinding.FragmentClassBinding
import hr.algebra.dnd5e.databinding.FragmentRaceBinding


class ClassFragment : Fragment() {

    private lateinit var binding: FragmentClassBinding
    private lateinit var adapter: ReferenceAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReferences.layoutManager = LinearLayoutManager(requireContext())

        Dnd5eFetcher().fetchClasses { classes ->
            adapter = ReferenceAdapter(requireContext(), classes) { charClass ->
                val activity = requireActivity() as CharacterCreateActivity
                if (activity.selectedClass != charClass.name){
                    activity.selectedSubclass = null
                }
                activity.selectedClass = charClass.name
                activity.selectedClassIndex = charClass.index
                adapter.selectedName = charClass.name
                adapter.notifyDataSetChanged()
            }
            adapter.selectedName = (requireActivity() as CharacterCreateActivity).selectedClass
            binding.rvReferences.adapter = adapter
        }

    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.selectedName = (requireActivity() as CharacterCreateActivity).selectedClass
            adapter.notifyDataSetChanged()
        }
    }
}
