package hr.algebra.dnd5e.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.algebra.dnd5e.fragment.AbilityScoresFragment
import hr.algebra.dnd5e.fragment.ClassFragment
import hr.algebra.dnd5e.fragment.RaceFragment
import hr.algebra.dnd5e.fragment.SectionPlaceholderFragment
import hr.algebra.dnd5e.fragment.SkillsFragment
import hr.algebra.dnd5e.fragment.SubclassFragment

class CharacterCreateAdapter(activity: FragmentActivity, private val position: Int): FragmentStateAdapter(activity) {

    override fun getItemCount() = 5

    override fun createFragment(index: Int): Fragment = when(index){
        0 -> RaceFragment()
        1 -> ClassFragment()
        2 -> SubclassFragment()
        3 -> AbilityScoresFragment()
        else -> SkillsFragment()
    }
}