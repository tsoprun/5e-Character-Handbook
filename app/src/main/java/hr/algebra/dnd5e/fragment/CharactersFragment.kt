package hr.algebra.dnd5e.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.dnd5e.adapter.CharacterAdapter
import hr.algebra.dnd5e.databinding.FragmentCharactersBinding
import hr.algebra.dnd5e.framework.fetchCharacters
import hr.algebra.dnd5e.model.Character
import hr.algebra.dnd5e.CharacterCreatePagerActivity
import hr.algebra.dnd5e.DND_PROVIDER_CONTENT_URI
import hr.algebra.dnd5e.framework.toContentValues
import hr.algebra.dnd5e.framework.startActivity



class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var characters: MutableList<Character>

    // inicijalizACIJSKA
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        seedIfEmpty()
        characters = requireContext().fetchCharacters()
        return binding.root
    }

    private fun seedIfEmpty() {
        if(requireContext().fetchCharacters().isNotEmpty()) return
        insert(Character(null, "Aragorn", "Human", "Ranger", "Hunter", 5, 44, 44, 16, 30,
            16, 14, 14, 12, 13, 14, "", false))
        insert(Character(null, "Gimli", "Dwarf", "Fighter", "Champion", 4, 52, 52, 18, 25,
            18, 12, 16, 10, 11, 9, "", false))
        insert(Character(null, "Legolas", "Elf", "Ranger", "Hunter", 5, 40, 40, 15, 35,
            12, 18, 13, 11, 14, 12, "", false))
    }

    private fun insert(character: Character) {
        requireContext().contentResolver.insert(
            DND_PROVIDER_CONTENT_URI,
            character.toContentValues()
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.fabAdd.setOnClickListener {
            requireContext().startActivity<CharacterCreatePagerActivity>()
            }
    }

    override fun onResume() {
        super.onResume()
        characters = requireContext().fetchCharacters()
        binding.rvCharacters.adapter = CharacterAdapter(requireContext(), characters)
    }

}




