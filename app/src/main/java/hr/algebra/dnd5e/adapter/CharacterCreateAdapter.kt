package hr.algebra.dnd5e.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.algebra.dnd5e.CHARACTER_POS
import hr.algebra.dnd5e.fragment.CharacterSheetFragment
import hr.algebra.dnd5e.fragment.SectionPlaceholderFragment

class CharacterCreateAdapter(activity: FragmentActivity, private val position: Int): FragmentStateAdapter(activity) {

    override fun getItemCount() = 5

    override fun createFragment(index: Int): Fragment = when(index){
        0 -> SectionPlaceholderFragment.newInstance("Race")
        1 -> SectionPlaceholderFragment.newInstance("Class")
        2 -> SectionPlaceholderFragment.newInstance("Subclass")
        3 -> SectionPlaceholderFragment.newInstance("Ability scores")
        else -> SectionPlaceholderFragment.newInstance("Skills")
    }
}