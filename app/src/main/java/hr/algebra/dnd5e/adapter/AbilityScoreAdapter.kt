package hr.algebra.dnd5e.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import hr.algebra.dnd5e.R
import hr.algebra.dnd5e.model.Ability
import hr.algebra.dnd5e.model.AbilityScore
import kotlin.math.floor

class AbilityScoreAdapter(
    private val context: Context,
    private val scores: List<AbilityScore>
) : RecyclerView.Adapter<AbilityScoreAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_ability_score, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(scores [position])
    }

    override fun getItemCount() = scores.count()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        private val ivIcon = itemView.findViewById<ImageView>(R.id.ivAbilityIcon)
        private val tvName = itemView.findViewById<TextView>(R.id.tvAbilityName)
        private val tvModifier = itemView.findViewById<TextView>(R.id.tvModifier)
        private val tvValue = itemView.findViewById<TextView>(R.id.tvValue)
        private val btnMinus = itemView.findViewById<MaterialButton>(R.id.btnMinus)
        private val btnPlus = itemView.findViewById<MaterialButton>(R.id.btnPlus)


    fun bind(score: AbilityScore) {
        tvName.text = label(score.ability)
        ivIcon.setImageResource(iconFor(score.ability))
        render(score)
        btnPlus.setOnClickListener {
            if (score.value < MAX) {
                score.value++
                render(score)
            }
        }
        btnMinus.setOnClickListener {
            if (score.value > MIN) {
                score.value--
                render(score)
            }
        }

    }
        private fun render(score: AbilityScore) {
            tvValue.text = score.value.toString()
            val mod = floor((score.value - 10)/ 2.0).toInt()
            tvModifier.text = if (mod >= 0) "+$mod" else "$mod"
        }
        private fun iconFor(abiltiy: Ability) = when (abiltiy) {
            Ability.STR -> R.drawable.ic_str
            Ability.DEX -> R.drawable.ic_dex
            Ability.CON -> R.drawable.ic_con
            Ability.INT -> R.drawable.ic_int
            Ability.WIS -> R.drawable.ic_wis
            Ability.CHA -> R.drawable.ic_cha
        }

        private fun label(abiltiy: Ability) = when(abiltiy){
            Ability.STR -> "Strength"
            Ability.DEX -> "Dexterity"
            Ability.CON -> "Constitution"
            Ability.INT -> "Intelligence"
            Ability.WIS -> "Wisdom"
            Ability.CHA -> "Charisma"
        }

    }

    companion object{
        const val MIN=4
        const val MAX=18
    }

}


