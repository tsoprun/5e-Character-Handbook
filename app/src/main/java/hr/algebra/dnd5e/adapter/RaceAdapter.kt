package hr.algebra.dnd5e.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import hr.algebra.dnd5e.R
import hr.algebra.dnd5e.model.RaceRef

class RaceAdapter (
    private val context: Context,
    private val races: List<RaceRef>,
    private val onClick:(RaceRef) -> Unit
) : RecyclerView.Adapter<RaceAdapter.ViewHolder>(){


    var selectedName: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_api_reference, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val race = races[position]
        holder.bind(race, race.name==selectedName)
        holder.itemView.setOnClickListener {onClick(race)}
    }

    override fun getItemCount() = races.count()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        fun bind(race: RaceRef, isSelected: Boolean) {
            tvName.text=race.name

            val card = itemView as MaterialCardView
            val ctx = itemView.context
            if (isSelected) {
                card.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.bg_dark))
                card.strokeColor = ContextCompat.getColor(ctx, R.color.gold_light)
                card.strokeWidth = ctx.resources.getDimensionPixelSize(R.dimen._2dp)
            } else {
                card.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.surface_dark))
                card.strokeColor = ContextCompat.getColor(ctx, R.color.bronze_dim)
                card.strokeWidth = ctx.resources.getDimensionPixelSize(R.dimen._1dp)
            }
        }


    }
}