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
import hr.algebra.dnd5e.model.SubclassRef

class SubclassAdapter (
    private val context: Context,
    private val subclasses: List<SubclassRef>,
    private val onClick:(SubclassRef) -> Unit
) : RecyclerView.Adapter<SubclassAdapter.ViewHolder>(){


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
        val subclass = subclasses[position]
        holder.bind(subclass, subclass.name==selectedName)
        holder.itemView.setOnClickListener {onClick(subclass)}
    }

    override fun getItemCount() = subclasses.count()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        fun bind(subclass: SubclassRef, isSelected: Boolean) {
            tvName.text=subclass.name

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