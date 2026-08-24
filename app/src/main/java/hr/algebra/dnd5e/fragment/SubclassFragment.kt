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
import hr.algebra.dnd5e.databinding.FragmentSubclassBinding
import hr.algebra.dnd5e.framework.fetchSubclasses


class SubclassFragment : Fragment() {

    private lateinit var binding: FragmentSubclassBinding
    private lateinit var adapter: ReferenceAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubclassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReferences.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as CharacterCreateActivity
        val classIndex = activity.selectedClassIndex ?: return // nema klase > nema podklase

        val subclasses = requireContext().fetchSubclasses(classIndex)
        adapter = ReferenceAdapter(requireContext(), subclasses) { subclass ->
            activity.selectedSubclass = subclass.name
            adapter.selectedName = subclass.name
            adapter.notifyDataSetChanged()
        }
        adapter.selectedName = activity.selectedSubclass
        binding.rvReferences.adapter = adapter
    }
}

