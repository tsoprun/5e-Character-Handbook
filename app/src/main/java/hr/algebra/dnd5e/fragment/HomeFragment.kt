package hr.algebra.dnd5e.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.dnd5e.R
import hr.algebra.dnd5e.adapter.CharacterAdapter
import hr.algebra.dnd5e.databinding.FragmentCharactersBinding
import hr.algebra.dnd5e.databinding.FragmentHomeBinding
import hr.algebra.dnd5e.framework.fetchCharacters


class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding

    // inicijalizACIJSKA
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardCharacters.setOnClickListener {
            findNavController().navigate(R.id.menuCharacters)
        }
        binding.cardBestiary.setOnClickListener { comingSoon() }
        binding.cardItems.setOnClickListener { comingSoon() }

        hr.algebra.dnd5e.api.Dnd5eFetcher().fetchRaces { races ->
            android.util.Log.d("DND", "races=${races.size} ${races.map { it.name }}")
        }

        hr.algebra.dnd5e.api.Dnd5eFetcher().fetchClasses { classes ->
            android.util.Log.d("DND", "classes=${classes.size} ${classes.map { it.name }}")
        }

    }

    private fun comingSoon() {
        Toast.makeText(requireContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show()
    }


}