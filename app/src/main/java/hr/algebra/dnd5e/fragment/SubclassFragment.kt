package hr.algebra.dnd5e.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.dnd5e.CharacterCreatePagerActivity
import hr.algebra.dnd5e.adapter.SubclassAdapter
import hr.algebra.dnd5e.databinding.FragmentSubclassBinding
import hr.algebra.dnd5e.framework.fetchSubclasses


class SubclassFragment : Fragment() {

    private lateinit var binding: FragmentSubclassBinding
    private lateinit var adapter: SubclassAdapter


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
        val activity = requireActivity() as CharacterCreatePagerActivity
        val classIndex = activity.selectedClassIndex ?: return // nema klase > nema podklase

        val subclasses = requireContext().fetchSubclasses(classIndex)
        adapter = SubclassAdapter(requireContext(), subclasses) { subclass ->
            activity.selectedSubclass = subclass.name
            adapter.selectedName = subclass.name
            adapter.notifyDataSetChanged()
        }
        adapter.selectedName = activity.selectedSubclass
        binding.rvReferences.adapter = adapter
    }
}

