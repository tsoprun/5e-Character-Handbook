package hr.algebra.dnd5e.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.dnd5e.R
import hr.algebra.dnd5e.model.Ability
import hr.algebra.dnd5e.model.AbilityScore
import hr.algebra.dnd5e.model.SkillRef
import kotlin.math.floor
import android.view.View
import android.widget.CheckBox
import android.widget.TextView

class SkillAdapter (
    private val context: Context,
    private val skills: List<SkillRef>,
    private val abilityScores: List<AbilityScore>,
    private val selectedSkills: MutableSet<String>
): RecyclerView.Adapter<SkillAdapter.ViewHolder>(){

override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
): ViewHolder {
    return ViewHolder(
        LayoutInflater.from(context)
            .inflate(R.layout.item_skill, parent, false)
    )
}

override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
) {
    holder.bind(skills[position])
}

    override fun getItemCount()=skills.count()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val cbProficient = itemView.findViewById<CheckBox>(R.id.cbProficient)
        private val tvName = itemView.findViewById<TextView>(R.id.tvSkillName)
        private val tvAbility = itemView.findViewById<TextView>(R.id.tvSkillAbility)
        private val tvModifier = itemView.findViewById<TextView>(R.id.tvSkillModifier)

        fun bind(skill: SkillRef) {
            tvName.text = skill.name
            tvAbility.text = "(${skill.ability.replaceFirstChar { it.uppercase()}})"

            cbProficient.setOnCheckedChangeListener(null)
            cbProficient.isChecked = skill.apiIndex in selectedSkills
            render(skill)

            cbProficient.setOnCheckedChangeListener { _, checked ->
                if (checked) selectedSkills.add(skill.apiIndex)
                else selectedSkills.remove(skill.apiIndex)
                render(skill)
            }
        }

        private fun render(skill: SkillRef){
            val proficient = skill.apiIndex in selectedSkills
            val mod = abilityMod(skill.ability) + if (proficient) PROFICIENT_BONUS else 0
            tvModifier.text = if (mod >= 0) "+$mod" else "$mod"
        }

        private fun abilityMod(ability: String):Int{
            val score = abilityScores.firstOrNull { it.abilty == Ability.valueOf(ability.uppercase()) }
                ?.value ?: 10
            return floor((score-10)/2.0).toInt()
        }

    }
    companion object {
        const val PROFICIENT_BONUS = 2
    }
}